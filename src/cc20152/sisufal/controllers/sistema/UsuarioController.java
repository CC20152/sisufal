/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.sistema;

import cc20152.sisufal.dao.IBaseDAO;
import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.models.Servidor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author AtaideAl
 */
public class UsuarioController implements Initializable{  
    ServidorDAO servidorDao = new ServidorDAO();
    
    String pacote = "controllers/sistema/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroUsuarioFXML.fxml"; //Caminho do FXML
    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private ComboBox<Servidor> cmbServidores;
    
    @FXML
    private void novoUsuario (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Usuário");
        stage.setScene(scene);
        stage.show();
       
    }
    
    
    @FXML
    private void cancelarCadastro (ActionEvent event){
        Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void cadastroUsuario (ActionEvent event){
        System.out.println("SALVOU");
        ObservableList<Servidor> data = FXCollections.observableArrayList();
        List<Servidor> listaServidores = new ArrayList<Servidor>();
        listaServidores = servidorDao.listAll();
        for(Servidor servidor: listaServidores){
           data.add(servidor);  
        }
        cmbServidores.setCellFactory(new Callback<ListView<Servidor>,ListCell<Servidor>>(){
 
            @Override
            public ListCell<Servidor> call(ListView<Servidor> p) {
                 
                final ListCell<Servidor> cell = new ListCell<Servidor>(){
 
                    @Override
                    protected void updateItem(Servidor t, boolean bln) {
                        super.updateItem(t, bln);
                        if(t != null){
                            setText(t.getNome());
                        }else{
                            setText(null);
                        }
                    }
  
                };
                 
                return cell;
            }
        });
        
        cmbServidores.setItems(data);

    }
    
    @FXML
    private void fecharLista (ActionEvent event){
        Stage stage = (Stage) btnVoltarLista.getScene().getWindow();
        stage.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
