package com.mycompany.presentation.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.domain.management.ManagementDomain;
import com.mycompany.domain.management.interfaces.IBatchReportGenerate;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import com.mycompany.domain.management.pdf.PDF;
import com.mycompany.presentation.objects.UIBatch;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;

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
    private TableView<UIBatch> tw_SearchTableProductionQueue;
    @FXML
    private TableColumn<UIBatch, String> tc_ProductionQueue_BatchID;
    @FXML
    private TableColumn<UIBatch, String> tc_ProductionQueue_DateOfCreation;
    @FXML
    private TableColumn<UIBatch, String> tc_ProductionQueue_Amount;
    @FXML
    private TableColumn<UIBatch, String> tc_ProductionQueue_Type;
    @FXML
    private TableColumn<UIBatch, String> tc_ProductionQueue_Deadline;
    @FXML
    private TableColumn<UIBatch, String> tc_ProductionQueue_SpeedForProduction;
    @FXML
    private TextField text_SearchProductionQueue;
    //private Button btn_SearchProductionQueue;
    @FXML
    private AnchorPane ap_CompletedBatchesLayout;
    @FXML
    private TableView<UIBatch> tw_SearchTableCompletedBatches;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_batchID;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_MacineID;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_Type;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_DateOfCreation;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_Deadline;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_DateOfCompletion;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_TotalAmount;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_GoodAmount;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_DefectAmount;
    @FXML
    private TextField text_SearchCompletedBarches;
    @FXML
    private TextField textf_CreateBatchOrder_AmountToProduces;
    @FXML
    private TextField tf_SpeedCreateBatchOrder;
    @FXML
    private DatePicker dp_CreateBatchOrder;
    @FXML
    private TableView<UIBatch> tw_CreateBatchOrder_BatchesOnSpecificDay;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_BatchID;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_DateofCreation;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_Amount;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_Type;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_Deadline;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_SpeedForProduction;
    @FXML
    private TableColumn<UIBatch, String> tc_CreatBatchOrder_ProductionTime;
    @FXML
    private DatePicker dp_ShowOEE;
    @FXML
    private TextArea Texta_ShowOEE_Text;
    @FXML
    private AnchorPane ap_ShowOEE;
    @FXML
    private Label lbl_CreateBatchOrder_error;
    @FXML
    private CheckBox toggleSpeedBtn;
    @FXML
    private Button btn_Edit;
    @FXML
    private AnchorPane ap_editBatch;
    @FXML
    private DatePicker dp_EditBatch;
    @FXML
    private TextField tf_SpeedEditBatch;
    @FXML
    private TextField tf_AmountToProduceEditBatch;
    @FXML
    private ComboBox<BeerTypes> cb_beerType;
    @FXML
    private ComboBox<BeerTypes> cb_beertypeCreateBatch;
    @FXML
    private Button btn_generateBatchreport;

    // Class calls
    private IManagementDomain managementDomain;
    private IBatchReportGenerate ibrg;

    // Variables
    private List<Batch> batches;
    private List<BeerTypes> beerTypes;
    private ObservableList<UIBatch> queuedBatcheObservableList;
    private ObservableList<UIBatch> productionListObservableList;
    private ObservableList<BeerTypes> beerTypesObservableList;
    private ObservableList<UIBatch> completedBatchObservableList;
    private ArrayList<Batch> queuedBathchesList;
    private ArrayList<Batch> completedBatchList;
    private LocalDate productionListDate;
    private UIBatch selectedQueuedBatch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        managementDomain = new ManagementDomain();

        queuedBatcheObservableList = FXCollections.observableArrayList();
        productionListObservableList = FXCollections.observableArrayList();
        beerTypesObservableList = FXCollections.observableArrayList();
        queuedBatcheObservableList = FXCollections.observableArrayList();
        completedBatchObservableList = FXCollections.observableArrayList();
        queuedBathchesList = new ArrayList<>();
        completedBatchList = new ArrayList<>();

        updateQueuedArrayList();
        updateCompletedArrayList();
        initialiseBeerTypes();

        updateObservableQueueudList();
        updateObservableCompletedList();

        initializeObservableCompletedBatchList();
        initializeObservableQueueList();
        initializeObervableProductionList();

        enableSearchQueuedList();

        setVisibleAnchorPane(ap_ProductionQueueLayout);

        productionListDate = LocalDate.now();
        dp_CreateBatchOrder.setValue(productionListDate);
        btn_Edit.setDisable(true);

        cb_beerType.setItems(beerTypesObservableList);
        cb_beertypeCreateBatch.setItems(beerTypesObservableList);
        cb_beertypeCreateBatch.setValue(beerTypesObservableList.get(0));
        tf_SpeedCreateBatchOrder.setText(String.valueOf(beerTypesObservableList.get(0).getProductionSpeed()));

        //Sets values in the text fields on the edit page in the system
        tw_SearchTableProductionQueue.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem() != null) {
                    selectedQueuedBatch = tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem();
                    if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("0")) {
                        cb_beerType.getSelectionModel().select(0);
                    } else if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("1")) {
                        cb_beerType.getSelectionModel().select(1);
                    } else if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("2")) {
                        cb_beerType.getSelectionModel().select(2);
                    } else if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("3")) {
                        cb_beerType.getSelectionModel().select(3);
                    } else if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("4")) {
                        cb_beerType.getSelectionModel().select(4);
                    } else if (tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("5")) {
                        cb_beerType.getSelectionModel().select(5);
                    }
                    tf_AmountToProduceEditBatch.setText(tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getTotalAmount().getValue());
                    tf_SpeedEditBatch.setText(tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getSpeedforProduction().getValue());
                    dp_EditBatch.setValue(LocalDate.parse(tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getDeadline().getValue()));
                    btn_Edit.setDisable(false);
                }
            }
        });

    }

    @FXML
    private void MenuItemChangesAction(ActionEvent event) {
        if (event.getSource() == mi_ProductionQueue) {
            setVisibleAnchorPane(ap_ProductionQueueLayout);
            updateObservableQueueudList();
            enableSearchQueuedList();
            btn_Edit.setDisable(true);
        }
        if (event.getSource() == mi_CompletedBatches) {
            updateCompletedArrayList();
            updateObservableCompletedList();
            enableSearchCompletedList();
            setVisibleAnchorPane(ap_CompletedBatchesLayout);
        }
        if (event.getSource() == mi_CreateBatchOrder) {
            setVisibleAnchorPane(ap_CreateBatchOrder);
            updateQueuedArrayList();
            updateObservableProductionList(productionListDate);
            //Sets values in the textfields on the create batch page in the system.
            tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem() != null) {
                        if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("0")) {
                            cb_beertypeCreateBatch.getSelectionModel().select(0);
                        } else if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("1")) {
                            cb_beertypeCreateBatch.getSelectionModel().select(1);
                        } else if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("2")) {
                            cb_beertypeCreateBatch.getSelectionModel().select(2);
                        } else if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("3")) {
                            cb_beertypeCreateBatch.getSelectionModel().select(3);
                        } else if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("4")) {
                            cb_beertypeCreateBatch.getSelectionModel().select(4);
                        } else if (tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getType().getValue().equalsIgnoreCase("5")) {
                            cb_beertypeCreateBatch.getSelectionModel().select(5);
                        }
                        textf_CreateBatchOrder_AmountToProduces.setText(tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getTotalAmount().getValue());
                        tf_SpeedCreateBatchOrder.setText(tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getSpeedforProduction().getValue());
                    }
                }
            });
        }
        if (event.getSource() == mi_ShowOEE) {
            setVisibleAnchorPane(ap_ShowOEE);
            Texta_ShowOEE_Text.setText("Date\t\t | OEE in Procentes" + "\n");
            ap_ShowOEE.toFront();
        }
    }

    @FXML
    private void GetOrdersForSpecificDay(ActionEvent event) {
        productionListObservableList.clear();
        productionListDate = dp_CreateBatchOrder.getValue();
        updateQueuedArrayList();
        updateObservableProductionList(productionListDate);
    }

    @FXML
    private void CreateBatchAction(ActionEvent event) {
        Integer typeofProduct = cb_beertypeCreateBatch.getSelectionModel().getSelectedIndex();
        Integer amounttoProduce = null;
        Float speed = null;
        String deadline = dp_CreateBatchOrder.getValue().toString();

        try {
            amounttoProduce = Integer.parseInt(textf_CreateBatchOrder_AmountToProduces.getText());
        } catch (NumberFormatException e1) {
            textf_CreateBatchOrder_AmountToProduces.setText("Enter valid input");
            amounttoProduce = null;
        }
        try {
            speed = Float.parseFloat(tf_SpeedCreateBatchOrder.getText());
        } catch (NumberFormatException e3) {
            tf_SpeedCreateBatchOrder.setText("Enter valid input");
            speed = null;
        }
        if (speed != null && amounttoProduce != null) {
            if (amounttoProduce >= 0 && amounttoProduce < 65535) {
                managementDomain.createBatch(new Batch(typeofProduct, amounttoProduce, deadline, speed));
                updateQueuedArrayList();
                updateObservableProductionList(productionListDate);
            }
        }
    }

    @FXML
    private void GenerateOEEAction(ActionEvent event
    ) {
        LocalDate dateToCreateOEE = dp_ShowOEE.getValue();
        if (dateToCreateOEE != null) {
            String oee = managementDomain.calculateOEE(dateToCreateOEE, 28800);
            Texta_ShowOEE_Text.appendText(dateToCreateOEE.toString());
            Texta_ShowOEE_Text.appendText(" | ");
            Texta_ShowOEE_Text.appendText(oee + " %" + "\n");
        }
    }

    private void initialiseBeerTypes() {
        beerTypes = managementDomain.getBeerTypes();
        beerTypes.forEach((beer) -> {
            beerTypesObservableList.add(beer);
        });
    }

    private void initializeObservableCompletedBatchList() {
        tw_SearchTableCompletedBatches.setPlaceholder(new Label());
        tw_SearchTableCompletedBatches.setItems(completedBatchObservableList);

        tc_CompletedBatches_batchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_CompletedBatches_MacineID.setCellValueFactory(callData -> callData.getValue().getMachineID());
        tc_CompletedBatches_Type.setCellValueFactory(callData -> beerTypes.get(Integer.parseInt(callData.getValue().getType().getValueSafe())).typeNameProperty());
        tc_CompletedBatches_DateOfCreation.setCellValueFactory(callData -> callData.getValue().getDateofCreation());
        tc_CompletedBatches_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_CompletedBatches_DateOfCompletion.setCellValueFactory(callData -> callData.getValue().getDateofCompletion());
        tc_CompletedBatches_TotalAmount.setCellValueFactory(callData -> callData.getValue().getTotalAmount());
        tc_CompletedBatches_GoodAmount.setCellValueFactory(callData -> callData.getValue().getGoodAmount());
        tc_CompletedBatches_DefectAmount.setCellValueFactory(callData -> callData.getValue().getDefectAmount());

    }

    private void initializeObservableQueueList() {
        tw_SearchTableProductionQueue.setPlaceholder(new Label());
        tw_SearchTableProductionQueue.setItems(queuedBatcheObservableList);

        tc_ProductionQueue_BatchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_ProductionQueue_Type.setCellValueFactory(callData -> beerTypes.get(Integer.parseInt(callData.getValue().getType().getValueSafe())).typeNameProperty());
        tc_ProductionQueue_DateOfCreation.setCellValueFactory(callData -> callData.getValue().getDateofCreation());
        tc_ProductionQueue_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_ProductionQueue_SpeedForProduction.setCellValueFactory(callData -> callData.getValue().getSpeedforProduction());
        tc_ProductionQueue_Amount.setCellValueFactory(callData -> callData.getValue().getTotalAmount());
    }

    private void initializeObervableProductionList() {
        tw_CreateBatchOrder_BatchesOnSpecificDay.setPlaceholder(new Label());
        tw_CreateBatchOrder_BatchesOnSpecificDay.setItems(productionListObservableList);

        tc_CreatBatchOrder_BatchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_CreatBatchOrder_DateofCreation.setCellValueFactory(callData -> callData.getValue().getDateofCreation());
        tc_CreatBatchOrder_Amount.setCellValueFactory(callData -> callData.getValue().getTotalAmount());
        tc_CreatBatchOrder_Type.setCellValueFactory(callData -> beerTypes.get(Integer.parseInt(callData.getValue().getType().getValueSafe())).typeNameProperty());
        tc_CreatBatchOrder_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_CreatBatchOrder_SpeedForProduction.setCellValueFactory(callData -> callData.getValue().getSpeedforProduction());
        tc_CreatBatchOrder_ProductionTime.setCellValueFactory(callData -> callData.getValue().CalulateProductionTime());
    }

    @FXML
    private void toggleSpeed(ActionEvent event) {
        if (toggleSpeedBtn.isSelected()) {
            tf_SpeedCreateBatchOrder.setEditable(true);
            tf_SpeedCreateBatchOrder.setDisable(false);
        } else {
            tf_SpeedCreateBatchOrder.setEditable(false);
            tf_SpeedCreateBatchOrder.setDisable(true);
        }
    }

    /**
     * Pulls all the queued bathces from the productionlist in the database.
     * Should be called everytime a batch is updated.
     */
    private void updateQueuedArrayList() {
        if (!queuedBathchesList.isEmpty()) {
            queuedBathchesList.clear();
        }
        queuedBathchesList = managementDomain.getQueuedBatches();
    }

    private void updateCompletedArrayList() {
        if (!completedBatchList.isEmpty()) {
            completedBatchList.clear();
        }
        completedBatchList = managementDomain.getCompletedBatches();
    }

    /**
     * Updates the observable production list based on a date, so that you only
     * see bathces for the selected date.
     *
     * @param dateToCompare is of type localdate.
     */
    private void updateObservableProductionList(LocalDate dateToCompare) {
        if (!productionListObservableList.isEmpty()) {
            productionListObservableList.clear();
        }
        for (Batch b : queuedBathchesList) {
            if (b.getDeadline().equals(dateToCompare.toString())) {
                productionListObservableList.add(new UIBatch(
                        String.valueOf(b.getBatchID()),
                        String.valueOf(b.getType()),
                        b.getDateofCreation(),
                        b.getDeadline(),
                        String.valueOf(b.getSpeedforProduction()),
                        String.valueOf(b.getTotalAmount()),
                        String.valueOf(b.CalulateProductionTime())
                ));
            }
        }
        initializeObervableProductionList();
    }

    /**
     * Updates the observable queued list. Initializes the queued batch
     * tableview Makes sure that you can live search the queued list, by calling
     * enableSearchQueuedList
     */
    private void updateObservableQueueudList() {
        if (!queuedBatcheObservableList.isEmpty()) {
            queuedBatcheObservableList.clear();
        }
        for (Batch b : queuedBathchesList) {
            queuedBatcheObservableList.add(new UIBatch(
                    b.getProductionListID(),
                    String.valueOf(b.getBatchID()),
                    String.valueOf(b.getType()),
                    b.getDateofCreation(),
                    b.getDeadline(),
                    String.valueOf(b.getSpeedforProduction()),
                    String.valueOf(b.getTotalAmount())
            ));
        }
        initializeObservableQueueList();
        enableSearchQueuedList();
    }

    private void updateObservableCompletedList() {
        if (!completedBatchObservableList.isEmpty()) {
            completedBatchObservableList.clear();
        }
        for (Batch b : completedBatchList) {
            completedBatchObservableList.add(new UIBatch(
                    String.valueOf(b.getProductionListID()),
                    String.valueOf(b.getBatchID()),
                    String.valueOf(b.getMachineID()),
                    String.valueOf(b.getType()),
                    b.getDateofCreation(),
                    b.getDeadline(),
                    b.getDateofCompletion(),
                    String.valueOf(b.getTotalAmount()),
                    String.valueOf(b.getGoodAmount()),
                    String.valueOf(b.getDefectAmount())
            ));
        }
        initializeObservableCompletedBatchList();
    }

    @FXML
    private void onEditSelectedBatch(ActionEvent event) {
        setVisibleAnchorPane(ap_editBatch);
    }

    //Action handler for when you are done editing a batch.
    //Saves the edited batch to database, pulls a new list with queued batches from the database
    //updates the tableviews throughout the system.
    @FXML
    private void onCompleteEditActionHandler(ActionEvent event) {
        UIBatch oldBatch = selectedQueuedBatch;
        Integer amounttoProduce = null;
        Float speed = null;
        try {
            amounttoProduce = Integer.parseInt(tf_AmountToProduceEditBatch.getText());
        } catch (NumberFormatException e1) {
            tf_AmountToProduceEditBatch.setText("Enter valid input");
            amounttoProduce = null;
        }
        try {
            speed = Float.parseFloat(tf_SpeedEditBatch.getText());
        } catch (NumberFormatException e3) {
            tf_SpeedCreateBatchOrder.setText("Enter valid input");
            speed = null;
        }
        Batch newBatch = null;
        if (speed != null && amounttoProduce != null) {
            if (Integer.valueOf(amounttoProduce) >= 0 && Integer.valueOf(amounttoProduce) < 65535) {
                newBatch = new Batch(
                        Integer.parseInt(oldBatch.getProductionListID().getValue()),
                        Integer.parseInt(oldBatch.getBatchID().getValue()),
                        cb_beerType.getSelectionModel().getSelectedItem().getIndexNumber(),
                        Integer.parseInt(tf_AmountToProduceEditBatch.getText()),
                        dp_EditBatch.getValue().toString(),
                        Float.parseFloat(tf_SpeedEditBatch.getText()),
                        oldBatch.getDateofCreation().getValue());

                managementDomain.editQueuedBatch(newBatch);
                updateQueuedArrayList();
                updateObservableProductionList(productionListDate);
                updateObservableQueueudList();
                text_SearchProductionQueue.clear();
                enableSearchQueuedList();
                setVisibleAnchorPane(ap_ProductionQueueLayout);
            }
        }

    }

    private void enableSearchCompletedList() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<UIBatch> filteredData = new FilteredList<>(completedBatchObservableList, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        text_SearchCompletedBarches.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(batchfinal -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (batchfinal.getBatchID().getValue().toLowerCase().contentEquals(lowerCaseFilter)) {
                    return true;
                } else if (batchfinal.getDateofCompletion().getValue().toLowerCase().contentEquals(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<UIBatch> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tw_SearchTableCompletedBatches.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tw_SearchTableCompletedBatches.setItems(sortedData);
    }

    /**
     * Adds support for live search to the queued batch list.
     */
    private void enableSearchQueuedList() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<UIBatch> filteredData = new FilteredList<>(queuedBatcheObservableList, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        text_SearchProductionQueue.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(batch -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (batch.getBatchID().getValue().toLowerCase().contentEquals(lowerCaseFilter)) {
                    return true;
                } else if (batch.getDeadline().getValue().toLowerCase().contentEquals(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<UIBatch> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tw_SearchTableProductionQueue.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tw_SearchTableProductionQueue.setItems(sortedData);
    }

    /**
     * The method dictates what anchorpane is visible
     *
     * @param pane The pane that is sent with the method, is the pane that is
     * set to be visible
     */
    private void setVisibleAnchorPane(AnchorPane pane) {
        ap_CompletedBatchesLayout.setVisible(false);
        ap_CreateBatchOrder.setVisible(false);
        ap_ProductionQueueLayout.setVisible(false);
        ap_ShowOEE.setVisible(false);
        ap_editBatch.setVisible(false);
        if (ap_CompletedBatchesLayout.equals(pane)) {
            ap_CompletedBatchesLayout.setVisible(true);
        } else if (ap_CreateBatchOrder.equals(pane)) {
            ap_CreateBatchOrder.setVisible(true);
        } else if (ap_ProductionQueueLayout.equals(pane)) {
            ap_ProductionQueueLayout.setVisible(true);
        } else if (ap_ShowOEE.equals(pane)) {
            ap_ShowOEE.setVisible(true);
        } else if (ap_editBatch.equals(pane)) {
            ap_editBatch.setVisible(true);
        }
    }

    @FXML
    private void comboboxAction(ActionEvent event) {
        tf_SpeedCreateBatchOrder.setText(String.valueOf(cb_beertypeCreateBatch.getSelectionModel().getSelectedItem().getProductionSpeed()));

    }

    /**
     * Action handler to generate PDF document of the choosen batch. The user
     * can select a batch in a list and then click on the generate batch button
     * to get a save window where the user can choose the directory where to
     * save the specific pdf document.
     *
     * @param event
     */
    @FXML
    private void GeneratingBatchreportActionn(ActionEvent event) {
        Stage primaryStage = new Stage();

        if (event.getSource() == btn_generateBatchreport && !tw_SearchTableCompletedBatches.getSelectionModel().isEmpty()) {

            int index = tw_SearchTableCompletedBatches.getSelectionModel().getSelectedIndex();
            UIBatch batch = tw_SearchTableCompletedBatches.getItems().get(index);

            System.out.println("Test1");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Report");
            fileChooser.setInitialFileName("BatchReport"); // Ignore user filename input
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("pdf Files", "*.pdf"));

            try {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    File dir = file.getParentFile();    //gets the selected directory
                    //update the file chooser directory to user selected so the choice is "remembered"
                    fileChooser.setInitialDirectory(dir);
                    System.out.println(fileChooser.getInitialDirectory().getAbsolutePath());
                    ibrg = new PDF();
                    PDDocument doc = ibrg.createNewPDF(
                            Integer.valueOf(batch.getBatchID().getValue()),
                            Integer.valueOf(batch.getBatchID().getValue()),
                            Integer.valueOf(batch.getMachineID().getValue()));

                    ibrg.savePDF(doc, fileChooser.getInitialFileName(), fileChooser.getInitialDirectory().getAbsolutePath());

                }
            } catch (IOException ex) {
                Logger.getLogger(ManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (NullPointerException ex) {
                Logger.getLogger(ManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Batch selection error");
                alert.setHeaderText("Specified batch does not contain machine information properties");
                alert.setContentText("Batch does not contain temperature or humidity data");
                alert.showAndWait();
            }
        }
    }

}
