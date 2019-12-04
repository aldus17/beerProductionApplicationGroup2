package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.SearchData;
import java.util.List;

public interface ISearchDataHandler {
    public List<BatchReport> getBatchList(SearchData searchDataObj);
}
