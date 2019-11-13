package com.mycompany.crossCutting.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Batch {

    //String representations
    private String stringBatchID;
    private String stringType;
    private String stringDateofCompletion;
    private String stringSpeedforProduction;
    private String stringTotalAmount;

    //StringProperty representations
    private StringProperty productionListID;
    private StringProperty BatchID;
    private StringProperty MachineID;
    private StringProperty type;
    private StringProperty dateofCreation;
    private StringProperty deadline;
    private StringProperty dateofCompletion;
    private StringProperty speedforProduction;
    private StringProperty totalAmount;
    private StringProperty goodAmount;
    private StringProperty defectAmount;

    //Queue batch object
    public Batch(String sBatchID, String sType,
            String sDateofCompletion, String sSpeedforProduction,
            String sTotalAmount) {
        this.stringBatchID = sBatchID;
        this.stringType = sType;
        this.stringDateofCompletion = sDateofCompletion;
        this.stringSpeedforProduction = sSpeedforProduction;
        this.stringTotalAmount = sTotalAmount;
    }

    public Batch(String BatchID, String MachineID, String type,
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

    public Batch(String productionListID, String BatchID, String type, String totalAmount, String deadline, String speedforProduction) {
        this.productionListID = new SimpleStringProperty(productionListID);
        this.BatchID = new SimpleStringProperty(BatchID);
        this.type = new SimpleStringProperty(type);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.deadline = new SimpleStringProperty(deadline);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
      
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

    public StringProperty CalulateProductionTime() {

        int productionTime = (int) (Double.parseDouble(totalAmount.toString()) / Double.parseDouble(speedforProduction.toString()));

        return new SimpleStringProperty(String.valueOf(productionTime));
    }

    public String toString() {
        return stringBatchID + " " + stringType + " " + stringDateofCompletion + " " + stringSpeedforProduction + " " + stringTotalAmount;
    }

    public String getStringBatchID() {
        return stringBatchID;
    }

    public String getStringType() {
        return stringType;
    }

    public String getStringDateofCompletion() {
        return stringDateofCompletion;
    }

    public String getStringSpeedforProduction() {
        return stringSpeedforProduction;
    }

    public String getStringTotalAmount() {
        return stringTotalAmount;
    }

    public void setStringBatchID(String BatchID) {
        this.stringBatchID = BatchID;
    }

}
