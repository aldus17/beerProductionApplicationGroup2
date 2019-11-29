package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.BeerTypes;
import java.util.List;

public interface IManagementData {
    
    public List<BeerTypes> getBeerTypes();    
}