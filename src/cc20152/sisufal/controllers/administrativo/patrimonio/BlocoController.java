/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.administrativo.patrimonio;

import cc20152.sisufal.dao.impl.PatrimonioDAO;
import cc20152.sisufal.dao.impl.BlocoDAO;
import cc20152.sisufal.dao.impl.SalaDAO;
import cc20152.sisufal.controllers.administrativo.patrimonio.*;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.models.Patrimonio;
import cc20152.sisufal.models.Movimentacao;
import cc20152.sisufal.models.Bloco;
import cc20152.sisufal.models.Sala;
import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.sun.prism.impl.Disposer.Record;
import java.util.Objects;
import java.util.Optional;
import javafx.scene.control.ButtonType;


/**
 *
 * @author Gabriel Fabrício
 */
public class BlocoController implements Initializable {
    private BlocoDAO blocoDAO = new BlocoDAO();
    String pacote = "controllers/administrativo/patrimonio/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroBlocoFXML.fxml";
    ObservableList<Bloco> data = FXCollections.observableArrayList();
    
    private String tipo;
    private Bloco bloco;

    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TextField txtNome;
    @FXML
        private TextField txtNumero;
    @FXML
        private TableView<Bloco> tableBloco;  
    @FXML
        private TextField txtPesquisa;
    @FXML
        private ComboBox<String> cmbPesquisa; 
    
        
    @FXML
    private void pesquisar(ActionEvent event){
        if(txtPesquisa.getText().equals(""))
            listarGridBloco();
        else{
            listarGridBlocoPesquisa(cmbPesquisa.getValue());
        }
    }
    
    @FXML
    private void novoBloco (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Sala");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void cadastrarPatrimonio (ActionEvent event){
        
    }

    @FXML
    private void btnSalvar(ActionEvent event) {
        
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo nome não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtNumero.getText().equals("")){
            aviso.setHeaderText("Campo numero não pode estar vazio");
            aviso.show();
            return ;
        }

        Bloco old = this.bloco;
        if(this.bloco == null){
            this.bloco = new Bloco();
        }

        Bloco bloco = new Bloco();
        bloco.setNome(this.txtNome.getText());
        bloco.setCodigo(this.txtNumero.getText());
        
        String result = null;

        if(this.tipo == null){
            result = blocoDAO.save(bloco);
        }else{
            bloco.setId(old.getId());
            result = blocoDAO.update(bloco);
        }

        System.out.println(result);
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Bloco cadastrado com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(bloco);
           }
           else{
                this.data.remove(old);
                this.data.add(bloco);
           }

           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();           
        }

        else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao cadastrar bloco!");
           alerta.show();
        }
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
        if(!url.getPath().contains(this.fxml)){
            listarGridBloco();
        }       
    }
    /*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtNome.setTooltip(new Tooltip("Digite o nome do patrimônio"));
        Bloco c = new Bloco();
        Sala g = new Sala();
        c.setId(0); c.setNome("Escolha um bloco");
        
        this.cmbBloco.getItems().add(c);
        this.cmbBloco.getItems().addAll(new BlocoDAO().listAll());
        this.cmbSala.getItems().add(g);
        this.cmbSala.getItems().addAll(new blocoDAO().listAll());
        this.cmbBloco.getSelectionModel().selectFirst();
        this.cmbSala.getSelectionModel().selectFirst();
        
        if(!url.getPath().contains(this.fxml)){
            listarGridPatrimonio();
        }
    }    
    */
    private void listarComboPesquisa() {
        ArrayList<String> listaPesquisa = new ArrayList<>();
        listaPesquisa.add("Nome");
        listaPesquisa.add("Numero");
        this.cmbPesquisa.getItems().addAll(listaPesquisa);
        this.cmbPesquisa.getSelectionModel().selectFirst();
    }

    private void listarGridBlocoPesquisa(String tipo){
        HashMap hashPesquisa = new HashMap();
        hashPesquisa.put("tipo", tipo);
        hashPesquisa.put("texto", txtPesquisa.getText());
        List<Bloco> lista = blocoDAO.listWithParams(hashPesquisa);
        data.setAll(lista);
    }

    private void listarGridBloco(){
       List<Bloco> listaBloco = new ArrayList<>();
       listaBloco = new BlocoDAO().listAll();
       this.tableBloco.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       this.tableBloco.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("codigo"));
       
       TableColumn colEditar = this.tableBloco.getColumns().get(2);
       colEditar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Bloco.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableBloco.getColumns().get(3);
       colDeletar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Bloco.class, BlocoController.class).new DeletarCell();
           }
       });
      
       for (Bloco e : listaBloco) {
           data.add(e);
       }
       
       tableBloco.setItems(data);
    }

    public void setData(ObservableList<Bloco> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Bloco bloco){
        this.tipo = tipo;
        this.data = (ObservableList<Bloco>)data;
        this.bloco = (Bloco) bloco;
        preencherCampos();
    }

    public void deletar(Bloco bloco, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            BlocoDAO blocoDAO = new BlocoDAO();
            blocoDAO.delete(bloco);
            _data.remove(bloco);
        }
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.bloco.getNome());
        this.txtNumero.setText(this.bloco.getCodigo());

            
    }

    
}
