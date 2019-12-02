package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.MachineTempData;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BatchDataHandler implements IBatchDataHandler {

    private final String QUEUED_STATUS = "Queued";
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
                Integer.parseInt(batchObject.getBatchID().getValue()),
                Float.parseFloat(batchObject.getType().getValue()),
                Float.parseFloat(batchObject.getTotalAmount().getValue()),
                Date.valueOf(batchObject.getDeadline().getValue()),
                Float.parseFloat(batchObject.getSpeedforProduction().getValue()),
                QUEUED_STATUS
        );
    }

    public ArrayList<Batch> getQueuedBatches() {
        ArrayList<Batch> queuedbatches = new ArrayList<>();
        SimpleSet set = dbConnection.query("SELECT * FROM Productionlist WHERE status=?", QUEUED_STATUS);
        for (int i = 0; i < set.getRows(); i++) {
            queuedbatches.add(
                    new Batch(
                            String.valueOf(set.get(i, "batchid")),
                            String.valueOf(set.get(i, "productid")),
                            String.valueOf(set.get(i, "deadline")),
                            String.valueOf(set.get(i, "speed")),
                            String.valueOf(set.get(i, "productamount"))
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
            return new Integer(String.valueOf(batchSet.get(0, "batchid")));
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
                + "FROM productioninfo AS pl "
                + "WHERE pl.productionlistid = ? AND pl.brewerymachineid = ?; ",
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
                + "FROM productioninfo AS pl "
                + "WHERE pl.productionlistid = ? AND pl.brewerymachineid = ?; ",
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

    public static void main(String[] args) {
        BatchDataHandler b = new BatchDataHandler();
//        MachineState ms = b.getMachineState("410");
//        ManagementDomain md = new ManagementDomain();
//
//        for (Object o : ms.getStateObjList()) {
//            String s = o.toString();
//            System.out.println(s);
//        }
//
//        System.out.println("Test " + md.getDifferenceTimeInState("12:31:22", "13:40:49"));
//
//        BatchReport batchReport = b.getBatchReportProductionData(8, 1);
//        System.out.println("Test\n" + batchReport.toString());

        MachineTempData machineTempData = b.getMachineTempData(195, 1);
        for (Object o : machineTempData.getMachineTempDataObjList()) {
            String s = o.toString();
            System.out.println(s);
        }
        MachineHumiData machineHumiData = b.getMachineHumiData(195, 1);
        for (Object o : machineHumiData.getMachineHumiDataObjList()) {
            String s = o.toString();
            System.out.println(s);
        }

    }
}
