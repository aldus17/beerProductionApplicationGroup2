/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.sql.Date;
import java.util.TreeMap;

/**
 *
 * @author jacob
 */
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

    @Override
    public MachineState getMachineState(String prodListID) {

        SimpleSet stateSet = dbConnection.query("SELECT tis.machinestateid, tis.starttimeinstate "
                + "FROM timeinstate AS tis, productionlist AS pl "
                + "WHERE pl.productionlistid = 410;");

        if (stateSet.isEmpty()) {
            return null;
        } else {
            MachineState machineState = null;
            TreeMap<Integer, String> map = new TreeMap<>();
            for (int i = 0; i < stateSet.getRows(); i++) {
                machineState = new MachineState(
                       String.valueOf(batchSet.get(i, "speed"))
                );
            }
            return machineState;
        }
    }
    public static void main(String[] args) {
        BatchDataHandler b = new BatchDataHandler();
        b.getMachineState("410");
        System.out.println(b.getMachineState("410").getTimeInStates());
    }

}
