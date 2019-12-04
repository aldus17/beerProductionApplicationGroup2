/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crossCutting.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jacob
 */
public class BatchFinal {

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
    private StringProperty finalBatchInformationID;

    public BatchFinal(String batchID, String finalBatchInformationID, String productionListID,
            String BreweryMachineID, String deadline,
            String dateOfCreation, String dateOfCompletion,
            String type, String totalCount,
            String defectCount, String acceptedCount) {

        this.BatchID = new SimpleStringProperty(batchID);
        this.finalBatchInformationID = new SimpleStringProperty(finalBatchInformationID);
        this.productionListID = new SimpleStringProperty(productionListID);
        this.MachineID = new SimpleStringProperty(BreweryMachineID);
        this.deadline = new SimpleStringProperty(deadline);
        this.dateofCreation = new SimpleStringProperty(dateOfCreation);
        this.dateofCompletion = new SimpleStringProperty(dateOfCompletion);
        this.type = new SimpleStringProperty(type);
        this.totalAmount = new SimpleStringProperty(totalCount);
        this.defectAmount = new SimpleStringProperty(defectCount);
        this.goodAmount = new SimpleStringProperty(acceptedCount);

    }

    public StringProperty getProductionListID() {
        return productionListID;
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

    public StringProperty getFinalBatchInformationID() {
        return finalBatchInformationID;
    }

    


}
