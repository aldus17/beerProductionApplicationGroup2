package com.mycompany.management.domain;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.management.interfaces.IManagermentDomain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test implements IManagermentDomain{

    private Batch batch;

    public Test() {
    }
    
    @Override
    public void CreateBatch(int typeofProduct, int amountToProduce, double speed, LocalDate deadline) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Batch> BatchObjects(String key, String value) {
        
        List<Batch> list = new ArrayList<>();
        
        batch = new Batch("10", "1", "1", "Idag", "NU", "senere", "1", "1000", "0", "10000");
        
        list.add(batch);
        
        return list;
    }

    @Override
    public double CalulateOEE(LocalDate searchDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
