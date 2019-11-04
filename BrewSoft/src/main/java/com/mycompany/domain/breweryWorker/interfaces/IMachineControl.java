package com.mycompany.domain.breweryWorker.interfaces;

public interface IMachineControl {

    public void startProduction(float batchID, float productID, float quantity, float machSpeed);
    
    public void resetMachine();
    
    public void stopProduction();
    
    public void abortProduction();
    
    public void clearState();
}
