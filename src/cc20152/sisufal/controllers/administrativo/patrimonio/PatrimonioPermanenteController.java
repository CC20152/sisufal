/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.administrativo.patrimonio;

import cc20152.sisufal.dao.impl.PatrimonioPermanenteDAO;
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
public class PatrimonioPermanenteController implements Initializable {
    private PatrimonioPermanenteDAO patrimonioDAO = new PatrimonioPermanenteDAO();
    String pacote = "controllers/administrativo/patrimonio/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroPatrimonioPermanenteFXML.fxml";
    ObservableList<Patrimonio> data = FXCollections.observableArrayList();
    
    private String tipo;
    private Patrimonio patrimonio;

    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TextField txtNome;
    @FXML
        private TextField txtNumero;
    @FXML
        private ComboBox<Bloco> cmbBloco;
    @FXML
        private ComboBox<Sala> cmbSala;
    @FXML
        private TableView<Sala> tableSala;
    @FXML
        private TableView<Patrimonio> tablePatrimonio;    
    
    @FXML
    private void btnBuscar(ActionEvent event){
        PatrimonioPermanenteDAO patrimonioDAO = new PatrimonioPermanenteDAO();
        Patrimonio patrimonio = new Patrimonio();
        
        if(!this.txtNome.getText().equals("")){
            patrimonio.setNome(this.txtNome.getText());
        }
        
        if(this.cmbBloco.getSelectionModel() != null && !this.cmbBloco.getSelectionModel().isSelected(0)){
            patrimonio.setBloco(this.cmbBloco.getValue().getId());
        }
        
        if(this.cmbSala.getSelectionModel() != null && !this.cmbSala.getSelectionModel().isSelected(0)){
            patrimonio.setSala(this.cmbSala.getValue().getId());
        }
        
        List<Patrimonio> list = patrimonioDAO.listWithParams(patrimonio);
        
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void novoPatrimonio (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Patrimônio");
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
        Patrimonio patrimonio = new Patrimonio();
        patrimonio.setNome(this.txtNome.getText());
        patrimonio.setNumero(this.txtNumero.getText());
        patrimonio.setBloco(this.cmbBloco.getValue().getId());
        patrimonio.setSala(this.tableSala.getSelectionModel().getSelectedItem().getId());
        
        String result = patrimonioDAO.save(patrimonio);
        System.out.println(result);
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Patrimônio cadastrado com sucesso!");
           alerta.show();
           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao cadastrar patrimônio!");
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
            listarGridPatrimonio();
        }else{
            listarComboBloco();
            listarTableSala();
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
        this.cmbSala.getItems().addAll(new SalaDAO().listAll());
        this.cmbBloco.getSelectionModel().selectFirst();
        this.cmbSala.getSelectionModel().selectFirst();
        
        if(!url.getPath().contains(this.fxml)){
            listarGridPatrimonio();
        }
    }
    */
    private void listarComboBloco() {
        List<Bloco> listaBloco = new ArrayList<>();
        listaBloco = new BlocoDAO().listAll();
        this.cmbBloco.getItems().addAll(listaBloco);
        //this.cmbBloco.getItems().setAll(listaBloco);
        this.cmbBloco.getSelectionModel().selectFirst();

    }
    /*
    private void listarCombotxtNumero() {
        List<Bloco> listaBloco = new ArrayList<>();
        listaBloco = new BlocoDAO().listAll();
        this.cmbBloco.getItems().setAll(listaBloco);
        this.cmbBloco.getSelectionModel().selectFirst();
    }
    */
    private void listarTableSala() {
        List<Sala> listaSala = this.cmbBloco.getValue().getSalas();
        listaSala = new SalaDAO().listAll();
        this.tableSala.getItems().setAll(listaSala);
        this.tableSala.getSelectionModel().selectFirst();
    }

    private void listarGridPatrimonio(){
       List<Patrimonio> listaPatrimonio = new ArrayList<>();
       listaPatrimonio = new PatrimonioPermanenteDAO().listAll();
       this.tablePatrimonio.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       this.tablePatrimonio.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("numero"));
       this.tablePatrimonio.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("bloco"));
       this.tablePatrimonio.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("sala"));
       TableColumn colEditar = this.tablePatrimonio.getColumns().get(4);
       colEditar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Patrimonio.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tablePatrimonio.getColumns().get(5);
       colDeletar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Patrimonio.class, PatrimonioController.class).new DeletarCell();
           }
       });
      
       for (Patrimonio e : listaPatrimonio) {
           data.add(e);
       }
       
       tablePatrimonio.setItems(data);
    }

    public void setData(ObservableList<Patrimonio> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Patrimonio patrimonio){
        this.tipo = tipo;
        this.data = (ObservableList<Patrimonio>)data;
        this.patrimonio = (Patrimonio) patrimonio;
        preencherCampos();
    }

    public void deletar(Patrimonio patrimonio, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            PatrimonioPermanenteDAO patrimonioDAO = new PatrimonioPermanenteDAO();
            patrimonioDAO.delete(patrimonio);
            _data.remove(patrimonio);
        }
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.patrimonio.getNome());
        this.txtNumero.setText(this.patrimonio.getNumero());
        ObservableList<Bloco> cc = this.cmbBloco.getItems();
        ObservableList<Sala> gg = this.tableSala.getItems();
        int i = 0;
        for(Bloco c : cc){
            if(Objects.equals(c.getId(), this.patrimonio.getBloco())){
                this.cmbBloco.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbBloco.getSelectionModel().select(i);
        for(Sala g : gg){
            if(Objects.equals(g.getId(), this.patrimonio.getSala())){
                this.tableSala.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.tableSala.getSelectionModel().select(i);
    
    }

    
}
