/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.time.LocalDate;
import java.util.List;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jacob
 */
public class ManagementDomain implements IManagementDomain {
    private final int BATCHID_MIN = 0;
    private final int BATCHID_MAX = 65535;
    
    private IBatchDataHandler batchDataHandler = new BatchDataHandler();

    @Override
    public void CreateBatch(Batch batch) {
        Batch idLessBatch = batch;
        Batch batchWithID = new Batch(
                createBatchID(batchDataHandler.getLatestBatchID()),
                idLessBatch.getStringType(),
                idLessBatch.getStringDateofCompletion(),
                idLessBatch.getStringSpeedforProduction(),
                idLessBatch.getStringTotalAmount());
        System.out.println("Batch " + idLessBatch.toString() );
        batchDataHandler.insertBatchToQueue(batchWithID);
    }

    @Override
    public List<Batch> BatchObjects(String searchKey, String searchValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double CalulateOEE(LocalDate searchDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BeerTypes> GetBeerTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String createBatchID(Integer batchIDRetrieve){
        Integer batchid = batchIDRetrieve;
        if(batchid == null){
            return String.valueOf(BATCHID_MIN);
        } else if(batchIDRetrieve>=BATCHID_MIN && batchIDRetrieve<BATCHID_MAX){
           return String.valueOf(batchIDRetrieve + 1); 
        } else {
            return String.valueOf(BATCHID_MIN);
        }
    }

}
