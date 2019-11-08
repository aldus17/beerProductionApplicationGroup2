package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Machine;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineData {

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
        Machine m = new Machine(1, "tek-mmmi-db0a.tek.c.sdu.dk", 5432);
        
        
        
        
    }
}
