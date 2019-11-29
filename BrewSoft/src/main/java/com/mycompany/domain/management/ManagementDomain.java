package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.data.interfaces.IManagementData;
import com.mycompany.domain.breweryWorker.MachineSubscriber;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementDomain implements IManagementDomain {

    private final int BATCHID_MIN = 0;
    private final int BATCHID_MAX = 65535;

    private IBatchDataHandler batchDataHandler = new BatchDataHandler();
    private IManagementData managementData = new BatchDataHandler(); // = new ManagementData(); Missing the class that implements the interface

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
                idLessBatch.getTotalAmount().getValue(),
                idLessBatch.getDeadline().getValue(),
                idLessBatch.getSpeedforProduction().getValue());
        batchDataHandler.insertBatchToQueue(batchWithID);
    }
    
    public void editQueuedBatch(Batch batch){
        batchDataHandler.editQueuedBatch(batch);
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
        return managementData.getBeerTypes();
    }

    public Map<Integer, String> getTimeInStates(String prodListID) {

        MachineState ms = batchDataHandler.getMachineState(prodListID);
        Map<Integer, String> finalTimeInStatesList = new TreeMap<>();

        System.out.println(Arrays.toString(ms.getStateObjList().toArray()));

        List<MachineState> msl = new ArrayList<>();

        for (Object object : ms.getStateObjList()) {
            msl.add((MachineState) object);
        }
        System.out.println(Arrays.toString(msl.toArray()));
        Collections.sort(msl, Comparator.comparing(MachineState::getTimeInState));
        
        System.out.println(Arrays.toString(msl.toArray()));
        for (int i = 1; i < msl.size(); i++) {
            // tag første object, gemmer det object i variable, checke den variable mod det næste object, hvis det samme continue
            MachineState firstObj = msl.get(i - 1);
            MachineState secondObj = msl.get(i);

            if (!firstObj.getMachinestateID().equals(secondObj.getMachinestateID())) {
                finalTimeInStatesList.put(Integer.valueOf(firstObj.getMachinestateID()), getDifferenceTimeInState(firstObj.getTimeInState(), secondObj.getTimeInState()));
            } else {

            }
        }
        System.out.println(finalTimeInStatesList.toString());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

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
        return String.format("%02d:%02d:%02d", hours, minutes, seconds); //02d e.g. 01 or 00 or 22
//        return formatted;
    }

    public String getAdditionTimeInState(String stateValue1, String stateValue2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long difference = 0;
        try {

            Date date1 = format.parse(stateValue1);
            Date date2 = format.parse(stateValue2);
            difference = date2.getTime() + date1.getTime();

        } catch (ParseException ex) {
            System.out.println("The beginning of the specified string cannot be parsed");
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }

        long seconds = (difference / 1000) % 60;
        long minutes = (difference / (1000 * 60)) % 60;
        long hours = difference / (1000 * 60 * 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds); //02d e.g. 01 or 00 or 22
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

    public static void main(String[] args) {
        ManagementDomain md = new ManagementDomain();

        Map<Integer, String> testMap = new TreeMap<>();
        testMap = md.getTimeInStates("410");

        System.out.println(Arrays.toString(testMap.keySet().toArray()) + " " + Arrays.toString(testMap.values().toArray()));
    }
}