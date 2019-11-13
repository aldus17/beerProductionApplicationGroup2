package com.mycompany.crossCutting.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Batch {
   
    //StringProperty representations
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
    public Batch(String batchID, String type, 
            String deadline, String speedforProduction, 
            String totalAmount) {
         this.BatchID = new SimpleStringProperty(batchID);
         this.type = new SimpleStringProperty(type);
         this.deadline = new SimpleStringProperty(deadline);
         this.speedforProduction = new SimpleStringProperty(speedforProduction);
         this.totalAmount = new SimpleStringProperty(totalAmount);
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
     
}
