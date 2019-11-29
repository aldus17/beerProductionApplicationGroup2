package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.MachineState;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IBatchDataHandler {

     public void insertBatchToQueue(Batch batch);

     public ArrayList<Batch> getQueuedBatches();

     public Integer getLatestBatchID();
     public MachineState getMachineState(String prodListID);
     
     public void editQueuedBatch (Batch batch);
     public List getAcceptedCount (LocalDate dateofcompleation);
    
}
