package com.mycompany.management.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ManagementController implements Initializable {

    @FXML
    private MenuItem mi_ProductionList;
    @FXML
    private MenuItem mi_BatchReport;
    @FXML
    private MenuItem mi_Show;
    @FXML
    private MenuItem mi_Calculate;
    @FXML
    private MenuItem mi_PlanBatch;
    @FXML
    private AnchorPane ap_ProductionListLayout;
    @FXML
    private TableView<?> tw_SearchTable;
    @FXML
    private TableColumn<?, ?> tc_ProductionList_BatchID;
    @FXML
    private TableColumn<?, ?> tc_ProductionList_DateOfCreation;
    @FXML
    private TableColumn<?, ?> tc_ProductionList_Amount;
    @FXML
    private TableColumn<?, ?> tc_ProductionList_Type;
    @FXML
    private TableColumn<?, ?> tc_ProductionList_Deadline;
    @FXML
    private TableColumn<?, ?> tc_ProductionList_SpeedForProduction;
    @FXML
    private TextField text_SearchProductionList;
    @FXML
    private Button btn_SearchProductionList;
    @FXML
    private AnchorPane ap_BatchReportLayout;
    @FXML
    private TableView<?> tw_SearchTable1;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_batchID;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_MacineID;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_Type;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_DateOfCreation;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_Deadline;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_DateOfCompletion;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_SpeedForProduction;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_TotalAmount;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_GoodAmount;
    @FXML
    private TableColumn<?, ?> tc_BatchReport_DefectAmount;
    @FXML
    private TextField text_SearchBatchReport;
    @FXML
    private Button btn_SearchBatchReport;
    @FXML
    private AnchorPane ap_PlanBatch;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
