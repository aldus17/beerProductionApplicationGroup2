package com.mycompany.presentation.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIBatch {

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
    private StringProperty calulateProductionTime;

    public UIBatch(String BatchID, String type, String dateofCreation,
            String deadline, String speedforProduction, String totalAmount,
            String calulateProductionTime) {
        this.BatchID = new SimpleStringProperty(BatchID);
        this.type = new SimpleStringProperty(type);
        this.dateofCreation = new SimpleStringProperty(dateofCreation);
        this.deadline = new SimpleStringProperty(deadline);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.calulateProductionTime = new SimpleStringProperty(calulateProductionTime);
    }

    public UIBatch(int productionListID, String BatchID, String type, String dateofCreation,
            String deadline, String speedforProduction, String totalAmount) {
        this.productionListID = new SimpleStringProperty(String.valueOf(productionListID));
        this.BatchID = new SimpleStringProperty(BatchID);
        this.type = new SimpleStringProperty(type);
        this.dateofCreation = new SimpleStringProperty(dateofCreation);
        this.deadline = new SimpleStringProperty(deadline);
        this.speedforProduction = new SimpleStringProperty(speedforProduction);
        this.totalAmount = new SimpleStringProperty(totalAmount);
    }

    public UIBatch(String productionListID, String BatchID, String MachineID,
            String type, String dateofCreation,
            String deadline, String dateofCompletion,
            String totalAmount, String goodAmount,
            String defectAmount) {
        this.productionListID = new SimpleStringProperty(productionListID);
        this.BatchID = new SimpleStringProperty(BatchID);
        this.MachineID = new SimpleStringProperty(MachineID);
        this.type = new SimpleStringProperty(type);
        this.dateofCreation = new SimpleStringProperty(dateofCreation);
        this.deadline = new SimpleStringProperty(deadline);
        this.dateofCompletion = new SimpleStringProperty(dateofCompletion);
        this.totalAmount = new SimpleStringProperty(totalAmount);
        this.goodAmount = new SimpleStringProperty(goodAmount);
        this.defectAmount = new SimpleStringProperty(defectAmount);
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
        return calulateProductionTime;
    }
    
    public String toString() {
        return this.BatchID.getValueSafe()+" : "+ this.dateofCreation.getValueSafe()+" : "+ this.productionListID.getValueSafe();
    }
}
