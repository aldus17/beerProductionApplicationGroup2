package com.mycompany.crossCutting.objects;

import java.util.TreeMap;

public class MachineState {

    private TreeMap<Integer, String> timeInStates;

    public MachineState(TreeMap<Integer, String> timeInStates) {
        this.timeInStates = timeInStates;
    }

    public TreeMap<Integer, String> getTimeInStates() {
        return timeInStates;
    }
    
    


}
