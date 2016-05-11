/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.util;

import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;

/**
 *
 * @author Dayvson
 */
public class BotoesLista {
    
    private ObservableList<?> data;
    private Class<?> classe;
    private Class<?> classeDAO;
    private Class<?> controller;
    private String fxml;
    private String pacote = "util/";
  
    public BotoesLista(ObservableList<?> data, Class<?> object, String fxml){
        this.data = data;
        this.classe = object;
        this.fxml = fxml;
    }
    
    public BotoesLista(ObservableList<?> data, Class<?> object, Class<?> controller){
        this.data = data;
        this.classe = object;
        this.controller = controller;
    }
    
    public class EditarCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Editar");
        public EditarCell(){
            cellButton.setOnAction((ActionEvent t) -> {
                Object obj = classe.cast(EditarCell.this.getTableView().getItems().get(EditarCell.this.getIndex()));
                String path = getClass().getResource("").toString();
                path = path.replace(pacote, "");
                try {
                    URL url = new URL(path + fxml);
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent root = (Parent)loader.load();
                    controller = loader.getController().getClass();
                    Method mth = controller.getMethod("setEditar", String.class, ObservableList.class, classe);
                    mth.invoke(loader.getController(), "editar", data, obj);
                    Scene scene1 = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Editar");
                    stage.setScene(scene1);
                    stage.show();
                }catch(IOException e){}catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(BotoesLista.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }else{
                setGraphic(null);
            }
        }
    }
    
     public class DeletarCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Deletar");
        public DeletarCell(){
            cellButton.setOnAction((ActionEvent t) -> {
                Object obj = classe.cast(DeletarCell.this.getTableView().getItems().get(DeletarCell.this.getIndex()));
                try {
                    Method mth = controller.getMethod("deletar", classe, ObservableList.class);
                    mth.invoke(controller.newInstance(), obj, data);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException ex) {
                    Logger.getLogger(BotoesLista.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }else{
                setGraphic(null);
            }
        }
    }
    
}
