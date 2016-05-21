/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino.concurso;

import cc20152.sisufal.dao.impl.BancaConcursoDAO;
import cc20152.sisufal.dao.impl.ConcursoDAO;
import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.models.BancaConcurso;
import cc20152.sisufal.models.Concurso;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.util.AutoCompleteComboBoxListener;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.DataUtil;
import cc20152.sisufal.util.ServidorConverter;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.ListSelectionView;

/**
 *
 * @author Dayvson
 */
public class ConcursoController implements Initializable {
    
    private ObservableList<Concurso> data;
    
    final String pacote = "controllers/ensino/concurso/"; //Pacote do controller
    final String fxml = "fxml/cadastro/CadastroConcursoFXML.fxml"; //Caminho do FXML
    final String bancaConcursofxml = "fxml/lista/ListaBancaConcursoFXML.fxml";
    
    private String tipo;
    
    private Concurso concurso;
    
    @FXML
    private DatePicker dataInicial;
    
    @FXML
    private DatePicker dataFinal;
    
    @FXML
    private TextField txtEdital;
    
    @FXML
    private TextField txtAreaEstudo;
    
    @FXML
    private TextField txtModalidade;
    
    @FXML
    private ComboBox cmbSupervisor;
  
    @FXML
    private TableView<Concurso> tbConcurso;
    
    @FXML
    private GridPane painelBanca;
    
    private ListSelectionView<Servidor> lista;
    
    @FXML
    private void btnBuscar(ActionEvent event){
        ConcursoDAO concursoDAO = new ConcursoDAO();
        Concurso concurso = new Concurso();
        
        if(!this.txtEdital.getText().equals("")){
            concurso.setNumeroEdital(this.txtEdital.getText());
        }
        
        if(this.dataInicial.getValue() != null){
            LocalDate local = this.dataInicial.getValue();
            Date date = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            concurso.setDataInicio(date);
        }
        
        if(this.dataFinal.getValue() != null){
            LocalDate local = this.dataFinal.getValue();
            Date date = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            concurso.setDataFim(date);
        }
        
        List<Concurso> list = concursoDAO.listWithParams(concurso);
        
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        boolean temBanca = false;
        
        
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        if(this.txtEdital.getText().equals("") || this.txtEdital.getText() == null){
            aviso.setHeaderText("Campo edital não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtAreaEstudo.getText().equals("") || this.txtEdital.getText() == null){
            aviso.setHeaderText("Campo disciplina não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbSupervisor.getValue() == null){
            aviso.setHeaderText("Campo supervisor não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtModalidade.getText().equals("") || this.txtModalidade.getText() == null){
            aviso.setHeaderText("Campo situação não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.dataInicial.getValue() == null){
            aviso.setHeaderText("Campo data de início não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.dataFinal.getValue() == null){
            aviso.setHeaderText("Campo data fim não pode estar vazio");
            aviso.show();
            return ;
        }else if(!this.lista.getTargetItems().isEmpty()){
            temBanca = true;
        }else{
           aviso.setHeaderText("É necessário ao menos um servidor na banca!");
           aviso.show();
           return ;
        }
        
        Concurso old = this.concurso;
        if(this.concurso == null){
            this.concurso = new Concurso();
        }
        
        this.concurso.setSupervisor((Servidor) this.cmbSupervisor.getValue());
        this.concurso.setAreaEstudo(this.txtAreaEstudo.getText());
        this.concurso.setModalidade(this.txtModalidade.getText());
        this.concurso.setNumeroEdital(this.txtEdital.getText());
        this.concurso.setDataInicio(Date.from(this.dataInicial.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.concurso.setDataFim(Date.from(this.dataFinal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
       
        
        ConcursoDAO concursoDAO = new ConcursoDAO();
        String result = null;
        if(this.tipo == null){
            result = concursoDAO.save(this.concurso);
            String codigo[] = result.trim().split(" - ");
            this.concurso.setId(Integer.parseInt(codigo[0]));
        }else{
            new BancaConcursoDAO().delete(this.concurso);
            result = concursoDAO.update(this.concurso);
        }
       
        BancaConcurso banca = new BancaConcurso();
        banca.getConcurso().setId(this.concurso.getId());
        
        ArrayList<Servidor> listaServidores = new ArrayList<>(lista.getTargetItems());
        
        banca.setListaServidores(listaServidores);
        
        String bancaResult = new BancaConcursoDAO().save(banca);
        
        if(bancaResult.contains("OK") && result.contains("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Concurso salvo com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(concurso);
           }else{
                this.data.remove(old);
                this.data.add(concurso);
           }
           
           Stage stage = (Stage) txtEdital.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao salvar concurso!");
           alerta.show();
        }
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) txtEdital.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void novoConcurso (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxml);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        ConcursoController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Concurso");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    private void listarConcursos(){
       data = FXCollections.observableArrayList();
       
       List<Concurso> listaConcurso = new ArrayList<>();
       listaConcurso = new ConcursoDAO().listAll();
       
       TableColumn colNome = this.tbConcurso.getColumns().get(0);
       colNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Concurso, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Concurso, String> p) {
               return new SimpleObjectProperty(p.getValue().getNumeroEdital());
           }
       });
       
       TableColumn colPeriodo = this.tbConcurso.getColumns().get(1);
       colPeriodo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Concurso, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Concurso, String> p) {
               return new SimpleObjectProperty(p.getValue().getModalidade());
           }
       });
       
       TableColumn colAreaEstudo = this.tbConcurso.getColumns().get(2);
       colAreaEstudo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Concurso, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Concurso, String> p) {
               return new SimpleObjectProperty(p.getValue().getAreaEstudo());
           }
       });
       
