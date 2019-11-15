package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import java.util.ArrayList;

public interface IBatchDataHandler {

     //public void insertBatchToQueue(int typeofProduct, int amountToProduce, double speed, LocalDate deadline);
     public void insertBatchToQueue(Batch batch);

     public ArrayList<Batch> getQueuedBatches();

     public Integer getLatestBatchID();


}
