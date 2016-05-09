/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        stage.setOnCloseRequest((new EventHandler<WindowEvent>(){

        @Override
        public void handle(WindowEvent arg0) {
                            arg0.consume();
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Atenção");
            alerta.setHeaderText("Deseja mesmo sair?");
            alerta.setContentText("Todas as telas serão fechadas e informações não salvas serão perdidas!");
            Optional<ButtonType> res = alerta.showAndWait();
            if(res.get() == ButtonType.OK){
                Platform.exit();
            }
        }
        }));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
