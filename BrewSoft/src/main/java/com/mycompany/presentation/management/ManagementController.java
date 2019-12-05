package com.mycompany.presentation.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.SearchData;
import com.mycompany.domain.management.ManagementDomain;
import com.mycompany.domain.management.interfaces.IBatchReportGenerate;
import com.mycompany.domain.management.pdf.PDF;
import com.mycompany.presentation.objects.UIBatch;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import java.util.ArrayList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private Button btn_SearchProductionQueue;
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
    private TableColumn<UIBatch, String> tc_CompletedBatches_SpeedForProduction;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_TotalAmount;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_GoodAmount;
    @FXML
    private TableColumn<UIBatch, String> tc_CompletedBatches_DefectAmount;
    @FXML
    private TextField text_SearchCompletedBarches;
    private Button btn_SearchCompletedBatches;
    private Button btn_generateBatch;
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
    private TextField tf_TypeOfProductEditBatch;
    @FXML
    private ToggleGroup tg_queuedbatches;
    @FXML
    private RadioButton rb_QueuedBatchID;
    private RadioButton rb_QueuedDeadline;
    @FXML
    private ComboBox<BeerTypes> cb_beerType;
    @FXML
    private ComboBox<BeerTypes> cb_beertypeCreateBatch;

    // Class calls
    private IManagementDomain managementDomain;
    private IBatchReportGenerate ibrg; // TODO Get class ..

    // Variables
    private List<Batch> batches;
    private List<Batch> completedBatches;
    private List<BeerTypes> beerTypes;
    private ObservableList<UIBatch> queuedBatcheObservableList;
    private ObservableList<UIBatch> productionListObservableList;
    private ObservableList<BeerTypes> beerTypesObservableList;
    private List<Batch> queuedBathchesList;
    private LocalDate productionListDate;
    private UIBatch selectedQueuedBatch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        managementDomain = new ManagementDomain();

        queuedBatcheObservableList = FXCollections.observableArrayList();
        productionListObservableList = FXCollections.observableArrayList();
        beerTypesObservableList = FXCollections.observableArrayList();
        queuedBathchesList = new ArrayList<>();

        updateQueuedArrayList();
        initialiseBeerTypes();

        updateObservableQueueudList();

        initializeObservableCompletedBatchList();
        initializeObservableQueueList();
        initializeObervableProductionList();

        enableSearchQueuedList();
        enableSearchCompletedList();

        setVisibleAnchorPane(ap_ProductionQueueLayout);

        productionListDate = LocalDate.now();
        dp_CreateBatchOrder.setValue(productionListDate);
        btn_Edit.setDisable(true);

        cb_beerType.setItems(beerTypesObservableList);
        cb_beertypeCreateBatch.setItems(beerTypesObservableList);
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
                    tf_AmountToProduceEditBatch.setText(String.valueOf(tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getTotalAmount().getValue()));
                    tf_SpeedEditBatch.setText(String.valueOf(tw_SearchTableProductionQueue.getSelectionModel().getSelectedItem().getSpeedforProduction().getValue()));
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
                        textf_CreateBatchOrder_AmountToProduces.setText(String.valueOf(tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getTotalAmount().getValue()));
                        textf_CreateBatchOrder_Speed.setText(String.valueOf(tw_CreateBatchOrder_BatchesOnSpecificDay.getSelectionModel().getSelectedItem().getSpeedforProduction().getValue()));
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

    private void OnSearchAction() {
        productionListObservableList.clear();

        SearchData sd = new SearchData(text_SearchCompletedBarches.getText(), 0.0f);
        completedBatches = managementDomain.batchObjects("CompletedBatches", sd);

        for (Batch batch : completedBatches) {
            productionListObservableList.add(new UIBatch(
                    String.valueOf(batch.getBatchID()),
                    String.valueOf(batch.getMachineID()),
                    String.valueOf(batch.getType()),
                    batch.getDateofCreation(),
                    batch.getDeadline(),
                    batch.getDateofCompletion(),
                    String.valueOf(batch.getTotalAmount()),
                    String.valueOf(batch.getGoodAmount()),
                    String.valueOf(batch.getDefectAmount())
            ));
        }

        initializeObservableCompletedBatchList();
        enableSearchCompletedList();
        tw_SearchTableCompletedBatches.refresh();
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
        String typeofProduct = String.valueOf(cb_beertypeCreateBatch.getSelectionModel().getSelectedItem().getIndexNumber());
        String amounttoProduce = textf_CreateBatchOrder_AmountToProduces.getText();
        String speed = textf_CreateBatchOrder_Speed.getText();
        String deadline = dp_CreateBatchOrder.getValue().toString();

        if (!amounttoProduce.isEmpty() && !typeofProduct.isEmpty() && !speed.isEmpty() && !deadline.isEmpty()) {
            lbl_CreateBatchOrder_error.setText("");
            if (Float.valueOf(amounttoProduce) >= 0.0f && Float.valueOf(amounttoProduce) < 65535.0f) {
                managementDomain.createBatch(new Batch(-1, Integer.parseInt(typeofProduct), Integer.parseInt(amounttoProduce), deadline, Float.parseFloat(speed)));
                updateQueuedArrayList();
                updateObservableProductionList(productionListDate);

            } else {
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
        if (dateToCreateOEE != null) {
            String oee = managementDomain.calculateOEE(dateToCreateOEE, 1);
            Texta_ShowOEE_Text.appendText(dateToCreateOEE.toString());
            Texta_ShowOEE_Text.appendText(" | ");
            Texta_ShowOEE_Text.appendText(oee + " %" + "\n");
        }
    }

    private void generatingBatchreportAction(ActionEvent e) {
        Stage primaryStage = new Stage();
        // TODO: Use createPDF from Domain
        // Extract batch id, machine id, production list ID, from the the list to use in the createPDF method
        // generated batchreport pdf will then be created, and needs to be loaded in.
        if (e.getSource() == btn_generateBatch) {

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
                    PDDocument doc = ibrg.createNewPDF(Integer.valueOf(
                            batch.getBatchID().getValue()),
                            Integer.valueOf(batch.getProductionListID().getValue()),
                            Integer.valueOf(batch.getMachineID().getValue()));

                    ibrg.savePDF(doc, fileChooser.getInitialFileName(), fileChooser.getInitialDirectory().getAbsolutePath());

                }
            } catch (IOException ex) {
                Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Batch selection error");
                alert.setHeaderText("Specified batch does not contain machine information properties");
                alert.setContentText("Batch does not contain temperature or humidity data");
                alert.showAndWait();
            }
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
        tw_SearchTableCompletedBatches.setItems(productionListObservableList);

        tc_CompletedBatches_batchID.setCellValueFactory(callData -> callData.getValue().getBatchID());
        tc_CompletedBatches_MacineID.setCellValueFactory(callData -> callData.getValue().getMachineID());
        tc_CompletedBatches_Type.setCellValueFactory(callData -> callData.getValue().getType());
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
        tc_ProductionQueue_Type.setCellValueFactory(callData -> callData.getValue().getType());
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
        tc_CreatBatchOrder_Type.setCellValueFactory(callData -> callData.getValue().getType());
        tc_CreatBatchOrder_Deadline.setCellValueFactory(callData -> callData.getValue().getDeadline());
        tc_CreatBatchOrder_SpeedForProduction.setCellValueFactory(callData -> callData.getValue().getSpeedforProduction());
        tc_CreatBatchOrder_ProductionTime.setCellValueFactory(callData -> callData.getValue().CalulateProductionTime());
    }

    @FXML
    private void toggleSpeed(ActionEvent event) {

        if (toggleSpeedBtn.isSelected()) {
            textf_CreateBatchOrder_Speed.setEditable(true);
            textf_CreateBatchOrder_Speed.setDisable(false);
        } else {
            textf_CreateBatchOrder_Speed.setEditable(false);
            textf_CreateBatchOrder_Speed.setDisable(true);
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

        batches = managementDomain.getQueuedBatches();

        //TODO Might need some changes.
        for (Batch batch : batches) {
            if (batch.getDeadline().equals(dateToCompare.toString())) {
                productionListObservableList.add(new UIBatch(
                        String.valueOf(batch.getBatchID()),
                        batch.getDateofCreation(),
                        String.valueOf(batch.getType()),
                        String.valueOf(batch.getTotalAmount()),
                        batch.getDeadline(),
                        String.valueOf(batch.getSpeedforProduction()),
                        String.valueOf(batch.CalulateProductionTime())
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
        for (Batch batch : queuedBathchesList) {
            queuedBatcheObservableList.add(new UIBatch(
                    String.valueOf(batch.getBatchID()),
                    String.valueOf(batch.getType()),
                    String.valueOf(batch.getTotalAmount()),
                    batch.getDeadline(),
                    String.valueOf(batch.getSpeedforProduction())
            ));
        }
        initializeObservableQueueList();
        enableSearchQueuedList();
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
        Batch newBatch = new Batch(Integer.parseInt(oldBatch.getProductionListID().getValue()),
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
        rb_QueuedBatchID.setSelected(false);
        rb_QueuedDeadline.setSelected(false);
        text_SearchProductionQueue.clear();
        enableSearchQueuedList();
        setVisibleAnchorPane(ap_ProductionQueueLayout);
    }

    private void enableSearchCompletedList() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<UIBatch> filteredData = new FilteredList<>(productionListObservableList, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        text_SearchCompletedBarches.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(UIBatch -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (UIBatch.getBatchID().getValue().toLowerCase().contentEquals(lowerCaseFilter)) {
                    return true;
                } else if (UIBatch.getDeadline().getValue().toLowerCase().contains(lowerCaseFilter)) {
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
                } else if (batch.getDeadline().getValue().toLowerCase().contains(lowerCaseFilter)) {
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
        textf_CreateBatchOrder_Speed.setText(String.valueOf(cb_beertypeCreateBatch.getSelectionModel().getSelectedItem().getProductionSpeed()));

    }

    @FXML
    private void GeneratingBatchreportAction(ActionEvent event) {
    }
}
