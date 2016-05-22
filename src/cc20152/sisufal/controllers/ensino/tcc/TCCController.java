/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino.tcc;

import cc20152.sisufal.dao.impl.CursoDAO;
import cc20152.sisufal.dao.impl.DiscenteDAO;
import cc20152.sisufal.dao.impl.OrientadorDAO;
import cc20152.sisufal.dao.impl.TCCDAO;
import cc20152.sisufal.models.Convidado;
import cc20152.sisufal.models.Curso;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.Orientador;
import cc20152.sisufal.models.TCC;
import cc20152.sisufal.util.AutoCompleteComboBoxListener;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.BotoesLista.DeletarCell;
import cc20152.sisufal.util.BotoesLista.EditarCell;
import cc20152.sisufal.util.DiscenteConverter;
import cc20152.sisufal.util.OrientadorConverter;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Button;
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


/**
 *
 * @author AtaideAl
 */
public class TCCController implements Initializable {
    String pacote = "controllers/ensino/tcc/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroTCCFXML.fxml"; 
    
    @FXML
        private TextField txtPesquisa;
    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TextField txtTitulo;
    @FXML
        private DatePicker dataInicio;
    @FXML
        private DatePicker dataFim;
    @FXML
        private ComboBox<Curso> cmbCurso;
    @FXML
        private ComboBox<Discente> cmbDiscente;
    @FXML
        private ComboBox<Orientador> cmbOrientador;
    @FXML
        private ComboBox<Orientador> cmbProfessor;
    @FXML
        private ComboBox<Discente> cmbDiscenteBanca;
    @FXML
        private ComboBox<Convidado> cmbConvidado;   
    @FXML
        private TableView<Orientador> tableProfessor;
    @FXML
        private TableView<Discente> tableDiscente;
    @FXML
        private TableView<Convidado> tableConvidado;
    @FXML
        private TextField txtConvidado;    
    @FXML
        private TableView<TCC> tableTcc; 
    
    
    private TCC tcc;
    private String tipo;
    private ObservableList<TCC> data;
    
