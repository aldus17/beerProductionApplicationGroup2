/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.breweryWorker.domain.machineControls;

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
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

/**
 *
 * @author Andreas
 */
public class MachineConnection {
    private List<EndpointDescription> endpoints;
    private OpcUaClient client;
    private OpcUaClientConfigBuilder cfg;
    private String hostname = "127.0.0.1";
    //private String hostname = "192.168.0.122";
    private String port = "4840";

    public MachineConnection() {
        try {
            endpoints = DiscoveryClient.getEndpoints("opc.tcp://" + hostname + ":" + port).get();
            System.out.println(endpoints);
            System.out.println("test: " + endpoints.get(0).getEndpointUrl());
        } catch (InterruptedException ex) {
            //Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("1");
            // Gracious fallback
        } catch (ExecutionException ex) {
            //Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            client = OpcUaClient.create(cfg.build());
            client.connect().get();
        } catch (UaException ex) {
            //Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
            System.out.println("3");
        } catch (InterruptedException ex) {
            //Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
            System.out.println("4");
        } catch (ExecutionException ex) {
            //Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
            System.out.println("5");
        }
    }

    public OpcUaClient getClient() {
        return client;
    }
}
