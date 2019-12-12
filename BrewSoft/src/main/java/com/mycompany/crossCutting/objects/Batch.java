package com.mycompany.crossCutting.objects;

import java.util.Objects;

public class Batch {

    private int productionListID;
    private int BatchID;
    private int MachineID;
    private final int type;
    private String dateofCreation;
    private final String deadline;
    private String dateofCompletion;
    private float speedforProduction;
    private final int totalAmount;
    private float acceptedcount;
    private float defectAmount;

    public Batch(int batchID, int type, int totalAmount,
            String deadline, float speedforProduction) {
        this.BatchID = batchID;
        this.type = type;
        this.totalAmount = totalAmount;
        this.deadline = deadline;
        this.speedforProduction = speedforProduction;
    }

    public Batch(int type, int totalAmount,
            String deadline, float speedforProduction) {
        this.type = type;
        this.totalAmount = totalAmount;
        this.deadline = deadline;
        this.speedforProduction = speedforProduction;
    }

    public Batch(int BatchID, int MachineID, int type,
            String dateofCreation, String deadline, String dateofCompletion,
            float speedforProduction, int totalAmount, float acceptedcount,
            float defectAmount) {
        this.BatchID = BatchID;
        this.MachineID = MachineID;
        this.type = type;
        this.dateofCreation = dateofCreation;
        this.deadline = deadline;
        this.dateofCompletion = dateofCompletion;
        this.speedforProduction = speedforProduction;
        this.totalAmount = totalAmount;
        this.acceptedcount = acceptedcount;
        this.defectAmount = defectAmount;
    }

    public Batch(int productionListID, int BatchID, int type, int totalAmount,
            String deadline, float speedforProduction, String dateofCreation) {
        this.productionListID = productionListID;
        this.BatchID = BatchID;
        this.type = type;
        this.totalAmount = totalAmount;
        this.deadline = deadline;
        this.speedforProduction = speedforProduction;
        this.dateofCreation = dateofCreation;
    }

    public Batch(int productionListID, int BatchID, int type, int totalAmount,
            String deadline, float speedforProduction) {
        this.productionListID = productionListID;
        this.BatchID = BatchID;
        this.type = type;
        this.totalAmount = totalAmount;
        this.deadline = deadline;
        this.speedforProduction = speedforProduction;
    }

    public Batch(int productionListID, int BatchID, int MachineID, int type,
            String dateofCreation, String deadline, String dateofCompletion,
            int totalAmount, float acceptedcount, float defectAmount) {
        this.productionListID = productionListID;
        this.BatchID = BatchID;
        this.MachineID = MachineID;
        this.type = type;
        this.dateofCreation = dateofCreation;
        this.deadline = deadline;
        this.dateofCompletion = dateofCompletion;
        this.totalAmount = totalAmount;
        this.acceptedcount = acceptedcount;
        this.defectAmount = defectAmount;
    }

    public int getBatchID() {
        return BatchID;
    }

    public int getMachineID() {
        return MachineID;
    }

    public int getType() {
        return type;
    }

    public String getDateofCreation() {
        return dateofCreation;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDateofCompletion() {
        return dateofCompletion;
    }

    public float getSpeedforProduction() {
        return speedforProduction;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public float getGoodAmount() {
        return acceptedcount;
    }

    public float getDefectAmount() {
        return defectAmount;
    }

    public int getProductionListID() {
        return productionListID;
    }

    public int CalulateProductionTime() {

        int productionTime = (int) (totalAmount / speedforProduction);

        return productionTime;
    }

    //Test equal, override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Batch batch = (Batch) o;
        if (BatchID == batch.BatchID && type == batch.type && totalAmount == batch.totalAmount && Objects.equals(deadline, batch.deadline) && speedforProduction == batch.speedforProduction) {
            return true;
        }
        if (BatchID == batch.BatchID && MachineID == batch.MachineID && type == batch.type && Objects.equals(dateofCreation, batch.dateofCreation) && Objects.equals(deadline, batch.deadline) && Objects.equals(dateofCompletion, batch.dateofCompletion) && speedforProduction == batch.speedforProduction && totalAmount == batch.totalAmount && acceptedcount == batch.acceptedcount && defectAmount == batch.defectAmount) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.BatchID;
        return hash;
    }
}
