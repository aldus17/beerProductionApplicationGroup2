package com.mycompany.crossCutting.objects;

import java.util.Comparator;

public class MachineState implements Comparator<MachineState> {

    private int machinestateID;
    private String timeInState;

    public MachineState() {
    }

    public MachineState(int machinestateID, String timeInState) {
        this.machinestateID = machinestateID;
        this.timeInState = timeInState;

    }

    public int getMachinestateID() {
        return machinestateID;
    }

    public String getTimeInState() {
        return timeInState;
    }

    @Override
    public int compare(MachineState o1, MachineState o2) {
        return o1.getMachinestateID() - o2.getMachinestateID();
    }

    @Override
    public String toString() {
        return "MachineState{" + "machinestateID=" + machinestateID + ", timeInState=" + timeInState + '}';
    }
}
