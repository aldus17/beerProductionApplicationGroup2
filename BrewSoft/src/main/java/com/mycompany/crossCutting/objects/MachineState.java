package com.mycompany.crossCutting.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MachineState implements Comparator<MachineState> {

    private String machinestateID;
    private String timeInState;
    private List<Object> stateObjList;

    public MachineState(String machinestateID, String timeInState) {
        this.machinestateID = machinestateID;
        this.timeInState = timeInState;
        this.stateObjList = new ArrayList<>();

    }

    public MachineState(String machinestateID, String timeInState, List<Object> stateObj) {
        this.machinestateID = machinestateID;
        this.timeInState = timeInState;
        this.stateObjList = new ArrayList<>();
    }

    public void setStateObjList(List<Object> stateObjList) {
        this.stateObjList = stateObjList;
    }

    public String getMachinestateID() {
        return machinestateID;
    }

    public String getTimeInState() {
        return timeInState;
    }

    public List<Object> getStateObjList() {
        return stateObjList;
    }

    @Override
    public String toString() {
        return machinestateID + " " + timeInState;
    }





    @Override
    public int compare(MachineState o1, MachineState o2) {
        return o1.getMachinestateID().compareTo(o2.getMachinestateID());
    }

}
