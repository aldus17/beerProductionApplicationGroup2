package com.mycompany.domain.breweryWorker;

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

public class MachineConnection {

    private List<EndpointDescription> endpoints;
    private OpcUaClient client;
    private OpcUaClientConfigBuilder cfg;
    private String hostname;
    private int port;
    private URI uri;

    public MachineConnection() {
    }

    public MachineConnection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() {
        try {
            this.endpoints = DiscoveryClient.getEndpoints("opc.tcp://" + hostname + ":" + port).get();

            this.cfg = new OpcUaClientConfigBuilder();

            EndpointDescription original = this.endpoints.get(0);
            this.uri = new URI(original.getEndpointUrl()).parseServerAuthority();
            String endpointUrl = String.format(
                    "%s://%s:%s%s",
                    this.uri.getScheme(),
                    this.hostname,
                    this.uri.getPort(),
                    this.uri.getPath()
            );

            EndpointDescription endpoint = new EndpointDescription(endpointUrl,
                    original.getServer(),
                    original.getServerCertificate(),
                    original.getSecurityMode(),
                    original.getSecurityPolicyUri(),
                    original.getUserIdentityTokens(),
                    original.getTransportProfileUri(),
                    original.getSecurityLevel());

            this.cfg.setEndpoint(endpoint);

            this.client = OpcUaClient.create(this.cfg.build());
            this.client.connect().get();

        } catch (UaException ex) {
            Logger.getLogger(MachineConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MachineConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OpcUaClient getClient() {
        return this.client;
    }
}
