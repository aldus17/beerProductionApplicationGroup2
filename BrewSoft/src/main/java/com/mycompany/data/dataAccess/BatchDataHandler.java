package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.MachineTempData;
import com.mycompany.crossCutting.objects.OeeObject;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.data.interfaces.IManagementData;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatchDataHandler implements IBatchDataHandler, IManagementData {

    private final String QUEUED_STATUS = "queued";
    private DatabaseConnection dbConnection;

    public BatchDataHandler() {
        dbConnection = new DatabaseConnection();
    }

    @Override
    public void insertBatchToQueue(Batch batch) {
        Batch batchObject = batch;
        dbConnection.queryUpdate("INSERT INTO ProductionList "
                + "(batchid, productid, productamount, deadline, speed, status)"
                + "VALUES(?,?,?,?,?,?)",
                batchObject.getBatchID(),
                batchObject.getType(),
                batchObject.getTotalAmount(),
                Date.valueOf(batchObject.getDeadline()),
                batchObject.getSpeedforProduction(),
                QUEUED_STATUS.toLowerCase()
        );
    }

    public ArrayList<Batch> getQueuedBatches() {
        ArrayList<Batch> queuedbatches = new ArrayList<>();
        SimpleSet set = dbConnection.query("SELECT * FROM Productionlist WHERE status=?", QUEUED_STATUS);
        for (int i = 0; i < set.getRows(); i++) {
            queuedbatches.add(
                    new Batch(
                            (int) set.get(i, "productionlistid"),
                            (int) set.get(i, "batchid"),
                            (int) set.get(i, "productid"),
                            (int) set.get(i, "productamount"),
                            String.valueOf(set.get(i, "deadline")),
                            Float.parseFloat(String.valueOf(set.get(i, "speed"))),
                            String.valueOf(set.get(i, "dateofcreation"))
                    ));
        }
        return queuedbatches;
    }

    @Override
    public Integer getLatestBatchID() {
        SimpleSet batchSet = dbConnection.query("SELECT * FROM productionlist ORDER BY productionlistID DESC limit 1");
        if (batchSet.isEmpty()) {
            return null;
        } else {
            Batch batch = null;
            for (int i = 0; i < batchSet.getRows(); i++) {
                batch = new Batch(
                        (int) batchSet.get(i, "batchid"),
                        (int) batchSet.get(i, "productid"),
                        (int) batchSet.get(i, "productamount"),
                        String.valueOf(batchSet.get(i, "deadline")),
                        Float.parseFloat(String.valueOf(batchSet.get(i, "speed")))
                );
            }
            return batch.getBatchID();
        }
    }

    @Override
    public MachineState getMachineState(int prodListID) {

        /* TEST QUERY IN DATABASE
        SELECT tis.machinestateid, tis.starttimeinstate, pl.productionlistid
        FROM timeinstate AS tis, productionlist AS pl
        WHERE pl.productionlistid = 110
        ORDER BY starttimeinstate ASC;

        SELECT *
        FROM timeinstate AS tis
        WHERE tis.productionlistid = 109
        ORDER BY starttimeinstate ASC;
         */
        SimpleSet stateSet1 = dbConnection.query("SELECT * "
                + "FROM timeinstate "
                + "WHERE productionlistid =? "
                + "AND starttimeinstate IS NOT NULL "
                + "ORDER BY starttimeinstate ASC; ", prodListID);

        /*
        SELECT fbi.dateofcompletion, tis.machinestateid
        FROM finalbatchinformation AS fbi, timeinstate AS tis
        WHERE tis.productionlistid = (
            SELECT machinestateid
            FROM timeinstate AS tis2, finalbatchinformation AS fbi2
            WHERE tis2.productionlistid = fbi2.productionlistid)
        AND tis.brewerymachineid = fbi.brewerymachineid
         */
        if (stateSet1.isEmpty()) {
            System.out.println("stateSet is empty");
            return null;
        } else {
            MachineState machineState = new MachineState("", "");
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < stateSet1.getRows(); i++) {
                list.add(
                        machineState = new MachineState(
                                String.valueOf(stateSet1.get(i, "machinestateid")),
                                String.valueOf(stateSet1.get(i, "starttimeinstate"))
                        ));
            }
            machineState.setStateObjList(list);
            return machineState;
        }
    }

    /*
    After a finished batch production the MES must be able to produce a batch report of the produced batch.
    The batch report must minimum contain the following.
    • Batch ID.
    • Product type.
    • Amount of products (total, defect and acceptable).
    • Amount of time used in the different states.
    • Logging of temperature over the production time.
    • Logging of humidity over the production time.
    The batch report could be in PDF or dashboard style format. The data can be presented in various charts
    or in tables.
     */
    @Override
    public BatchReport getBatchReportProductionData(int batchID, int machineID) {
        SimpleSet reportSet = dbConnection.query("SELECT pl.batchid, fbi.brewerymachineid, fbi.deadline, fbi.dateofcreation, fbi.dateofcompletion, pt.productname, fbi.totalcount, fbi.defectcount, fbi.acceptedcount "
                + "FROM finalbatchinformation AS fbi, productionlist AS pl, producttype AS pt "
                + "WHERE fbi.productionlistid = pl.productionlistid "
                + "AND pl.batchid = ? "
                + "AND fbi.brewerymachineid = ? "
                + "AND pt.productid = fbi.productid; ",
                batchID, machineID);
        if (reportSet.isEmpty()) {
            return null;
        } else {
            BatchReport batchReport = new BatchReport();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < reportSet.getRows(); i++) {
                batchReport = new BatchReport(
                        Integer.valueOf(String.valueOf(reportSet.get(i, "batchid"))),
                        Integer.valueOf(String.valueOf(reportSet.get(i, "brewerymachineid"))),
                        String.valueOf(reportSet.get(i, "deadline")),
                        String.valueOf(reportSet.get(i, "dateofcreation")),
                        String.valueOf(reportSet.get(i, "dateofcompletion")),
                        String.valueOf(reportSet.get(i, "productname")),
                        Integer.valueOf(String.valueOf(reportSet.get(i, "totalcount"))),
                        Integer.valueOf(String.valueOf(reportSet.get(i, "defectcount"))),
                        Integer.valueOf(String.valueOf(reportSet.get(i, "acceptedcount")))
                );
            }
            return batchReport;
        }
    }

    /*
    SELECT DISTINCT pl.brewerymachineid, pl.temperature, pl.humidity
    FROM productioninfo AS pl
    WHERE pl.productionlistid = 195 AND pl.brewerymachineid = 1
    GROUP BY pl.temperature, pl.humidity
     */
    public MachineTempData getMachineTempData(int prodID, int machineID) {
        SimpleSet prodInfoDataSet = dbConnection.query("SELECT DISTINCT pl.brewerymachineid, pl.temperature "
                + "FROM productioninfo AS pl, finalbatchinformation AS fbi "
                + "WHERE pl.productionlistid =? AND "
                + "pl.brewerymachineid =? AND "
                + "fbi.productionlistid = pl.productionlistid; ",
                prodID, machineID);
        if (prodInfoDataSet.isEmpty()) {
            return null;
        } else {
            MachineTempData machineTempData = new MachineTempData();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < prodInfoDataSet.getRows(); i++) {
                list.add(machineTempData = new MachineTempData(
                        Integer.valueOf(String.valueOf(prodInfoDataSet.get(i, "brewerymachineid"))),
                        Double.valueOf(String.valueOf(prodInfoDataSet.get(i, "temperature"))))
                );
            }
            machineTempData.setMachineTempDataObjList(list);
            return machineTempData;
        }
    }

    public MachineHumiData getMachineHumiData(int prodID, int machineID) {
        SimpleSet prodInfoDataSet = dbConnection.query("SELECT DISTINCT pl.brewerymachineid, pl.humidity "
                + "FROM productioninfo AS pl, finalbatchinformation AS fbi "
                + "WHERE pl.productionlistid =? AND "
                + "pl.brewerymachineid =? AND "
                + "fbi.productionlistid = pl.productionlistid; ",
                prodID, machineID);
        if (prodInfoDataSet.isEmpty()) {
            return null;
        } else {
            MachineHumiData machineHumiData = new MachineHumiData();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < prodInfoDataSet.getRows(); i++) {
                list.add(machineHumiData = new MachineHumiData(
                        Integer.valueOf(String.valueOf(prodInfoDataSet.get(i, "brewerymachineid"))),
                        Double.valueOf(String.valueOf(prodInfoDataSet.get(i, "humidity"))))
                );
            }
            machineHumiData.setMachineHumiDataObjList(list);
            return machineHumiData;
        }
    }

    // private helper-method to convert simpleSet to arrayList
    private ArrayList<BatchReport> simpleSetToArrayList(SimpleSet set) {
        ArrayList<BatchReport> list = new ArrayList<>();
        set = new SimpleSet();
        for (int i = 0; i < set.getRows(); i++) {
            BatchReport br = new BatchReport(
                    (int) set.get(i, "batchID"),
                    (int) set.get(i, "BreweryMachineID"),
                    set.get(i, "deadline").toString(),
                    set.get(i, "dateOfCreation").toString(),
                    set.get(i, "dateOfCompletion").toString(),
                    set.get(i, "productType").toString(),
                    (int) set.get(i, "totalCount"),
                    (int) set.get(i, "defectCount"),
                    (int) set.get(i, "acceptedCount"));

            list.add(br);
        }

        return list;
    }

    @Override
    public List<BeerTypes> getBeerTypes() {
        List<BeerTypes> beerTypeList = new ArrayList<>();
        SimpleSet beerTypes = dbConnection.query("SELECT * FROM producttype");

        for (int i = 0; i < beerTypes.getRows(); i++) {
            beerTypeList.add(
                    new BeerTypes(
                            (int) beerTypes.get(i, "productid"),
                            String.valueOf(beerTypes.get(i, "productname")),
                            Float.parseFloat(String.valueOf(beerTypes.get(i, "speed")))
                    )
            );
        }
        return beerTypeList;
    }

    @Override
    public void editQueuedBatch(Batch batch) {
        dbConnection.queryUpdate("UPDATE productionlist SET batchid = ?, productid = ?, "
                + "productamount = ? ,deadline =?, speed =? WHERE productionlistid =?",
                (int) batch.getBatchID(),
                (int) batch.getType(),
                (float) batch.getTotalAmount(),
                Date.valueOf(batch.getDeadline()),
                (float) batch.getSpeedforProduction(),
                (int) batch.getProductionListID());
    }

    @Override
    public List getAcceptedCount(LocalDate dateofcompleation) {
        List list = new ArrayList<>();
        SimpleSet set;
        set = dbConnection.query("SELECT fbi.productid, fbi.acceptedcount, pt.idealcycletime FROM finalbatchinformation AS fbi, producttype AS pt WHERE fbi.dateofcompletion = ? AND fbi.productid = pt.productid", dateofcompleation);

        for (int i = 0; i < set.getRows(); i++) {
            list.add(new OeeObject(
                    (int) set.get(i, "productid"),
                    (int) set.get(i, "acceptedcount"),
                    (double) set.get(i, "idealcycletime")));
        }
        return list;
    }

    @Override
    public ArrayList<Batch> getCompletedBatches() {
        ArrayList<Batch> completedbatches = new ArrayList<>();
        SimpleSet set = dbConnection.query("SELECT pl.batchid, fb.* "
                + "FROM productionlist AS pl, finalbatchinformation as fb "
                + "WHERE pl.productionlistid = fb.productionlistid;");
        for (int i = 0; i < set.getRows(); i++) {
            completedbatches.add(
                    new Batch(
                            (int) set.get(i, "productionlistid"),
                            (int) set.get(i, "batchid"),
                            (int) set.get(i, "brewerymachineid"),
                            (int) set.get(i, "productid"),
                            String.valueOf(set.get(i, "dateofcreation")),
                            String.valueOf(set.get(i, "deadline")),
                            String.valueOf(set.get(i, "dateofcompletion")),
                            (int) set.get(i, "totalcount"),
                            (float) set.get(i, "acceptedcount"),
                            (float) set.get(i, "defectcount")
                    )
            );
        }
        return completedbatches;
    }

}
