/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.sistema;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author AtaideAl
 */
public class UsuarioController implements Initializable{  
    String pacote = "controllers/sistema/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroUsuarioFXML.fxml"; //Caminho do FXML
    
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
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
}
