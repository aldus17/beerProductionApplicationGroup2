/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.management.domain;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.management.interfaces.IManagermentDomain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a_hen
 */
public class Test implements IManagermentDomain{

    private Batch batch;

    public Test() {
    }
    
    @Override
    public void CreateBatch(int typeofProduct, int amounttoProduce, double speed, LocalDate deadline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Batch> BatchReportSearch() {
        
        List<Batch> list = new ArrayList<>();
        
        batch = new Batch("10", "1", "1", "Idag", "NU", "senere", "1", "1000", "0", "10000");
        
        list.add(batch);
        
        return list;
    }

    @Override
    public List<Object> ProductQueueSearch(String searchString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
