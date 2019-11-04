package com.mycompany.domain.management.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import java.time.LocalDate;
import java.util.List;

public interface IManagementDomain {

    public void CreateBatch(int typeofProduct, int amountToProduce, double speed, LocalDate deadline);
    public List<Batch> BatchObjects (String searchKey, String searchValue);
    public double CalulateOEE(LocalDate searchDate);
    public List<BeerTypes> GetBeerTypes();
    
}
