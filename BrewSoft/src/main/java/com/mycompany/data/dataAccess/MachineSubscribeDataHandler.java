package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IMachineSubscriberDataHandler;
import java.sql.Date;
import java.sql.Time;

public class MachineSubscribeDataHandler implements IMachineSubscriberDataHandler{

    public DatabaseConnection connection;

    public MachineSubscribeDataHandler() {
        connection = new DatabaseConnection();
    }

    public void insertProductionInfo(int productionListID, int BreweryMachineID, float humidity, float temperature) {
        connection.queryUpdate("INSERT INTO ProductionInfo(productionListID, BreweryMachineID, humidity, temperature) VALUES (?,?,?,?)",
                productionListID, BreweryMachineID, humidity, temperature);
    }

    public void insertTimesInStates(int ProductionListID, int BreweryMachineID, String StartTimeInState, int MachinestateID) {
        connection.queryUpdate("INSERT INTO timeInstate (ProductionListID, BreweryMachineID, StartTimeInState, machineStateID) VALUES (?,?,?,?)",
                ProductionListID, BreweryMachineID, Time.valueOf(StartTimeInState), MachinestateID);
    }

    public void insertStopsDuringProduction(int ProductionListID, int BreweryMachineID, int stopReasonID) {
        connection.queryUpdate("INSERT INTO stopDuringProduction (ProductionListID, BreweryMachineID, stopReasonID) VALUES (?,?,?)",
                ProductionListID, BreweryMachineID, stopReasonID);
    }

    public void insertProductionList(int batchID, int productID, float productAmount, String deadline, float speed, String status) {
        connection.queryUpdate("INSERT INTO productionList (batchID, productID, productAmount, deadline, speed, status) values (?,?,?,?,?,?)",
                batchID, productID, productAmount, Date.valueOf((deadline)), speed, status);
    }

    public void insertFinalBatchInformation(int ProductionListID, int BreweryMachineID, String deadline, String dateOfCreation, String dateOfCompleation, int productID, int totalCount, int defectCount, int acceptedCount) {
        connection.queryUpdate("INSERT INTO finalBatchInformation (ProductionListID, BreweryMachineID, deadline, dateOfCreation, dateOfCompletion, productID, totalCount, defectCount, acceptedCount) values(?,?,?,?,?,?,?,?,?)",
                ProductionListID, BreweryMachineID, Date.valueOf(deadline), Date.valueOf(dateOfCreation), Date.valueOf(dateOfCompleation), productID, totalCount, defectCount, acceptedCount);
    }
    
    public Batch getNextBatch(){
                SimpleSet batchSet = connection.query("SELECT * FROM productionlist ORDER BY deadline ASC limit 1");
        if (batchSet.isEmpty()) {
            return null;
        } else {
            Batch batch = null;
            for (int i = 0; i < batchSet.getRows(); i++){
                batch = new Batch(
                       String.valueOf(batchSet.get(i, "batchid")),
                       String.valueOf(batchSet.get(i, "productid")),
                       String.valueOf(batchSet.get(i, "productamount")),
                       String.valueOf(batchSet.get(i, "deadline")),
                       String.valueOf(batchSet.get(i, "speed"))   
                );
            }
            return batch;
        }
    }
    
    @Override
    public void changeProductionListStatus(int productionListID, String newStatus) {
       connection.queryUpdate("UPDATE productionList SET status = ? WHERE productionListID = ?", newStatus, productionListID);
    }

    public static void main(String[] args) {
        MachineSubscribeDataHandler mspaint = new MachineSubscribeDataHandler();            
        
//        ArrayList<BatchReport> batchReportList = new ArrayList<>();
//        SimpleSet set = null;
//       
//        
//        for (int i = 0; i < set.getRows(); i++) {
//            BatchReport b = new BatchReport(
//                    (int) set.get(i, "finalBatchInformationID"),
//                    (int) set.get(i, "productionListID"),
//                    (int) set.get(i, "BreweryMachineID"),
//                    set.get(i, "deadline").toString(),
//                    set.get(i, "dateOfCreation").toString(),
//                    set.get(i, "dateOfCompletion").toString(),
//                    (int) set.get(i, "productID"),
//                    (int) set.get(i, "totalCount"),
//                    (int) set.get(i, "defectCount"),
//                    (int) set.get(i, "acceptedCount"));
//            batchReportList.add(b);
//        }
    }

    

}
