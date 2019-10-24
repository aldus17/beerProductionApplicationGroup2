/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.breweryWorker.presentation;

//import com.mycompany.brewsoft.breweryWorker.domain.machineControls.MachineController;
//import com.mycompany.brewsoft.breweryWorker.domain.interfaces.IBrewerDomain;
import com.mycompany.brewsoft.breweryWorker.domain.interfaces.IBrewerDomain;
import com.mycompany.breweryWorker.domain.machineControls.MachineController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author jacob
 */
public class BrewWorker_UI_Controller implements Initializable {
    
    //Buttons
    @FXML
    private Button btn_Start, btn_Reset, btn_Clear, btn_Stop, btn_Abort;
    
    //Label Ingredients
    @FXML
    private Label lbl_Barley, lbl_Hops, lbl_Malt, lbl_Wheat, lbl_Yeast;
    
    //Label Production data
    @FXML
    private Label lbl_Temprature, lbl_BatchID, lbl_Produced, lbl_Humidity, lbl_TotalProducts, lbl_Acceptable,
            lbl_Vibration,lbl_ProductsPrMinute, lbl_Defect;

    //ProgressBar
    @FXML
    private ProgressBar pb_Maintenance;
    
    IBrewerDomain controls = new MachineController();

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnControlAction(ActionEvent event) {
        if(event.getSource()==btn_Start){
            controls.startProduction(1, 1, 100, 100);
        } else if(event.getSource() == btn_Reset){
            controls.resetMachine();
        } else if(event.getSource()== btn_Clear){
            controls.clearState();
        } else if(event.getSource()==btn_Stop){
            controls.stopProduction();
        } else if(event.getSource()==btn_Abort){
            controls.abortProduction();
        }
    }
    
}
