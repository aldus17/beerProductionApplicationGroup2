package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.TemporaryProductionBatch;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IMachineSubscriberDataHandler;
import java.sql.Date;
import java.time.LocalDate;

public class MachineSubscribeDataHandler implements IMachineSubscriberDataHandler {

    public DatabaseConnection connection;

    public MachineSubscribeDataHandler() {
        connection = new DatabaseConnection();
    }

    @Override
    public void insertProductionInfo(int productionListID, int BreweryMachineID, float humidity, float temperature) {
        connection.queryUpdate("INSERT INTO ProductionInfo(productionListID, breweryMachineID, humidity, temperature) VALUES (?,?,?,?)",
                productionListID, BreweryMachineID, humidity, temperature);
    }

    @Override
    public void insertTimesInStates(int ProductionListID, int BreweryMachineID, int MachinestateID) {
        connection.queryUpdate("INSERT INTO timeInstate (productionListID, breweryMachineID, machineStateID) VALUES (?,?,?)",
                ProductionListID, BreweryMachineID, MachinestateID);
    }

    @Override
    public void insertStopsDuringProduction(int ProductionListID, int BreweryMachineID, int stopReasonID) {
        connection.queryUpdate("INSERT INTO stopDuringProduction (ProductionListID, BreweryMachineID, stopReasonID) VALUES (?,?,?)",
                ProductionListID, BreweryMachineID, stopReasonID);
    }

    public void insertProductionList(int batchID, int productID, float productAmount, String priority, float speed, String status) {
        connection.queryUpdate("INSERT INTO productionList (batchID, productID, productAmount, priority, speed, status) values (?,?,?,?,?,?)",
                batchID, productID, productAmount, priority, speed, status);
    }

    @Override
    public void insertFinalBatchInformation(int ProductionListID, int BreweryMachineID, String deadline, String dateOfCreation, int productID, float totalCount, int defectCount, int acceptedCount) {
        System.out.println("datahandler");
        connection.queryUpdate("INSERT INTO finalBatchInformation (ProductionListID, BreweryMachineID, deadline, dateOfCreation, productID, totalCount, defectCount, acceptedCount) values(?,?,?,?,?,?,?,?)",
                ProductionListID,
                BreweryMachineID,
                Date.valueOf(deadline),
                Date.valueOf(dateOfCreation),
                productID,
                totalCount,
                defectCount,
                acceptedCount);
    }

    @Override
    public Batch getNextBatch() {
        Batch batch = null;
        SimpleSet batchSet = connection.query("SELECT * FROM productionlist WHERE status = 'Queued' OR status = 'stopped' ORDER BY deadline ASC limit 1"); // hent queue

        if (batchSet.isEmpty()) {
            return null;
        } else if (batchSet.get(0, "status") == "stopped") {
            TemporaryProductionBatch tpb = getTemporaryProductionBatch((int) batchSet.get(0, "productionlistid"));
            for (int i = 0; i < batchSet.getRows(); i++) {
                batch = new Batch(
                        String.valueOf(batchSet.get(i, "productionListID")),
                        String.valueOf(batchSet.get(i, "batchid")),
                        String.valueOf(batchSet.get(i, "productid")),
                        String.valueOf(((int) batchSet.get(i, "productamount")) - tpb.getAcceptedCount()),
                        String.valueOf(batchSet.get(i, "deadline")),
                        String.valueOf(batchSet.get(i, "speed")),
                        String.valueOf(batchSet.get(i, "dateofcreation"))
                );
            }
        } else {
            for (int i = 0; i < batchSet.getRows(); i++) {
                batch = new Batch(
                        String.valueOf(batchSet.get(i, "productionListID")),
                        String.valueOf(batchSet.get(i, "batchid")),
                        String.valueOf(batchSet.get(i, "productid")),
                        String.valueOf(batchSet.get(i, "productamount")),
                        String.valueOf(batchSet.get(i, "deadline")),
                        String.valueOf(batchSet.get(i, "speed")),
                        String.valueOf(batchSet.get(i, "dateofcreation"))
                );
            }
        }
        return batch;
    }

    @Override
    public void changeProductionListStatus(int productionListID, String newStatus) {
        connection.queryUpdate("UPDATE productionList SET status = ? WHERE productionListID = ?",
                newStatus,
                productionListID);
    }

    @Override
    public void insertStoppedProductionToTempTable(TemporaryProductionBatch tempBatch) {
        connection.queryUpdate("INSERT INTO temporaryproduction (productionlistid, acceptedcount,defectcount,dateforstop) VALUES (?,?,?,?)",
                tempBatch.getProductionListId(),
                tempBatch.getAcceptedCount(),
                tempBatch.getDefectCount(),
                tempBatch.getDateForStop());
    }

    private TemporaryProductionBatch getTemporaryProductionBatch(int productionlistid) {
        TemporaryProductionBatch tpb = null;
        SimpleSet set = connection.query("SELECT tp.*, pl.productamount FROM temporaryproduction AS tp, productiomlist AS pl WHERE tp.productionlistid = ?", productionlistid); // hent queue
        if (set.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < set.getRows(); i++) {
                tpb = new TemporaryProductionBatch(
                        productionlistid,
                        (float) set.get(i, "acceptedcount"),
                        (float) set.get(i, "defectcount"),
                        (float) set.get(i, "totalcount"),
                        (LocalDate) set.get(i, "dateforstop"));
            }
            return tpb;
        }
    }
}
