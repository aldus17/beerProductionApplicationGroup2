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

    @Override
    public Map<Integer, String> getTimeInStates(int prodListID) {

        MachineState ms = batchDataHandler.getMachineState(prodListID);
        Map<Integer, String> finalTimeInStatesList = new TreeMap<>();

        System.out.println(Arrays.toString(ms.getStateObjList().toArray()));

        List<MachineState> msl = new ArrayList<>();

        for (Object object : ms.getStateObjList()) {
            msl.add((MachineState) object);

        }
        System.out.println(Arrays.toString(msl.toArray()));
        Collections.sort(msl, Comparator.comparing(MachineState::getTimeInState));

        MachineState firstObj = msl.get(0);

        System.out.println(Arrays.toString(msl.toArray()));
        for (int i = 1; i < msl.size(); i++) {
            // tag første object, gemmer det object i variable, checke den variable mod det næste object, hvis det samme continue

            MachineState secondObj = msl.get(i);
//            System.out.println("firstObj: " + firstObj.toString());
//            System.out.println("secondObj: " + secondObj.toString());
            String diff = getDifferenceTimeInState(firstObj.getTimeInState(), secondObj.getTimeInState());
            if (finalTimeInStatesList.containsKey(Integer.valueOf(firstObj.getMachinestateID()))) {
                String t = finalTimeInStatesList.get(Integer.valueOf(firstObj.getMachinestateID()));
                diff = getAdditionTimeInState(diff, t);

            }
            finalTimeInStatesList.put(Integer.valueOf(firstObj.getMachinestateID()), diff);
//            System.out.println(getDifferenceTimeInState(firstObj.getTimeInState(), secondObj.getTimeInState()));
            if (!firstObj.getMachinestateID().equals(secondObj.getMachinestateID())) {

                firstObj = msl.get(i);
            }
        }

        return finalTimeInStatesList;

    }

    public String getDifferenceTimeInState(String stateValue1, String stateValue2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long difference = 0;
//        String formatted = "";
        try {

            Date date1 = format.parse(stateValue1);
            Date date2 = format.parse(stateValue2);
            difference = date2.getTime() - date1.getTime();

        } catch (ParseException ex) {
            System.out.println("The beginning of the specified string cannot be parsed");
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        long seconds = (difference / 1000) % 60;
        long minutes = (difference / (1000 * 60)) % 60;
        long hours = difference / (1000 * 60 * 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds); //02d e.g. 01 or 00 or 22
//        return formatted;
    }

    public String getAdditionTimeInState(String stateValue1, String stateValue2) {

        String[] s1 = stateValue1.split(":");
        String[] s2 = stateValue2.split(":");

        int hours = Integer.valueOf(s1[0]) + Integer.valueOf(s2[0]);
        int minutes = Integer.valueOf(s1[1]) + Integer.valueOf(s2[1]);

        int seconds = Integer.valueOf(s1[2]) + Integer.valueOf(s2[2]);

        if (seconds > 60) {
            int remainer = seconds % 60;
            minutes += (seconds - remainer) / 60;
            seconds = remainer;
        }
        if (minutes > 60) {
            int remainer = minutes & 60;
            hours += (minutes - remainer) / 60;
            minutes = remainer;
        }
        String daysIncluded = "";
        int days = 0;

        if (hours > 24) {
            int remainer = hours % 24;
            days += (hours - remainer) / 24;
            hours = remainer;
            daysIncluded = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
            return daysIncluded;
        }
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

//        Map<Integer, String> testMap = new TreeMap<>();
//        testMap = md.getTimeInStates(410);
//
//        System.out.println(Arrays.toString(testMap.keySet().toArray()) + " " + Arrays.toString(testMap.values().toArray()));
        Map<Integer, String> testMap2 = new TreeMap<>();
        MachineState ms = new MachineState("", "");
        testMap2 = md.getTimeInStates(410);

        System.out.println(testMap2.toString());
        System.out.println(
                "Test Addition: " + md.getAdditionTimeInState("13:10:10", "12:10:10"));
        System.out.println(
                "Test Get difference " + md.getDifferenceTimeInState("12:03:05", "13:05:10"));
    }
}
