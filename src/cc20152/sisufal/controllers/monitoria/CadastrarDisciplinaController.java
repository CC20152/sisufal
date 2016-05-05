/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.monitoria;

import cc20152.sisufal.dao.impl.DisciplinaDAO;
import cc20152.sisufal.models.Disciplina;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;


/**
 *
 * @author Dayvson
 */
public class CadastrarDisciplinaController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        Disciplina disciplina = new Disciplina();
        if(txtNome.getText().equals("")){
            System.out.println("O campo nome não pode estar vazio");
            return ;
        }
        disciplina.setNome(txtNome.getText());
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String result = disciplinaDAO.save(disciplina);
        System.out.println(result);
        if(result.equals("OK")){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Sucesso");
            alerta.setHeaderText("Disciplina cadastrada com sucesso!");
            alerta.show();
        }
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtNome.setTooltip(new Tooltip("Digite o nome da disciplina"));
    }    
    
}
