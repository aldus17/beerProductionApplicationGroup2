package com.mycompany.domain.breweryWorker;

import com.mycompany.domain.breweryWorker.interfaces.IMachineSubscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

public class MachineSubscriber implements IMachineSubscribe {

    private static final AtomicLong clientHandles = new AtomicLong(1L);
    private MachineConnection mconn;
    private Map<String, Consumer<String>> consumerMap;

    // Production detail nodes
    private NodeId batchIdNode;
    private NodeId totalProductsNode;
    private NodeId tempNode;
    private NodeId humidityNode;
    private NodeId vibrationNode;
    private NodeId producedCountNode;
    private NodeId defectCountNode;
    private NodeId productsPrMinuteNode;
    private NodeId acceptableCountNode;

    // TODO Production detail nodes. Not used.
    private NodeId productBadNode;
    private NodeId productProducedAmountNode;
    private NodeId productProducedNode;

    // Machine specific nodes
    private NodeId stopReasonNode;
    private NodeId stateCurrentNode;
    private NodeId maintenanceCounterNode;
    private NodeId maintenanceStateNode;
    private NodeId maintenanceTriggerNode;

    // Material nodes
    private NodeId barleyNode;
    private NodeId hopsNode;
    private NodeId maltNode;
    private NodeId wheatNode;
    private NodeId yeastNode;

    // TODO pull ip and port from DB
    public MachineSubscriber() {
        this("127.0.0.1", 4840);
    }

    public MachineSubscriber(String hostname, int port) {
        mconn = new MachineConnection(hostname, port);
        mconn.connect();
        consumerMap = new HashMap();
    }

