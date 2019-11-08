/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private Random random = new Random();
    private DatabaseConnection dbConnection;

    //TODO: Implement connections, so that a new connection is not established on each call to the database. 
    public BatchDataHandler() {
        dbConnection = new DatabaseConnection();
    }

    public void insertBatchToQueue(int typeofProduct, int amountToProduce, double speed, LocalDate deadline) {
        dbConnection.queryUpdate("INSERT INTO ProductionList VALUES(?,?,?,?,?,?)", random.nextInt(65335), typeofProduct, amountToProduce, null, speed, QUEUED_STATUS);
//        try(PreparedStatement pstBatchToQueue = dbConnection.prepareStatement("INSERT INTO ProductionList VALUES(?,?,?,?,?,?")){
//            pstBatchToQueue.setInt(1,random.nextInt(65335)); //TODO: Implement batchID
//            pstBatchToQueue.setInt(2,typeofProduct);
//            pstBatchToQueue.setFloat(3, amountToProduce);
//            pstBatchToQueue.setDate(4, Date.valueOf(deadline));
//            pstBatchToQueue.setDouble(5, speed);
//            pstBatchToQueue.setString(6, QUEUED_STATUS);
//            pstBatchToQueue.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(BatchDataHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void main(String[] args) {
        BatchDataHandler bdh = new BatchDataHandler();
        bdh.insertBatchToQueue(2, 2000, 150.0, null);
    }
}
