/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.pesquisa.projeto;

import cc20152.sisufal.dao.impl.InstituicaoFinanciadoraDAO;
import cc20152.sisufal.models.InstituicaoFinanciadora;
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
 * @author anderson
 */
public class InstituicaoFinanciadoraController implements Initializable   {
    private ObservableList<InstituicaoFinanciadora> data;
    
    final String pacote = "controllers/pesquisa/projeto/";
    private String fxml = "fxml/cadastro/CadastroInstituicaoFinanciadoraFXML.fxml";
    
    private String tipo;
    private InstituicaoFinanciadora instituicaoFinanciadora;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCodigo;
    
    @FXML
    private Button btnCancelarCadastro;
    
    @FXML
    private Button btnVoltarLista;
    
    @FXML
    private TableView<InstituicaoFinanciadora> tableInstituicoesFinanciadoras;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(!url.getPath().contains(this.fxml)){
            listarInstituicoesFinanciadoras();
        }
    }
    
    @FXML
    private void btnBuscar(ActionEvent event){
        InstituicaoFinanciadoraDAO instituicaoFinanciadoraDAO = new InstituicaoFinanciadoraDAO();
        InstituicaoFinanciadora instituicaoFinanciadora = new InstituicaoFinanciadora();
        
        if(this.txtCodigo.getText().equals("") && this.txtNome.getText().equals("")) {
            listarInstituicoesFinanciadoras();
        } else {
            if(!this.txtCodigo.getText().equals("")){
                instituicaoFinanciadora.setCodigo(this.txtCodigo.getText());
            }

            if(!this.txtNome.getText().equals("")){
                instituicaoFinanciadora.setNome(this.txtNome.getText());
            }

            List<InstituicaoFinanciadora> list = instituicaoFinanciadoraDAO.listWithParams(instituicaoFinanciadora);

            this.data.clear();
            this.data.addAll(list);
        }
    }
    
    @FXML
    private void novaInstituicao (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxml);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        InstituicaoFinanciadoraController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Instituiçao Financiadora");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @FXML
    private void salvarInstituicao(ActionEvent event) throws IOException{
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtCodigo.getText().equals("")) {
            aviso.setHeaderText("Campo codigo não pode estar vazio");
            aviso.show();
            return ;
        } else if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo nome não pode estar vazio");
            aviso.show();
            return ;
        } 
        
        InstituicaoFinanciadora old = this.instituicaoFinanciadora;
        if(this.instituicaoFinanciadora == null){
            this.instituicaoFinanciadora = new InstituicaoFinanciadora();
        }
        
        this.instituicaoFinanciadora.setNome(this.txtNome.getText());
        this.instituicaoFinanciadora.setCodigo(this.txtCodigo.getText());
        
        InstituicaoFinanciadoraDAO instituicaoFinanciadoraDAO = new InstituicaoFinanciadoraDAO();
        String result = null;
        if(this.tipo == null){
            result = instituicaoFinanciadoraDAO.save(instituicaoFinanciadora);
        }else{
            result = instituicaoFinanciadoraDAO.update(instituicaoFinanciadora);
        }
        
        System.out.println(result);
        if(!result.equals("-1")){ 
           if(this.tipo == null) this.data.add(instituicaoFinanciadora); 
           else this.data.set(this.data.indexOf(instituicaoFinanciadora), instituicaoFinanciadora);
               
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Instituiçao Financiadora cadastrado com sucesso!");
           alerta.show();
           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao cadastrar instituiçao financiadora!");
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
    
    private void setData(ObservableList<InstituicaoFinanciadora> data){
        this.data = data;
    }
    
    private void listarInstituicoesFinanciadoras(){
       data = FXCollections.observableArrayList();
       
       List<InstituicaoFinanciadora> listaInstituicaoFinanciadoras = new ArrayList<>();
       listaInstituicaoFinanciadoras = new InstituicaoFinanciadoraDAO().listAll();
       
       this.tableInstituicoesFinanciadoras.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("codigo"));
       this.tableInstituicoesFinanciadoras.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nome"));
      
       TableColumn colEditar = this.tableInstituicoesFinanciadoras.getColumns().get(2);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, InstituicaoFinanciadora.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableInstituicoesFinanciadoras.getColumns().get(3);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, InstituicaoFinanciadora.class, InstituicaoFinanciadoraController.class).new DeletarCell();
           }
       });
      
       for (InstituicaoFinanciadora e : listaInstituicaoFinanciadoras) {
           data.add(e);
       }
       
       tableInstituicoesFinanciadoras.setItems(data);       
    }
    
    public void setEditar(String tipo, ObservableList<?> data, InstituicaoFinanciadora instituicaoFinanciadora){
        this.tipo = tipo;
        this.data = (ObservableList<InstituicaoFinanciadora>)data;
        this.instituicaoFinanciadora = (InstituicaoFinanciadora) instituicaoFinanciadora;
        preencherCampos();
    }
    
    public void deletar(InstituicaoFinanciadora instituicaoFinanciadora, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            InstituicaoFinanciadoraDAO instituicaoFinanciadoraDAO = new InstituicaoFinanciadoraDAO();
            instituicaoFinanciadoraDAO.delete(instituicaoFinanciadora);
            _data.remove(instituicaoFinanciadora);
        }
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.instituicaoFinanciadora.getNome());
        this.txtCodigo.setText(this.instituicaoFinanciadora.getCodigo());
    }
}
