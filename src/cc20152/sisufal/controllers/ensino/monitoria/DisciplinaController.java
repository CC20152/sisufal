/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino.monitoria;

import cc20152.sisufal.dao.impl.CursoDAO;
import cc20152.sisufal.dao.impl.DisciplinaDAO;
import cc20152.sisufal.models.Disciplina;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.util.BotoesLista;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.sun.prism.impl.Disposer.Record;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Dayvson
 */
public class DisciplinaController implements Initializable {
    
    private ObservableList<Disciplina> data;
    
    final String pacote = "controllers/ensino/monitoria/"; //Pacote do controller
    final String fxml = "fxml/cadastro/CadastroDisciplinaFXML.fxml"; //Caminho do FXML
    
    private String tipo;
    
    private Disciplina disciplina;
    
    @FXML
    private TextField txtCargaHoraria;
     
    @FXML
    private TextField txtNome;
    
    @FXML
    private ComboBox cmbTurno;
    
    @FXML
    private ComboBox cmbCurso;
    
    @FXML
    private TableView<Disciplina> tbDisciplina;
    
    @FXML
    private void btnBuscar(ActionEvent event){
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        Disciplina disciplina = new Disciplina();
        if(!this.txtNome.getText().equals("")){
            disciplina.setNome(this.txtNome.getText());
        }
        if(this.cmbCurso.getValue() != null && !this.cmbCurso.getSelectionModel().isSelected(0)){
            disciplina.getCurso().setId(Integer.parseInt((this.cmbCurso.getValue().toString().split("-"))[0]));
        }
        if(this.cmbTurno.getValue() != null && !this.cmbTurno.getSelectionModel().isSelected(0)){
            disciplina.setTurno(this.cmbTurno.getValue().toString());
        }
        List<Disciplina> list = disciplinaDAO.listWithParams(disciplina);
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo nome não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtCargaHoraria.getText().equals("")){
            aviso.setHeaderText("Campo carga horária não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbCurso.getValue() == null && this.cmbCurso.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo curso não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbTurno.getValue() == null && this.cmbTurno.getSelectionModel().isSelected(0)){
            aviso.setHeaderText("Campo turno não pode estar vazio");
            aviso.show();
            return ;
        }
        
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(this.txtNome.getText());
        disciplina.setCargaHoraria(this.txtCargaHoraria.getText());
        disciplina.getCurso().setId(Integer.parseInt((this.cmbCurso.getValue().toString().trim().split("-"))[0]));
        disciplina.getCurso().setNome(((this.cmbCurso.getValue().toString().trim().split("-"))[1]));
        disciplina.setTurno(this.cmbTurno.getValue().toString());
       
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String result = disciplinaDAO.save(disciplina);
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Disciplina cadastrada com sucesso!");
           alerta.show();
           this.data.add(disciplina);
           Stage stage = (Stage) txtNome.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao cadastrar disciplina!");
           alerta.show();
        }
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void novaDisciplina (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxml);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        DisciplinaController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Disciplina");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    
    
    private void listarDisciplinas(){
       data = FXCollections.observableArrayList();
       List<Disciplina> listaDisciplina = new ArrayList<>();
       listaDisciplina = new DisciplinaDAO().listAll();
       this.tbDisciplina.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       this.tbDisciplina.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
       this.tbDisciplina.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("curso"));
       this.tbDisciplina.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("turno"));
      
       TableColumn colEditar = this.tbDisciplina.getColumns().get(4);
       colEditar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Disciplina.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tbDisciplina.getColumns().get(5);
       colDeletar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Disciplina.class, DisciplinaDAO.class).new DeletarCell();
           }
       });
      
       for (Disciplina e : listaDisciplina) {
           data.add(e);
       }
       
       tbDisciplina.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtNome.setTooltip(new Tooltip("Digite o nome da disciplina"));
        ArrayList<String> listaTurno = new ArrayList<>();
        
        listaTurno.add("MATUTINO");
        listaTurno.add("VESPERTINO");
        listaTurno.add("NOTURNO");
       
        this.cmbCurso.getItems().add("Escolha um curso");
        this.cmbCurso.getItems().addAll(new CursoDAO().listAll());
        this.cmbTurno.getItems().add("Escolha um turno");
        this.cmbTurno.getItems().addAll(listaTurno);
        this.cmbCurso.getSelectionModel().selectFirst();
        this.cmbTurno.getSelectionModel().selectFirst();
        
        if(!url.getPath().contains(this.fxml)){
            listarDisciplinas();
        }
    }    
    
    public void setData(ObservableList<Disciplina> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Disciplina disciplina){
        this.tipo = tipo;
        this.data = (ObservableList<Disciplina>)data;
        this.disciplina = (Disciplina) disciplina;
        preencherCampos();
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.disciplina.getNome());
        this.txtCargaHoraria.setText(this.disciplina.getCargaHoraria());
        this.cmbCurso.setValue(this.disciplina.getCurso());
        this.cmbTurno.setValue(this.disciplina.getTurno());
    }
    
}
