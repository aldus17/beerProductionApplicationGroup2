package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.OeeObject;
import com.mycompany.crossCutting.objects.SearchData;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.dataAccess.SearchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.data.interfaces.IManagementData;
import com.mycompany.data.interfaces.ISearchDataHandler;
import com.mycompany.domain.breweryWorker.MachineSubscriber;
import java.util.List;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementDomain implements IManagementDomain {

    private final int BATCHID_MIN = 0;
    private final int BATCHID_MAX = 65535;

    private IBatchDataHandler batchDataHandler;
    private ISearchDataHandler searchDataHandler;
    private IManagementData managementData;

    public ManagementDomain() {
        this.batchDataHandler = new BatchDataHandler();
        this.searchDataHandler = new SearchDataHandler();
        this.managementData = new BatchDataHandler(); // missing suitable class
    }

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
                idLessBatch.getType(),
                idLessBatch.getTotalAmount(),
                idLessBatch.getDeadline(),
                idLessBatch.getSpeedforProduction());
        batchDataHandler.insertBatchToQueue(batchWithID);
    }

    public void editQueuedBatch(Batch batch) {
        batchDataHandler.editQueuedBatch(batch);
    }

    @Override
    public List<BatchReport> batchObjects(String searchKey, SearchData searchDataObj) {
        return searchDataHandler.getBatchList(searchDataObj);
    }

    @Override
    public List<BeerTypes> getBeerTypes() {
        return managementData.getBeerTypes();
    }

    public Map<Integer, String> getTimeInStates(String prodListID) {

        MachineState ms = batchDataHandler.getMachineState(prodListID);
        Map<Integer, String> finalTimeInStatesList = new TreeMap<>();


        List<MachineState> msl = new ArrayList<>();

        for (Object object : ms.getStateObjList()) {
            msl.add((MachineState) object);
        }
        Collections.sort(msl, Comparator.comparing(MachineState::getTimeInState));

        for (int i = 1; i < msl.size(); i++) {
            // tag første object, gemmer det object i variable, checke den variable mod det næste object, hvis det samme continue
            MachineState firstObj = msl.get(i - 1);
            MachineState secondObj = msl.get(i);

            if (!firstObj.getMachinestateID().equals(secondObj.getMachinestateID())) {
                finalTimeInStatesList.put(Integer.valueOf(firstObj.getMachinestateID()), getDifferenceTimeInState(firstObj.getTimeInState(), secondObj.getTimeInState()));
            } else {

            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public String getDifferenceTimeInState(String stateValue1, String stateValue2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long difference = 0;
        try {

            Date date1 = format.parse(stateValue1);
            Date date2 = format.parse(stateValue2);
            difference = date2.getTime() - date1.getTime();

        } catch (ParseException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        long seconds = (difference / 1000) % 60;
        long minutes = (difference / (1000 * 60)) % 60;
        long hours = difference / (1000 * 60 * 60);
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getAdditionTimeInState(String stateValue1, String stateValue2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long difference = 0;
        try {

            Date date1 = format.parse(stateValue1);
            Date date2 = format.parse(stateValue2);
            difference = date2.getTime() + date1.getTime();

        } catch (ParseException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }

        long seconds = (difference / 1000) % 60;
        long minutes = (difference / (1000 * 60)) % 60;
        long hours = difference / (1000 * 60 * 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds); //02d e.g. 01 or 00 or 22
    }

    private int createBatchID(Integer batchIDRetrieve) {
        Integer batchid = batchIDRetrieve;
        if (batchid == null) {
            return BATCHID_MIN;
        } else if (batchIDRetrieve >= BATCHID_MIN && batchIDRetrieve < BATCHID_MAX) {
            return batchIDRetrieve + 1;
        } else {
            return BATCHID_MIN;
        }
    }

    @Override
    public String calculateOEE(LocalDate dateofcompletion, int plannedproductiontime) {
        List<OeeObject> list = new ArrayList<>();
        float OEE = 0.0f;
        list = batchDataHandler.getAcceptedCount(dateofcompletion);

        for (OeeObject oeeObject : list) {
            OEE += (oeeObject.getAcceptedCount() * oeeObject.getIdealcycletime());
        }

        float calculatedOEE = (OEE / plannedproductiontime) / 100;
        
        return String.format("%.2f", calculatedOEE);
    }

    @Override
    public ArrayList<Batch> getQueuedBatches() {
        return batchDataHandler.getQueuedBatches();
    }
}
