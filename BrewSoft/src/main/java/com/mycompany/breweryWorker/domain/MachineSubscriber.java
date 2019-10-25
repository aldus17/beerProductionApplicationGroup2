/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.breweryWorker.domain;

import com.mycompany.breweryWorker.domain.interfaces.IMachineSubscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

/**
 *
 * @author Andreas
 */
public class MachineSubscriber implements IMachineSubscribe {

    private static final AtomicLong clientHandles = new AtomicLong(1L);
    private MachineConnection mconn;
    private Map<String, Consumer<String>> consumerMap;

    //TODO: pull ip and port from DB
    public MachineSubscriber() {
        this("127.0.0.1", 4840);
    }

    public MachineSubscriber(String hostname, int port) {
        mconn = new MachineConnection(hostname, port);
        mconn.connect();
        consumerMap = new HashMap();
    }

    public void subscribe() {

        //Production detail nodes
        NodeId batchIdNode = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");         //+
        NodeId totalProductsNode = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");   //+
        NodeId tempNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");            //+
        NodeId humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");        //+
        NodeId vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");       //+
        NodeId producedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");    //+
        NodeId defectCountNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");      //+
        NodeId productsPrMinuteNode = new NodeId(6, "::Program:Cube.Status.MachSpeed");         //+
        NodeId acceptableCountNode = new NodeId(6, "::Program:product.good");                   //+     
        
        //Production detail nodes. Not used.
        NodeId productBadNode = new NodeId(6, "::Program:product.bad");
        NodeId productProducedAmountNode = new NodeId(6, "::Program:product.produce_amount");
        NodeId productProducedNode = new NodeId(6, "::Program:product.produced");

        //Machine specific nodes
        NodeId stopReasonNode = new NodeId(6, "::Program:Cube.Admin.StopReason.ID");
        NodeId stateCurrentNode = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        NodeId maintenanceCounterNode = new NodeId(6, "::Program:Maintenance.Counter");
        NodeId maintenanceStateNode = new NodeId(6, "::Program:Maintenance.State");
        NodeId maintenanceTriggerNode = new NodeId(6, "::Program:Maintenance.Trigger");

        //Material nodes
        NodeId barleyNode = new NodeId(6, "::Program:Inventory.Barley");
        NodeId hopsNode = new NodeId(6, "::Program:Inventory.Hops");
        NodeId maltNode = new NodeId(6, "::Program:Inventory.Malt");
        NodeId wheatNode = new NodeId(6, "::Program:Inventory.Wheat");
        NodeId yeastNode = new NodeId(6, "::Program:Inventory.Yeast");

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

        // important: client handle must be unique per item
        UInteger batchIdClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger totalProductsClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger tempClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger humidityClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger vibrationClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger producedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger defectClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger stopReasonClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger stateClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        //UInteger t = clientHandles.getAndIncrement();
        UInteger productsPrMinuteHandle = Unsigned.uint(clientHandles.getAndIncrement());

//        System.out.println(clientHandles.getAndIncrement());
//
//        System.out.println(batchIdClientHandle);
//        System.out.println(totalProductsClientHandle);
//        System.out.println(tempClientHandle);
//        System.out.println(humidityClientHandle);
//        System.out.println(vibrationClientHandle);
//        System.out.println(producedClientHandle);
//        System.out.println(defectClientHandle);
//        System.out.println(stopReasonClientHandle);
//        System.out.println(stateClientHandle);
//        System.out.println(productsPrMinuteHandle);

        UInteger barleyClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger hopsClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger maltClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger wheatClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger yeastClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        UInteger maintenanceCounterClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger maintenanceStateClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger maintenanceTriggerClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        UInteger productBadClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger acceptableClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger productProducedAmountClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger productProducedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        MonitoringParameters batchIdParameters = new MonitoringParameters(
                batchIdClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );

        MonitoringParameters totalProductsParameters = new MonitoringParameters(
                totalProductsClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters tempParameters = new MonitoringParameters(
                tempClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters humidityParameters = new MonitoringParameters(
                humidityClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters vibrationParameters = new MonitoringParameters(
                vibrationClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters producedParameters = new MonitoringParameters(
                producedClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters defectParameters = new MonitoringParameters(
                defectClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters acceptableParameters = new MonitoringParameters(
                acceptableClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        
         MonitoringParameters stopReasonParameters = new MonitoringParameters(
                stopReasonClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
         );

         MonitoringParameters stateParameters = new MonitoringParameters(
                stateClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
         );
       
        MonitoringParameters productsPrMinuteParameters = new MonitoringParameters(
                productsPrMinuteHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
        MonitoringParameters barleyParameters = new MonitoringParameters(
                barleyClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters hopsParameters = new MonitoringParameters(
                hopsClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maltParameters = new MonitoringParameters(
                maltClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters wheatParameters = new MonitoringParameters(
                wheatClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters yeastParameters = new MonitoringParameters(
                yeastClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maintenanceCounterParameters = new MonitoringParameters(
                maintenanceCounterClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maintenanceStateParameters = new MonitoringParameters(
                maintenanceStateClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters maintenanceTriggerParameters = new MonitoringParameters(
                maintenanceTriggerClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productBadParameters = new MonitoringParameters(
                productBadClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productProducedAmountParameters = new MonitoringParameters(
                productProducedAmountClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
        MonitoringParameters productProducedParameters = new MonitoringParameters(
                productProducedClientHandle,
                1000.0,
                null,
                Unsigned.uint(10),
                true
        );
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
        
        

//        BiConsumer<UaMonitoredItem, DataValue> onBatchIdItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onBatchIdParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onQuantityItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onQuantityParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onTempratureItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onTempratureParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onHumidityItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onHumidityParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onVibrationItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onVibrationParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onProducedItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onProducedParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onFailedItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onFailedParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onStopReasonItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onStopReasonParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onStateReadItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onStateReadParameter);
//        BiConsumer<UaMonitoredItem, DataValue> onSpeedReadItem = (item, id) -> item.setValueConsumer(MachineSubscriber::onSpeedReadParameter);
        try {
            UaSubscription subscription = mconn.getClient().getSubscriptionManager().createSubscription(1000.0).get();
            //List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, requestList, onItemCreated).get();
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
    
    private static void onSubscribeValue(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    @Override
    public void setConsumer(Consumer<String> consumer, String itemName) {
        consumerMap.put(itemName, consumer);
        System.out.println(itemName);
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
        System.out.println(BATCHID_NODENAME + " " + dataValue.getValue() + " " + consumerMap.size());
        consumerMap.get(BATCHID_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void productsPrMinuteConsumerStarter(DataValue dataValue) {
        System.out.println(PRODUCTS_PR_MINUTE_NODENAME);
        consumerMap.get(PRODUCTS_PR_MINUTE_NODENAME).accept(dataValue.getValue().getValue().toString());
        System.out.println(dataValue.getValue());
    }

    private void stateConsumerStarter(DataValue dataValue) {
        System.out.println(STATE_CURRENT_NODENAME);
        consumerMap.get(STATE_CURRENT_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void stopReasonConsumerStarter(DataValue dataValue) {
        System.out.println(STOP_REASON_NODENAME);
        consumerMap.get(STOP_REASON_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void defectConsumerStarter(DataValue dataValue) {
        System.out.println(DEFECT_PRODUCTS_NODENAME);
        consumerMap.get(DEFECT_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void acceptableConsumerStarter(DataValue dataValue) {
        consumerMap.get(ACCEPTABLE_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void producedConsumerStarter(DataValue dataValue) {
        System.out.println(PRODUCED_PRODUCTS_NODENAME);
        consumerMap.get(PRODUCED_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void vibrationConsumerStarter(DataValue dataValue) {
        System.out.println(VIBRATION_NODENAME);
        consumerMap.get(VIBRATION_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void humidityConsumerStarter(DataValue dataValue) {
        System.out.println(HUMIDITY_NODENAME);
        consumerMap.get(HUMIDITY_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void temperatureConsumerStarter(DataValue dataValue) {
        System.out.println(TEMPERATURE_NODENAME);
        consumerMap.get(TEMPERATURE_NODENAME).accept(dataValue.getValue().getValue().toString());
    }

    private void totalProductsConsumerStarter(DataValue dataValue) {
        System.out.println(TOTAL_PRODUCTS_NODENAME);
        consumerMap.get(TOTAL_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
    }
    private void maintenanceCounterConsumerStarter(DataValue dataValue) {
        consumerMap.get(MAINTENANCE_COUNTER_NODENAME).accept(dataValue.getValue().getValue().toString());
    }
    
    public String stopReasonTranslator(String stopReason){
        switch(stopReason){
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
    
    public String stateTranslator(String state){
        switch(state){
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
