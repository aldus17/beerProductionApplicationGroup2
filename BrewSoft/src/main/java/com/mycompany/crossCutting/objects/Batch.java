package com.mycompany.crossCutting.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Batch {

    //StringProperty representations
    private StringProperty productionListID; // int 
    private StringProperty BatchID; //int
    private StringProperty MachineID; //int 
    private StringProperty type; //int 
    private StringProperty dateofCreation; //string
    private StringProperty deadline; //string
    private StringProperty dateofCompletion; //string
    private StringProperty speedforProduction; //float
    private StringProperty totalAmount; //int
    private StringProperty goodAmount; //float lav navn om til acceptedcount
    private StringProperty defectAmount; //float

    public Batch() {
    }

    //Queue batch object
    public Batch(String batchID, String type, String totalAmount,
            String deadline, String speedforProduction) {
        this.BatchID = new SimpleStringProperty(batchID);
        this.type = new SimpleStringProperty(type);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.deadline = new SimpleStringProperty(deadline);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
    }

    // Used for BatchReport purpose
    public Batch(String productionListID, String BatchID, String MachineID, String type,
            String dateofCreation, String deadline, String dateofCompletion,
            String speedforProduction, String totalAmount, String goodAmount,
            String defectAmount) {
        this.BatchID = new SimpleStringProperty(BatchID);
        this.MachineID = new SimpleStringProperty(MachineID);
        this.type = new SimpleStringProperty(type);
        this.dateofCreation = new SimpleStringProperty(dateofCreation);
        this.deadline = new SimpleStringProperty(deadline);
        this.dateofCompletion = new SimpleStringProperty(dateofCompletion);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.goodAmount = new SimpleStringProperty(goodAmount);
        this.defectAmount = new SimpleStringProperty(defectAmount);
    }

    public Batch(String productionListID, String BatchID, String type, String totalAmount, String deadline, String speedforProduction, String dateofCreation) {
        this.productionListID = new SimpleStringProperty(productionListID);
        this.BatchID = new SimpleStringProperty(BatchID);
        this.type = new SimpleStringProperty(type);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.deadline = new SimpleStringProperty(deadline);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
        this.dateofCreation = new SimpleStringProperty(dateofCreation);
    }

    // this is used for editQueuedBatch
    public Batch(String productionListID, String BatchID, String type, String totalAmount, String deadline, String speedforProduction) {
        this.productionListID = new SimpleStringProperty(productionListID);
        this.BatchID = new SimpleStringProperty(BatchID);
        this.type = new SimpleStringProperty(type);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.deadline = new SimpleStringProperty(deadline);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
    }
    
    // For Search
    // TODO: make constructor
    public Batch(String finalBatchInformationID, String productionListID,
            String BreweryMachineID, String deadline,
            String dateOfCreation, String dateOfCompletion,
            String productID, String totalCount,
            String defectCount, String acceptedCount) {
        
    }

    public StringProperty getBatchID() {
        return BatchID;
    }

    public StringProperty getMachineID() {
        return MachineID;
    }

    public StringProperty getType() {
        return type;
    }

    public StringProperty getDateofCreation() {
        return dateofCreation;
    }

    public StringProperty getDeadline() {
        return deadline;
    }

    public StringProperty getDateofCompletion() {
        return dateofCompletion;
    }

    public StringProperty getSpeedforProduction() {
        return speedforProduction;
    }

    public StringProperty getTotalAmount() {
        return totalAmount;
    }

    public StringProperty getGoodAmount() {
        return goodAmount;
    }

    public StringProperty getDefectAmount() {
        return defectAmount;
    }

    public StringProperty getProductionListID() {
        return productionListID;
    }

    public StringProperty CalulateProductionTime() {

        int productionTime = (int) (Double.parseDouble(totalAmount.getValue()) / Double.parseDouble(speedforProduction.getValue()));

        return new SimpleStringProperty(String.valueOf(productionTime));
    }

    public String toString() {
        return this.BatchID.getValue() + this.MachineID.getValue() + this.dateofCompletion.getValue() + this.dateofCreation.getValue() + this.deadline.getValue() + this.defectAmount.getValue() + this.goodAmount.getValue() + this.productionListID.getValue() + this.speedforProduction.getValue() + this.totalAmount.getValue() + this.type.getValue();
    }
}
