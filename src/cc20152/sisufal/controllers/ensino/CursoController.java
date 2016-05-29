/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino;

import cc20152.sisufal.dao.impl.CursoDAO;
import cc20152.sisufal.models.Curso;
import cc20152.sisufal.util.BotoesLista;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author Dayvson
 */
public class CursoController implements Initializable {
    String pacote = "controllers/ensino/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroCursoFXML.fxml"; //Caminho do FXML
    
    private Curso curso;
    private String tipo;
    private ObservableList<Curso> data;
    
    @FXML
    private Button btnVoltar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCodigo;
    
    @FXML
    private TableView<Curso> tbCurso;
    
    @FXML
    private void novoCurso(ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Curso");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void btnBuscar(ActionEvent event){
        CursoDAO cursoDAO = new CursoDAO();
        Curso curso = new Curso();
        
        if(!this.txtNome.getText().equals("")){
            curso.setNome(this.txtNome.getText());
        }
        
        if(!this.txtCodigo.getText().equals("")){
            curso.setCodigo(this.txtCodigo.getText());
        }
        
        
        List<Curso> list = cursoDAO.listWithParams(curso);
        
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void btnSalvar(ActionEvent event){
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo nome não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtCodigo.getText().equals("")){
            aviso.setHeaderText("Campo código não pode estar vazio");
            aviso.show();
            return ;
        }
        
        Curso old = this.curso;
        if(this.curso == null){
            this.curso = new Curso();
        }
        
        this.curso.setNome(this.txtNome.getText());
        this.curso.setCodigo(this.txtCodigo.getText());
        
        
        CursoDAO cursoDAO = new CursoDAO();
        String result = null;
        if(this.tipo == null){
            result = cursoDAO.save(curso);
        }else{
            result = cursoDAO.update(curso);
        }
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Curso salva com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(curso);
           }else{
                this.data.remove(old);
                this.data.add(curso);
           }
           
           Stage stage = (Stage) txtNome.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao salvar curso!");
           alerta.show();
        }
    }
    
    @FXML
    private void btnVoltarLista (ActionEvent event){
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnCancelarCadastro (ActionEvent event){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void listarCursos(){
       data = FXCollections.observableArrayList();
       
       List<Curso> listaCurso = new ArrayList<>();
       listaCurso = new CursoDAO().listAll();
       
       this.tbCurso.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       this.tbCurso.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("codigo"));
      
       TableColumn colEditar = this.tbCurso.getColumns().get(2);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Curso.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tbCurso.getColumns().get(3);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Curso.class, CursoController.class).new DeletarCell();
           }
       });
       
       for (Curso e : listaCurso) {
           data.add(e);
       }
       
       tbCurso.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      if(!url.getPath().contains(this.fxml)){
        listarCursos();
      }
    }    
    
    public void setData(ObservableList<Curso> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Curso curso){
        this.tipo = tipo;
        this.data = (ObservableList<Curso>)data;
        this.curso = (Curso) curso;
        preencherCampos();
    }
    
    public void deletar(Curso curso, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            CursoDAO cursoDAO = new CursoDAO();
            String result = cursoDAO.delete(curso);
            if(result.contains("OK")){ 
                _data.remove(curso);
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
        this.txtNome.setText(this.curso.getNome());
        this.txtCodigo.setText(this.curso.getCodigo());
    }
    
}
