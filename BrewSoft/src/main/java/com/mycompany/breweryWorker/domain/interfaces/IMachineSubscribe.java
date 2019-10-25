/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.breweryWorker.domain.interfaces;

import java.util.function.Consumer;

/**
 *
 * @author jacob
 */
public interface IMachineSubscribe {
    
    public void subscribe();
    
    public void setConsumer(Consumer<String> consumer, String itemName);
    
    public final static String BARLEY_NODENAME = "Barley";
    public final static String HOPS_NODENAME = "Hops";
    public final static String MALT_NODENAME = "Malt";
    public final static String WHEAT_NODENAME = "Wheat";
    public final static String YEAST_NODENAME = "Yeast";
    
    public final static String BATCHID_NODENAME = "BatchID";
    public final static String TOTAL_PRODUCTS_NODENAME = "TotalProducts";
    public final static String TEMPERATURE_NODENAME = "Temperature";
    public final static String HUMIDITY_NODENAME = "Humidity";
    public final static String VIBRATION_NODENAME = "Vibration";
    public final static String PRODUCED_PRODUCTS_NODENAME = "ProducedAmount";
    public final static String DEFECT_PRODUCTS_NODENAME = "DefectProducts";
    public final static String STOP_REASON_NODENAME = "StopReason";
    public final static String STATE_CURRENT_NODENAME = "StateCurrent";
    public final static String PRODUCTS_PR_MINUTE_NODENAME = "ProductsPrMinute";
    //public final static String ACCEPTABLE_PRODUCTS_NODENAME = "AcceptableProducts";

    

    
    
    
    
    
    
}
