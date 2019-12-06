package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.TemporaryProductionBatch;

public interface IMachineSubscriberDataHandler {

    public void insertProductionInfo(int productionListID, int BreweryMachineID, float humidity, float temperature);

    public void insertTimesInStates(int ProductionListID, int BreweryMachineID, int MachinestatesID);

    public void insertStopsDuringProduction(int ProductionListID, int BreweryMachineID, int stopReasonsID);

    public void insertFinalBatchInformation(int ProductionListID, int BreweryMachineID,
            String deadline, String dateOfCreation, int productID, float totalCount, int defectCount, int acceptedCount);

    public void insertStoppedProductionToTempTable(TemporaryProductionBatch tempBatch);

    public void changeProductionListStatus(int productionListID, String newStatus);

    public Batch getNextBatch();

}
