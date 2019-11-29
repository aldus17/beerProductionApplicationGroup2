package com.mycompany.domain.management.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.SearchData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IManagementDomain {

    //public void CreateBatch(int typeofProduct, int amountToProduce, double speed, LocalDate deadline);
    public void createBatch(Batch batch);
    public ArrayList<Batch> getQueuedBatches();
    public void editQueuedBatch(Batch batch);
    public List<Batch> batchObjects(String searchKey, SearchData searchDataObj);
    public List<BeerTypes> getBeerTypes();
    public String calculateOEE (LocalDate dateofcompletion, int plannedproductiontime);

}
