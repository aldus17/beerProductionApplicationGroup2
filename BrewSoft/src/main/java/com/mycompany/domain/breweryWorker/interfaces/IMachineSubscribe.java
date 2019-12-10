package com.mycompany.domain.breweryWorker.interfaces;

import com.mycompany.crossCutting.objects.Batch;
import java.util.function.Consumer;

public interface IMachineSubscribe {

    public void subscribe();

    public void setConsumer(Consumer<String> consumer, String nodeName);

    public String stateTranslator(String state);

    public void setCurrentBatch(Batch currentBatch);

    public String stopReasonTranslator(String stopReason);

    public void stoppedproduction(int productionlistid);
    
    public String getCurrentProductType();

    // Node names production materials.
    public final static String BARLEY_NODENAME = "Barley";
    public final static String HOPS_NODENAME = "Hops";
    public final static String MALT_NODENAME = "Malt";
    public final static String WHEAT_NODENAME = "Wheat";
    public final static String YEAST_NODENAME = "Yeast";

    // Node names production data.
    public final static String BATCHID_NODENAME = "BatchID";
    public final static String TOTAL_PRODUCTS_NODENAME = "TotalProducts";
    public final static String TEMPERATURE_NODENAME = "Temperature";
    public final static String HUMIDITY_NODENAME = "Humidity";
    public final static String VIBRATION_NODENAME = "Vibration";
    public final static String PRODUCED_PRODUCTS_NODENAME = "ProducedAmount";
    public final static String DEFECT_PRODUCTS_NODENAME = "DefectProducts";
    public final static String PRODUCTS_PR_MINUTE_NODENAME = "ProductsPrMinute";
    public final static String ACCEPTABLE_PRODUCTS_NODENAME = "AcceptableProducts";

    // Node names machine specific data.
    public final static String STOP_REASON_NODENAME = "StopReason";
    public final static String STATE_CURRENT_NODENAME = "StateCurrent";
    public final static String MAINTENANCE_COUNTER_NODENAME = "MaintenanceCounter";

    // TODO Get this data from database
    // Machine state constants
    public final static String DEACTIVATED = "0";
    public final static String CLEARING = "1";
    public final static String STOPPED = "2";
    public final static String STARTING = "3";
    public final static String IDLE = "4";
    public final static String SUSPENDED = "5";
    public final static String EXECUTE = "6";
    public final static String STOPPING = "7";
    public final static String ABORTING = "8";
    public final static String ABORTED = "9";
    public final static String HOLDING = "10";
    public final static String HELD = "11";
    public final static String RESETTING = "15";
    public final static String COMPLETING = "16";
    public final static String COMPLETE = "17";
    public final static String DEACTIVATING = "18";
    public final static String ACTIVATING = "19";

    // Stop reason constants
    public final static String UNDEFINED = "0";
    public final static String EMPTY_INVENTORY = "10";
    public final static String MAINTENANCE = "11";
    public final static String MANUAL_STOP = "12";
    public final static String MOTOR_POWER_LOSS = "13";
    public final static String MANUAL_ABORT = "14";
}
