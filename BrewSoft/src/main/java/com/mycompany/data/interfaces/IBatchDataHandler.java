package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.MachineTempData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IBatchDataHandler {

    public void insertBatchToQueue(Batch batch);

    public ArrayList<Batch> getQueuedBatches();

    public Integer getLatestBatchID();

    public List<MachineState> getMachineState(int prodListID, int machineID);

    public List<MachineTempData> getMachineTempData(int prodID, int machineID);

    public List<MachineHumiData> getMachineHumiData(int prodID, int machineID);

    // public MachineData getMachineTempData(int prodID, int machineID);
    public BatchReport getBatchReportProductionData(int batchID, int machineID);

    public void editQueuedBatch(Batch batch);

    public List getAcceptedCount(LocalDate dateofcompleation);
    
    public List<BeerTypes> getBeerTypes();
}
