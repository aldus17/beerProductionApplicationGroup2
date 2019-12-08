package com.mycompany.crossCutting.objects;

public class BatchReport {

    private int batchID;
    private int BreweryMachineID;
    private String deadline;
    private String dateOfCreation;
    private String dateOfCompletion;
    private String productType;
    private int totalCount;
    private double defectCount;
    private double AcceptedCount;

    public BatchReport() {

    }

    public BatchReport(int batchID, int BreweryMachineID, String deadline, String dateOfCreation,
            String dateOfCompletion, String productType, int totalCount, double defectCount, double AcceptedCount) {
        this.batchID = batchID;
        this.BreweryMachineID = BreweryMachineID;
        this.deadline = deadline;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.productType = productType;
        this.totalCount = totalCount;
        this.defectCount = defectCount;
        this.AcceptedCount = AcceptedCount;

    }

    public int getBatchID() {
        return batchID;
    }

    public int getBreweryMachineID() {
        return BreweryMachineID;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getDateOfCompletion() {
        return dateOfCompletion;
    }

    public String getProductType() {
        return productType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getDefectCount() {
        return defectCount;
    }

    public double getAcceptedCount() {
        return AcceptedCount;
    }

    @Override
    public String toString() {
        return "BatchReport{" + "batchID=" + batchID + ", BreweryMachineID=" + BreweryMachineID + ", deadline=" + deadline + ", dateOfCreation=" + dateOfCreation + ", dateOfCompletion=" + dateOfCompletion + ", productType=" + productType + ", totalCount=" + totalCount + ", defectCount=" + defectCount + ", AcceptedCount=" + AcceptedCount + '}';
    }

}
