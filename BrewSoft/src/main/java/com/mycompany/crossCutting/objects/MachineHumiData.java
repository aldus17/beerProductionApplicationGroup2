package com.mycompany.crossCutting.objects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MachineHumiData {

    private int machineID;
    private double humidity;

    public MachineHumiData() {
    }

    public MachineHumiData(int machineID, double humidity) {
        this.machineID = machineID;
        this.humidity = humidity;

    }

    public int getMachineID() {
        return machineID;
    }

    public double getHumidity() {
        return round(humidity, 3);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public String toString() {
        return "MachineHumiData{" + "machineID=" + machineID + ", humidity=" + humidity;
    }
}
