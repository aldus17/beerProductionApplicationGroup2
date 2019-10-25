package com.mycompany.management.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import java.time.LocalDate;
import java.util.List;

public interface IManagermentDomain {

    public void CreateBatch(int typeofProduct, int amountToProduce, double speed, LocalDate deadline);
    public List<Batch> BatchObjects (String searchKey, String searchValue);
    public double CalulateOEE(LocalDate searchDate);
    
}
