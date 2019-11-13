/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateStringConverter;

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
   public List getQueuedBatches(){
       ArrayList <Batch> queuedbatches = new ArrayList<>();
       SimpleSet set = dbConnection.query("SELECT * FROM Productionlist WHERE status='"+QUEUED_STATUS+"'");
       for(int i = 0; i<set.getRows();i++){
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

}
