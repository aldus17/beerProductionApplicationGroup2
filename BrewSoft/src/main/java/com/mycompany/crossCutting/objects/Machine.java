/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crossCutting.objects;

/**
 *
 * @author ALEKSTUD
 */
public class Machine {

    private String hostname;
    private int port;
    private int machineID;

    public Machine(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        

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
