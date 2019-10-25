package com.mycompany.management.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface IManagermentDomain {

    public void CreateBatch(int typeofProduct, int amounttoProduce, double speed, LocalDate deadline);
    public List<Object> BatchReportSearch (String searchString);
    public List<Object> ProductQueueSearch (String searchString);
    
}
