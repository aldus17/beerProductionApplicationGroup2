package com.mycompany.crossCutting.objects;

import java.util.Comparator;
import java.util.Objects;

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

//    // Below equals and hashCode is used to Test the contents of the list recieved from the Database
//    // which is then stored in a List, and in order to get a precise comparson on the objects
//    // equals and hascode methods needs to be used, otherwise the test will fail. 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MachineState machineState = (MachineState) o;
        return machinestateID == machineState.getMachinestateID()
                && Objects.equals(timeInState, machineState.timeInState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machinestateID, timeInState);
    }

}
