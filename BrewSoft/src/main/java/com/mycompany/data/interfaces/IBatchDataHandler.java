package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.MachineTempData;
import java.util.ArrayList;

public interface IBatchDataHandler {

     public void insertBatchToQueue(Batch batch);

     public ArrayList<Batch> getQueuedBatches();

     public Integer getLatestBatchID();
     public MachineState getMachineState(int prodListID);
     
     public MachineTempData getMachineTempData(int prodID, int machineID);
     public MachineHumiData getMachineHumiData(int prodID, int machineID);
     
    // public MachineData getMachineTempData(int prodID, int machineID);
     public BatchReport getBatchReportProductionData(int batchID, int machineID);
    
};

