package com.mycompany.domain.breweryWorker;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.TemporaryProductionBatch;
import com.mycompany.data.dataAccess.MachineSubscribeDataHandler;
import com.mycompany.data.interfaces.IMachineSubscriberDataHandler;
import com.mycompany.domain.breweryWorker.interfaces.IMachineSubscribe;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalTime;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

public class MachineSubscriber implements IMachineSubscribe {

    private static final AtomicLong ATOMICLOMG = new AtomicLong(1L);
    private MachineConnection mconn;
    private Map<String, Consumer<String>> consumerMap;

    private IMachineSubscriberDataHandler msdh = new MachineSubscribeDataHandler();

    // Production detail nodes
    private final NodeId batchIdNode = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
    private final NodeId totalProductsNode = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
    private final NodeId tempNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
    private final NodeId humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
    private final NodeId vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
    private final NodeId producedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
    private final NodeId defectCountNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
    private final NodeId productsPrMinuteNode = new NodeId(6, "::Program:Cube.Status.MachSpeed");
    private final NodeId acceptableCountNode = new NodeId(6, "::Program:product.good");

    // Production detail nodes.
    private final NodeId productBadNode = new NodeId(6, "::Program:product.bad");
    private final NodeId productProducedAmountNode = new NodeId(6, "::Program:product.produce_amount");
    private final NodeId productProducedNode = new NodeId(6, "::Program:product.produced");

    // Machine specific nodes
    private final NodeId stopReasonNode = new NodeId(6, "::Program:Cube.Admin.StopReason.ID");
    private final NodeId stateCurrentNode = new NodeId(6, "::Program:Cube.Status.StateCurrent");
    private final NodeId maintenanceCounterNode = new NodeId(6, "::Program:Maintenance.Counter");
    private final NodeId maintenanceStateNode = new NodeId(6, "::Program:Maintenance.State");
    private final NodeId maintenanceTriggerNode = new NodeId(6, "::Program:Maintenance.Trigger");

    // Material nodes
    private final NodeId barleyNode = new NodeId(6, "::Program:Inventory.Barley");
    private final NodeId hopsNode = new NodeId(6, "::Program:Inventory.Hops");
    private final NodeId maltNode = new NodeId(6, "::Program:Inventory.Malt");
    private final NodeId wheatNode = new NodeId(6, "::Program:Inventory.Wheat");
    private final NodeId yeastNode = new NodeId(6, "::Program:Inventory.Yeast");

    private String batchIDValue;
    private String totalProductValue;
    private String temperaturValue = "0";
    private String humidityValue;
    private String vibrationValue = "0";
    private String productionCountValue;
    private String defectCountValue;
    private String acceptableCountValue;
    private String productionPrMinValue;

    private String StopReasonID;
    private String currentStateValue;
    private String maintenanceValue;

    private String barleyValue;
    private String hopsValue;
    private String maltValue;
    private String wheatValue;
    private String yeastValue;

    private Batch batch;

    // TODO pull ip and port from DB
    public MachineSubscriber() {
        this("192.168.0.122", 4840);
    }

    public MachineSubscriber(String hostname, int port) {
        mconn = new MachineConnection(hostname, port);
        mconn.connect();
        consumerMap = new HashMap();
    }

    @Override
    public void setCurrentBatch(Batch currentBatch) {
        this.batch = currentBatch;
    }

