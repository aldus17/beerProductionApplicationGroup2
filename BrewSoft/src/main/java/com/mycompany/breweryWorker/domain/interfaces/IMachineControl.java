package com.mycompany.brewsoft.breweryWorker.domain.interfaces;

public interface IBrewerDomain {

    public void startProduction(float batchID, float productID, float quantity, float machSpeed);
    
    public void resetMachine();
    
    public void stopProduction();
    
    public void abortProduction();
    
    public void clearState();
}
