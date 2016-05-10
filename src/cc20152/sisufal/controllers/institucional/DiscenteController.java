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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 *
 * @author AtaideAl
 */
public class DiscenteController implements Initializable {
    private DiscenteDAO discenteDAO = new DiscenteDAO();
    String pacote = "controllers/institucional/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroDiscenteFXML.fxml"; //Caminho do FXML
    ObservableList<Discente> data = FXCollections.observableArrayList();
    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TextField txtNome;
    @FXML
        private TextField txtMatricula;
    @FXML
        private TextField txtCPF;
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
    
    
    @FXML
    private void novoDiscente (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro Discente");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void pesquisar(ActionEvent event){
        if(txtPesquisa.getText().equals(""))
            listarGridDiscentes();
        else{
            listarGridDiscentesPesquisa(cmbPesquisa.getValue());
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
        Discente discente = new Discente();
        discente.setNome(this.txtNome.getText());
        discente.setMatricula(this.txtMatricula.getText());
        discente.setCurso(this.cmbCurso.getValue());
        discente.setPeriodoIngresso(this.cmbPeriodo.getValue());
        discente.setCpf(this.txtCPF.getText());
        
        //System.out.println(servidor.getClasse().getId()+"-"+servidor.getClasse().getNome());
        
        String result = discenteDAO.save(discente);
        System.out.println(result);
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Discente cadastrado com sucesso!");
           alerta.show();
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
      
       //tableDiscentes.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("editar"));
       //tableServidores.getColumns().get(4).setCellFactory(cellFactory);
        
       data.setAll(listaDiscentes);
    }
    
    private void listarComboPesquisa() {
        ArrayList<String> listaPesquisa = new ArrayList<>();
        listaPesquisa.add("Nome");
        listaPesquisa.add("Matricula");
        listaPesquisa.add("Curso");
        listaPesquisa.add("Periodo");
        this.cmbPesquisa.getItems().addAll(listaPesquisa);
        this.cmbPesquisa.getSelectionModel().selectFirst();
    }
    
    private void listarGridDiscentesPesquisa(String tipo){
//        HashMap hashPesquisa = new HashMap();
//        hashPesquisa.put("tipo", tipo);
//        hashPesquisa.put("texto", txtPesquisa.getText());
//        List<Servidor> lista = servidorDAO.listWithParams(hashPesquisa);
//        data.setAll(lista);
    }
}
