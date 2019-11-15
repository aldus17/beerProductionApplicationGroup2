package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.sql.Date;
import java.util.ArrayList;

public class BatchDataHandler implements IBatchDataHandler {

    private final String QUEUED_STATUS = "Queued";
    private DatabaseConnection dbConnection;

    public BatchDataHandler() {
        dbConnection = new DatabaseConnection();
    }
//ldsc.fromString(batchObject.getStringDateofCompletion())

    @Override
    public void insertBatchToQueue(Batch batch) {
        Batch batchObject = batch;
        batchObject.toString();
        dbConnection.queryUpdate("INSERT INTO ProductionList "
                + "(batchid, productid, productamount, deadline, speed, status)"
                + "VALUES(?,?,?,?,?,?)",
                Integer.parseInt(batchObject.getStringBatchID()),
                Float.parseFloat(batchObject.getStringType()),
                Float.parseFloat(batchObject.getStringTotalAmount()),
                Date.valueOf(batchObject.getStringDateofCompletion()),
                Float.parseFloat(batchObject.getStringSpeedforProduction()),
                QUEUED_STATUS
        );
    }

    //TODO: Implement method that gets the latest queued batch id. 
    // if queue is empty, look at the last produced batch.
    @Override
    public Integer getLatestBatchID() {
        SimpleSet batchSet = dbConnection.query("SELECT * FROM productionlist ORDER BY productionlistID DESC limit 1");
        if (batchSet.isEmpty()) {
            return null;
        } else {
            Batch batch = null;
            for (int i = 0; i < batchSet.getRows(); i++) {
                batch = new Batch(
                        String.valueOf(batchSet.get(i, "batchid")),
                        String.valueOf(batchSet.get(i, "productid")),
                        String.valueOf(batchSet.get(i, "productamount")),
                        String.valueOf(batchSet.get(i, "deadline")),
                        String.valueOf(batchSet.get(i, "speed"))
                );
            }
            return new Integer(batch.getStringBatchID());
        }
    }

    // private helper-method to convert simpleSet to arrayList
    private ArrayList<BatchReport> simpleSetToArrayList(SimpleSet set) {
        ArrayList<BatchReport> list = new ArrayList<>();
        set = new SimpleSet();
        for (int i = 0; i < set.getRows(); i++) {
            BatchReport br = new BatchReport(
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

            list.add(br);
        }

        return list;
    }

}
