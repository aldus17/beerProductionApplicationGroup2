/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.presentation.chooseuser;

import com.mycompany.presentation.breweryWorker.BrewWorker_UI_Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class ChooseViewController implements Initializable {

    @FXML
    private Button btn_login;
    @FXML
    private ListView<String> lv_choseServer;
    @FXML
    private ChoiceBox<String> cb_choseView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> viewChoices = FXCollections.observableArrayList("Brewery Worker", "Manager");
        cb_choseView.setItems(viewChoices);
        cb_choseView.setValue("Manager");
        
        ObservableList<String> serverChoices = FXCollections.observableArrayList("localhost", "Machine");
        lv_choseServer.setItems(serverChoices);
    }    

    @FXML
    private void login(ActionEvent event) {
        String view = cb_choseView.getSelectionModel().getSelectedItem();
        String server = lv_choseServer.getSelectionModel().getSelectedItem();
        if(view.equalsIgnoreCase("manager")) {
            //manager view
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/managment/Managment_UI.fxml"));
                Parent managerParent = loader.load();
                Scene managerScene = new Scene(managerParent);
                Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                app_stage.centerOnScreen();
                //app_stage.setMaximized(true);
                app_stage.setScene(managerScene);
                app_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ChooseViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //brewery worker view
            if(server.equalsIgnoreCase("localhost")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/breweryWorker/BrewWorker_UI.fxml"));
                    Parent brewParent = loader.load();
                    BrewWorker_UI_Controller controller = loader.getController();
                    controller.setServer("localhost", 4840);
                    controller.setConsumers();
                    Scene brewScene = new Scene(brewParent);
                    Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    brewParent.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", app_stage.widthProperty().divide(60)));                    
                    app_stage.setScene(brewScene);
                    app_stage.centerOnScreen();
                    app_stage.setMaximized(true);
                    app_stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ChooseViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/breweryWorker/BrewWorker_UI.fxml"));
                    Parent brewParent = loader.load();
                    Scene brewScene = new Scene(brewParent);
                    Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    BrewWorker_UI_Controller controller = loader.getController();
                    controller.setServer("192.168.0.122", 4840);
                    controller.setConsumers();
                    app_stage.centerOnScreen();
                    app_stage.setMaximized(true);
                    app_stage.setScene(brewScene);
                    app_stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ChooseViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
