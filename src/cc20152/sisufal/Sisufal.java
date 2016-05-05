/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Dayvson
 */
public class Sisufal extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //System.out.println(""+getClass().getResource(""));
        Parent root = FXMLLoader.load(getClass().getResource("fxml/TelaPrincipalFXML.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Principal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
