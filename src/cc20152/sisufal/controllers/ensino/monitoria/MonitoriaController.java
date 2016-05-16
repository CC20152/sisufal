package cc20152.sisufal.controllers.ensino.monitoria;

import cc20152.sisufal.dao.impl.DiscenteDAO;
import cc20152.sisufal.dao.impl.DisciplinaDAO;
import cc20152.sisufal.dao.impl.MonitoriaDAO;
import cc20152.sisufal.dao.impl.OrientadorDAO;
import cc20152.sisufal.dao.impl.PeriodoDAO;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.Monitoria;
import cc20152.sisufal.models.Disciplina;
import cc20152.sisufal.models.Orientador;
import cc20152.sisufal.models.Periodo;
import cc20152.sisufal.util.BotoesLista;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dayvson
 */
public class MonitoriaController implements Initializable{

   private ObservableList<Monitoria> data;
    
    final String pacote = "controllers/ensino/monitoria/"; //Pacote do controller
    final String fxml = "fxml/cadastro/CadastroMonitoriaFXML.fxml"; //Caminho do FXML
    
    private String tipo;
    
    private Monitoria monitoria;
    
    @FXML
    private DatePicker dataInicial;
    
    @FXML
    private DatePicker dataFinal;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private ComboBox cmbDisciplina;
    
    @FXML
    private ComboBox cmbOrientador;
    
    @FXML
    private ComboBox cmbSituacao;
    
    @FXML
    private ComboBox cmbPeriodo;
    
    @FXML
    private ComboBox cmbDiscente;
    
    @FXML
    private TableView<Monitoria> tbMonitoria;
    
    @FXML
    private void btnBuscar(ActionEvent event){
        MonitoriaDAO monitoriaDAO = new MonitoriaDAO();
        Monitoria monitoria = new Monitoria();
        
        if(!this.txtNome.getText().equals("")){
            monitoria.getDiscente().setNome(this.txtNome.getText());
        }
        
        if(this.cmbDisciplina.getValue() != null && !this.cmbDisciplina.getSelectionModel().isSelected(0)){
            monitoria.setDisciplina((Disciplina) this.cmbDisciplina.getValue());
        }
        
        if(this.dataInicial.getValue() != null){
            System.out.println(this.dataInicial.getValue());
            LocalDate local = this.dataInicial.getValue();
            Date date = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            monitoria.setDataInicio(date);
        }
        
        if(this.dataFinal.getValue() != null){
            LocalDate local = this.dataFinal.getValue();
            Date date = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            monitoria.setDataFim(date);
        }
        
        
        List<Monitoria> list = monitoriaDAO.listWithParams(monitoria);
        
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        if(this.cmbDiscente.getValue() == null || this.cmbDiscente.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo discente não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbDisciplina.getValue() == null || this.cmbDisciplina.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo disciplina não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbOrientador.getValue() == null || this.cmbOrientador.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo orientador não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbSituacao.getValue() == null || this.cmbSituacao.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo situação não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbPeriodo.getValue() == null || this.cmbPeriodo.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo período não pode estar vazio");
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
        }
        
        Monitoria old = this.monitoria;
        if(this.monitoria == null){
            this.monitoria = new Monitoria();
        }
        
        this.monitoria.setDiscente((Discente) this.cmbDiscente.getValue());
        this.monitoria.setOrientador((Orientador) this.cmbOrientador.getValue());
        this.monitoria.setDisciplina((Disciplina) this.cmbDisciplina.getValue());
        this.monitoria.setPeriodo((Periodo) this.cmbPeriodo.getValue());
        this.monitoria.setDataInicio(Date.from(this.dataInicial.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.monitoria.setDataFim(Date.from(this.dataFinal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.monitoria.setSitCertificado(this.cmbSituacao.getValue().toString());
        
        MonitoriaDAO monitoriaDAO = new MonitoriaDAO();
        String result = null;
        if(this.tipo == null){
            result = monitoriaDAO.save(this.monitoria);
        }else{
            result = monitoriaDAO.update(this.monitoria);
        }
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Monitoria salva com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(monitoria);
           }else{
                this.data.remove(old);
                this.data.add(monitoria);
           }
           
           Stage stage = (Stage) cmbPeriodo.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao salvar monitoria!");
           alerta.show();
        }
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void novaMonitoria (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxml);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        MonitoriaController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Monitoria");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    private void listarMonitorias(){
       data = FXCollections.observableArrayList();
       
       List<Monitoria> listaMonitoria = new ArrayList<>();
       listaMonitoria = new MonitoriaDAO().listAll();
       
       TableColumn colNome = this.tbMonitoria.getColumns().get(0);
       colNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Monitoria, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Monitoria, String> p) {
               return new SimpleObjectProperty(p.getValue().getDiscente().getNome());
           }
       });
       
