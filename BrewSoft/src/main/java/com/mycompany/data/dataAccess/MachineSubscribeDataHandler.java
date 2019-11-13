package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import java.util.ArrayList;

public class MachineSubscribeDataHandler {

    public DatabaseConnection connection;

    public MachineSubscribeDataHandler() {
        connection = new DatabaseConnection();
    }

    public void insertProductionInfo(int productionListID, int BreweryMachineID, float humidity, float temperature) {
        connection.queryUpdate("INSERT INTO ProductionInfo(productionListID, BreweryMachineID, humidity, temperature) VALUES (?,?,?,?)",
                productionListID, BreweryMachineID, humidity, temperature);
    }

    public void insertTimesInStates(int ProductionListID, int BreweryMachineID, String StartTimeInState, int MachinestatesID) {
        connection.queryUpdate("INSERT INTO timeInstates (ProductionListID, BreweryMachineID, StartTimeInState, machineStatesID) VALUES (?,?,?,?)",
                ProductionListID, BreweryMachineID, StartTimeInState, MachinestatesID);
    }

    public void insertStopsDuringProduction(int ProductionListID, int BreweryMachineID, int stopReasonsID) {
        connection.queryUpdate("INSERT INTO stopsDuringProduction (ProductionListID, BreweryMachineID, stopReasonsID) VALUES (?,?,?)",
                ProductionListID, BreweryMachineID, stopReasonsID);
    }

    public void insertProductionList(int batchID, int productID, float productAmount, String deadline, float speed, String status) {
        connection.queryUpdate("INSERT INTO productionList (batchID, productID, productAmount, deadline, speed, status) values (?,?,?,?,?,?)",
                batchID, productID, productAmount, deadline, speed, status);
    }

    public void insertFinalBatchInformation(int ProductionListID, int BreweryMachineID, String deadline, String dateOfCreation, String dateOfCompleation, int productID, int totalCount, int defectCount, int acceptedCount) {
        connection.queryUpdate("INSERT INTO finalBatchInformation (ProductionListID, BreweryMachineID, deadline, dateOfCreation, dateOfCompleation, productID, totalCount, defectCount, acceptedCount) values(?,?,?,?,?,?,?,?,?)",
                ProductionListID, BreweryMachineID, deadline, dateOfCreation, dateOfCompleation, productID, totalCount, defectCount, acceptedCount);
    }

    public SimpleSet getBatches() {
        return connection.query("SELECT * FROM finalbatchinformation");

    }

//    public void insertFinalBatchInformationData() {
    // BEFORE the TODO: Wait until Aleksander H is done with refactoring the subscription class
    // as we need data methods to get specific datavalues to create the logic below
    // TODO: Under Brewer domain class make logic for what a complete batch is
    // Logic for productAmount not met by the production
    /**
     * If (machine state = COMPLETE && that the productionList productAmount
     * order = TotalCount) {
     *
     * read production info insert data to final batch report
     *
     * if (productAmount = TotalCount) { create new queue with defectCount set
     * to productAmount }
     *
     * }
     */
    public static void main(String[] args) {
        MachineSubscribeDataHandler mspaint = new MachineSubscribeDataHandler();

        
        ArrayList<BatchReport> batchReportList = new ArrayList<>();
        SimpleSet set = null;
        set = mspaint.getBatches();
        for (int i = 0; i < set.getRows(); i++) {
            BatchReport b = new BatchReport(
                    (int) set.get(i, "finalBatchInformationID"),
                    (int) set.get(i, "productionListID"),
                    (int) set.get(i, "BreweryMachineID"),
                    set.get(i, "deadline").toString(),
                    set.get(i, "dateOfCreation").toString(),
                    set.get(i, "dateOfCompletion").toString(),
                    (int) set.get(i, "productID"),
                    (int) set.get(i, "totalCount"),
                    (int) set.get(i, "defectCount"),
                    (int) set.get(i, "acceptedCount"));
            batchReportList.add(b);
        }
        
        
        System.out.println(batchReportList.get(0).getTotalCount());
    }

}
