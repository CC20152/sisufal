/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.controllers.institucional;

import cc20152.sisufal.dao.impl.ClasseDocenteDAO;
import cc20152.sisufal.dao.impl.ServidorDAO;
import cc20152.sisufal.models.ClasseDocente;
import cc20152.sisufal.models.Servidor;
import cc20152.sisufal.util.BotoesLista;
import cc20152.sisufal.util.MaskTextField;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
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
public class ServidorController implements Initializable {
    String pacote = "controllers/institucional/"; //Pacote do controller
    String fxml = "fxml/cadastro/CadastroServidorFXML.fxml"; //Caminho do FXML
    ServidorDAO servidorDAO = new ServidorDAO();
    ClasseDocenteDAO classeDocenteDAO = new ClasseDocenteDAO();
    ObservableList<Servidor> data = FXCollections.observableArrayList();
    
    private Servidor servidor;
    private String tipo;
    
    @FXML
        private Button btnCancelarCadastro;
    @FXML
        private Button btnVoltarLista;
    @FXML
        private TableView<Servidor> tableServidores;
    @FXML
        private TextField txtNome;
    @FXML
        private TextField txtSiape;
    @FXML
        private TextField txtCargo;
    @FXML
        private MaskTextField txtCpf;
    @FXML
        private TextField txtPesquisa;
    @FXML
        private CheckBox chkProfessor;
    @FXML
        private ComboBox<ClasseDocente> cmbClasse;
    @FXML
        private ComboBox<String> cmbPesquisa;
    
    
    @FXML
    private void novoServidor (ActionEvent event) throws IOException{
        //System.out.println("---------");
        String path = getClass().getResource("").toString();
        path = path.replace(pacote,"");
        URL url =  new URL(path+fxml);
        //System.out.println(url);
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        ServidorController controller = loader.getController();
        controller.setData(this.data);
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
    
    @FXML
    private void habilitarClasse(ActionEvent event){
        cmbClasse.setDisable(!chkProfessor.isSelected());
    }
    
    @FXML
    private void pesquisar(ActionEvent event){
        if(txtPesquisa.getText().equals(""))
            listarGridServidores();
        else{
            listarGridServidoresPesquisa(cmbPesquisa.getValue());
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
        }else if(this.txtSiape.getText().equals("")){
            aviso.setHeaderText("Campo siape não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtCargo.getText().equals("")){
            aviso.setHeaderText("Campo cargo não pode estar vazio");
            aviso.show();
            return ;
        }else if(this.txtCpf.getText().equals("")){
            aviso.setHeaderText("Campo cpf não pode estar vazio");
            aviso.show();
            return ;
        }
        
        Servidor old = this.servidor;
        if(this.servidor == null){
            this.servidor = new Servidor();
        }
        
        
        Servidor servidor = new Servidor();
        servidor.setNome(this.txtNome.getText());
        servidor.setSiape(this.txtSiape.getText());
        servidor.setCargo(this.txtCargo.getText());
        servidor.setCPF(this.txtCpf.getText());
        if(chkProfessor.isSelected())
            servidor.setClasse(this.cmbClasse.getValue());
        
        //System.out.println(servidor.getClasse().getId()+"-"+servidor.getClasse().getNome());
        
        String result = null;

        if(this.tipo == null){
            result = servidorDAO.save(servidor);
        }else{
            servidor.setId(old.getId());
            result = servidorDAO.update(servidor);
        }
                
        if(result.equals("OK")){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Sucesso");
           alerta.setHeaderText("Servidor cadastrado com sucesso!");
           alerta.show();
           
            if(this.tipo == null){
                this.data.add(servidor);
           }else{
                this.data.remove(old);
                this.data.add(servidor);
           }
           
           Stage stage = (Stage) btnCancelarCadastro.getScene().getWindow();
           stage.close();
        }else{
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Erro");
           alerta.setHeaderText("Erro ao cadastrar servidor!");
           alerta.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!url.getPath().contains(this.fxml)){
            tableServidores.setItems(data);
            listarGridServidores();
            listarComboPesquisa();
        }else{
            txtCpf.setMask("NNN.NNN.NNN-NN");
            listarComboClasse();
        }
    }    
    
       public void setData(ObservableList<Servidor> data){
        this.data = data;
    }
    
    private void listarGridServidores(){
       List<Servidor> listaServidores = new ArrayList<Servidor>();
       listaServidores = servidorDAO.listAll();
       tableServidores.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nome"));
       tableServidores.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("siape"));
       tableServidores.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cargo"));
       tableServidores.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("classe"));
      
       TableColumn colEditar = this.tableServidores.getColumns().get(4);
       colEditar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Servidor.class, fxml).new EditarCell();
           }
       });
       
       TableColumn colDeletar = this.tableServidores.getColumns().get(5);
       colDeletar.setCellFactory(new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
           @Override
           public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
               return new BotoesLista(data, Servidor.class, ServidorController.class).new DeletarCell();
           }
       });
       //tableServidores.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("editar"));
       //tableServidores.getColumns().get(4).setCellFactory(cellFactory);
        
       data.setAll(listaServidores);
    }
    
    private void listarComboClasse(){
        List<ClasseDocente> listaClasseDocente = new ArrayList<ClasseDocente>();
        listaClasseDocente = classeDocenteDAO.listAll();
        cmbClasse.getItems().addAll(listaClasseDocente);
        cmbClasse.getSelectionModel().selectFirst();
    }

    private void listarComboPesquisa() {
        ArrayList<String> listaPesquisa = new ArrayList<>();
        listaPesquisa.add("Nome");
        listaPesquisa.add("Siape");
        listaPesquisa.add("Cargo");
        listaPesquisa.add("Classe");
        this.cmbPesquisa.getItems().addAll(listaPesquisa);
        this.cmbPesquisa.getSelectionModel().selectFirst();
    }
    
    
    private void listarGridServidoresPesquisa(String tipo){
        HashMap hashPesquisa = new HashMap();
        hashPesquisa.put("tipo", tipo);
        hashPesquisa.put("texto", txtPesquisa.getText());
        List<Servidor> lista = servidorDAO.listWithParams(hashPesquisa);
        data.setAll(lista);
    }
    
    public void setEditar(String tipo, ObservableList<?> data, Servidor servidor){
        this.tipo = tipo;
        this.data = (ObservableList<Servidor>)data;
        this.servidor = (Servidor) servidor;
        preencherCampos();
    }
    
    private void preencherCampos(){
        this.txtNome.setText(this.servidor.getNome());
        this.txtSiape.setText(this.servidor.getSiape());
        this.txtCpf.setText(this.servidor.getCPF());
        this.txtCargo.setText(this.servidor.getCargo());
        //System.out.println("id:"+this.servidor.getClasse().getId());
        if(this.servidor.getClasse().getId()!=null){
            this.chkProfessor.setSelected(true);
            this.cmbClasse.setDisable(false);
        }
        ObservableList<ClasseDocente> cl = this.cmbClasse.getItems();
        
        int i=0;
        for(ClasseDocente c : cl){
            if(Objects.equals(c.getId(), this.servidor.getClasse().getId())){
                this.cmbClasse.getSelectionModel().select(i);
            }
            i++;
        }
        if(i == 0) this.cmbClasse.getSelectionModel().select(i);
    }
    
    
        public void deletar(Servidor servidor, ObservableList<?> _data){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Atençao!");
        alerta.setHeaderText("Deseja realmente excluir este registro?");
        alerta.setContentText("Você não poderá voltar atrás!");
        Optional<ButtonType> res = alerta.showAndWait();
        if(res.get() == ButtonType.OK){
            servidorDAO.delete(servidor);
            _data.remove(servidor);
        }
    }
    
}
