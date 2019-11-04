/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Machine;
import com.mycompany.data.dataAccess.Connect.DBConnections;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEKSTUD
 */
public class MachineData extends DBConnections {

    DBConnections dbconn;

    public MachineData() {
        dbconn = new DBConnections();
    }

    public void insertMachine(Object machine) {

        PreparedStatement storeMachine;

        try {
            String query = "INSERT INTO Machine "
                    + "(hostname, port)"
                    + "VALUES"
                    + "(?, ?)";
            storeMachine = dbConnection.prepareStatement(query);
            if (machine instanceof Machine) {
                storeMachine.setString(1, ((Machine) machine).getHostname());
                storeMachine.setInt(2, ((Machine) machine).getPort());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MachineData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readMachine(int machineID) {

        PreparedStatement fetchMachine = null;
        String hostname = "";
        int port = 0;
        try {
            connect();
            String query = "SELECT * "
                    + "FROM Machine "
                    + "WHERE machineID = ?"
                    + ";";
            fetchMachine = dbConnection.prepareStatement(query);
            fetchMachine.setInt(1, machineID);
            dbResultSet = fetchMachine.executeQuery(query);

            while (dbResultSet.next()) {
                hostname = dbResultSet.getString("hostname");
                port = dbResultSet.getInt("port");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnections.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String[] args) {
        Machine m = new Machine("tek-mmmi-db0a.tek.c.sdu.dk", 5432);
        
        MachineData md = new MachineData();
        md.insertMachine(m);
        
        
    }
}