       TableColumn colDataInicio = this.tbConcurso.getColumns().get(3);
       colDataInicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Concurso, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Concurso, String> p) {
               return new SimpleObjectProperty(DataUtil.getDataApresentacao(p.getValue().getDataInicio()));
           }
       });
       
       TableColumn colDataFim = this.tbConcurso.getColumns().get(4);
       colDataFim.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Concurso, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Concurso, String> p) {
               return new SimpleObjectProperty(DataUtil.getDataApresentacao(p.getValue().getDataFim()));
           }
       });
      
       TableColumn colSupervisor = this.tbConcurso.getColumns().get(5);
       colSupervisor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Concurso, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Concurso, String> p) {
               return new SimpleObjectProperty(p.getValue().getSupervisor().getNome());
           }
       });
       
       this.tbConcurso.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>(""));
       
       TableColumn colBanca = this.tbConcurso.getColumns().get(6);
       colBanca.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Concurso.class, fxml, "mostrarBancaConcurso", "Ver Banca", "banca").new BotaoCell();
           }
       });
       
       TableColumn colEditar = this.tbConcurso.getColumns().get(7);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Concurso.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tbConcurso.getColumns().get(8);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Concurso.class, ConcursoController.class).new DeletarCell();
           }
       });
      
       for (Concurso e : listaConcurso) {
           data.add(e);
       }
       
       tbConcurso.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!url.getPath().contains(this.fxml)){
            listarConcursos();
            
        }else{
            lista = new ListSelectionView<>();
            lista.setSourceHeader(new Label("Disponíveis"));
            lista.setTargetHeader(new Label("Selecionados"));
            lista.getSourceItems().addAll(new ServidorDAO().listAll());
            this.painelBanca.add(lista, 0, 0);
            this.painelBanca.setAlignment(Pos.CENTER);
            this.cmbSupervisor.getItems().addAll(new ServidorDAO().listAll());
            this.cmbSupervisor.setConverter(new ServidorConverter());
            new AutoCompleteComboBoxListener<>(this.cmbSupervisor, "Escolha um Supervisor");
        }
    }    
    
    public void setData(ObservableList<Concurso> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Concurso concurso){
        this.tipo = tipo;
        this.data = (ObservableList<Concurso>)data;
        this.concurso = (Concurso) concurso;
        preencherCampos();
    }
    
    public void mostrarBancaConcurso(String tipo, ObservableList<?> _data, Concurso concurso) throws MalformedURLException, IOException{
       String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.bancaConcursofxml);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        BancaConcursoController controller = loader.getController();
        controller.setInit(tipo, _data, concurso);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Banca");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    public void deletar(Concurso concurso, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            ConcursoDAO concursoDAO = new ConcursoDAO();
            String result = concursoDAO.delete(concurso);
            if(result.contains("OK")){ 
                _data.remove(concurso);
            }else{
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro ao deletar");
                alerta.setHeaderText("Não foi possivel excluir registro");
                alerta.setContentText("Houve um erro ao excluir registro");
                alerta.show();
            } 
        }
    }
    
    private void preencherCampos(){
        this.txtAreaEstudo.setText(this.concurso.getAreaEstudo());
        this.txtEdital.setText(this.concurso.getNumeroEdital());
        this.txtModalidade.setText(this.concurso.getModalidade());
        this.dataInicial.setValue(new Date(this.concurso.getDataInicio().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.dataFinal.setValue(new Date(this.concurso.getDataFim().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        ArrayList<Servidor> listaServidores = new BancaConcursoDAO().listWithParams(this.concurso).get(0).getListaServidores();
        
        this.lista.getSourceItems().removeIf(item -> (listaServidores.stream().filter(e -> e.getId() == item.getId()).count())>0);
        
        this.lista.getTargetItems().addAll(listaServidores);
       
        ObservableList<Servidor> or = this.cmbSupervisor.getItems();
        
        int i = 0;
        for(Servidor p : or){
            if(Objects.equals(p.getId(), this.concurso.getSupervisor().getId())){
                this.cmbSupervisor.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbSupervisor.getSelectionModel().select(i);
    }
}
