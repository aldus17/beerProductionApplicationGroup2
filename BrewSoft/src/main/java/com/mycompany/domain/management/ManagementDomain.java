package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.crossCutting.objects.OeeObject;
import com.mycompany.crossCutting.objects.SearchData;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.dataAccess.Connect.TestDatabase;
import com.mycompany.data.dataAccess.SearchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.data.interfaces.IManagementData;
import com.mycompany.data.interfaces.ISearchDataHandler;
import com.mycompany.domain.breweryWorker.MachineSubscriber;
import com.mycompany.domain.management.interfaces.IManagementDomain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private final IBatchDataHandler batchDataHandler;
    private final ISearchDataHandler searchDataHandler;
    private final IManagementData managementData;

    public ManagementDomain() {
        this.batchDataHandler = new BatchDataHandler();
        this.searchDataHandler = new SearchDataHandler();
        this.managementData = new BatchDataHandler(); // missing suitable class
    }
    
    //Used in tests 
    public ManagementDomain(BatchDataHandler bdh, SearchDataHandler sdh, BatchDataHandler mdh){
        this.batchDataHandler = bdh;
        this.searchDataHandler = sdh;
        this.managementData = mdh;
    }

    public ManagementDomain(TestDatabase testDatabase) {
        this.batchDataHandler = new BatchDataHandler(testDatabase);
        this.searchDataHandler = new SearchDataHandler();
        this.managementData = new BatchDataHandler();
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

    @Override
    public void editQueuedBatch(Batch batch) {
        batchDataHandler.editQueuedBatch(batch);
    }

    @Override
    public List<Batch> batchObjects(String searchKey, SearchData searchDataObj) {
        return searchDataHandler.getBatchList(searchDataObj);
    }

    @Override
    public List<BeerTypes> getBeerTypes() {
        return managementData.getBeerTypes();
    }

    /**
     * Collects all machinestate data from the datalayer and calculates the
     * difference in order to get the time used in that paticular state. If more
     * values of same machine state appear, it takes the first entry of that
     * state and then ignores all the repeated machine states (If present) and
     * takes the difference when it sees a different state. E.g. state 6 | state
     * 6 | state 17 will calculate the first state 6 entry and the new state 17.
     *
     * @param prodListID of type int
     * @param machineID of  type int
     *
     * @return returns a Map<Integer, String> of the calulated states where key
     * is the state and the String is the value of format "HH:mm:ss"
     */
    @Override
    public Map<Integer, String> getTimeInStates(int prodListID, int machineID) {

        List<MachineState> ms = batchDataHandler.getMachineState(prodListID, machineID);
        Map<Integer, String> finalTimeInStatesList = new TreeMap<>();

        Collections.sort(ms, Comparator.comparing(MachineState::getTimeInState));

        MachineState firstObj = ms.get(0);

        for (int i = 1; i < ms.size(); i++) {
            MachineState secondObj = ms.get(i);
            String diff = getDifferenceTimeInState(String.valueOf(firstObj.getTimeInState()), String.valueOf(secondObj.getTimeInState()));

            if (finalTimeInStatesList.containsKey(firstObj.getMachinestateID())) {
                String t = finalTimeInStatesList.get(firstObj.getMachinestateID());
                diff = getAdditionTimeInState(diff, t);
            }

            finalTimeInStatesList.put(firstObj.getMachinestateID(), diff);
            if (!(firstObj.getMachinestateID() == secondObj.getMachinestateID())) {
                firstObj = ms.get(i);
            }
        }
        return finalTimeInStatesList;
    }

    /**
     * Takes stateValue2 and subtracts stateValue1.
     * <ul><li> Format must be "HH:mm:ss" of type String.<ul/>
     * E.g. "02:10:05" and "01:10:10" the method will output "01:00:05".
     *
     * @param stateValue1 of type String of format "HH:mm:ss"
     * @param stateValue2 of type String of format "HH:mm:ss"
     *
     * @return returns a String of the subtraction of stateValue2 from
     * stateValue1 in the format "HH:mm:ss"
     */
    public String getDifferenceTimeInState(String stateValue1, String stateValue2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long difference = 0;
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
    }

    /**
     * Takes stateValue1 and adds that to stateValue2.
     * <ul><li> Format must be "HH:mm:ss" of type String.<ul/>
     * E.g. "01:10:10" and "02:10:05" the method will output "03:20:15". If
     * there are more hours than 24 then it will add 1 to days. E.g. "24:20:10"
     * and "01:20:10" the method will output "01:00:40:20". The same goes for
     * minutes and seconds.
     *
     * @param stateValue1 of  type String of format "HH:mm:ss"
     * @param stateValue2 of  type String of format "HH:mm:ss"
     *
     * @return returns a String of the addition of stateValue1 and stateValue2
     * in the format "DD:HH:mm:ss"
     */
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

        float calculatedOEE = (OEE / plannedproductiontime) * 100;
        return String.format("%.2f", calculatedOEE);
    }

    @Override
    public ArrayList<Batch> getQueuedBatches() {
        return batchDataHandler.getQueuedBatches();
    }

    @Override
    public ArrayList<Batch> getCompletedBatches() {
        return managementData.getCompletedBatches();
    }
}