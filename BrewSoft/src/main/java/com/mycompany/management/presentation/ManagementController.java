package com.mycompany.management.presentation;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.management.domain.Test;
import com.mycompany.management.interfaces.IBatchReportGenerate;
import com.mycompany.management.interfaces.IManagermentDomain;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ManagementController implements Initializable {

    @FXML
    private MenuItem mi_Show;
    @FXML
    private AnchorPane ap_CreateBatchOrder;
    @FXML
    private MenuItem mi_ProductionQueue;
    @FXML
    private MenuItem mi_CompletedBatches;
    @FXML
    private MenuItem mi_CreateBatchOrder;
    @FXML
    private AnchorPane ap_ProductionQueueLayout;
    @FXML
    private TableView<Batch> tw_SearchTableProductionQueue;
    @FXML
    private TableColumn<Batch, String> tc_ProductionQueue_BatchID;
    @FXML
    private TableColumn<Batch, String> tc_ProductionQueue_DateOfCreation;
    @FXML
    private TableColumn<Batch, String> tc_ProductionQueue_Amount;
    @FXML
    private TableColumn<Batch, String> tc_ProductionQueue_Type;
    @FXML
    private TableColumn<Batch, String> tc_ProductionQueue_Deadline;
    @FXML
    private TableColumn<Batch, String> tc_ProductionQueue_SpeedForProduction;
    @FXML
    private TextField text_SearchProductionQueue;
    @FXML
    private Button btn_SearchProductionQueue;
    @FXML
    private AnchorPane ap_CompletedBatchesLayout;
    @FXML
    private TableView<Batch> tw_SearchTableCompletedBatches;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_batchID;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_MacineID;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_Type;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_DateOfCreation;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_Deadline;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_DateOfCompletion;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_SpeedForProduction;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_TotalAmount;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_GoodAmount;
    @FXML
    private TableColumn<Batch, String> tc_CompletedBatches_DefectAmount;
    @FXML
    private TextField text_SearchCompletedBarches;
    @FXML
    private Button btn_SearchCompletedBatches;
    @FXML
    private Button btn_GenerateBatchreportCompletedBatches; // TODO Consider delete, attributte is never used
    @FXML
    private Button btn_CreateBatchOrder_Create; // TODO Consider delete, attributte is never used
    @FXML
    private TextField textf_CreateBatchOrder_AmountToProduces;
    @FXML
    private TextField textf_CreateBatchOrder_TypeofProduct;
    @FXML
    private TextField textf_CreateBatchOrder_Speed;
    @FXML
    private DatePicker dp_CreateBatchOrder;
    @FXML
    private DatePicker dp_ShowOEE;
    @FXML
    private Button btn_ShowOEE_GenerateOEE; // TODO Consider delete, attributte is never used
    @FXML
    private TextArea Texta_ShowOEE_Text;
    @FXML
    private AnchorPane ap_ShowOEE;

    IManagermentDomain imd = new Test();
    IBatchReportGenerate ibrg;

    private List<Batch> batches;
    private ObservableList<Batch> batcheObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        batcheObservableList = FXCollections.observableArrayList();
        InitializeObservableBatchList();
        InitializeObservableQueueList();

        ap_CompletedBatchesLayout.setVisible(false);
        ap_CreateBatchOrder.setVisible(false);
        ap_ShowOEE.setVisible(false);
        ap_ProductionQueueLayout.setVisible(true);
        ap_ProductionQueueLayout.toFront();
    }

    @FXML
    private void MenuItemChangesAction(ActionEvent event) {
        if (event.getSource() == mi_ProductionQueue) {
            ap_ProductionQueueLayout.setVisible(true);
            ap_ProductionQueueLayout.toFront();
            ap_CompletedBatchesLayout.setVisible(false);
            ap_CreateBatchOrder.setVisible(false);
            ap_ShowOEE.setVisible(false);
        }
        if (event.getSource() == mi_CompletedBatches) {
            ap_CompletedBatchesLayout.setVisible(true);
            ap_CompletedBatchesLayout.toFront();
            ap_ProductionQueueLayout.setVisible(false);
            ap_CreateBatchOrder.setVisible(false);
            ap_ShowOEE.setVisible(false);
        }
        if (event.getSource() == mi_CreateBatchOrder) {
            ap_CreateBatchOrder.setVisible(true);
            ap_CreateBatchOrder.toFront();
            ap_ProductionQueueLayout.setVisible(false);
            ap_CompletedBatchesLayout.setVisible(false);
            ap_ShowOEE.setVisible(false);
        }
        if (event.getSource() == mi_Show) {
            ap_ProductionQueueLayout.setVisible(false);
            ap_CompletedBatchesLayout.setVisible(false);
            ap_CreateBatchOrder.setVisible(false);
            ap_ShowOEE.setVisible(true);
            Texta_ShowOEE_Text.appendText("Date            | OEE in Procentes \n");
            ap_ShowOEE.toFront();
        }
    }

    @FXML
    private void OnSearchAction(ActionEvent event) {
        
        batcheObservableList.clear();
        
        if (event.getSource() == btn_SearchCompletedBatches) {
            batches = imd.BatchObjects("CompletedBatches", text_SearchCompletedBarches.getText());
            
        }
        
        if (event.getSource() == btn_SearchProductionQueue) {
            batches = imd.BatchObjects("BatchesinQueue", text_SearchProductionQueue.getText());
        }
        
        for (Batch batch : batches) {
            batcheObservableList.add(batch);
        }
        
        tw_SearchTableCompletedBatches.refresh();
    }

    @FXML
    private void GeneratingBatchreportAction(ActionEvent event) {
        tw_SearchTableCompletedBatches.getSelectionModel().getSelectedItem();
        ibrg.GeneratePDFDocument(); // TODO Find out witch part the batch should be found on.
    }

    @FXML
    private void CreateBatchAction(ActionEvent event) {
        int typeofProduct = Integer.parseInt(textf_CreateBatchOrder_TypeofProduct.getText());
        int amounttoProduce = Integer.parseInt(textf_CreateBatchOrder_AmountToProduces.getText());
        float speed = Float.parseFloat(textf_CreateBatchOrder_Speed.getText());
        LocalDate deadline = dp_CreateBatchOrder.getValue();

        imd.CreateBatch(typeofProduct, amounttoProduce, speed, deadline);
    }

    @FXML
    private void GenerateOEEAction(ActionEvent event) {
        LocalDate dateToCreateOEE = dp_ShowOEE.getValue();
        double oee = imd.CalulateOEE(dateToCreateOEE);
        
        Texta_ShowOEE_Text.appendText(dateToCreateOEE.toString());
        Texta_ShowOEE_Text.appendText(" | ");
        Texta_ShowOEE_Text.appendText(String.valueOf(oee) + "\n");
    }

    private void InitializeObservableBatchList() {
        tw_SearchTableCompletedBatches.setPlaceholder(new Label());
        tw_SearchTableCompletedBatches.setItems(batcheObservableList);

        tc_CompletedBatches_batchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_CompletedBatches_MacineID.setCellValueFactory(callData -> callData.getValue().getMachineID());
        tc_CompletedBatches_Type.setCellValueFactory(callData -> callData.getValue().getType());
        tc_CompletedBatches_DateOfCreation.setCellValueFactory(callData -> callData.getValue().getDateofCreation());
        tc_CompletedBatches_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_CompletedBatches_DateOfCompletion.setCellValueFactory(callData -> callData.getValue().getDateofCompletion());
        tc_CompletedBatches_SpeedForProduction.setCellValueFactory(callData -> callData.getValue().getSpeedforProduction());
        tc_CompletedBatches_TotalAmount.setCellValueFactory(callData -> callData.getValue().getTotalAmount());
        tc_CompletedBatches_GoodAmount.setCellValueFactory(callData -> callData.getValue().getGoodAmount());
        tc_CompletedBatches_DefectAmount.setCellValueFactory(callData -> callData.getValue().getDefectAmount());

    }

    private void InitializeObservableQueueList() {
        tw_SearchTableProductionQueue.setPlaceholder(new Label());
        tw_SearchTableProductionQueue.setItems(batcheObservableList);

        tc_ProductionQueue_BatchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_ProductionQueue_Type.setCellValueFactory(callData -> callData.getValue().getType());
        tc_ProductionQueue_DateOfCreation.setCellValueFactory(callData -> callData.getValue().getDateofCreation());
        tc_ProductionQueue_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_ProductionQueue_SpeedForProduction.setCellValueFactory(callData -> callData.getValue().getSpeedforProduction());
        tc_ProductionQueue_Amount.setCellValueFactory(callData -> callData.getValue().getTotalAmount());
    }
}
