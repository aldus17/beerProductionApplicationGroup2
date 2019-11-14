package com.mycompany.crossCutting.objects;

import java.util.ArrayList;
import java.util.List;

public class MachineState {

    private String machinestateID;
    private String timeInState;
    private List<Object> stateObj;

    public MachineState(String machinestateID, String timeInState) {
        this.machinestateID = machinestateID;
        this.timeInState = timeInState;
        this.stateObj = new ArrayList<>();
    }

    public MachineState(String machinestateID, String timeInState, List<Object> stateObj) {
        this.machinestateID = machinestateID;
        this.timeInState = timeInState;
        this.stateObj = new ArrayList<>();
    }

    public void setStateObj(List<Object> stateObj) {
        this.stateObj = stateObj;
    }

    public String getMachinestateID() {
        return machinestateID;
    }

    public String getTimeInState() {
        return timeInState;
    }

    public List<Object> getStateObj() {
        return stateObj;
    }

    @Override
    public String toString() {
        return machinestateID + " " + timeInState;
    }

}
