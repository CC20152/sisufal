/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.pesquisa.projeto;

import cc20152.sisufal.dao.impl.DiscenteDAO;
import cc20152.sisufal.dao.impl.GrupoProjetoDAO;
import cc20152.sisufal.util.InstituicaoFinanciadoraConverter;
import cc20152.sisufal.dao.impl.InstituicaoFinanciadoraDAO;
import cc20152.sisufal.dao.impl.ProjetoDAO;
import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.models.Discente;
import cc20152.sisufal.models.GrupoProjeto;
import cc20152.sisufal.models.InstituicaoFinanciadora;
import cc20152.sisufal.models.Projeto;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.util.AutoCompleteComboBoxListener;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.DataUtil;
import cc20152.sisufal.util.DiscenteConverter;
import cc20152.sisufal.util.ServidorConverter;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author anderson
 */
public class ProjetoController implements Initializable {
    
    private ObservableList<Projeto> data;
    
    final String pacote = "controllers/pesquisa/projeto/";
    private String fxmlCadastro = "fxml/cadastro/CadastroProjetoFXML.fxml";
    
    private String tipo;
    private Projeto projeto;
    ObservableValue<Integer> grupoId;
    private GrupoProjeto grupoProjeto;
    
    @FXML
    private TextField txtTitulo;
    
    @FXML
    private TextField txtTipo;
    
    @FXML
    private DatePicker datInicio;
    
    @FXML
    private DatePicker datFim;
    
    @FXML
    private ComboBox cmbCoodenador;
    
    @FXML
    private ComboBox cmbFinanciadora;
    
    @FXML
    private Button btnVoltarLista;
    
    @FXML
    private Button btnCancelarCadastro;
    
    @FXML
    private ComboBox cmbServidores;
    
    @FXML
    private ComboBox cmbDiscentes;
    
    @FXML
    private TableView<Projeto> tableProjetos;
    
    @FXML
    private TableView<Servidor> tableDocentes;
    