       TableColumn colPeriodo = this.tbMonitoria.getColumns().get(1);
       colPeriodo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Monitoria, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Monitoria, String> p) {
               return new SimpleObjectProperty(p.getValue().getPeriodo().getNome());
           }
       });
       
       TableColumn colDataInicio = this.tbMonitoria.getColumns().get(2);
       colDataInicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Monitoria, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Monitoria, String> p) {
               return new SimpleObjectProperty(p.getValue().getDataInicio().toString());
           }
       });
       
       TableColumn colDataFim = this.tbMonitoria.getColumns().get(3);
       colDataFim.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Monitoria, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Monitoria, String> p) {
               return new SimpleObjectProperty(p.getValue().getDataFim().toString());
           }
       });
      
       TableColumn colOrientador = this.tbMonitoria.getColumns().get(4);
       colOrientador.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Monitoria, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Monitoria, String> p) {
               return new SimpleObjectProperty(p.getValue().getOrientador().getNome());
           }
       });
       
       this.tbMonitoria.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("sitCertificado"));
      
       TableColumn colEditar = this.tbMonitoria.getColumns().get(6);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Monitoria.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tbMonitoria.getColumns().get(7);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Monitoria.class, MonitoriaController.class).new DeletarCell();
           }
       });
      
       for (Monitoria e : listaMonitoria) {
           data.add(e);
       }
       
       tbMonitoria.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(0); disciplina.setNome("Escolha uma Disciplina");
        this.cmbDisciplina.getItems().add(disciplina);
        this.cmbDisciplina.getItems().addAll(new DisciplinaDAO().listAll());
        this.cmbDisciplina.getSelectionModel().selectFirst();
        
        if(!url.getPath().contains(this.fxml)){
            listarMonitorias();
        }else{
            ArrayList<String> listaSituacao = new ArrayList<>();

            listaSituacao.add("Escolha uma situação");
            listaSituacao.add("EMITIDO");
            listaSituacao.add("NÃO EMITIDO");
            listaSituacao.add("EM PROCESSO");

            

            Discente discente = new Discente();
            discente.setId(0); discente.setNome("Escolha um discente");

            Orientador orientador = new Orientador();
            orientador.setId(0); orientador.setNome("Escolha um orientador");

            Periodo periodo = new Periodo();
            periodo.setId(0); periodo.setNome("Escolha um período");

            this.cmbOrientador.getItems().add(orientador);
            this.cmbOrientador.getItems().addAll(new OrientadorDAO().listAll());
            this.cmbDiscente.getItems().add(discente);
            this.cmbDiscente.getItems().addAll(new DiscenteDAO().listAll());
            
            this.cmbSituacao.getItems().addAll(listaSituacao);
            this.cmbPeriodo.getItems().add(periodo);
            this.cmbPeriodo.getItems().addAll(new PeriodoDAO().listAll());

            this.cmbOrientador.getSelectionModel().selectFirst();
            this.cmbDiscente.getSelectionModel().selectFirst();
            this.cmbPeriodo.getSelectionModel().selectFirst();
            this.cmbSituacao.getSelectionModel().selectFirst();
        }
    }    
    
    public void setData(ObservableList<Monitoria> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Monitoria monitoria){
        this.tipo = tipo;
        this.data = (ObservableList<Monitoria>)data;
        this.monitoria = (Monitoria) monitoria;
        preencherCampos();
    }
    
    public void deletar(Monitoria monitoria, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            MonitoriaDAO monitoriaDAO = new MonitoriaDAO();
            monitoriaDAO.delete(monitoria);
            _data.remove(monitoria);
        }
    }
    
    private void preencherCampos(){
        
        this.dataInicial.setValue(new Date(this.monitoria.getDataInicio().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.dataFinal.setValue(new Date(this.monitoria.getDataFim().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        ObservableList<Disciplina> dd = this.cmbDisciplina.getItems();
        ObservableList<Periodo> pp = this.cmbPeriodo.getItems();
        ObservableList<Discente> disc = this.cmbDiscente.getItems();
        ObservableList<Orientador> or = this.cmbOrientador.getItems();
        
        int i = 0;

        for(Disciplina d : dd){
            if(Objects.equals(d.getId(), this.monitoria.getDisciplina().getId())){
                this.cmbDisciplina.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbDisciplina.getSelectionModel().select(i);
        
        i = 0;
        for(Periodo p : pp){
            if(Objects.equals(p.getId(), this.monitoria.getPeriodo().getId())){
                this.cmbPeriodo.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbPeriodo.getSelectionModel().select(i);
        
        i = 0;
        for(Discente p : disc){
            if(Objects.equals(p.getId(), this.monitoria.getDiscente().getId())){
                this.cmbDiscente.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbDiscente.getSelectionModel().select(i);
        
        i = 0;
        for(Orientador p : or){
            if(Objects.equals(p.getId(), this.monitoria.getOrientador().getId())){
                this.cmbOrientador.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbOrientador.getSelectionModel().select(i);
        
        this.cmbSituacao.setValue(this.monitoria.getSitCertificado());
    }
}
