package com.mycompany.crossCutting.objects;

public class MachineState {

    private String productionListID;
    private String timeInStateID;
    private String MachineStateID;
    private String timeInStates;
    
    public MachineState(String productionListID, String timeInStateID, String MachineStateID, String timeInStates) {
        this.productionListID = productionListID;
        this.timeInStateID = timeInStateID;
        this.MachineStateID = MachineStateID;
        this.timeInStates = timeInStates;
    }

    public String getProductionListID() {
        return productionListID;
    }

    public String getTimeInStateID() {
        return timeInStateID;
    }

    public String getMachineStateID() {
        return MachineStateID;
    }

    public String getTimeInStates() {
        return timeInStates;
    }
    

    

}
