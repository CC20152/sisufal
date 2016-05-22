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
public class SalaController implements Initializable {
    private SalaDAO salaDAO = new SalaDAO();
    String pacote = "controllers/administrativo/patrimonio/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroSalaFXML.fxml";
    ObservableList<Sala> data = FXCollections.observableArrayList();
    
    private String tipo;
    private Sala sala;

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
        private TableView<Sala> tableSala;   
    
    @FXML
    private void btnBuscar(ActionEvent event){
        SalaDAO salaDAO = new SalaDAO();
        Sala sala = new Sala();
        
        if(!this.txtNome.getText().equals("")){
            sala.setNome(this.txtNome.getText());
        }
        
        if(this.cmbBloco.getSelectionModel() != null && !this.cmbBloco.getSelectionModel().isSelected(0)){
            sala.setBloco(this.cmbBloco.getValue());
            //sala.setNomeBloco(this.cmbBloco.getValue().getNome());
        }
        /*
        if(this.cmbSala.getSelectionModel() != null && !this.cmbSala.getSelectionModel().isSelected(0)){
            sala.getSala().setId(this.cmbSala.getValue().getId());
        }
        */
        List<Sala> list = salaDAO.listWithParams(sala);
        
        this.data.clear();
        this.data.addAll(list);
    }
    
    @FXML
    private void novaSala (ActionEvent event) throws IOException{
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
        }else if(this.cmbBloco.getValue() == null){
            aviso.setHeaderText("Campo bloco não pode estar vazio");
            aviso.show();
            return ;
        }

        Sala old = this.sala;
        if(this.sala == null){
            this.sala = new Sala();
        }
        Sala sala = new Sala();
        sala.setNome(this.txtNome.getText());
        sala.setCodigo(this.txtNumero.getText());
        sala.setBloco(this.cmbBloco.getValue());
        //sala.setNomeBloco(this.cmbBloco.getValue().getNome());
        this.cmbBloco.getValue().addSalas(sala);

        String result = null;

        if(this.tipo == null){
            result = salaDAO.save(sala);
        }else{
            sala.setId(old.getId());
            result = salaDAO.update(sala);
        }

        System.out.println(result);
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           

           if(this.tipo == null){
                alerta.setHeaderText("Sala cadastrada com sucesso!");
                alerta.show();
                this.data.add(sala);
           }else{
                alerta.setHeaderText("Sala editada com sucesso!");
                alerta.show();
                this.data.remove(old);
                this.data.add(sala);
           }

           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao cadastrar sala!");
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
            listarGridSala();
        }
        else{
            listarComboBloco();           
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
        this.cmbBloco.getSelectionModel();
    }
    
    private void listarGridSala(){
       List<Sala> listaSala = new ArrayList<>();
       listaSala = new SalaDAO().listAll();
       this.tableSala.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       this.tableSala.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("codigo"));
       this.tableSala.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("bloco"));
      
       TableColumn colEditar = this.tableSala.getColumns().get(3);
       colEditar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Sala.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableSala.getColumns().get(4);
       colDeletar.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
           @Override
           public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
               return new BotoesLista(data, Sala.class, SalaController.class).new DeletarCell();
           }
       });
      
       for (Sala e : listaSala) {
           data.add(e);
       }
       
       tableSala.setItems(data);
    }

    public void setData(ObservableList<Sala> data){
        this.data = data;
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Sala sala){
        this.tipo = tipo;
        this.data = (ObservableList<Sala>)data;
        this.sala = (Sala) sala;
        preencherCampos();
    }

    public void deletar(Sala sala, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            SalaDAO salaDAO = new SalaDAO();
            salaDAO.delete(sala);
            _data.remove(sala);
        }
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.sala.getNome());
        this.txtNumero.setText(this.sala.getCodigo());

    }

    
}
