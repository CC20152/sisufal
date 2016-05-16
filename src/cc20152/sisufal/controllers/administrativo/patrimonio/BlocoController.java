/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.administrativo.patrimonio;

import cc20152.sisufal.controllers.institucional.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 *
 * @author Gabriel Fabrício
 */
public class BlocoController implements Initializable {

    String pacote = "controllers/administrativo/patrimonio/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroBlocoFXML.fxml"; //Caminho do FXML
    
    @FXML
        private Button btnVoltar;
    @FXML
        private Button btnCancelar;
    
    @FXML
    private void novoBloco(ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Bloco");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void btnSalvar(ActionEvent event){
        
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}