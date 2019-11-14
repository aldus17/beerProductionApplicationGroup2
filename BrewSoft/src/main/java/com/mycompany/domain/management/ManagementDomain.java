/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.domain.breweryWorker.MachineSubscriber;
import java.time.LocalDate;
import java.util.List;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jacob
 */
public class ManagementDomain implements IManagementDomain {

    private final int BATCHID_MIN = 0;
    private final int BATCHID_MAX = 65535;

    private IBatchDataHandler batchDataHandler = new BatchDataHandler();

    /**
     * Method that creates takes a batch with no batch ID and generates a new
     * batch with a batch ID.
     *
     * @param batch The method takes a batch with no ID and generates one for
     * it. The batch is then sent to the datalayer, where it is then saved to
     * the database
     */
    @Override
    public void createBatch(Batch batch) {
        Batch idLessBatch = batch;
        Batch batchWithID = new Batch(
                createBatchID(batchDataHandler.getLatestBatchID()),
                idLessBatch.getType().getValue(),
                idLessBatch.getDeadline().getValue(),
                idLessBatch.getSpeedforProduction().getValue(),
                idLessBatch.getTotalAmount().getValue());
        batchDataHandler.insertBatchToQueue(batchWithID);
    }

    @Override
    public List<Batch> batchObjects(String searchKey, String searchValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double calulateOEE(LocalDate searchDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BeerTypes> getBeerTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Integer, String> getTimeInStates(String prodListID) {

        MachineState ms = batchDataHandler.getMachineState(prodListID);
        List<String> listOfTime = new ArrayList<>();
        TreeMap<Integer, String> allTimeValues = new TreeMap<>();
        TreeMap<Integer, String> timeDifferenceMap = new TreeMap<>();

        for (int i = 0; i <= 19; i++) {
//            allTimeValues.put(i, Integer.valueOf(ms.getMachineStateID()) == i ? ms.getTimeInStates() : "");

        }

        for (int i = 0; i < allTimeValues.size(); i++) {
            int firstValue = i;
            int secondValue = i + 1;

            timeDifferenceMap.put(i, getDifferenceTimeInState(allTimeValues.get(firstValue), allTimeValues.get(secondValue)));
        }

        return timeDifferenceMap;

    }

    public String getDifferenceTimeInState(String stateValue1, String stateValue2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long difference = 0;
//        String formatted = "";
        try {

            Date date1 = format.parse(stateValue1);
            Date date2 = format.parse(stateValue2);
            difference = date2.getTime() - date1.getTime();

//            Date differenceInTime = new Date(difference);
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//            formatted = formatter.format(differenceInTime);
        } catch (ParseException ex) {
            System.out.println("The beginning of the specified string cannot be parsed");
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        long seconds = (difference / 1000) % 60;
        long minutes = (difference / (1000 * 60)) % 60;
        long hours = difference / (1000 * 60 * 60);
//        long s = difference % 60;
//        long m = (difference / 60) % 60;
//        long h = (difference / (60 * 60)) % 24;
//        return String.format("%d:%02d:%02d", h, m, s);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
//        return formatted;
    }

    private String createBatchID(Integer batchIDRetrieve) {
        Integer batchid = batchIDRetrieve;
        if (batchid == null) {
            return String.valueOf(BATCHID_MIN);
        } else if (batchIDRetrieve >= BATCHID_MIN && batchIDRetrieve < BATCHID_MAX) {
            return String.valueOf(batchIDRetrieve + 1);
        } else {
            return String.valueOf(BATCHID_MIN);
        }
    }

    @Override
    public ArrayList<Batch> getQueuedBatches() {
        return batchDataHandler.getQueuedBatches();
    }

}
