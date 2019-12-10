package com.mycompany.domain.management.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.SearchData;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IManagementDomain {

    public void createBatch(Batch batch);

    public ArrayList<Batch> getQueuedBatches();

    public ArrayList<Batch> getCompletedBatches();

    public void editQueuedBatch(Batch batch);

    public List<Batch> batchObjects(String searchKey, SearchData searchDataObj);

    public List<BeerTypes> getBeerTypes();

    public Map<Integer, String> getTimeInStates(int prodListID, int machineID);

    public String calculateOEE(LocalDate dateofcompletion, int plannedproductiontime);

}
