/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.interfaces;

import com.mycompany.crossCutting.objects.BeerTypes;
import java.util.List;

/**
 *
 * @author Andreas
 */
public interface IManagementData {
    
    public List<BeerTypes> getBeerTypes();
    
}
