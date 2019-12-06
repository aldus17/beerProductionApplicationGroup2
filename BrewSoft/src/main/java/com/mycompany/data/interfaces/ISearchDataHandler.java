package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.SearchData;
import java.util.List;

public interface ISearchDataHandler {

    public List<Batch> getBatchList(SearchData searchDataObj);
}
