package com.mycompany.management.interfaces;

import java.time.LocalDate;

public interface IManagermentDomain {

    public void CreateBatch(int typeofProduct, int amounttoProduce, double speed, LocalDate deadline);
    
}
