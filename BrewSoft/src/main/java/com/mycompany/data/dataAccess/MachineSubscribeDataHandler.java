/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Machine;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ALEKSTUD
 */
public class MachineSubscribeDataHandler {

    private Machine machine;

    public DatabaseConnection connection;

    public MachineSubscribeDataHandler() {
        connection = new DatabaseConnection();
    }

    public void insertProductionInfoData(int machineID, int batchID, float humidity, float temperature) {
        connection.queryUpdate("INSERT INTO ProductionInfo(batchID, machineID, humidity, temperature) VALUES (?,?,?,?)", machineID, batchID, humidity, temperature);
    }

//    public void insert
//    
//    public boolean insertProductionInfoData(int machineID, int batchID) {
//
//        Boolean insertProdInfoUpdated = false;
//        String insertIntoQuery = "INSERT INTO ProductionInfo";
//        String valuesQuery = "VALUES (?,?,?,?,?)";
//        String query = insertIntoQuery + valuesQuery;
//        
//        
//        try {
//            PreparedStatement pstProdInfo = dbConnection.prepareStatement(query);
//            pstProdInfo.setInt(1, productionInfoID()); // TODO: Define what productionInfoID actually is, taken from a class via getter?
//            pstProdInfo.setInt(2, batchID);
//            pstProdInfo.setInt(3, machineID);
//            pstProdInfo.setFloat(4, 999); // TODO: get humidity subscription data 
//            pstProdInfo.setFloat(4, 999); // TODO: get temperature subscription data
//
//            int updateResult = pstProdInfo.executeUpdate();
//
//            if (updateResult > 0) {
//                insertProdInfoUpdated = true;
//            }
//        } catch (SQLException e) {
//            System.out.println("a database access error occurs on insertProductionInfoData");
//            return insertProdInfoUpdated;
//        }
//        return insertProdInfoUpdated;
//
//    }
//    public boolean updateProductionInfoData(int machineID, int batchID) {
//
//        Boolean prodInfoUpdated = false;
//
//        try {
//            connect();
//            String updateQuery = "UPDATE ProductionInfo";
//            String setQuery = "SET humidity = ?, temperature = ?";
//            String whereQuery = "WHERE machineID = ? AND batchID = ?;";
//            String query = updateQuery + setQuery + whereQuery;
//
//            PreparedStatement pstUpdateProdInfo = dbConnection.prepareStatement(query);
//
//            pstUpdateProdInfo.setFloat(1, 999); // TODO: Replace 999 with method that checks for humidity changes from machinesubscriber class
//            pstUpdateProdInfo.setFloat(2, 999); // TODO: Replace 999 with method that checks for Temperature changes from machinesubscriber class
//            pstUpdateProdInfo.setInt(3, machineID);
//            pstUpdateProdInfo.setInt(4, batchID);
//
//            int updateResult = pstUpdateProdInfo.executeUpdate();
//
//            if (updateResult > 0) {
//                prodInfoUpdated = true;
//            }
//        } catch (SQLException e) {
//            System.out.println("a database access error occurs on updateProductionInfoData");
//            return prodInfoUpdated;
//        }
//
//        return prodInfoUpdated;
//
//    }

    //public void insertFinalBatchInformationData() {

        // BEFORE the TODO: Wait until Aleksander H is done with refactoring the subscription class
        // as we need data methods to get specific datavalues to create the logic below
        // TODO: Under Brewer domain class make logic for what a complete batch is
        // Logic for productAmount not met by the production
        /**
         * If (machine state = COMPLETE && that the productionList productAmount
         * order = TotalCount) {
         *
         * read production info insert data to final batch report
         *
         * if (productAmount = TotalCount) { create new queue with defectCount
         * set to productAmount }
         *
         * }
         */
    //}

    // NOT TO BE USED
//    private int productionInfoID() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public boolean updateFinalBatchInfo() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//
//    }
//
//    public boolean insertFinalBatchInfo() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public boolean readFinalBatchInfo() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//
//    }

    /*
    public MachineState readMachineStates(int machineID) {
        
    }
     */
    public static void main(String[] args) {
        MachineSubscribeDataHandler mspaint = new MachineSubscribeDataHandler();
        mspaint.insertProductionInfoData(1, 2, 10.6f, 15.2f);
    }

}
