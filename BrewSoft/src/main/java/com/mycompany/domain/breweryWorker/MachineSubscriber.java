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

    // TODO Production detail nodes. Not used.
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

    private String barleyValueString;

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

        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        requestList.add(new MonitoredItemCreateRequest(readValueId(batchIdNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(totalProductsNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(tempNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(humidityNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(vibrationNode), MonitoringMode.Reporting, monitoringParameters()));
        requestList.add(new MonitoredItemCreateRequest(readValueId(producedCountNode), MonitoringMode.Reporting, monitoringParameters()));
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

        Consumer<DataValue> onBarleyReadItem = (DataValue dataValue) -> consumerStarter(BARLEY_NODENAME, dataValue);
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

    // Returns the value of the node.
    public String barleyValue() {
        return this.barleyValueString;
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
        // Sets the value of the node to the attribute.
        if (nodename == BARLEY_NODENAME) {
            this.barleyValueString = dataValue.getValue().getValue().toString();
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

    private class ConsumerImpl implements Consumer<String> {

        public ConsumerImpl() {
        }
        public String inner;

        @Override
        public void accept(String text) {
            this.inner = text;
            barleyValueString = text;
            System.out.println("Text: " + text);
            System.out.println("Barley Value: " + inner);
        }

        public String getInner() {
            return inner;
        }
    }
}
