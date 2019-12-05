package com.mycompany.crossCutting.objects;

import com.mycompany.crossCutting.interfaces.ISearchData;

public class SearchData implements ISearchData {

    private final String dateOfCompletion;
    private final float batchID;

    public SearchData(String dateOfCompletion, float batchID) {
        this.dateOfCompletion = dateOfCompletion;
        this.batchID = batchID;
    }

    @Override
    public String getDateOfCompletion() {
        return this.dateOfCompletion;
    }

    @Override
    public float getBatchID() {
        return this.batchID;
    }
}
