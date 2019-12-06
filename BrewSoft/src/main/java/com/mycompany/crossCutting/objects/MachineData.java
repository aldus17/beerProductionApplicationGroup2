package com.mycompany.crossCutting.objects;

import java.util.ArrayList;
import java.util.List;

public class MachineData {

    private int machineID;
    private double humidity;
    private double temperature;
    private List<Object> machineDataObjList;

    public MachineData() {
        this.machineDataObjList = new ArrayList<>();
    }

    public MachineData(int machineID, double humidity, double temperature) {
        this.machineID = machineID;
        this.humidity = humidity;
        this.temperature = temperature;
        this.machineDataObjList = new ArrayList<>();
    }

    public List<Object> getMachineDataObjList() {
        return machineDataObjList;
    }

    public boolean addMachineDataObjects(Object o) {
        return machineDataObjList.add(o);
    }

    public void setMachineDataObjList(List<Object> list) {
        this.machineDataObjList = list;
    }

    public int getMachineID() {
        return machineID;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }
}
