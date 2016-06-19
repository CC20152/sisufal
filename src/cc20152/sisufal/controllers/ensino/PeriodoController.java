/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino;

import cc20152.sisufal.controllers.ensino.*;
import cc20152.sisufal.dao.impl.CursoDAO;
import cc20152.sisufal.dao.impl.PeriodoDAO;
import cc20152.sisufal.models.Curso;
import cc20152.sisufal.models.Periodo;
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
 * @author AtaideAl
 */
public class PeriodoController implements Initializable {
    String pacote = "controllers/ensino/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroPeriodoFXML.fxml"; //Caminho do FXML
    
    private Periodo periodo;
    private String tipo;
    private ObservableList<Periodo> data;
    
    @FXML
    private Button btnVoltar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TableView<Periodo> tbPeriodo;
    
    @FXML
    private void novoPeriodo(ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        PeriodoController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Período");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void btnBuscar(ActionEvent event){
        PeriodoDAO periodoDAO = new PeriodoDAO();
        Periodo periodo = new Periodo();
        
        if(this.txtNome.getText().equals("")){
            listarPeriodos();
        }else{
            periodo.setNome(this.txtNome.getText());
            List<Periodo> list = periodoDAO.listWithParams(periodo);
            this.data.clear();
            this.data.addAll(list);
        }
    }
    
    @FXML
    private void btnSalvar(ActionEvent event){
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo período não pode estar vazio");
            aviso.show();
            return ;
        }
        
        Periodo old = this.periodo;
        if(this.periodo == null){
            this.periodo = new Periodo();
        }
        
        this.periodo.setNome(this.txtNome.getText());
        
        PeriodoDAO periodoDAO = new PeriodoDAO();
        String result = null;
        if(this.tipo == null){
            result = periodoDAO.save(periodo);
        }else{
            result = periodoDAO.update(periodo);
        }
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Período salvo com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(periodo);
           }else{
                this.data.remove(old);
                this.data.add(periodo);
           }
           
           Stage stage = (Stage) btnCancelar.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao salvar periodo!");
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
    
    private void listarPeriodos(){
       data = FXCollections.observableArrayList();
       
       List<Periodo> listaPeriodo = new ArrayList<>();
       listaPeriodo = new PeriodoDAO().listAll();
       
       this.tbPeriodo.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
      
       TableColumn colEditar = this.tbPeriodo.getColumns().get(1);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Periodo.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tbPeriodo.getColumns().get(2);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Periodo.class, PeriodoController.class).new DeletarCell();
           }
       });
       
       for (Periodo e : listaPeriodo) {
           data.add(e);
       }
       
       tbPeriodo.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      if(!url.getPath().contains(this.fxml)){
        listarPeriodos();
      }
    }    
    
    public void setData(ObservableList<Periodo> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Periodo periodo){
        this.tipo = tipo;
        this.data = (ObservableList<Periodo>)data;
        this.periodo = (Periodo) periodo;
        preencherCampos();
    }
    
    public void deletar(Periodo periodo, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            PeriodoDAO periodoDAO = new PeriodoDAO();
            String result = periodoDAO.delete(periodo);
            if(result.contains("OK")){ 
                _data.remove(periodo);
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
        this.txtNome.setText(this.periodo.getNome());
    }
    
}
