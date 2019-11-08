/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
//ldsc.fromString(batchObject.getStringDateofCompletion())
    @Override
    public void insertBatchToQueue(Batch batch) {
        Batch batchObject = batch;
        dbConnection.queryUpdate("INSERT INTO ProductionList VALUES(?,?,?,?,?,?)",
                Integer.parseInt(batchObject.getStringBatchID()),
                Float.parseFloat(batchObject.getStringType()),
                Float.parseFloat(batchObject.getStringTotalAmount()),
                Date.valueOf(batchObject.getStringDateofCompletion()),
                Float.parseFloat(batchObject.getStringSpeedforProduction()),
                QUEUED_STATUS
        );
    }

}
