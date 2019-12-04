package com.mycompany.crossCutting.objects;

public class BatchReport {
    private int finalBatchInformationID;
    private int productionListID;
    private int BreweryMachineID;
    private String deadline;
    private String dateOfCreation;
    private String dateOfCompletion;
    private int productID;
    private int totalCount;
    private int defectCount;
    private int AcceptedCount;

    public BatchReport(int finalBatchInformationID, int productionListID,
            int BreweryMachineID, String deadline, String dateOfCreation,
            String dateOfCompletion, int productID, int totalCount,
            int defectCount, int AcceptedCount) {
        this.finalBatchInformationID = finalBatchInformationID;
        this.productionListID = productionListID;
        this.BreweryMachineID = BreweryMachineID;
        this.deadline = deadline;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.productID = productID;
        this.totalCount = totalCount;
        this.defectCount = defectCount;
        this.AcceptedCount = AcceptedCount;
    }

    public int getFinalBatchInformationID() {
        return finalBatchInformationID;
    }

    public int getProductionListID() {
        return productionListID;
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

    public int getProductID() {
        return productID;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getDefectCount() {
        return defectCount;
    }

    public int getAcceptedCount() {
        return AcceptedCount;
    }
    
}
