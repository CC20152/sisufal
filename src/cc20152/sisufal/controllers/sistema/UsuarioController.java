/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.sistema;

import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.dao.impl.UsuarioDAO;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.models.Usuario;
import cc20152.sisufal.util.AutoCompleteComboBoxListener;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.ServidorConverter;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
public class UsuarioController implements Initializable{  

    String pacote = "controllers/sistema/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroUsuarioFXML.fxml"; //Caminho do FXML
        
    private ObservableList<Usuario> data;
    private String tipo;
    
    private Usuario usuario;
    
    @FXML
    private TextField txtLogin;
     
    @FXML
    private TextField txtSenha;
    
    @FXML
    private ComboBox cmbServidor;
    
    @FXML
    private CheckBox checkAlterar;
    
    @FXML
    private TableView<Usuario> tbUsuario;
    
    @FXML
    private void btnBuscar(ActionEvent event){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        
        List<Usuario> list = usuarioDAO.listAll();
        
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtLogin.getText().equals("")){
            aviso.setHeaderText("Campo login não pode estar vazio");
            aviso.show();
            return ;
        }else if((this.txtSenha.getText().equals("") && this.tipo == null) || (this.txtSenha.getText().equals("") && this.checkAlterar.isSelected())){
            aviso.setHeaderText("Campo senha não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtSenha.getText().length() < 8){
            aviso.setHeaderText("Campo senha precisa ter no mínimo 8 caracteres");
            aviso.show();
            return ;
        }
        
        Usuario old = this.usuario;
        if(this.usuario == null){
            this.usuario = new Usuario();
        }
        
        this.usuario.setLogin(this.txtLogin.getText());
        
        if(this.tipo == null || this.checkAlterar.isSelected())
            this.usuario.setPassword(this.txtSenha.getText());
        
        if(!this.cmbServidor.getSelectionModel().isEmpty())
            this.usuario.setServidor((Servidor) this.cmbServidor.getValue());
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String result = null;
        if(this.tipo == null){
            result = usuarioDAO.save(usuario);
        }else{
            result = usuarioDAO.update(usuario);
        }
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Usuário salvo com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(usuario);
           }else{
                this.data.remove(old);
                this.data.add(usuario);
           }
           
           Stage stage = (Stage) txtLogin.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao salvar usuário!");
           alerta.show();
        }
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) txtLogin.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void novoUsuario (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxml);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        UsuarioController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Usuário");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    private void listarUsuarios(){
       data = FXCollections.observableArrayList();
       
       List<Usuario> listaUsuario = new ArrayList<>();
       listaUsuario = new UsuarioDAO().listAll();
       
       this.tbUsuario.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("login"));
        TableColumn colCarga =  this.tbUsuario.getColumns().get(1);
       colCarga.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuario, String> p) {
               if(p.getValue().getServidor().getNome() == null)
                    return new SimpleObjectProperty<>("Não servidor");
               else
                    return new SimpleObjectProperty<>(p.getValue().getServidor().getNome().toUpperCase());
           }
       });
       this.tbUsuario.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
       
       TableColumn colEditar = this.tbUsuario.getColumns().get(3);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Usuario.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tbUsuario.getColumns().get(4);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Usuario.class, UsuarioController.class).new DeletarCell();
           }
       });
      
       for (Usuario e : listaUsuario) {
           data.add(e);
       }
       
       tbUsuario.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!url.getPath().contains(this.fxml)){
            listarUsuarios();
        }else{
            this.cmbServidor.getItems().setAll(new ServidorDAO().listAll());
            this.cmbServidor.setConverter(new ServidorConverter());
            new AutoCompleteComboBoxListener<>(this.cmbServidor, "Escolha um Servidor (opcional)");
            this.checkAlterar.setVisible(false);
        }
    }    
    
    public void setData(ObservableList<Usuario> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Usuario usuario){
        this.tipo = tipo;
        this.data = (ObservableList<Usuario>)data;
        this.usuario = (Usuario) usuario;
        preencherCampos();
    }
    
    public void deletar(Usuario usuario, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.delete(usuario);
            _data.remove(usuario);
        }
    }
    
    private void preencherCampos(){
        this.txtLogin.setText(this.usuario.getLogin());
        ObservableList<Servidor> cc = this.cmbServidor.getItems();
        int i = 0;
        for(Servidor c : cc){
            if(Objects.equals(c.getId(), this.usuario.getServidor().getId())){
                this.cmbServidor.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbServidor.getSelectionModel().select(i);
        this.txtSenha.setDisable(true);
        this.checkAlterar.setVisible(true);
        this.checkAlterar.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(checkAlterar.isSelected())
                    txtSenha.setDisable(false);
                else
                    txtSenha.setDisable(true);
            }
        });
    }
    
}