    @FXML
    private TableView<Discente> tableDiscentes;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(!url.getPath().contains(this.fxmlCadastro)){
            listarProjetos();
        } else {
            listarDadosCadastro();
        }
    }
    
    public void listarDadosCadastro() {
        this.tableDocentes.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn colRemoverDocente = this.tableDocentes.getColumns().get(1);
        colRemoverDocente.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new BotoesLista(tableDocentes.getItems(), Servidor.class, fxmlCadastro, "removerDocente", "Remover", "").new BotaoCell();
            }
        });
        
        this.tableDiscentes.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.tableDiscentes.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("tipo"));
        TableColumn colRemoverDiscente = this.tableDiscentes.getColumns().get(2);
        colRemoverDiscente.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new BotoesLista(tableDiscentes.getItems(), Discente.class, fxmlCadastro, "removerDiscente", "Remover", "").new BotaoCell();
            }
        });
        
        this.cmbCoodenador.getItems().addAll(new ServidorDAO().listAll());
        this.cmbCoodenador.setConverter(new ServidorConverter());
        new AutoCompleteComboBoxListener<>(this.cmbCoodenador, "Escolha um coordenador");

        this.cmbFinanciadora.getItems().addAll(new InstituicaoFinanciadoraDAO().listAll());
        this.cmbFinanciadora.setConverter(new InstituicaoFinanciadoraConverter());
        new AutoCompleteComboBoxListener<>(this.cmbFinanciadora, "Escolha uma instituiçao financiadora");

        List<Servidor> servidores = new ServidorDAO().listAll();
        for (Servidor servidor : servidores) {
            if(!tableDocentes.getItems().contains(servidor)) {
                this.cmbServidores.getItems().add(servidor);
            }
        }
        this.cmbServidores.setConverter(new ServidorConverter());
        new AutoCompleteComboBoxListener<>(this.cmbServidores, "Adicionar Docente");

        this.cmbDiscentes.getItems().addAll(new DiscenteDAO().listAll());
        this.cmbDiscentes.setConverter(new DiscenteConverter());
        new AutoCompleteComboBoxListener<>(this.cmbDiscentes, "Adicionar Discente");
    }
    
    @FXML
    private void btnBuscar(ActionEvent event){

    }
    
    @FXML
    private void adicionarDocente(ActionEvent event){
        if(this.cmbServidores.getValue() != null) {
            Servidor servidor = (Servidor) this.cmbServidores.getValue();
            this.tableDocentes.getItems().add(servidor);
            this.cmbServidores.getSelectionModel().clearSelection();
        }
    }
    
    @FXML
    private void adicionarDiscente(ActionEvent event){

    }
    
    @FXML
    private void novoProjeto (ActionEvent event) throws IOException{
        String path = getClass().getResource("").toString();
        path = path.replace(this.pacote, "");
        URL url =  new URL(path + this.fxmlCadastro);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        ProjetoController controller = loader.getController();
        controller.setData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Projeto");
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @FXML
    private void salvarProjeto(ActionEvent event) throws IOException, SQLException{
        Alert aviso = new Alert(Alert.AlertType.ERROR);
        aviso.setTitle("Erro");
        
        if(this.txtTitulo.getText().equals("")) {
            aviso.setHeaderText("Campo titulo não pode estar vazio");
            aviso.show();
            return ;
        } else if(this.txtTipo.getText().equals("")){
            aviso.setHeaderText("Campo tipo não pode estar vazio");
            aviso.show();
            return ;
        } else if(this.datInicio.getValue() == null){
            aviso.setHeaderText("Campo data de inicio não pode estar vazio");
            aviso.show();
            return ;
        } else if(this.datFim.getValue() == null){
            aviso.setHeaderText("Campo data de fim não pode estar vazio");
            aviso.show();
            return ;
        } else if(this.cmbCoodenador.getValue() == null){
            aviso.setHeaderText("Campo coordenador não pode estar vazio");
            aviso.show();
            return ;
        } else if(this.cmbFinanciadora.getValue() == null){
            aviso.setHeaderText("Campo instituiçao financiadora não pode estar vazio");
            aviso.show();
            return ;
        } 
        
        Projeto old = this.projeto;
        if(this.projeto == null) {
            this.projeto = new Projeto();
        }

        this.projeto.setTitulo(this.txtTitulo.getText());
        this.projeto.setTipo(this.txtTipo.getText());
        this.projeto.setDataInicio(Date.from(this.datInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.projeto.setDataFim(Date.from(this.datFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.projeto.setServidorCoordenador((Servidor) this.cmbCoodenador.getValue());
        this.projeto.setFinanciadora((InstituicaoFinanciadora) this.cmbFinanciadora.getValue());
        
        for (Servidor s : tableDocentes.getItems()){
            this.projeto.getGrupoProjeto().getListaServidores().add(s);
        }
        
        ProjetoDAO projetoDAO = new ProjetoDAO();
        String result = null;
        
        if(this.tipo == null){
            result = projetoDAO.save(projeto);
        }else{
            result = projetoDAO.update(projeto);
        }
        
        System.out.println(result);
        if(!result.equals("-1")){
            if(this.tipo == null) this.data.add(projeto); 
            else this.data.set(this.data.indexOf(projeto), projeto);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sucesso");
            alerta.setHeaderText("Projeto cadastrado com sucesso!");
            alerta.show();
            Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
            stage.close();
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro ao cadastrar projeto!");
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
    
    private void setData(ObservableList<Projeto> data){
        this.data = data;
    }
    
    private void listarProjetos(){
       data = FXCollections.observableArrayList();
       
       List<Projeto> listaProjetos = new ArrayList<>();
       listaProjetos = new ProjetoDAO().listAll();
       
       TableColumn colTitulo = this.tableProjetos.getColumns().get(0);
       colTitulo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projeto, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Projeto, String> p) {
               return new SimpleObjectProperty(p.getValue().getTitulo());
           }
       });
       
       TableColumn colCoordenador = this.tableProjetos.getColumns().get(1);
       colCoordenador.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projeto, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Projeto, String> p) {
               return new SimpleObjectProperty(p.getValue().getServidorCoordenador().getNome());
           }
       });
       
       TableColumn colTipo = this.tableProjetos.getColumns().get(2);
       colTipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projeto, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Projeto, String> p) {
               return new SimpleObjectProperty(p.getValue().getTipo());
           }
       });
       
       TableColumn colDataInicio = this.tableProjetos.getColumns().get(3);
       colDataInicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projeto, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Projeto, String> p) {
               return new SimpleObjectProperty(DataUtil.getDataApresentacao(p.getValue().getDataInicio()));
           }
       });
       
       TableColumn colDataFim = this.tableProjetos.getColumns().get(4);
       colDataFim.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projeto, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Projeto, String> p) {
               return new SimpleObjectProperty(DataUtil.getDataApresentacao(p.getValue().getDataFim()));
           }
       });
       
       TableColumn colEditar = this.tableProjetos.getColumns().get(5);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Projeto.class, fxmlCadastro).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableProjetos.getColumns().get(6);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Projeto.class, ProjetoController.class).new DeletarCell();
           }
       });
      
       for (Projeto p : listaProjetos) {
           data.add(p);
       }
       
       tableProjetos.setItems(data);
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Projeto projeto){
        this.tipo = tipo;
        this.data = (ObservableList<Projeto>)data;
        this.projeto = (Projeto) projeto;
        preencherCampos();
    }
    
    public void deletar(Projeto projeto, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            ProjetoDAO projetoDAO = new ProjetoDAO();
            projetoDAO.delete(projeto);
            _data.remove(projeto);
        }
    }
    
    private void preencherCampos(){
        this.txtTitulo.setText(this.projeto.getTitulo());
        this.txtTipo.setText(this.projeto.getTipo());
        this.datInicio.setValue(new Date(this.projeto.getDataInicio().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.datFim.setValue(new Date(this.projeto.getDataFim().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        this.tableDocentes.setItems(FXCollections.observableArrayList(this.projeto.getGrupoProjeto().getListaServidores()));

        ObservableList<Servidor> servidores = this.cmbCoodenador.getItems();
        int i = 0;
        for(Servidor s : servidores){
            if(Objects.equals(s.getId(), this.projeto.getServidorCoordenador().getId())){
                this.cmbCoodenador.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbCoodenador.getSelectionModel().select(i);
        
        ObservableList<InstituicaoFinanciadora> financiadoras = this.cmbFinanciadora.getItems();
        i = 0;
        for(InstituicaoFinanciadora f : financiadoras){
            if(Objects.equals(f.getId(), this.projeto.getFinanciadora().getId())){
                this.cmbFinanciadora.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbFinanciadora.getSelectionModel().select(i);
    }
    
    public void removerDocente(String tipo, ObservableList<Servidor> servidores, Servidor servidor) {
        servidores.remove(servidor);
    }
}
