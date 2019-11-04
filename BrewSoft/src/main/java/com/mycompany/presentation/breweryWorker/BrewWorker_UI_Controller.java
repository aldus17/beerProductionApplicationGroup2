/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.presentation.breweryWorker;

//import com.mycompany.brewsoft.breweryWorker.domain.machineControls.MachineController;
//import com.mycompany.brewsoft.breweryWorker.domain.interfaces.IBrewerDomain;
import com.mycompany.domain.breweryWorker.MachineController;
import com.mycompany.domain.breweryWorker.MachineSubscriber;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import com.mycompany.domain.breweryWorker.interfaces.IMachineControl;
import com.mycompany.domain.breweryWorker.interfaces.IMachineSubscribe;
import java.util.function.Consumer;
import javafx.application.Platform;

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
 
    //Label Machine specific
    @FXML
    private Label lbl_StopReason, lbl_State, lbl_MaintenancePercent;
    
    //ProgressBar
    @FXML
    private ProgressBar pb_Maintenance;
    
    IMachineControl controls = new MachineController();
    IMachineSubscribe subscriber = new MachineSubscriber();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Consumer<String> barleyUpdater = text -> Platform.runLater(()-> lbl_Barley.setText(text));
        Consumer<String> hopsUpdater = text -> Platform.runLater(()-> lbl_Hops.setText(text));
        Consumer<String> maltUpdater = text -> Platform.runLater(()-> lbl_Malt.setText(text));
        Consumer<String> wheatUpdater = text -> Platform.runLater(()-> lbl_Wheat.setText(text));
        Consumer<String> yeastUpdater = text -> Platform.runLater(()-> lbl_Yeast.setText(text));
        
        Consumer<String> temperatureUpdater = text -> Platform.runLater(()-> lbl_Temprature.setText(text));
        Consumer<String> batchIdUpdater = text -> Platform.runLater(()-> lbl_BatchID.setText(text));
        Consumer<String> producedUpdater = text -> Platform.runLater(()-> lbl_Produced.setText(text));
        Consumer<String> humidityUpdater = text -> Platform.runLater(()-> lbl_Humidity.setText(text));
        Consumer<String> totalProductsUpdater = text -> Platform.runLater(()-> lbl_TotalProducts.setText(text));
        Consumer<String> acceptableUpdater = text -> Platform.runLater(()-> lbl_Acceptable.setText(text));
        Consumer<String> vibrationUpdater = text -> Platform.runLater(()-> lbl_Vibration.setText(text));
        Consumer<String> productsPrMinuteUpdater = text -> Platform.runLater(()-> lbl_ProductsPrMinute.setText(text));
        Consumer<String> stopReasonUpdater = text -> Platform.runLater(()-> lbl_StopReason.setText(subscriber.stopReasonTranslator(text)));
        Consumer<String> stateUpdater = text -> Platform.runLater(()-> lbl_State.setText(subscriber.stateTranslator(text)));
        Consumer<String> defectUpdater = text -> Platform.runLater(()-> lbl_Defect.setText(text));
        
        Consumer<String> maintenanceCounterUpdater = text -> Platform.runLater(()-> {
            pb_Maintenance.setProgress(Double.valueOf(text)/30000);
            lbl_MaintenancePercent.setText(String.valueOf((Double.valueOf(text)/30000)*100) + "%");
        });
                         
        
        subscriber.setConsumer(batchIdUpdater, subscriber.BATCHID_NODENAME);
        subscriber.setConsumer(temperatureUpdater, subscriber.TEMPERATURE_NODENAME);
        subscriber.setConsumer(producedUpdater, subscriber.PRODUCED_PRODUCTS_NODENAME);
        subscriber.setConsumer(humidityUpdater, subscriber.HUMIDITY_NODENAME);
        subscriber.setConsumer(totalProductsUpdater, subscriber.TOTAL_PRODUCTS_NODENAME);
        subscriber.setConsumer(acceptableUpdater, subscriber.ACCEPTABLE_PRODUCTS_NODENAME);
        subscriber.setConsumer(vibrationUpdater, subscriber.VIBRATION_NODENAME);
        subscriber.setConsumer(productsPrMinuteUpdater, subscriber.PRODUCTS_PR_MINUTE_NODENAME);
        
        subscriber.setConsumer(stopReasonUpdater, subscriber.STOP_REASON_NODENAME);
        subscriber.setConsumer(stateUpdater, subscriber.STATE_CURRENT_NODENAME);
        subscriber.setConsumer(defectUpdater, subscriber.DEFECT_PRODUCTS_NODENAME);
        
        subscriber.setConsumer(barleyUpdater, subscriber.BARLEY_NODENAME);
        subscriber.setConsumer(hopsUpdater, subscriber.HOPS_NODENAME);
        subscriber.setConsumer(maltUpdater, subscriber.MALT_NODENAME);
        subscriber.setConsumer(wheatUpdater, subscriber.WHEAT_NODENAME);
        subscriber.setConsumer(yeastUpdater, subscriber.YEAST_NODENAME);
        
        subscriber.setConsumer(maintenanceCounterUpdater, subscriber.MAINTENANCE_COUNTER_NODENAME);
        
        
        subscriber.subscribe();
    }    

    @FXML
    private void OnControlAction(ActionEvent event) {
        
        if(event.getSource()==btn_Start){
            controls.startProduction(1, 1, 11000, 100);
        } else if(event.getSource() == btn_Reset){
            
            controls.resetMachine();
            lbl_Produced.setText("0");
        } else if(event.getSource()== btn_Clear){
            controls.clearState();
        } else if(event.getSource()==btn_Stop){
            controls.stopProduction();
        } else if(event.getSource()==btn_Abort){
            controls.abortProduction();
        }
    }
    
}
