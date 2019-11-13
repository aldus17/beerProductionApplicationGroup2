package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;

public interface IMachineSubscriberDataHandler {

    public void insertProductionInfo(int productionListID, int BreweryMachineID, float humidity, float temperature);

    public void insertTimesInStates(int ProductionListID, int BreweryMachineID, String StartTimeInState, int MachinestatesID);

    public void insertStopsDuringProduction(int ProductionListID, int BreweryMachineID, int stopReasonsID);

    public void insertFinalBatchInformation(int ProductionListID, int BreweryMachineID,
            String deadline, String dateOfCreation, String dateOfCompleation, int productID, float totalCount, int defectCount, int acceptedCount);

    public void changeProductionListStatus(int productionListID, String newStatus);

    public Batch getNextBatch();
    
    

}
