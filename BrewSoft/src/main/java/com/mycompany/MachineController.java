package com.mycompany.brewsoft;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

/**
 *
 * @author Mathias
 */
public class MachineController {
    private List<EndpointDescription> endpoints;
    private OpcUaClient client;
    private OpcUaClientConfigBuilder cfg;
    //private String hostname = "127.0.0.1:4840";
    private String hostname = "192.168.0.122";
    private String port = ":4840";
    
    /**
     * @TODO: Refactor so endpoint and connection is separated
     */
    
    public MachineController() {
        try {
            endpoints = DiscoveryClient.getEndpoints("opc.tcp://" + hostname + port).get();
            System.out.println(endpoints);
            System.out.println("test: " + endpoints.get(0).getEndpointUrl());
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("1");
            // Gracious fallback
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
            System.out.println("2");
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
            System.out.println("3");
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
            System.out.println("4");
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
            System.out.println("5");
        }
    }
    
    public void startProduction() {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        DataValue dataValue;
        try {
            dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant v = dataValue.getValue();
            int state = (Integer)v.getValue();
            System.out.println("State = " + state);
            if(state == 2 || state == 17) {
                System.out.println("State = 2, Stopped OR State = 17, Completed");
                
                nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
                client.writeValue(nodeId, DataValue.valueOnly(new Variant(1))).get();
                
                nodeId  = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
                client.writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
            }
            if(state == 4) {
                System.out.println("State = 4, idle");
                
                // Set parameter[0], batchid >65536
                float batchID = 1; // Change to create a "random" id
                NodeId batchIDNode = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
                DataValue dv = new DataValue (new Variant((float)batchID), null, null, null);
                //System.out.println(dv);
                StatusCode t = client.writeValue(batchIDNode, dv).get();
                //System.out.println(t);
                
                // Set parameter[1], Product id [0..5]
                float productID = 0; // Change to input from DB or user
                NodeId productIdNode = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
                
                client.writeValue(productIdNode, DataValue.valueOnly(new Variant((float)productID))).get();
                
                // Set parameter[2], Amount >65536
                float quantity = 100;
                NodeId quantityNode = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
                client.writeValue(quantityNode, DataValue.valueOnly(new Variant((float)quantity))).get();
                
                // Set the speed of production, table for speeds in projektoplæg.pdf 
                // Need to calculate the "right" speeds, maybe in mathlab
                // curMachSpeed = MachSpeed/2 dont know why
                float machSpeed = 50;
                NodeId speedNode = new NodeId(6, "::Program:Cube.Command.MachSpeed");
                client.writeValue(speedNode, dataValue.valueOnly(new Variant((float)machSpeed)));
                
                /**
                 * Next 19 is for test output only
                 *
                    System.out.println(productIdNode);
                    
                    
                    dataValue = client.readValue(0, TimestampsToReturn.Both, batchIDNode).get();
                    Variant param0 = dataValue.getValue();
                    float para0 = (float)param0.getValue();
                    System.out.println("Parameter[0](Batch ID): " + para0);

                    dataValue = client.readValue(0, TimestampsToReturn.Both, productIdNode).get();
                    Variant param1 = dataValue.getValue();
                    float para1 = (float)param1.getValue();
                    System.out.println("Parameter[1](Amount): " + para1);

                    dataValue = client.readValue(0, TimestampsToReturn.Both, quantityNode).get();
                    Variant param2 = dataValue.getValue();
                    float para2 = (float)param2.getValue();
                    System.out.println("Parameter[2](Quantity): " + para2);
                    
                    dataValue = client.readValue(0, TimestampsToReturn.Both, speedNode).get();
                    Variant speed = dataValue.getValue();
                    float s = (float)speed.getValue();
                    System.out.println("Machine speed: " + s);
                 //*/
                
                // Start the production
                nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
                client.writeValue(nodeId, DataValue.valueOnly(new Variant(2))).get();
                
                nodeId  = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
                client.writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
            }
            if(state == 9) {
                System.out.println("State = 9, aborted");
                
                nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
                client.writeValue(nodeId, DataValue.valueOnly(new Variant(5))).get();
                
                nodeId  = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
                client.writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        }    
    }
    
//    public static void main(String[] args) {
//        MachineController mc = new MachineController();
//        mc.startProduction();
//        
//        Subscriber ss = new Subscriber();
//        ss.subscribeAll();
//    }
}
