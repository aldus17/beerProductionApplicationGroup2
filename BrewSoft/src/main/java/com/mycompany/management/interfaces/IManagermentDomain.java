package com.mycompany.management.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import java.time.LocalDate;
import java.util.List;

public interface IManagermentDomain {

    public void CreateBatch(int typeofProduct, int amounttoProduce, double speed, LocalDate deadline);
    public List<Batch> BatchReportSearch ();
    public List<Object> ProductQueueSearch (String searchString);
    
}
