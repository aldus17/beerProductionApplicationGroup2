package com.mycompany.crossCutting.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Machine {

    private StringProperty hostname;
    private IntegerProperty port;
    private IntegerProperty machineID;
    
    public Machine(Integer machineID, String hostname, Integer port) {
        this.machineID = new SimpleIntegerProperty(machineID);
        this.hostname = new SimpleStringProperty(hostname);
        this.port = new SimpleIntegerProperty(port);
    }
    
    public StringProperty hostnameProperty() {
        return this.hostname;
    }
    
    public IntegerProperty portProperty() {
        return this.port;
    }
    
    public IntegerProperty machineIDProperty() {
        return this.machineID;
    }

    public Integer getMachineID() {
        return machineID.getValue();
    }

    public String getHostname() {
        return hostname.getValue();
    }

    public void setHostname(String hostname) {
        this.hostname = new SimpleStringProperty(hostname);
    }

    public void setPort(int port) {
        this.port = new SimpleIntegerProperty(port);
    }

    public Integer getPort() {
        return port.getValue();
    }

    @Override
    public String toString() {
        return String.valueOf(getMachineID()) + hostname + String.valueOf(getPort()); //To change body of generated methods, choose Tools | Templates.
    }

    
}
