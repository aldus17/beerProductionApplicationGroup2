package com.mycompany.crossCutting.objects;

import java.util.ArrayList;
import java.util.List;

public class MachineHumiData {

    private int machineID;
    private double humidity;
    private List<Object> machineHumiDataObjList;

    public MachineHumiData() {
        this.machineHumiDataObjList = new ArrayList<>();
    }

    public MachineHumiData(int machineID, double humidity) {
        this.machineID = machineID;
        this.humidity = humidity;
        this.machineHumiDataObjList = new ArrayList<>();
    }

    public List<Object> getMachineHumiDataObjList() {
        return machineHumiDataObjList;
    }

    public boolean addMachineHumiDataObjList(Object o) {
        return machineHumiDataObjList.add(o);
    }

    public void setMachineHumiDataObjList(List<Object> list) {
        this.machineHumiDataObjList = list;
    }

    public int getMachineID() {
        return machineID;
    }

    public double getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "MachineHumiData{" + "machineID=" + machineID + ", humidity=" + humidity;
    }
}
