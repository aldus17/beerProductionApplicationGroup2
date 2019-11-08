/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.interfaces;

import java.time.LocalDate;

/**
 *
 * @author jacob
 */
public interface IBatchDataHandler {
 
     public void insertBatchToQueue(int typeofProduct, int amountToProduce, double speed, LocalDate deadline);
    
}
