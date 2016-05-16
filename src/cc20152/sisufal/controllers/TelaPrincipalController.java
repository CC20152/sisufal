/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers;

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
 * 
 */
public class TelaPrincipalController implements Initializable {
    
    final String pacote = "controllers/"; //Pacote do controller
    final String fxmlListaUsuario = "fxml/lista/ListaUsuarioFXML.fxml"; 
    final String fxmlListaTCC = "fxml/lista/ListaTCCFXML.fxml"; 
    final String fxmlListaDiscente = "fxml/lista/ListaDiscenteFXML.fxml"; 
    final String fxmlListaServidor = "fxml/lista/ListaServidorFXML.fxml"; 
    final String fxmlListaCurso = "fxml/lista/ListaCursoFXML.fxml";
    final String fxmlListaPatrimonio = "fxml/lista/ListaPatrimonioFXML.fxml";
    final String fxmlListaPatrimonioPermanente = "fxml/lista/ListaPatrimonioPermanenteFXML.fxml";

    final String listaMonitoriaFXML = "fxml/lista/ListaMonitoriaFXML.fxml"; 
    final String listaDisciplinaFXML = "fxml/lista/ListaDisciplinaFXML.fxml"; 
    
    @FXML
    private void menuUsuario (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxmlListaUsuario);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Usuarios");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void menuTCC (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxmlListaTCC);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("TCCs");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void menuDiscente(ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxmlListaDiscente);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Discentes");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void menuServidor(ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxmlListaServidor);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Servidores");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void menuDisciplina(ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path + this.listaDisciplinaFXML);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Disciplina");
        stage.setScene(scene);
        stage.show();
    }
    
     @FXML
    private void menuMonitoria(ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path + this.listaMonitoriaFXML);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Monitoria");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void menuCurso(ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path + this.fxmlListaCurso);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Curso");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void menuPatrimonioPermanente (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxmlListaPatrimonioPermanente);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Patrimônios Permanentes");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void menuPatrimonioConsumo (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxmlListaPatrimonio);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Patrimônios de Consumo");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