    @FXML
    private void novoTCC (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        TCCController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro TCC");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void cadastrarTCC (ActionEvent event){
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        if(this.txtTitulo.getText().equals("")){
            aviso.setHeaderText("Campo Título não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.dataInicio.getValue() == null){
            aviso.setHeaderText("Campo Data de Início não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.dataFim.getValue() == null){
            aviso.setHeaderText("Campo Data de Fim não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbCurso.getValue() == null){
            aviso.setHeaderText("Campo Curso não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbDiscente.getValue() == null){
            aviso.setHeaderText("Campo Discente não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbOrientador.getValue() == null){
            aviso.setHeaderText("Campo Orientador não pode estar vazio");
            aviso.show();
            return ;
        }       
        
        TCC old = this.tcc;
        if(this.tcc == null){
            this.tcc = new TCC();
        }
        this.tcc.setTitulo(this.txtTitulo.getText());
        this.tcc.setDataInicio(Date.from(this.dataInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.tcc.setDataFim(Date.from(this.dataFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.tcc.setCurso(this.cmbCurso.getValue());
        this.tcc.setDiscente(this.cmbDiscente.getValue());
        this.tcc.setOrientador(this.cmbOrientador.getValue());
        for (Orientador o : tableProfessor.getItems()){
            this.tcc.getBanca().getListaServidores().add(o);
        }
        for (Discente d : tableDiscente.getItems()){
            this.tcc.getBanca().getListaDiscentes().add(d);
        }  
        for (Convidado c : tableConvidado.getItems()){
            this.tcc.getBanca().getListaConvidados().add(c);
        }
        
        TCCDAO tccDAO = new TCCDAO();
        String result = null;
        if(this.tipo == null){
            result = tccDAO.save(this.tcc);
        }else{
            result = tccDAO.update(this.tcc);
            //System.out.println("ID NOVO:"+tcc.getBanca().getId());
        }
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("TCC salvo com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(tcc);
           }else{
                this.data.remove(old);
                this.data.add(tcc);
           }
           
           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao salvar TCC!");
           alerta.show();
        }
    }
    
    @FXML
    private void cancelarCadastro (ActionEvent event){
        Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void fecharLista (ActionEvent event){
        Stage stage = (Stage) btnVoltarLista.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnAddProfessor(ActionEvent event){
        Orientador o = new Orientador();
        o = this.cmbProfessor.getValue();
        if(o!=null)
            tableProfessor.getItems().add(o);
        this.cmbProfessor.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void btnRmvProfessor(ActionEvent event){
       Orientador o = new Orientador();
       o = tableProfessor.getSelectionModel().getSelectedItem();
       tableProfessor.getItems().remove(o);
    }
    
    @FXML
    private void btnAddDiscente(ActionEvent event){
       Discente d = new Discente();
       d = this.cmbDiscenteBanca.getValue();
       if(d!=null)
           tableDiscente.getItems().add(d);
       this.cmbDiscenteBanca.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void btnRmvDiscente(ActionEvent event){
       Discente d = new Discente();
       d = tableDiscente.getSelectionModel().getSelectedItem();
       tableDiscente.getItems().remove(d);
    }
    
    @FXML
    private void btnAddConvidado(ActionEvent event){
        if(!txtConvidado.getText().equals("")){
            Convidado c = new Convidado();
            c.setNome(txtConvidado.getText());
            tableConvidado.getItems().add(c);
        }
        txtConvidado.setText("");
    }
    
    @FXML
    private void btnRmvConvidado(ActionEvent event){
        int index = tableConvidado.getSelectionModel().getSelectedIndex();
        tableConvidado.getItems().remove(index);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!url.getPath().contains(this.fxml)){
            listarTCCs();
        }else{
            listarDadosCadastro();
        }
    }
    
     public void setData(ObservableList<TCC> data){
        this.data = data;
    }
    
    public void listarDadosCadastro(){
         this.tableProfessor.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
         this.tableDiscente.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
         this.tableConvidado.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
         
         this.cmbOrientador.getItems().addAll(new OrientadorDAO().listAll());
         this.cmbOrientador.setConverter(new OrientadorConverter());
         new AutoCompleteComboBoxListener<>(this.cmbOrientador, "Escolha um Orientador");
         
         this.cmbDiscente.getItems().addAll(new DiscenteDAO().listAll());
         this.cmbDiscente.setConverter(new DiscenteConverter());
         new AutoCompleteComboBoxListener<>(this.cmbDiscente, "Escolha um Discente");
         
         this.cmbCurso.getItems().addAll(new CursoDAO().listAll());
         this.cmbCurso.getSelectionModel().selectFirst();
         
         this.cmbProfessor.getItems().addAll(new OrientadorDAO().listAll());
         this.cmbProfessor.setConverter(new OrientadorConverter());
         new AutoCompleteComboBoxListener<>(this.cmbProfessor, "Escolha um Professor");
         
         this.cmbDiscenteBanca.getItems().addAll(new DiscenteDAO().listAll());
         this.cmbDiscenteBanca.setConverter(new DiscenteConverter());
         new AutoCompleteComboBoxListener<>(this.cmbDiscenteBanca, "Escolha um Discente");
         
    }
    
    public void listarTCCs(){
       data = FXCollections.observableArrayList();
       
       List<TCC> listaTCC = new ArrayList<>();
       listaTCC = new TCCDAO().listAll();
       tableTcc.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("titulo"));
       tableTcc.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("discente"));
       tableTcc.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
       tableTcc.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dataFim"));
      
        TableColumn colEditar = this.tableTcc.getColumns().get(4);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, TCC.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableTcc.getColumns().get(5);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, TCC.class, TCCController.class).new DeletarCell();
           }
       });
       
       for(TCC t:listaTCC){
           data.add(t);
       }
       tableTcc.setItems(data);  
    }
    
    public void setEditar(String tipo, ObservableList<?> data, TCC tcc){
        this.tipo = tipo;
        this.data = (ObservableList<TCC>)data;
        this.tcc = (TCC) tcc;
        preencherCampos();
    }
    
     public void deletar(TCC tcc, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            TCCDAO tccDAO = new TCCDAO();
            tccDAO.delete(tcc);
            _data.remove(tcc);
        }
    }
    
    
    private void preencherCampos(){
        
        this.txtTitulo.setText(tcc.getTitulo());
        this.dataInicio.setValue(new Date(this.tcc.getDataInicio().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.dataFim.setValue(new Date(this.tcc.getDataFim().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());      
        
                
        ObservableList<Curso> cc = this.cmbCurso.getItems();
        ObservableList<Discente> disc = this.cmbDiscenteBanca.getItems();
        ObservableList<Orientador> or = this.cmbOrientador.getItems();
        
        int i = 0;
        
        for(Curso c : cc){
            if(Objects.equals(c.getId(), this.tcc.getDiscente().getId())){
                this.cmbCurso.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbCurso.getSelectionModel().select(i);
        
        i = 0;
        for(Discente p : disc){
            if(Objects.equals(p.getId(), this.tcc.getDiscente().getId())){
                this.cmbDiscente.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbDiscente.getSelectionModel().select(i);
        
        i = 0;
        for(Orientador p : or){
            if(Objects.equals(p.getId(), this.tcc.getOrientador().getId())){
                this.cmbOrientador.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbOrientador.getSelectionModel().select(i);
        TCCDAO tccDAO = new TCCDAO();
        
        ArrayList<Orientador> listaProfessor = tccDAO.listaProfessorBanca(tcc);
        tableProfessor.getItems().setAll(listaProfessor);
        
        ArrayList<Discente> listaDiscente= tccDAO.listaDiscenteBanca(tcc);
        tableDiscente.getItems().setAll(listaDiscente);
        
        ArrayList<Convidado> listaConvidado = tccDAO.listaConvidadoBanca(tcc);
        tableConvidado.getItems().setAll(listaConvidado);
        
    }
    
    @FXML
    private void pesquisar(ActionEvent event){
        if(txtPesquisa.getText().equals(""))
            listarGridServidores();
        else{
            listarGridServidoresPesquisa(cmbPesquisa.getValue());
        }
    }
}
