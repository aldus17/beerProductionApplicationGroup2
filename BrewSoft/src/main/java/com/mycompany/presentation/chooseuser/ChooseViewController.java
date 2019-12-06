package com.mycompany.presentation.chooseuser;

import com.mycompany.crossCutting.objects.Machine;
import com.mycompany.domain.chooseuser.ChooseUser;
import com.mycompany.domain.chooseuser.interfaces.IChooseUser;
import com.mycompany.presentation.breweryWorker.BrewWorker_UI_Controller;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ChooseViewController implements Initializable {

    @FXML
    private Button btn_login;
    @FXML
    private ChoiceBox<String> cb_choseView;
    @FXML
    private TableView<Machine> tw_pickMachine;
    @FXML
    private TableColumn<Machine, Integer> tc_MachineID;
    @FXML
    private TableColumn<Machine, String> tc_MachineHost;
    @FXML
    private TableColumn<Machine, Integer> tc_MachinePort;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> viewChoices = FXCollections.observableArrayList("Brewery Worker", "Manager");
        cb_choseView.setItems(viewChoices);
        cb_choseView.setValue("Manager");
        IChooseUser cu = new ChooseUser();
        List<Machine> machineList = cu.getMachineList();
        ObservableList<Machine> serverChoices = FXCollections.observableArrayList();
        serverChoices.addAll(machineList);
        System.out.println(serverChoices);
        tw_pickMachine.setItems(serverChoices);
        tc_MachineID.setCellValueFactory(new PropertyValueFactory<>("machineID"));//cellData -> cellData.getValue().getMachineIDProperty());
        tc_MachineHost.setCellValueFactory(cellData -> cellData.getValue().hostnameProperty());
        tc_MachinePort.setCellValueFactory(new PropertyValueFactory<Machine, Integer>("port"));//cellData -> cellData.getValue().getPort());

    }    

    @FXML
    private void login(ActionEvent event) {
        String view = cb_choseView.getSelectionModel().getSelectedItem();
        Machine brewerymachine = tw_pickMachine.getSelectionModel().getSelectedItem();
        if(view.equalsIgnoreCase("manager")) {
            //manager view
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/managment/Managment_UI.fxml"));
                Parent managerParent = loader.load();
                Scene managerScene = new Scene(managerParent);
                Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                app_stage.centerOnScreen();
                app_stage.setScene(managerScene);
                app_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ChooseViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //brewery worker view
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/breweryWorker/BrewWorker_UI.fxml"));
                Parent brewParent = loader.load();
                BrewWorker_UI_Controller controller = loader.getController();
                controller.setMachine(brewerymachine);
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
        }
    }
    
}
