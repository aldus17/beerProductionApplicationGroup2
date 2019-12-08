package com.mycompany.crossCutting.objects;

import java.util.ArrayList;
import java.util.List;

public class MachineTempData {

    private int machineID;
    private double temperature;
    private List<Object> machineTempDataObjList;

    public MachineTempData() {
        this.machineTempDataObjList = new ArrayList<>();
    }

    public MachineTempData(int machineID, double temperature) {
        this.machineID = machineID;
        this.temperature = temperature;
        this.machineTempDataObjList = new ArrayList<>();
    }

    public List<Object> getMachineTempDataObjList() {
        return machineTempDataObjList;
    }

    public boolean addMachineDataObjects(Object o) {
        return machineTempDataObjList.add(o);
    }

    public void setMachineTempDataObjList(List<Object> list) {
        this.machineTempDataObjList = list;
    }

    public int getMachineID() {
        return machineID;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "MachineTempData{" + "machineID=" + machineID + ", temperature=" + temperature + '}';
    }
}
