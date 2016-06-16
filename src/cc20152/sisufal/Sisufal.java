/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal;

import cc20152.sisufal.dao.impl.UsuarioDAO;
import cc20152.sisufal.models.Usuario;
import cc20152.sisufal.util.Sessao;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Dayvson
 * Dialog de http://code.makery.ch/blog/javafx-dialogs-official/
 */
public class Sisufal extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Autentique-se");
        dialog.setHeaderText("Login");

        ButtonType loginButtonType = new ButtonType("Entrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Usuário");
        PasswordField password = new PasswordField();
        password.setPromptText("Senha");

        grid.add(new Label("Usuário:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Senha:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
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
        
        result.ifPresent(usernamePassword -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = new Usuario();
            usuario.setLogin(usernamePassword.getKey());
            
            usuario = usuarioDAO.logar(usuario);
            
            if(usuario != null){
                if(BCrypt.checkpw(usernamePassword.getValue(), usuario.getHash())){
                    Sessao sessao = Sessao.getInstance();
                    sessao.setUsuario(usuario);
                    stage.show();
                }else{
                    dialog.setHeaderText("Usuário ou senha incorretos");
                    dialog.showAndWait();
                }
            }else{
                dialog.setHeaderText("Usuário ou senha incorretos");
                dialog.showAndWait();
            }
        });
 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
