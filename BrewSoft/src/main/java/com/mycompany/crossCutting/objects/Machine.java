package com.mycompany.crossCutting.objects;

public class Machine {

    private String hostname;
    private int port;
    private final int machineID;

    public Machine(int machineID, String hostname, int port) {
        this.machineID = machineID;
        this.hostname = hostname;
        this.port = port;
    }

    public int getMachineID() {
        return machineID;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

}
