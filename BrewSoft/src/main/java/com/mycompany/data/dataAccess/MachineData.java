/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEKSTUD
 */
public class MachineData {

    DBConnection dbconn;

    public MachineData() {
        dbconn = new DBConnection();
    }

    public void saveMachine() {
        String query = "INSERT INTO Machine "
                + "(hostname, port)"
                + "VALUES"
                + "(?, ?)";
        
    }

    public void selectMachine(String machineHostName, int port) {

        PreparedStatement fetchMachine = null;

        try {
            String query = "SELECT * FROM Machine";
            fetchMachine = dbconn.dbConnection.prepareStatement(query);
            dbconn.connect();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
