/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.data.interfaces.IBatchDataHandler;
import java.time.LocalDate;

/**
 *
 * @author jacob
 */
public class BatchDataHandler implements IBatchDataHandler {

    @Override
    public void queueBatch(int typeofProduct, int amountToProduce, double speed, LocalDate deadline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //TODO: Use queue batch method
    }
    
    //TODO: implement queue batch method to database. 
    
}
