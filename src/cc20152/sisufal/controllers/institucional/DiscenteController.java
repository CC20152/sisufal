/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.institucional;

import cc20152.sisufal.dao.impl.CursoDAO;
import cc20152.sisufal.dao.impl.DiscenteDAO;
import cc20152.sisufal.dao.impl.PeriodoDAO;
import cc20152.sisufal.models.Curso;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.Periodo;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.MaskTextField;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author AtaideAl
 */
public class DiscenteController implements Initializable {
    private DiscenteDAO discenteDAO = new DiscenteDAO();
    String pacote = "controllers/institucional/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroDiscenteFXML.fxml"; //Caminho do FXML
    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TextField txtNome;
    @FXML
        private TextField txtMatricula;
    @FXML
        private MaskTextField txtCPF;
    @FXML
        private TextField txtPesquisa;
    @FXML
        private ComboBox<Periodo> cmbPeriodo;
    @FXML
        private ComboBox<Curso> cmbCurso;
    @FXML
        private ComboBox<String> cmbPesquisa;
    
    @FXML
        private TableView<Discente> tableDiscentes;
    
    ObservableList<Discente> data = FXCollections.observableArrayList();
    
    private Discente discente;
    private String tipo;
    
    
    @FXML
    private void novoDiscente (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        DiscenteController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Discente");
        stage.setScene(scene);
        stage.show();
    }
    
    public void setData(ObservableList<Discente> data){
        this.data = data;
    }
    
    @FXML
    private void pesquisar(ActionEvent event){
        if(txtPesquisa.getText().equals(""))
            listarGridDiscentes();
        else{
            listarGridDiscentesPesquisa();
        }
    }
    
    @FXML
    private void btnSalvar(ActionEvent event) {
        
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtNome.getText().equals("")){
            aviso.setHeaderText("Campo nome não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtMatricula.getText().equals("")){
            aviso.setHeaderText("Campo matricula não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtCPF.getText().equals("")){
            aviso.setHeaderText("Campo cpf não pode estar vazio");
            aviso.show();
            return ;
        }
        
        
        Discente old = this.discente;
        if(this.discente == null){
            this.discente = new Discente();
        }
        
        Discente discente = new Discente();
        discente.setNome(this.txtNome.getText());
        discente.setMatricula(this.txtMatricula.getText());
        discente.setCurso(this.cmbCurso.getValue());
        discente.setPeriodoIngresso(this.cmbPeriodo.getValue());
        discente.setCpf(this.txtCPF.getText());
        
        //System.out.println(servidor.getClasse().getId()+"-"+servidor.getClasse().getNome());
         String result = null;

        if(this.tipo == null){
            result = discenteDAO.save(discente);
        }else{
            discente.setId(old.getId());
            result = discenteDAO.update(discente);
        }
        
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Discente cadastrado com sucesso!");
           alerta.show();
           
           if(this.tipo == null){
                this.data.add(discente);
           }else{
                this.data.remove(old);
                this.data.add(discente);
           }
           
           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao cadastrar discente!");
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
            tableDiscentes.setItems(data);
            listarGridDiscentes();
            listarComboPesquisa();
        }else{
            txtCPF.setMask("NNN.NNN.NNN-NN");
            listarComboPeriodo();
            listarComboCurso();
        }
       
    }    

    private void listarComboPeriodo() {
        List<Periodo> listaPeriodo = new ArrayList<>();
        listaPeriodo = new PeriodoDAO().listAll();
        this.cmbPeriodo.getItems().setAll(listaPeriodo);
        this.cmbPeriodo.getSelectionModel().selectFirst();
    }

    private void listarComboCurso() {
        List<Curso> listaCurso= new ArrayList<>();
        listaCurso = new CursoDAO().listAll();
        this.cmbCurso.getItems().setAll(listaCurso);
        this.cmbCurso.getSelectionModel().selectFirst();
    }
    
    private void listarGridDiscentes(){
       List<Discente> listaDiscentes = new ArrayList<>();
       listaDiscentes = discenteDAO.listAll();
       tableDiscentes.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       tableDiscentes.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("matricula"));
       tableDiscentes.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("curso"));
       tableDiscentes.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("periodoIngresso"));
       tableDiscentes.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Editar"));
       tableDiscentes.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Deletar"));
       
        TableColumn colEditar = this.tableDiscentes.getColumns().get(4);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Discente.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableDiscentes.getColumns().get(5);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Discente.class, DiscenteController.class).new DeletarCell();
           }
       });
       
       
       data.setAll(listaDiscentes);
    }
    
    private void listarComboPesquisa() {
        ArrayList<String> listaPesquisa = new ArrayList<>();
        listaPesquisa.add("Nome");
        listaPesquisa.add("Matrícula");
        listaPesquisa.add("Curso");
        listaPesquisa.add("Periodo");
        this.cmbPesquisa.getItems().addAll(listaPesquisa);
        this.cmbPesquisa.getSelectionModel().selectFirst();
    }
    
    private void listarGridDiscentesPesquisa(){
        Discente d = new Discente();
         if(cmbPesquisa.getValue().equals("Nome")){
            d.setNome(txtPesquisa.getText());
        }else if(cmbPesquisa.getValue().equals("Matrícula")){
            d.setMatricula(txtPesquisa.getText());
        }if(cmbPesquisa.getValue().equals("Curso")){
            d.getCurso().setNome(txtPesquisa.getText());
        }if(cmbPesquisa.getValue().equals("Periodo")){
            d.getPeriodoIngresso().setNome(txtPesquisa.getText());
        }
        List<Discente> lista = discenteDAO.listWithParams(d);
        data.setAll(lista);
    }
    
    public void deletar(Discente discente, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            String result = discenteDAO.delete(discente);
            if(result.equals("OK"))
                _data.remove(discente);
            else{
		//lol
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Erro ao deletar discente!");
                alerta.show();
            }
        }
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Discente discente){
        this.tipo = tipo;
        this.data = (ObservableList<Discente>)data;
        this.discente = (Discente) discente;
        preencherCampos();
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.discente.getNome());
        this.txtMatricula.setText(this.discente.getMatricula());
        this.txtCPF.setText(this.discente.getCpf());
        ObservableList<Curso> cl = this.cmbCurso.getItems();
        ObservableList<Periodo> pr = this.cmbPeriodo.getItems();
        int i=0;
        for(Curso c : cl){
            if(Objects.equals(c.getId(), this.discente.getCurso().getId())){
                this.cmbCurso.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbCurso.getSelectionModel().select(i);
        i=0;
        for(Periodo p : pr){
            if(Objects.equals(p.getId(), this.discente.getPeriodoIngresso().getId())){
                this.cmbPeriodo.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbPeriodo.getSelectionModel().select(i);
    }
    
    
}
