package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.MachineTempData;
import com.mycompany.crossCutting.objects.OeeObject;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.dataAccess.Connect.TestDatabase;
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
    public BatchDataHandler(TestDatabase testDatabase) {
        dbConnection = new TestDatabase();
    }

    public BatchDataHandler(TestDatabase testDatabase) {
        dbConnection = testDatabase;
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
            queuedbatches.add(new Batch(
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
    public List<MachineState> getMachineState(int prodListID, int machineID) {

        SimpleSet stateSet = dbConnection.query("SELECT * "
                + "FROM timeinstate "
                + "WHERE productionlistid =? "
                + "AND starttimeinstate IS NOT NULL "
                + "ORDER BY starttimeinstate ASC; ", prodListID);
        SimpleSet stateSet2 = dbConnection.query("SELECT * FROM timeinstate WHERE productionlistid = ( "
                + "SELECT productionlistid "
                + "FROM finalbatchinformation "
                + "WHERE finalbatchinformationid > (SELECT finalbatchinformationid FROM finalbatchinformation WHERE productionlistid =? AND brewerymachineid =?) "
                + "AND brewerymachineid =? "
                + "ORDER BY finalbatchinformationid ASC LIMIT 1) "
                + "AND starttimeinstate IS NOT NULL "
                + "ORDER BY starttimeinstate ASC LIMIT 1;", prodListID, machineID, machineID);
        if (stateSet.isEmpty()) {
            throw new NullPointerException("StateSet is empty, check productionInfo table if productionListID + " + prodListID + " contains machine data");
        } else {
            MachineState machineState = new MachineState();
            List<MachineState> listOfStateObjects = new ArrayList<>();
            for (int i = 0; i < stateSet.getRows(); i++) {
                listOfStateObjects.add(machineState = new MachineState(
                        (int) stateSet.get(i, "machinestateid"),
                        String.valueOf(stateSet.get(i, "starttimeinstate"))
                ));
            }
            for (int i = 0; i < stateSet2.getRows(); i++) {
                listOfStateObjects.add(machineState = new MachineState(
                        (int) stateSet2.get(i, "machinestateid"),
                        String.valueOf(stateSet2.get(i, "starttimeinstate"))
                ));
            }
            return listOfStateObjects;
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
        SimpleSet reportSet = dbConnection.query("SELECT pl.batchid,"
                + "fbi.brewerymachineid, fbi.deadline, fbi.dateofcreation,"
                + "fbi.dateofcompletion, pt.productname, fbi.totalcount, "
                + "fbi.defectcount, fbi.acceptedcount "
                + "FROM finalbatchinformation AS fbi, productionlist AS pl, producttype AS pt "
                + "WHERE fbi.productionlistid = pl.productionlistid "
                + "AND pl.batchid =? "
                + "AND fbi.brewerymachineid =? "
                + "AND pt.productid = fbi.productid; ",
                batchID, machineID);
        if (reportSet.isEmpty()) {
            throw new NullPointerException("StateSet is empty, error at BatchDataHandler:getBatchReportProductionData");
        } else {
            BatchReport batchReport = new BatchReport();
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < reportSet.getRows(); i++) {
                batchReport = new BatchReport(
                        (int) reportSet.get(i, "batchid"),
                        (int) reportSet.get(i, "brewerymachineid"),
                        String.valueOf(reportSet.get(i, "deadline")),
                        String.valueOf(reportSet.get(i, "dateofcreation")),
                        String.valueOf(reportSet.get(i, "dateofcompletion")),
                        String.valueOf(reportSet.get(i, "productname")),
                        (int) reportSet.get(i, "totalcount"),
                        (double) reportSet.get(i, "defectcount"),
                        (double) reportSet.get(i, "acceptedcount")
                );
            }
            return batchReport;
        }
    }

    @Override
    public List<MachineTempData> getMachineTempData(int prodID, int machineID) {
        SimpleSet prodInfoDataSet = dbConnection.query("SELECT DISTINCT pl.brewerymachineid, pl.temperature "
                + "FROM productioninfo AS pl, finalbatchinformation AS fbi "
                + "WHERE pl.productionlistid =? AND "
                + "pl.brewerymachineid =? AND "
                + "fbi.productionlistid = pl.productionlistid; ",
                prodID, machineID);
        if (prodInfoDataSet.isEmpty()) {
            throw new NullPointerException("StateSet is empty, error at BatchDataHandler:getMachineTempData");
        } else {
            MachineTempData machineTempData = new MachineTempData();
            List<MachineTempData> machineTempDataList = new ArrayList<>();
            for (int i = 0; i < prodInfoDataSet.getRows(); i++) {
                machineTempDataList.add(machineTempData = new MachineTempData(
                        (int) prodInfoDataSet.get(i, "brewerymachineid"),
                        (double) prodInfoDataSet.get(i, "temperature"))
                );
            }
            return machineTempDataList;
        }
    }

    @Override
    public List<MachineHumiData> getMachineHumiData(int prodID, int machineID) {
        SimpleSet prodInfoDataSet = dbConnection.query("SELECT DISTINCT pl.brewerymachineid, pl.humidity "
                + "FROM productioninfo AS pl, finalbatchinformation AS fbi "
                + "WHERE pl.productionlistid =? AND "
                + "pl.brewerymachineid =? AND "
                + "fbi.productionlistid = pl.productionlistid; ",
                prodID, machineID);
        if (prodInfoDataSet.isEmpty()) {
            throw new NullPointerException("StateSet is empty, error at BatchDataHandler:getMachineHumiData");
        } else {
            MachineHumiData machineHumiData = new MachineHumiData();
            List<MachineHumiData> machineHumiDataList = new ArrayList<>();
            for (int i = 0; i < prodInfoDataSet.getRows(); i++) {
                machineHumiDataList.add(machineHumiData = new MachineHumiData(
                        (int) prodInfoDataSet.get(i, "brewerymachineid"),
                        (double) prodInfoDataSet.get(i, "humidity"))
                );
            }
            return machineHumiDataList;
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
                    (int) set.get(i, "acceptedCount")
            );
            list.add(br);
        }
        return list;
    }

    @Override
    public List<BeerTypes> getBeerTypes() {
        List<BeerTypes> beerTypeList = new ArrayList<>();
        SimpleSet beerTypes = dbConnection.query("SELECT * FROM producttype");

        for (int i = 0; i < beerTypes.getRows(); i++) {
            beerTypeList.add(new BeerTypes(
                    (int) beerTypes.get(i, "productid"),
                    String.valueOf(beerTypes.get(i, "productname")),
                    Float.parseFloat(String.valueOf(beerTypes.get(i, "speed")))
            ));
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
        set = dbConnection.query("SELECT fbi.productid, fbi.acceptedcount, "
                + "pt.idealcycletime FROM finalbatchinformation AS fbi, "
                + "producttype AS pt WHERE fbi.dateofcompletion = ? AND "
                + "fbi.productid = pt.productid", dateofcompleation);

        for (int i = 0; i < set.getRows(); i++) {
            list.add(new OeeObject(
                    (int) set.get(i, "productid"),
                    Float.parseFloat(String.valueOf(set.get(i, "acceptedcount"))),
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
            completedbatches.add(new Batch(
                    (int) set.get(i, "productionlistid"),
                    (int) set.get(i, "batchid"),
                    (int) set.get(i, "brewerymachineid"),
                    (int) set.get(i, "productid"),
                    String.valueOf(set.get(i, "dateofcreation")),
                    String.valueOf(set.get(i, "deadline")),
                    String.valueOf(set.get(i, "dateofcompletion")),
                    (int) set.get(i, "totalcount"),
                    Float.parseFloat(String.valueOf(set.get(i, "acceptedcount"))),
                    Float.parseFloat(String.valueOf(set.get(i, "defectcount")))
            ));
        }
        return completedbatches;
    }
}
