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
        
        Random random = new Random();
        idLessBatch.setStringBatchID(String.valueOf(random.nextInt(65535))); // Implement real id creater
        System.out.println("Batch " + idLessBatch.toString() );
        batchDataHandler.insertBatchToQueue(idLessBatch);
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
    
    private int createBatchID(int batchIDRetrieve){
        if(batchIDRetrieve>=BATCHID_MIN && batchIDRetrieve<BATCHID_MAX){
           return batchIDRetrieve + 1; 
        } else {
            return BATCHID_MIN;
        }
    }

}
