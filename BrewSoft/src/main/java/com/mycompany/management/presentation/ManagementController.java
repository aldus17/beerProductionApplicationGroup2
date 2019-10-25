package com.mycompany.management.presentation;

import com.mycompany.management.interfaces.IManagermentDomain;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    private TableView<?> tw_SearchTableProductionQueue;
    @FXML
    private TableColumn<?, ?> tc_ProductionQueue_BatchID;
    @FXML
    private TableColumn<?, ?> tc_ProductionQueue_DateOfCreation;
    @FXML
    private TableColumn<?, ?> tc_ProductionQueue_Amount;
    @FXML
    private TableColumn<?, ?> tc_ProductionQueue_Type;
    @FXML
    private TableColumn<?, ?> tc_ProductionQueue_Deadline;
    @FXML
    private TableColumn<?, ?> tc_ProductionQueue_SpeedForProduction;
    @FXML
    private TextField text_SearchProductionQueue;
    @FXML
    private Button btn_SearchProductionQueue;
    @FXML
    private AnchorPane ap_CompletedBatchesLayout;
    @FXML
    private TableView<?> tw_SearchTableCompletedBatches;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_batchID;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_MacineID;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_Type;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_DateOfCreation;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_Deadline;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_DateOfCompletion;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_SpeedForProduction;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_TotalAmount;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_GoodAmount;
    @FXML
    private TableColumn<?, ?> tc_CompletedBatches_DefectAmount;
    @FXML
    private TextField text_SearchCompletedBarches;
    @FXML
    private Button btn_SearchCompletedBatches;
    @FXML
    private Button btn_GenerateBatchreportCompletedBatches;
    @FXML
    private Button btn_CreateBatchOrder_Create;
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
    private Button btn_ShowOEE_GenerateOEE;
    @FXML
    private TextArea Texta_ShowOEE_Text;
    @FXML
    private AnchorPane ap_ShowOEE;

    IManagermentDomain imd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ap_CompletedBatchesLayout.setVisible(false);
        ap_CreateBatchOrder.setVisible(false);
        ap_ShowOEE.setVisible(false);
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
            ap_ShowOEE.setVisible(true);
            ap_ShowOEE.toFront();
            ap_ProductionQueueLayout.setVisible(false);
            ap_CompletedBatchesLayout.setVisible(false);
            ap_CreateBatchOrder.setVisible(false);
        }
    }

    @FXML
    private void OnSearchAction(ActionEvent event) {
    }

    @FXML
    private void GeneratingBatchreportAction(ActionEvent event) {
    }

    @FXML
    private void CreateBatchAction(ActionEvent event) {
        int typeofProduct = Integer.parseInt(textf_CreateBatchOrder_TypeofProduct.getText());
        int amounttoProduce = Integer.parseInt(textf_CreateBatchOrder_AmountToProduces.getText());
        float speed = Float.parseFloat(textf_CreateBatchOrder_Speed.getText());
        LocalDate date = dp_CreateBatchOrder.getValue();

        imd.CreateBatch(typeofProduct, amounttoProduce, speed, date);
    }

    @FXML
    private void GenerateOEEAction(ActionEvent event) {
    }
}
