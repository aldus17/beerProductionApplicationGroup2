package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.MachineState;
import java.util.ArrayList;

public interface IBatchDataHandler {

     public void insertBatchToQueue(Batch batch);

     public ArrayList<Batch> getQueuedBatches();

     public Integer getLatestBatchID();
     public MachineState getMachineState(String prodListID);
    
}
