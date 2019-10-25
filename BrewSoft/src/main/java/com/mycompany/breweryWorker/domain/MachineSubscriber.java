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
    private Map<String,Consumer<String>> consumerMap;

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
        NodeId batchIdNode = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        NodeId quantityNode = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        NodeId tempNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        NodeId humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        NodeId vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        NodeId producedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        NodeId defectCountNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        //NodeId stopReasonNode = new NodeId(6, "::Program:Cube.Admin.StopReason.Id");
        //NodeId stateCurrentNode = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        NodeId productsPrMinuteNode = new NodeId(6, "::Program:Cube.Status.MachSpeed");

        ReadValueId batchIdReadValueId = new ReadValueId(batchIdNode, AttributeId.Value.uid(), null, null);
        ReadValueId quantityReadValueId = new ReadValueId(quantityNode, AttributeId.Value.uid(), null, null);
        ReadValueId tempReadValueId = new ReadValueId(tempNode, AttributeId.Value.uid(), null, null);
        ReadValueId humidityReadValueId = new ReadValueId(humidityNode, AttributeId.Value.uid(), null, null);
        ReadValueId vibrationReadValueId = new ReadValueId(vibrationNode, AttributeId.Value.uid(), null, null);
        ReadValueId producedReadValueId = new ReadValueId(producedCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId failedReadValueId = new ReadValueId(defectCountNode, AttributeId.Value.uid(), null, null);
        //ReadValueId stopReasonReadValueId = new ReadValueId(stopReasonNode, AttributeId.Value.uid(), null, null);
        //ReadValueId stateReadValueId = new ReadValueId(stateCurrentNode, AttributeId.Value.uid(), null, null);
        ReadValueId productsPrMinuteValueId = new ReadValueId(productsPrMinuteNode, AttributeId.Value.uid(), null, null);

        // important: client handle must be unique per item
        UInteger batchIdClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger totalProductsClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger tempClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger humidityClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger vibrationClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger producedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());  
        UInteger failedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger stopReasonClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger stateClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        //UInteger t = clientHandles.getAndIncrement();
        UInteger productsPrMinuteHandle = Unsigned.uint(clientHandles.getAndIncrement());
        
        System.out.println(clientHandles.getAndIncrement());
        
        System.out.println(batchIdClientHandle);
        System.out.println(totalProductsClientHandle);
        System.out.println(tempClientHandle);
        System.out.println(humidityClientHandle);
        System.out.println(vibrationClientHandle);
        System.out.println(producedClientHandle);
        System.out.println(failedClientHandle);
        System.out.println(stopReasonClientHandle);
        System.out.println(stateClientHandle);
        System.out.println(productsPrMinuteHandle);

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
        MonitoringParameters failedParameters = new MonitoringParameters(
                failedClientHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );
/*
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
*/
        MonitoringParameters productsPrMinuteParameters = new MonitoringParameters(
                productsPrMinuteHandle,
                1000.0, // sampling interval
                null, // filter, null means use default
                Unsigned.uint(10), // queue size
                true // discard oldest
        );

        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        requestList.add(new MonitoredItemCreateRequest(batchIdReadValueId, MonitoringMode.Reporting, batchIdParameters));
        requestList.add(new MonitoredItemCreateRequest(quantityReadValueId, MonitoringMode.Reporting, totalProductsParameters));
        requestList.add(new MonitoredItemCreateRequest(tempReadValueId, MonitoringMode.Reporting, tempParameters));
        requestList.add(new MonitoredItemCreateRequest(humidityReadValueId, MonitoringMode.Reporting, humidityParameters));
        requestList.add(new MonitoredItemCreateRequest(vibrationReadValueId, MonitoringMode.Reporting, vibrationParameters));
        requestList.add(new MonitoredItemCreateRequest(producedReadValueId, MonitoringMode.Reporting, producedParameters));
        requestList.add(new MonitoredItemCreateRequest(failedReadValueId, MonitoringMode.Reporting, failedParameters));
        //requestList.add(new MonitoredItemCreateRequest(stopReasonReadValueId, MonitoringMode.Reporting, stopReasonParameters));
        //requestList.add(new MonitoredItemCreateRequest(stateReadValueId, MonitoringMode.Reporting, stateParameters));
        requestList.add(new MonitoredItemCreateRequest(productsPrMinuteValueId, MonitoringMode.Reporting, productsPrMinuteParameters));
        
        Consumer<DataValue> onBatchIdItem = (dataValue) -> batchIdConsumerStarter(dataValue);
        Consumer<DataValue> onTotalProductsItem = (dataValue) -> totalProductsConsumerStarter(dataValue);
        Consumer<DataValue> onTempratureItem = (dataValue) -> temperatureConsumerStarter(dataValue);
        Consumer<DataValue> onHumidityItem = (dataValue) -> humidityConsumerStarter(dataValue);
        Consumer<DataValue> onVibrationItem = (dataValue) -> vibrationConsumerStarter(dataValue);
        Consumer<DataValue> onProducedItem = (dataValue) -> producedConsumerStarter(dataValue);
        Consumer<DataValue> onFailedItem = (dataValue) -> failedConsumerStarter(dataValue);
        Consumer<DataValue> onStopReasonItem = (dataValue) -> stopReasonConsumerStarter(dataValue);
        Consumer<DataValue> onStateReadItem = (dataValue) -> stateConsumerStarter(dataValue);
        Consumer<DataValue> onProductsPrMinuteReadItem = (dataValue) -> productsPrMinuteConsumerStarter(dataValue);
        
        
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
            items.get(6).setValueConsumer(onFailedItem);
            //items.get(7).setValueConsumer(onStopReasonItem);
            //items.get(8).setValueConsumer(onStateReadItem);
            items.get(7).setValueConsumer(onProductsPrMinuteReadItem);

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

    private static void onBatchIdParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onQuantityParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onTempratureParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onHumidityParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onVibrationParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onProducedParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onFailedParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onStopReasonParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    private static void onStateReadParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }
    
    private static void onSpeedReadParameter(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }

    @Override
    public void setConsumer(Consumer<String> consumer, String itemName) {
        consumerMap.put(itemName, consumer);
        System.out.println(itemName);
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

    private void failedConsumerStarter(DataValue dataValue) {
        System.out.println(DEFECT_PRODUCTS_NODENAME);
        consumerMap.get(DEFECT_PRODUCTS_NODENAME).accept(dataValue.getValue().getValue().toString());
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

}
