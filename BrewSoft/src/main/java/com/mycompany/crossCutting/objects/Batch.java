package com.mycompany.crossCutting.objects;

public class Batch {

    private int productionListID;
    private final int BatchID;
    private int MachineID;
    private final int type;
    private String dateofCreation;
    private final String deadline;
    private String dateofCompletion;
    private final float speedforProduction;
    private final int totalAmount;
    private float acceptedcount;
    private float defectAmount;

    //Queue batch object
    public Batch(int batchID, int type, int totalAmount,
            String deadline, float speedforProduction) {
        this.BatchID = batchID;
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

    public Batch(int productionListID, int BatchID, int type, int totalAmount, String deadline, float speedforProduction, String dateofCreation) {
        this.productionListID = productionListID;
        this.BatchID = BatchID;
        this.type = type;
        this.totalAmount = totalAmount;
        this.deadline = deadline;
        this.speedforProduction = speedforProduction;
        this.dateofCreation = dateofCreation;
    }

    // this is used for editQueuedBatch
    public Batch(int productionListID, int BatchID, int type, int totalAmount, String deadline, float speedforProduction) {
        this.productionListID = productionListID;
        this.BatchID = BatchID;
        this.type = type;
        this.totalAmount = totalAmount;
        this.deadline = deadline;
        this.speedforProduction = speedforProduction;
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
}
