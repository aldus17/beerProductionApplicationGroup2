package com.mycompany.presentation.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.domain.management.ManagementDomain;
import com.mycompany.domain.management.interfaces.IBatchReportGenerate;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalDateStringConverter;
import javax.swing.JOptionPane;
import org.eclipse.milo.opcua.stack.core.Identifiers;

public class ManagementController implements Initializable {

    @FXML
    private MenuItem mi_ShowOEE;
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
    private TextField textf_CreateBatchOrder_AmountToProduces;
    @FXML
    private TextField textf_CreateBatchOrder_TypeofProduct;
    @FXML
    private TextField textf_CreateBatchOrder_Speed;
    @FXML
    private DatePicker dp_CreateBatchOrder;
    @FXML
    private ListView<BeerTypes> lv_CreateBatchOrder_TypeofBeer;
    @FXML
    private TableView<Batch> tw_CreateBatchOrder_BatchesOnSpecificDay;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_BatchID;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_DateofCreation;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_Amount;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_Type;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_Deadline;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_SpeedForProduction;
    @FXML
    private TableColumn<Batch, String> tc_CreatBatchOrder_ProductionTime;
    @FXML
    private DatePicker dp_ShowOEE;
    @FXML
    private TextArea Texta_ShowOEE_Text;
    @FXML
    private AnchorPane ap_ShowOEE;
    @FXML
    private Label lbl_CreateBatchOrder_error;

    // Class calls
    private IManagementDomain managementDomain;
    private IBatchReportGenerate ibrg; // TODO Get class ..

    // Variables
    private List<Batch> batches;
    private List<BeerTypes> beerTypes;
    private ObservableList<Batch> batcheObservableList;
    private ObservableList<Batch> queuedBatchesObservableList;
    private ObservableList<BeerTypes> beerTypesObservableList;
    private ArrayList<Batch> queuedBatcheslist;
    private LocalDate queuedBatchesDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        batcheObservableList = FXCollections.observableArrayList();
        queuedBatchesObservableList = FXCollections.observableArrayList();
        queuedBatcheslist = new ArrayList<>();

        InitializeObservableBatchList();
        InitializeObservableQueueList();
        InitializeObervableOrderList();

        lv_CreateBatchOrder_TypeofBeer.setPlaceholder(new Label());
        lv_CreateBatchOrder_TypeofBeer.setItems(beerTypesObservableList);

        ap_CompletedBatchesLayout.setVisible(false);
        ap_CreateBatchOrder.setVisible(false);
        ap_ShowOEE.setVisible(false);
        ap_ProductionQueueLayout.setVisible(true);
        ap_ProductionQueueLayout.toFront();

        managementDomain = new ManagementDomain();

        queuedBatchesDate = LocalDate.now();
        dp_CreateBatchOrder.setValue(queuedBatchesDate);
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
            if (queuedBatcheslist.isEmpty()) {
                queuedBatcheslist = managementDomain.getQueuedBatches();
                updateObservableOrderList(queuedBatchesDate);
            }
            beerTypes = managementDomain.getBeerTypes();

