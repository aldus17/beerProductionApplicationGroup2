package com.mycompany.domain.breweryWorker.interfaces;

public interface IMachineControl {

    public void startProduction();

    public void resetMachine();

    public void stopProduction();

    public void abortProduction();

    public void clearState();
}
