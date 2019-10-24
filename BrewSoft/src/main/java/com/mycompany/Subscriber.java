package com.mycompany.brewsoft;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

/**
 *
 * @author Mathias
 */
public class Subscriber {
    private List<EndpointDescription> endpoints;
    private OpcUaClient client;
    private OpcUaClientConfigBuilder cfg;
    //private String hostname = "127.0.0.1:4840";
    private String hostname = "192.168.0.122";
    private String port = ":4840";
    
    private static final AtomicLong clientHandles = new AtomicLong(1L);
    
    public Subscriber() {
        try {
            endpoints = DiscoveryClient.getEndpoints("opc.tcp://" + hostname + port).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        }
        cfg = new OpcUaClientConfigBuilder();
        //cfg.setEndpoint(endpoints.get(0));
        
        URI uri;
        try {
            EndpointDescription original = endpoints.get(0);
            uri = new URI(original.getEndpointUrl()).parseServerAuthority();
            String endpointUrl = String.format(
                "%s://%s:%s%s",
                uri.getScheme(),
                hostname,
                uri.getPort(),
                uri.getPath()
            );
            
            EndpointDescription endpoint = new EndpointDescription(endpointUrl, 
                original.getServer(),
                original.getServerCertificate(),
                original.getSecurityMode(),
                original.getSecurityPolicyUri(),
                original.getUserIdentityTokens(),
                original.getTransportProfileUri(),
                original.getSecurityLevel());
            
            cfg.setEndpoint(endpoint);
            System.out.println("test: " + endpoint);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            client = OpcUaClient.create(cfg.build());
            client.connect().get();
        } catch (UaException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        }
    }
    
    public void subscribeAll() {
        NodeId batchIdNode = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        NodeId quantityNode = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        NodeId tempNode = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        NodeId humidityNode = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        NodeId vibrationNode = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        NodeId producedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        NodeId failedCountNode = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        
        ReadValueId batchIdReadValueId = new ReadValueId(batchIdNode, AttributeId.Value.uid(), null, null);
        ReadValueId quantityReadValueId = new ReadValueId(quantityNode, AttributeId.Value.uid(), null, null);
        ReadValueId tempReadValueId = new ReadValueId(tempNode, AttributeId.Value.uid(), null, null);
        ReadValueId humidityReadValueId = new ReadValueId(humidityNode, AttributeId.Value.uid(), null, null);
        ReadValueId vibrationReadValueId = new ReadValueId(vibrationNode, AttributeId.Value.uid(), null, null);
        ReadValueId producedReadValueId = new ReadValueId(producedCountNode, AttributeId.Value.uid(), null, null);
        ReadValueId failedReadValueId = new ReadValueId(failedCountNode, AttributeId.Value.uid(), null, null);

        // important: client handle must be unique per item
        UInteger batchIdClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger quantityClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger tempClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger humidityClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger vibrationClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger producedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        UInteger failedClientHandle = Unsigned.uint(clientHandles.getAndIncrement());

        MonitoringParameters batchIdParameters = new MonitoringParameters(
            batchIdClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        
        MonitoringParameters quantityParameters = new MonitoringParameters(
           quantityClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        MonitoringParameters tempParameters = new MonitoringParameters(
           tempClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        MonitoringParameters humidityParameters = new MonitoringParameters(
           humidityClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        MonitoringParameters vibrationParameters = new MonitoringParameters(
           vibrationClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        MonitoringParameters producedParameters = new MonitoringParameters(
            producedClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        MonitoringParameters failedParameters = new MonitoringParameters(
            failedClientHandle,
            1000.0,     // sampling interval
            null,       // filter, null means use default
            Unsigned.uint(10),   // queue size
            true        // discard oldest
        );
        
        List<MonitoredItemCreateRequest> requestList = new ArrayList();
        requestList.add(new MonitoredItemCreateRequest(batchIdReadValueId, MonitoringMode.Reporting, batchIdParameters));
        requestList.add(new MonitoredItemCreateRequest(quantityReadValueId, MonitoringMode.Reporting, quantityParameters));
        requestList.add(new MonitoredItemCreateRequest(tempReadValueId, MonitoringMode.Reporting, tempParameters));
        requestList.add(new MonitoredItemCreateRequest(humidityReadValueId, MonitoringMode.Reporting, humidityParameters));
        requestList.add(new MonitoredItemCreateRequest(vibrationReadValueId, MonitoringMode.Reporting, vibrationParameters));
        requestList.add(new MonitoredItemCreateRequest(producedReadValueId, MonitoringMode.Reporting, producedParameters));
        requestList.add(new MonitoredItemCreateRequest(failedReadValueId, MonitoringMode.Reporting, failedParameters));
        
        BiConsumer<UaMonitoredItem, Integer> onItemCreated = (item, id) -> item.setValueConsumer(Subscriber::onSubscribeValue);
        
        try {
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, requestList, onItemCreated).get();
            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId=" + item.getReadValueId().getNodeId());
                } else{
                    System.out.println("failed to create item for nodeId=" + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void onSubscribeValue(UaMonitoredItem item, DataValue value) {
        System.out.println("Item: " + item.getReadValueId().getNodeId() + " Value: " + value.getValue());
    }
}
