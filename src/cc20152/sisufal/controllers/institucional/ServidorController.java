/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.institucional;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author AtaideAl
 */
public class ServidorController implements Initializable {
    String pacote = "controllers/corpoInstitucional/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroServidorFXML.fxml"; //Caminho do FXML
    ServidorDAO servidorDAO = new ServidorDAO();
    
    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TableView<Servidor> tableServidores;
    
    @FXML
    private void novoServidor (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Servidor");
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void cancelarCadastro (ActionEvent event){
        Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void fecharLista (ActionEvent event){
        Stage stage = (Stage) btnVoltarLista.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarGridServidores();
    }    
    
    private void listarGridServidores(){
       ObservableList<Servidor> data = FXCollections.observableArrayList();
       List<Servidor> listaServidores = new ArrayList<Servidor>();
       listaServidores = servidorDAO.listAll();
       tableServidores.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       tableServidores.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("siape"));
       tableServidores.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cargo"));
       tableServidores.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("classe"));
      
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
       tableServidores.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("editar"));
       //tableServidores.getColumns().get(4).setCellFactory(cellFactory);
        
       for (Servidor e : listaServidores) {
           System.out.println(e.toString());
           data.add(e);
       }
       tableServidores.setItems(data);
              
    }
}
