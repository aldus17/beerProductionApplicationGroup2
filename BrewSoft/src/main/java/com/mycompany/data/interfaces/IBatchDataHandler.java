package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;

public interface IBatchDataHandler {
 
     //public void insertBatchToQueue(int typeofProduct, int amountToProduce, double speed, LocalDate deadline);
     public void insertBatchToQueue(Batch batch);
     
     public Integer getLatestBatchID();
     
    
}
