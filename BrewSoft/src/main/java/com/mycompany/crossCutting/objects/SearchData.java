package com.mycompany.crossCutting.objects;

import com.mycompany.crossCutting.interfaces.ISearchData;

/**
 *
 * @author Mathias
 */
public class SearchData implements ISearchData {
    private String dateOfCompletion;
    private float batchID;
    
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
