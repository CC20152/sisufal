/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.ensino.concurso;

import cc20152.sisufal.dao.impl.BancaConcursoDAO;
import cc20152.sisufal.dao.impl.ConcursoDAO;
import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.models.BancaConcurso;
import cc20152.sisufal.models.Concurso;
import cc20152.sisufal.models.Orientador;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.relatorio.impl.RelatorioConcurso;
import cc20152.sisufal.util.AutoCompleteComboBoxListener;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.DataUtil;
import cc20152.sisufal.util.OrientadorConverter;
import cc20152.sisufal.util.ServidorConverter;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import org.controlsfx.control.ListSelectionView;

/**
 *
 * @author Dayvson
 */
public class BancaConcursoController implements Initializable {
    
    private ObservableList<Servidor> data;
    
    final String pacote = "controllers/ensino/concurso/"; //Pacote do controller
    final String fxml = "fxml/cadastro/CadastroConcursoFXML.fxml"; //Caminho do FXML
    
    private String tipo;
    
    private Concurso concurso;
    
    @FXML
    private TableView<Servidor> tbBancaConcurso;
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) tbBancaConcurso.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void declaracaoParticipante(ActionEvent event){
        RelatorioConcurso relatorio = new RelatorioConcurso();
        
        if(this.tbBancaConcurso.getSelectionModel().getSelectedIndex() < 0){
            Alert aviso = new Alert(Alert.AlertType.ERROR);
            aviso.setTitle("Erro");
            aviso.setHeaderText("É necessário selecionar um participante da banca");
            aviso.show();
            return ;
        }
        Map parametros = new HashMap();
        parametros.put("EDITAL", this.concurso.getNumeroEdital());
        parametros.put("SUPERVISOR", this.concurso.getSupervisor());
        parametros.put("DATA_INICIO", DataUtil.getDataApresentacao(this.concurso.getDataInicio()));
        parametros.put("DATA_FIM", DataUtil.getDataApresentacao(this.concurso.getDataFim()));
        
        try {
           relatorio.gerarDeclaracao(this.tbBancaConcurso.getSelectionModel().getSelectedItem(), parametros);
        } catch (JRException ex) {
            Logger.getLogger(BancaConcursoController.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(BancaConcursoController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    private void listarBancaConcurso(){
       data = FXCollections.observableArrayList();
       
       
       List<BancaConcurso> bancaConcurso = new BancaConcursoDAO().listWithParams(concurso);
       
       TableColumn colNome = this.tbBancaConcurso.getColumns().get(0);
       colNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Servidor, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Servidor, String> p) {
               return new SimpleObjectProperty(p.getValue().getNome());
           }
       });
       
       TableColumn colPeriodo = this.tbBancaConcurso.getColumns().get(1);
       colPeriodo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Servidor, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Servidor, String> p) {
               return new SimpleObjectProperty(p.getValue().getSiape());
           }
       });
       
       TableColumn colAreaEstudo = this.tbBancaConcurso.getColumns().get(2);
       colAreaEstudo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Servidor, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Servidor, String> p) {
               return new SimpleObjectProperty(p.getValue().getCPF());
           }
       });
       
       TableColumn colDataInicio = this.tbBancaConcurso.getColumns().get(3);
       colDataInicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Servidor, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Servidor, String> p) {
               return new SimpleObjectProperty(p.getValue().getCargo());
           }
       });
       
       for (Servidor e : bancaConcurso.get(0).getListaServidores()) {
           data.add(e);
       }
       
       tbBancaConcurso.setItems(data);       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setInit(String tipo, ObservableList<?> data, Concurso concurso){
        this.tipo = tipo;
        this.concurso = (Concurso) concurso;
        listarBancaConcurso();
    }
}