    @Override
    public void subscribe() {

        productionNodes();
        machineNodes();
        materialNodes();

        ReadValueId batchIdReadValueId = new ReadValueId(batchIdNode, AttributeId.Value.uid(), null, null);
        ReadValueId totalProductsReadValueId = new ReadValueId(totalProductsNode, AttributeId.Value.uid(), null, null);
        ReadValueId tempReadValueId = new ReadValueId(tempNode, AttributeId.Value.uid(), null, null);
        ReadValueId humidityReadValueId = new ReadValueId(humidityNode, AttributeId.Value.uid(), null, null);
        ReadValueId vibrationReadValueId = new ReadValueId(vibrationNode, AttributeId.Value.uid(), null, null);
        ReadValueId producedReadValueId = new ReadValueId(producedCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId defectReadValueId = new ReadValueId(defectCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId acceptableReadValueId = new ReadValueId(acceptableCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId stopReasonReadValueId = new ReadValueId(stopReasonNode, AttributeId.Value.uid(), null, null);
        ReadValueId stateReadValueId = new ReadValueId(stateCurrentNode, AttributeId.Value.uid(), null, null);
        ReadValueId productsPrMinuteValueId = new ReadValueId(productsPrMinuteNode, AttributeId.Value.uid(), null, null);

        ReadValueId barleyReadValueId = new ReadValueId(barleyNode, AttributeId.Value.uid(), null, null);
        ReadValueId hopsReadValueId = new ReadValueId(hopsNode, AttributeId.Value.uid(), null, null);
        ReadValueId maltReadValueId = new ReadValueId(maltNode, AttributeId.Value.uid(), null, null);
        ReadValueId wheatReadValueId = new ReadValueId(wheatNode, AttributeId.Value.uid(), null, null);
        ReadValueId yeastReadValueId = new ReadValueId(yeastNode, AttributeId.Value.uid(), null, null);

        ReadValueId maintenanceCounterReadValueId = new ReadValueId(maintenanceCounterNode, AttributeId.Value.uid(), null, null);
        ReadValueId maintenanceStateReadValueId = new ReadValueId(maintenanceStateNode, AttributeId.Value.uid(), null, null);
        ReadValueId maintenanceTriggerReadValueId = new ReadValueId(maintenanceTriggerNode, AttributeId.Value.uid(), null, null);

        ReadValueId productBadReadValueId = new ReadValueId(productBadNode, AttributeId.Value.uid(), null, null);
        ReadValueId productProducedAmountReadValueId = new ReadValueId(productProducedAmountNode, AttributeId.Value.uid(), null, null);
        ReadValueId productProducedReadValueId = new ReadValueId(productProducedNode, AttributeId.Value.uid(), null, null);

        MonitoringParameters batchIdParameters = monitoringParameters();
        MonitoringParameters totalProductsParameters = monitoringParameters();
        MonitoringParameters tempParameters = monitoringParameters();
        MonitoringParameters humidityParameters = monitoringParameters();
        MonitoringParameters vibrationParameters = monitoringParameters();
        MonitoringParameters producedParameters = monitoringParameters();
        MonitoringParameters defectParameters = monitoringParameters();
        MonitoringParameters acceptableParameters = monitoringParameters();
        MonitoringParameters stopReasonParameters = monitoringParameters();
        MonitoringParameters stateParameters = monitoringParameters();
        MonitoringParameters productsPrMinuteParameters = monitoringParameters();
        MonitoringParameters barleyParameters = monitoringParameters();
        MonitoringParameters hopsParameters = monitoringParameters();
        MonitoringParameters maltParameters = monitoringParameters();
        MonitoringParameters wheatParameters = monitoringParameters();
        MonitoringParameters yeastParameters = monitoringParameters();
        MonitoringParameters maintenanceCounterParameters = monitoringParameters();
        MonitoringParameters maintenanceStateParameters = monitoringParameters();
        MonitoringParameters maintenanceTriggerParameters = monitoringParameters();
        MonitoringParameters productBadParameters = monitoringParameters();
        MonitoringParameters productProducedAmountParameters = monitoringParameters();
        MonitoringParameters productProducedParameters = monitoringParameters();
        
        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        requestList.add(new MonitoredItemCreateRequest(batchIdReadValueId, MonitoringMode.Reporting, batchIdParameters));
        requestList.add(new MonitoredItemCreateRequest(totalProductsReadValueId, MonitoringMode.Reporting, totalProductsParameters));
        requestList.add(new MonitoredItemCreateRequest(tempReadValueId, MonitoringMode.Reporting, tempParameters));
        requestList.add(new MonitoredItemCreateRequest(humidityReadValueId, MonitoringMode.Reporting, humidityParameters));
        requestList.add(new MonitoredItemCreateRequest(vibrationReadValueId, MonitoringMode.Reporting, vibrationParameters));
        requestList.add(new MonitoredItemCreateRequest(producedReadValueId, MonitoringMode.Reporting, producedParameters));
        requestList.add(new MonitoredItemCreateRequest(defectReadValueId, MonitoringMode.Reporting, defectParameters));
        requestList.add(new MonitoredItemCreateRequest(acceptableReadValueId, MonitoringMode.Reporting, acceptableParameters));
        requestList.add(new MonitoredItemCreateRequest(stopReasonReadValueId, MonitoringMode.Reporting, stopReasonParameters));
        requestList.add(new MonitoredItemCreateRequest(stateReadValueId, MonitoringMode.Reporting, stateParameters));
        requestList.add(new MonitoredItemCreateRequest(productsPrMinuteValueId, MonitoringMode.Reporting, productsPrMinuteParameters));
        requestList.add(new MonitoredItemCreateRequest(barleyReadValueId, MonitoringMode.Reporting, barleyParameters));
        requestList.add(new MonitoredItemCreateRequest(hopsReadValueId, MonitoringMode.Reporting, hopsParameters));
        requestList.add(new MonitoredItemCreateRequest(maltReadValueId, MonitoringMode.Reporting, maltParameters));
        requestList.add(new MonitoredItemCreateRequest(wheatReadValueId, MonitoringMode.Reporting, wheatParameters));
        requestList.add(new MonitoredItemCreateRequest(yeastReadValueId, MonitoringMode.Reporting, yeastParameters));
        requestList.add(new MonitoredItemCreateRequest(maintenanceCounterReadValueId, MonitoringMode.Reporting, maintenanceCounterParameters));

        Consumer<DataValue> onBatchIdItem = (dataValue) -> batchIdConsumerStarter(dataValue);
        Consumer<DataValue> onTotalProductsItem = (dataValue) -> totalProductsConsumerStarter(dataValue);
        Consumer<DataValue> onTempratureItem = (dataValue) -> temperatureConsumerStarter(dataValue);
        Consumer<DataValue> onHumidityItem = (dataValue) -> humidityConsumerStarter(dataValue);
        Consumer<DataValue> onVibrationItem = (dataValue) -> vibrationConsumerStarter(dataValue);
        Consumer<DataValue> onProducedItem = (dataValue) -> producedConsumerStarter(dataValue);
        Consumer<DataValue> onDefectItem = (dataValue) -> defectConsumerStarter(dataValue);
        Consumer<DataValue> onAcceptableItem = (dataValue) -> acceptableConsumerStarter(dataValue);
        Consumer<DataValue> onStopReasonItem = (dataValue) -> stopReasonConsumerStarter(dataValue);
        Consumer<DataValue> onStateReadItem = (dataValue) -> stateConsumerStarter(dataValue);
        Consumer<DataValue> onProductsPrMinuteReadItem = (dataValue) -> productsPrMinuteConsumerStarter(dataValue);

        Consumer<DataValue> onBarleyReadItem = (dataValue) -> barleyConsumerStarter(dataValue);
        Consumer<DataValue> onHopsReadItem = (dataValue) -> hopsConsumerStarter(dataValue);
        Consumer<DataValue> onMaltReadItem = (dataValue) -> maltConsumerStarter(dataValue);
        Consumer<DataValue> onWheatReadItem = (dataValue) -> wheatConsumerStarter(dataValue);
        Consumer<DataValue> onYeastReadItem = (dataValue) -> yeastConsumerStarter(dataValue);

        Consumer<DataValue> onMaintenanceCounterReadItem = (dataValue) -> maintenanceCounterConsumerStarter(dataValue);

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

            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId=" + item.getReadValueId().getNodeId());
                } else {
                    System.out.println("failed to create item for nodeId=" + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void productionNodes() {

        batchIdNode = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        totalProductsNode = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        tempNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        producedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        defectCountNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        productsPrMinuteNode = new NodeId(6, "::Program:Cube.Status.MachSpeed");
        acceptableCountNode = new NodeId(6, "::Program:product.good");

        // TODO Production detail nodes. Not used.
        productBadNode = new NodeId(6, "::Program:product.bad");
        productProducedAmountNode = new NodeId(6, "::Program:product.produce_amount");
        productProducedNode = new NodeId(6, "::Program:product.produced");
    }

    private void machineNodes() {

        stopReasonNode = new NodeId(6, "::Program:Cube.Admin.StopReason.ID");
        stateCurrentNode = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        maintenanceCounterNode = new NodeId(6, "::Program:Maintenance.Counter");
        maintenanceStateNode = new NodeId(6, "::Program:Maintenance.State");
        maintenanceTriggerNode = new NodeId(6, "::Program:Maintenance.Trigger");
    }

    private void materialNodes() {

        barleyNode = new NodeId(6, "::Program:Inventory.Barley");
        hopsNode = new NodeId(6, "::Program:Inventory.Hops");
        maltNode = new NodeId(6, "::Program:Inventory.Malt");
        wheatNode = new NodeId(6, "::Program:Inventory.Wheat");
        yeastNode = new NodeId(6, "::Program:Inventory.Yeast");
    }
    
    private MonitoringParameters monitoringParameters() {
        return new MonitoringParameters(
                Unsigned.uint(clientHandles.getAndIncrement()),
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
    } 
       
    @Override
    public void setConsumer(Consumer<String> consumer, String itemName) {
        consumerMap.put(itemName, consumer);
    }

    private void barleyConsumerStarter(DataValue dataValue) {
        consumerMap.get(BARLEY_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void hopsConsumerStarter(DataValue dataValue) {
        consumerMap.get(HOPS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void maltConsumerStarter(DataValue dataValue) {
        consumerMap.get(MALT_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void wheatConsumerStarter(DataValue dataValue) {
        consumerMap.get(WHEAT_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void yeastConsumerStarter(DataValue dataValue) {
        consumerMap.get(YEAST_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void batchIdConsumerStarter(DataValue dataValue) {
        consumerMap.get(BATCHID_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void productsPrMinuteConsumerStarter(DataValue dataValue) {
        consumerMap.get(PRODUCTS_PR_MINUTE_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void stateConsumerStarter(DataValue dataValue) {
        consumerMap.get(STATE_CURRENT_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void stopReasonConsumerStarter(DataValue dataValue) {
        consumerMap.get(STOP_REASON_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void defectConsumerStarter(DataValue dataValue) {
        consumerMap.get(DEFECT_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void acceptableConsumerStarter(DataValue dataValue) {
        consumerMap.get(ACCEPTABLE_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void producedConsumerStarter(DataValue dataValue) {
        consumerMap.get(PRODUCED_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void vibrationConsumerStarter(DataValue dataValue) {
        consumerMap.get(VIBRATION_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void humidityConsumerStarter(DataValue dataValue) {
        consumerMap.get(HUMIDITY_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void temperatureConsumerStarter(DataValue dataValue) {
        consumerMap.get(TEMPERATURE_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void totalProductsConsumerStarter(DataValue dataValue) {
        consumerMap.get(TOTAL_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void maintenanceCounterConsumerStarter(DataValue dataValue) {
        consumerMap.get(MAINTENANCE_COUNTER_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    // Get data from database
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
}