            beerTypes.forEach((beer) -> {
                beerTypesObservableList.add(beer);
            });
            lv_CreateBatchOrder_TypeofBeer.refresh();
        }
        if (event.getSource() == mi_ShowOEE) {
            ap_ProductionQueueLayout.setVisible(false);
            ap_CompletedBatchesLayout.setVisible(false);
            ap_CreateBatchOrder.setVisible(false);
            ap_ShowOEE.setVisible(true);
            Texta_ShowOEE_Text.setText("Date\t\t | OEE in Procentes" + "\n");
            ap_ShowOEE.toFront();
        }
    }

    @FXML
    private void OnSearchAction(ActionEvent event) {

        batcheObservableList.clear();

        if (event.getSource() == btn_SearchCompletedBatches) {
            batches = managementDomain.batchObjects("CompletedBatches", text_SearchCompletedBarches.getText());
            tw_SearchTableCompletedBatches.refresh();
        }

        if (event.getSource() == btn_SearchProductionQueue) {
            batches = managementDomain.batchObjects("BatchesinQueue", text_SearchProductionQueue.getText());
            tw_SearchTableProductionQueue.refresh();
        }

        batches.forEach((batch) -> {
            batcheObservableList.add(batch);
        });
    }

    @FXML
    private void GeneratingBatchreportAction(ActionEvent event) {
        tw_SearchTableCompletedBatches.getSelectionModel().getSelectedItem();
        ibrg.GeneratePDFDocument(); // TODO Find out witch part the batch should be found on.
    }

    @FXML
    private void GetOrdersForSpecificDay(ActionEvent event) {
        queuedBatchesObservableList.clear();
        queuedBatchesDate = dp_CreateBatchOrder.getValue();
        if (queuedBatcheslist.isEmpty()) {
            queuedBatcheslist = managementDomain.getQueuedBatches();
        }
        updateObservableOrderList(queuedBatchesDate);
    }

    @FXML
    private void CreateBatchAction(ActionEvent event) {
        String typeofProduct = textf_CreateBatchOrder_TypeofProduct.getText();
        String amounttoProduce = textf_CreateBatchOrder_AmountToProduces.getText();
        String speed = textf_CreateBatchOrder_Speed.getText();
        String deadline = dp_CreateBatchOrder.getValue().toString();

        if (!amounttoProduce.isEmpty() && !typeofProduct.isEmpty() && !speed.isEmpty() && !deadline.isEmpty()) {
            lbl_CreateBatchOrder_error.setText("");
            if (Integer.parseInt(amounttoProduce) >= 0 && Integer.parseInt(amounttoProduce) < 65535) {
                managementDomain.createBatch(new Batch("", typeofProduct, deadline, speed, amounttoProduce));
                System.out.println("Batch created");
                queuedBatcheslist.clear();                                  //Clears list of queued batches
                queuedBatcheslist = managementDomain.getQueuedBatches();    //Repopulate the list, with the addition of a new batch. 
                updateObservableOrderList(queuedBatchesDate);               //Updates the observableorderlist

            } else {
                System.out.println("Invalid amount");
                JOptionPane.showMessageDialog(null, "Invalid number: Cannot exceed 65535");
            }
        } else {
            lbl_CreateBatchOrder_error.setText("Make sure no fields are empty");
            lbl_CreateBatchOrder_error.setTextFill(Color.web("#fc0303"));
        }

    }

    @FXML
    private void GenerateOEEAction(ActionEvent event) {
        LocalDate dateToCreateOEE = dp_ShowOEE.getValue();
        double oee = managementDomain.calulateOEE(dateToCreateOEE);

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

    private void InitializeObervableOrderList() {

        tw_CreateBatchOrder_BatchesOnSpecificDay.setPlaceholder(new Label());
        tw_CreateBatchOrder_BatchesOnSpecificDay.setItems(queuedBatchesObservableList);

        tc_CreatBatchOrder_BatchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_CreatBatchOrder_DateofCreation.setCellValueFactory(callData -> callData.getValue().getDateofCreation());
        tc_CreatBatchOrder_Amount.setCellValueFactory(callData -> callData.getValue().getTotalAmount());
        tc_CreatBatchOrder_Type.setCellValueFactory(callData -> callData.getValue().getType());
        tc_CreatBatchOrder_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_CreatBatchOrder_SpeedForProduction.setCellValueFactory(callData -> callData.getValue().getSpeedforProduction());
        tc_CreatBatchOrder_ProductionTime.setCellValueFactory(callData -> callData.getValue().CalulateProductionTime());
    }

    private void updateObservableOrderList(LocalDate dateToCompare) {
        for (Batch b : queuedBatcheslist) {
            if (b.getDeadline().getValue().equals(dateToCompare.toString())) {
                queuedBatchesObservableList.add(b);
            }
        }
        InitializeObervableOrderList();
    }

}