    @Override
    public void subscribe() {

        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        requestList.add(new MonitoredItemCreateRequest(readValueId(batchIdNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(totalProductsNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(tempNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(humidityNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(vibrationNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(productProducedNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(defectCountNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(acceptableCountNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(stopReasonNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(stateCurrentNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(productsPrMinuteNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(barleyNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(hopsNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(maltNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(wheatNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(yeastNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(maintenanceCounterNode), MonitoringMode.Reporting, monitoringParameters()));

        Consumer<DataValue> onBatchIdItem = (DataValue dataValue) -> consumerStarter(BATCHID_NODENAME, dataValue);
        Consumer<DataValue> onTotalProductsItem = (dataValue) -> consumerStarter(TOTAL_PRODUCTS_NODENAME, dataValue);
        Consumer<DataValue> onTempratureItem = (dataValue) -> consumerStarter(TEMPERATURE_NODENAME, dataValue);
        Consumer<DataValue> onHumidityItem = (dataValue) -> consumerStarter(HUMIDITY_NODENAME, dataValue);
        Consumer<DataValue> onVibrationItem = (dataValue) -> consumerStarter(VIBRATION_NODENAME, dataValue);
        Consumer<DataValue> onProducedItem = (dataValue) -> consumerStarter(PRODUCED_PRODUCTS_NODENAME, dataValue);
        Consumer<DataValue> onDefectItem = (dataValue) -> consumerStarter(DEFECT_PRODUCTS_NODENAME, dataValue);
        Consumer<DataValue> onAcceptableItem = (dataValue) -> consumerStarter(ACCEPTABLE_PRODUCTS_NODENAME, dataValue);
        Consumer<DataValue> onStopReasonItem = (dataValue) -> consumerStarter(STOP_REASON_NODENAME, dataValue);
        Consumer<DataValue> onStateReadItem = (dataValue) -> consumerStarter(STATE_CURRENT_NODENAME, dataValue);
        Consumer<DataValue> onProductsPrMinuteReadItem = (dataValue) -> consumerStarter(PRODUCTS_PR_MINUTE_NODENAME, dataValue);

        Consumer<DataValue> onBarleyReadItem = (dataValue) -> consumerStarter(BARLEY_NODENAME, dataValue);
        Consumer<DataValue> onHopsReadItem = (dataValue) -> consumerStarter(HOPS_NODENAME, dataValue);
        Consumer<DataValue> onMaltReadItem = (dataValue) -> consumerStarter(MALT_NODENAME, dataValue);
        Consumer<DataValue> onWheatReadItem = (dataValue) -> consumerStarter(WHEAT_NODENAME, dataValue);
        Consumer<DataValue> onYeastReadItem = (dataValue) -> consumerStarter(YEAST_NODENAME, dataValue);

        Consumer<DataValue> onMaintenanceCounterReadItem = (dataValue) -> consumerStarter(MAINTENANCE_COUNTER_NODENAME, dataValue);

        try {
            UaSubscription subscription = mconn.getClient().getSubscriptionManager().createSubscription(1000.0).get();
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, requestList).get();

            //Sets consumer on specific subscription.
            items.get(0).setValueConsumer(onBatchIdItem);
            items.get(1).setValueConsumer(onTotalProductsItem);
            items.get(2).setValueConsumer(onTempratureItem);
            items.get(3).setValueConsumer(onHumidityItem);
            items.get(4).setValueConsumer(onVibrationItem);
            items.get(5).setValueConsumer(onProducedItem);
            items.get(6).setValueConsumer(onDefectItem);
            items.get(7).setValueConsumer(onAcceptableItem);
            items.get(8).setValueConsumer(onStopReasonItem);
            items.get(9).setValueConsumer(onStateReadItem);
            items.get(10).setValueConsumer(onProductsPrMinuteReadItem);
            items.get(11).setValueConsumer(onBarleyReadItem);
            items.get(12).setValueConsumer(onHopsReadItem);
            items.get(13).setValueConsumer(onMaltReadItem);
            items.get(14).setValueConsumer(onWheatReadItem);
            items.get(15).setValueConsumer(onYeastReadItem);
            items.get(16).setValueConsumer(onMaintenanceCounterReadItem);

        } catch (InterruptedException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void setConsumer(Consumer<String> consumer, String nodeName) {
        consumerMap.put(nodeName, consumer);
    }

    // TODO Insert machine ID
    public void sendProductionData() {
        float checkHumidity = 0;
        float checkTemperatur = 0;
        System.out.println("Production Data");
        if (checkHumidity != Float.parseFloat(humidityValue) || checkTemperatur != Float.parseFloat(temperaturValue)) {
            System.out.println("TESTER " + humidityValue);
            checkHumidity = Float.parseFloat(humidityValue);
            checkTemperatur = Float.parseFloat(temperaturValue);
            msdh.insertProductionInfo(Integer.parseInt(batch.getProductionListID().getValue()), 1, Float.parseFloat(humidityValue), Float.parseFloat(temperaturValue));
        }
    }

    // TODO Insert machine ID and Production List ID
    public void sendTimeInState() {
        int checkCurrentState = -1;

        //String timeObject = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond();
        System.out.println("Current State: " + currentStateValue);
        if (checkCurrentState != Integer.parseInt(currentStateValue)) {
            checkCurrentState = Integer.parseInt(currentStateValue);
            msdh.insertTimesInStates(Integer.parseInt(batch.getProductionListID().getValue()), 1, Integer.parseInt(currentStateValue));
        }
    }

    public void sendStopDuingProduction() {
        msdh.insertStopsDuringProduction(Integer.parseInt(batch.getProductionListID().getValue()), 1, Integer.parseInt(StopReasonID));
    }

    public void completedBatch() {
        System.out.println(batch.getDateofCreation());
        System.out.println(batch.getBatchID());
        System.out.println(batch.getDateofCompletion());
        System.out.println(batch.getDeadline());
        System.out.println(batch.getDefectAmount());
        System.out.println(batch.getGoodAmount());
        System.out.println(batch.getMachineID());
        System.out.println(batch.getProductionListID());
        System.out.println(batch.getSpeedforProduction());
        System.out.println(batch.getTotalAmount());
        System.out.println(batch.getType());
        System.out.println("Completed: "+Float.parseFloat(batch.getTotalAmount().getValue()) +" <= " +Float.parseFloat(this.productionCountValue));
        if (Float.parseFloat(batch.getTotalAmount().getValue()) <= Float.parseFloat(this.productionCountValue)) {
            msdh.changeProductionListStatus(Integer.parseInt(batch.getProductionListID().getValue()), "Completed");
            System.out.println("completed test");
            System.out.println(Integer.parseInt(batch.getProductionListID().getValue())+ 1+ batch.getDeadline().getValue());
                    System.out.println(batch.getDateofCreation().getValue()+ Integer.parseInt(batch.getType().getValue()));
                    System.out.println(Float.parseFloat(batch.getTotalAmount().getValue())+ Integer.parseInt(defectCountValue)+ Integer.parseInt(acceptableCountValue));
            msdh.insertFinalBatchInformation(Integer.parseInt(batch.getProductionListID().getValue()), 1, batch.getDeadline().getValue(),
                    batch.getDateofCreation().getValue(), Integer.parseInt(batch.getType().getValue()),
                    Float.parseFloat(totalProductValue), Integer.parseInt(defectCountValue), Integer.parseInt(acceptableCountValue));
            System.out.println("completed test 2");
        }
    }

    public String getBatchIDValue() {
        return batchIDValue;
    }

    public String getTotalProductValue() {
        return totalProductValue;
    }

    public String getTemperaturValue() {
        return temperaturValue;
    }

    public String getHumidityValue() {
        return humidityValue;
    }

    public String getVibrationValue() {
        return vibrationValue;
    }

    public String getProductionCountValue() {
        return productionCountValue;
    }

    public String getDefectCountValue() {
        return defectCountValue;
    }

    public String getAcceptableCountValue() {
        return acceptableCountValue;
    }

    public String getProductionPrMinValue() {
        return productionPrMinValue;
    }

    public String getStopReasonID() {
        return StopReasonID;
    }

    public String getCurrentStateValue() {
        return currentStateValue;
    }

    public String getMaintenanceValue() {
        return maintenanceValue;
    }

    public String getBarleyValue() {
        return barleyValue;
    }

    public String getHopsValue() {
        return hopsValue;
    }

    public String getMaltValue() {
        return maltValue;
    }

    public String getWheatValue() {
        return wheatValue;
    }

    public String getYeastValue() {
        return yeastValue;
    }

    private MonitoringParameters monitoringParameters() {
        return new MonitoringParameters(
                Unsigned.uint(ATOMICLOMG.getAndIncrement()),
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
    }

    private ReadValueId readValueId(NodeId name) {
        return new ReadValueId(name, AttributeId.Value.uid(), null, null);
    }

    private void consumerStarter(String nodename, DataValue dataValue) {
        consumerMap.get(nodename).accept(dataValue.getValue().getValue().toString());

        switch (nodename) {
            case BATCHID_NODENAME:
                this.batchIDValue = dataValue.getValue().getValue().toString();
                break;
            case TOTAL_PRODUCTS_NODENAME:
                this.totalProductValue = dataValue.getValue().getValue().toString();
                break;
            case TEMPERATURE_NODENAME:
                this.temperaturValue = dataValue.getValue().getValue().toString();
            case HUMIDITY_NODENAME:
                if (nodename.equals(HUMIDITY_NODENAME)) {
                    this.humidityValue = dataValue.getValue().getValue().toString();
                }
                this.sendProductionData();
                break;
            case VIBRATION_NODENAME:
                this.vibrationValue = dataValue.getValue().getValue().toString();
                break;

            case DEFECT_PRODUCTS_NODENAME:
                this.defectCountValue = dataValue.getValue().getValue().toString();
                break;
            case PRODUCTS_PR_MINUTE_NODENAME:
                this.productionPrMinValue = dataValue.getValue().getValue().toString();
                break;
            case ACCEPTABLE_PRODUCTS_NODENAME:
                this.acceptableCountValue = dataValue.getValue().getValue().toString();
                break;
            case STOP_REASON_NODENAME:
                this.StopReasonID = dataValue.getValue().getValue().toString();
                //sendStopDuingProduction();
                break;
            case STATE_CURRENT_NODENAME:
                this.currentStateValue = dataValue.getValue().getValue().toString();
                System.out.println("state " + this.currentStateValue);
                sendTimeInState();
                break;
            case MAINTENANCE_COUNTER_NODENAME:
                this.maintenanceValue = dataValue.getValue().getValue().toString();
                break;
            case BARLEY_NODENAME:
                this.barleyValue = dataValue.getValue().getValue().toString();
                break;
            case HOPS_NODENAME:
                this.hopsValue = dataValue.getValue().getValue().toString();
                break;
            case MALT_NODENAME:
                this.maltValue = dataValue.getValue().getValue().toString();
                break;
            case WHEAT_NODENAME:
                this.wheatValue = dataValue.getValue().getValue().toString();
                break;
            case YEAST_NODENAME:
                this.yeastValue = dataValue.getValue().getValue().toString();
                break;
            case PRODUCED_PRODUCTS_NODENAME:
                this.productionCountValue = dataValue.getValue().getValue().toString();
                this.completedBatch();
                break;
            default:
                System.out.println("There are no Node for this!!");
        }
    }

    // TODO Get data from database.
    @Override
    public String stopReasonTranslator(String stopReason) {
        switch (stopReason) {
            case UNDEFINED:
                return "";
            case EMPTY_INVENTORY:
                return "Empty inventory";
            case MAINTENANCE:
                return "Maintenance";
            case MANUAL_STOP:
                return "Manual Stop";
            case MOTOR_POWER_LOSS:
                return "Motor power loss";
            case MANUAL_ABORT:
                return "Manual abort";
        }
        return "Unknown stop code " + stopReason;
    }

    @Override
    public String stateTranslator(String state) {
        switch (state) {
            case DEACTIVATED:
                return "Deactivated";
            case CLEARING:
                return "Clearing";
            case STOPPED:
                return "Stopped";
            case STARTING:
                return "Starting";
            case IDLE:
                return "Idle";
            case SUSPENDED:
                return "Suspended";
            case EXECUTE:
                return "Execute";
            case STOPPING:
                return "Stopping";
            case ABORTING:
                return "Aborting";
            case ABORTED:
                return "Aborted";
            case HOLDING:
                return "Holding";
            case HELD:
                return "Held";
            case RESETTING:
                return "Resetting";
            case COMPLETING:
                return "Completing";
            case COMPLETE:
                return "Complete";
            case DEACTIVATING:
                return "Deactivating";
            case ACTIVATING:
                return "Activating";
        }
        return "Unknown State code: " + state;
    }
    public void stoppedproduction(int productionlistid){
        TemporaryProductionBatch tpb = new TemporaryProductionBatch(productionlistid, Float.parseFloat(acceptableCountValue), Float.parseFloat(defectCountValue), Float.parseFloat(totalProductValue));
        msdh.insertStoppedProductionToTempTable(tpb);
    }

}
