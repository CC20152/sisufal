/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino.monitoria;

import cc20152.sisufal.dao.impl.CursoDAO;
import cc20152.sisufal.dao.impl.DisciplinaDAO;
import cc20152.sisufal.models.Curso;
import cc20152.sisufal.models.Disciplina;
import cc20152.sisufal.models.Servidor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;


/**
 *
 * @author Dayvson
 */
public class DisciplinaController implements Initializable {
    
    final String pacote = "controllers/ensino/monitoria/"; //Pacote do controller
    final String fxml = "fxml/cadastro/CadastroDisciplinaFXML.fxml"; //Caminho do FXML
    
    @FXML
    private TextField txtCargaHoraria;
     
    @FXML
    private TextField txtNome;
    
    @FXML
    private ComboBox cmbTurno;
    
    @FXML
    private ComboBox cmbCurso;
    
    @FXML
    private TableView<Disciplina> tbDisciplina;
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo nome não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtCargaHoraria.getText().equals("")){
            aviso.setHeaderText("Campo carga horária não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbCurso.getValue() == null){
            aviso.setHeaderText("Campo curso não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.cmbTurno.getValue() == null){
            aviso.setHeaderText("Campo turno não pode estar vazio");
            aviso.show();
            return ;
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(this.txtNome.getText());
        disciplina.setCargaHoraria(this.txtCargaHoraria.getText());
        disciplina.getCurso().setId(Integer.parseInt((this.cmbCurso.getValue().toString().split("-"))[0]));
        disciplina.setTurno(this.cmbTurno.getValue().toString());
        
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        String result = disciplinaDAO.save(disciplina);
        System.out.println(result);
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Disciplina cadastrada com sucesso!");
           alerta.show();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Erro ao cadastrar disciplina!");
           alerta.show();
        }
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void novaDisciplina (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxml);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Disciplina");
        stage.setScene(scene);
        stage.showAndWait();
    }
    private void listarDisciplinas(){
       ObservableList<Disciplina> data = FXCollections.observableArrayList();
       List<Disciplina> listaDisciplina = new ArrayList<>();
       listaDisciplina = new DisciplinaDAO().listAll();
       this.tbDisciplina.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       this.tbDisciplina.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
       this.tbDisciplina.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("curso"));
       this.tbDisciplina.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("turno"));
      
        Callback<TableColumn<Servidor, String>, TableCell<Servidor, String>> cellFactory = //
            new Callback<TableColumn<Servidor, String>, TableCell<Servidor, String>>()
            {
                @Override
                public TableCell call( final TableColumn<Servidor, String> param )
                {
                    final TableCell<Servidor, String> cell = new TableCell<Servidor, String>()
                    {
                        final Button btn = new Button( "Just Do It" );

                        @Override
                        public void updateItem( String item, boolean empty )
                        {
                            super.updateItem( item, empty );
                            if ( empty )
                            {
                                setGraphic( null );
                                setText( null );
                            }
                            else
                            {
                                btn.setOnAction( ( ActionEvent event ) ->
                                        {
                                            Servidor servidor = getTableView().getItems().get( getIndex() );
                                            System.out.println( servidor.toString() );
                                } );
                                setGraphic( btn );
                                setText( null );
                            }
                        }
                    };
                    return cell;
                }
            };
       this.tbDisciplina.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("editar"));
      
       for (Disciplina e : listaDisciplina) {
           data.add(e);
       }
       
       tbDisciplina.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtNome.setTooltip(new Tooltip("Digite o nome da disciplina"));
        ArrayList<String> listaTurno = new ArrayList<>();
        
        listaTurno.add("MATUTINO");
        listaTurno.add("VESPERTINO");
        listaTurno.add("NOTURNO");
        
        this.cmbCurso.getItems().addAll(new CursoDAO().listAll());
        this.cmbTurno.getItems().addAll(listaTurno);
        
        if(!url.getPath().contains(this.fxml)){
            listarDisciplinas();
        }
    }    
    
}
