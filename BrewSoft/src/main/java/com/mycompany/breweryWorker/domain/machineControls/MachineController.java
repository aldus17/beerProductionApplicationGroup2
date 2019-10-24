/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.breweryWorker.domain.machineControls;

import com.mycompany.brewsoft.breweryWorker.domain.interfaces.IBrewerDomain;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

/**
 *
 * @author ALEKSTUD
 */
public class MachineController implements IBrewerDomain {

    private MachineConnection mconn;

    public MachineController() {
        mconn = new MachineConnection();
    }

    public void startProduction(float batchID, float productID, float quantity, float machSpeed) {
        DataValue dataValue;
        try {
                NodeId nodeId;

                // Set parameter[0], batchid >65536
                NodeId batchIDNode = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
                DataValue dv = new DataValue(new Variant((float) batchID), null, null, null);
                mconn.getClient().writeValue(batchIDNode, dv).get();

                // Set parameter[1], Product id [0..5]
                NodeId productIdNode = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");

                mconn.getClient().writeValue(productIdNode, DataValue.valueOnly(new Variant((float) productID))).get();

                // Set parameter[2], Amount >65536
                NodeId quantityNode = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
                mconn.getClient().writeValue(quantityNode, DataValue.valueOnly(new Variant((float) quantity))).get();

                // Set the speed of production, table for speeds in projektoplæg.pdf 
                // Need to calculate the "right" speeds, maybe in mathlab
                NodeId speedNode = new NodeId(6, "::Program:Cube.Command.MachSpeed");
                mconn.getClient().writeValue(speedNode, DataValue.valueOnly(new Variant((float) machSpeed)));

                // Start the production
                nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
                mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(2))).get();

                nodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
                mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        } catch (ExecutionException ex) {
            Logger.getLogger(com.mycompany.brewsoft.MachineController.class.getName()).log(Level.SEVERE, null, ex);
            // Gracious fallback
        }
    }

    public void resetMachine() {
        try {
            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(1))).get();
            
            nodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopProduction() {
        try {
            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(3))).get();
            
            nodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abortProduction() {
        try {
            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(4))).get();
            
            nodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearState() {
        try {
            NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(5))).get();
            
            nodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
            mconn.getClient().writeValue(nodeId, DataValue.valueOnly(new Variant(true))).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
