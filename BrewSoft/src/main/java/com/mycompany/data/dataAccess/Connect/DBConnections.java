/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess.Connect;

import com.mycompany.breweryWorker.domain.MachineConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEKSTUD
 */
public class DBConnections {

    private final int port = 5432;
    private final String url = "jdbc:postgresql://tek-mmmi-db0a.tek.c.sdu.dk:" + port + "/si3_2019_group_2_db";
    private final String username = "si3_2019_group_2";
    private final String password = "did3+excises";

    protected Connection dbConnection = null;
    protected Statement dbStatement = null;
    protected PreparedStatement dbPreparedStatement = null;
    protected ResultSet dbResultSet = null;

    public DBConnections() {
        
    }

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            dbConnection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to brewSoft Database succesfull");
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
    }

    public void connect(String hostname, String dbname, int port, String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            dbConnection = DriverManager.getConnection("jdbc:postgresql://"
                    + hostname + ":" + port + "/" + dbname, username, password);
            System.out.println("Connection to brewSoft Database succesfull");
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
            Logger.getLogger(MachineConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
    }

    public void disconnect() {
        try {
            if (dbStatement != null) {
                dbStatement.close();
            }
            if (dbPreparedStatement != null) {
                dbPreparedStatement.close();
            }
            if (dbResultSet != null) {
                dbResultSet.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }

            System.out.println("Disconnected from BrewSoft Database");
        } catch (SQLException ex) {
            Logger.getLogger(MachineConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("A database access error occured");
        }
    }

    public static void main(String[] args) {
        DBConnections dbconn = new DBConnections();
        dbconn.connect();
    }

}
