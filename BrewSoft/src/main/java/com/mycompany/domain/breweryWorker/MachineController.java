package com.mycompany.domain.breweryWorker;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.data.dataAccess.MachineSubscribeDataHandler;
import com.mycompany.data.interfaces.IMachineSubscriberDataHandler;
import com.mycompany.domain.breweryWorker.interfaces.IMachineControl;
import com.mycompany.domain.breweryWorker.interfaces.IMachineSubscribe;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

public class MachineController implements IMachineControl {

    private final MachineConnection mconn;

    private final NodeId cntrlCmdNodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
    private final NodeId cmdChangeRequestNodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
    
    private IMachineSubscriberDataHandler msdh = new MachineSubscribeDataHandler();
    
    private IMachineSubscribe subscriber;

    public MachineController() {
        this("192.168.0.122", 4840, null);
    }

    public MachineController(String hostname, int port) {
        this(hostname, port, null);
    }
    
    public MachineController(String hostname, int port, IMachineSubscribe subscriber) {
        mconn = new MachineConnection(hostname, port);
        mconn.connect();
        this.subscriber = subscriber;
    }
    
    @Override
    public void startProduction() {
        Batch newBatch = msdh.getNextBatch();
        subscriber.setCurrentBatch(newBatch);
        // TODO Changes value of production List ID
        msdh.changeProductionListStatus(Integer.parseInt(newBatch.getProductionListID().getValue()), "In Production");
        
        try {
            // Set parameter[0], batchid > 65536
            NodeId batchIDNode = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
            System.out.println(Float.parseFloat(newBatch.getBatchID().getValue()));
            DataValue dv = new DataValue(new Variant(Float.parseFloat(newBatch.getBatchID().getValue())), null, null, null);
            mconn.getClient().writeValue(batchIDNode, dv).get();

            // Set parameter[1], Product id [0..5]
            NodeId productIdNode = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
            mconn.getClient().writeValue(productIdNode, DataValue.valueOnly(new Variant(Float.parseFloat(newBatch.getType().getValue())))).get();

            // Set parameter[2], Amount >65536
            NodeId quantityNode = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
            mconn.getClient().writeValue(quantityNode, DataValue.valueOnly(new Variant(Float.parseFloat(newBatch.getTotalAmount().getValue())))).get();

            // Set the speed of production, table for speeds in projektoplæg.pdf
            // Need to calculate the "right" speeds, maybe in mathlab
            NodeId speedNode = new NodeId(6, "::Program:Cube.Command.MachSpeed");
            mconn.getClient().writeValue(speedNode, DataValue.valueOnly(new Variant(Float.parseFloat(newBatch.getSpeedforProduction().getValue()))));
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Start the production
        sendCntrlCmd(new Variant(2));
        sendCmdRequest();
    }

    public void changeSpeed(float machSpeed) {
        NodeId speedNode = new NodeId(6, "::Program:Cube.Command.MachSpeed");
        mconn.getClient().writeValue(speedNode, DataValue.valueOnly(new Variant((float) machSpeed)));
    }

    @Override
    public void resetMachine() {
        sendCntrlCmd(new Variant(1));
        sendCmdRequest();
    }

    @Override
    public void stopProduction() {
        sendCntrlCmd(new Variant(3));
        sendCmdRequest();
    }

    @Override
    public void abortProduction() {
        sendCntrlCmd(new Variant(4));
        sendCmdRequest();
    }

    @Override
    public void clearState() {
        sendCntrlCmd(new Variant(5));
        sendCmdRequest();
    }

    private void sendCntrlCmd(Variant variantNo) {
        try {
            mconn.getClient().writeValue(cntrlCmdNodeId, DataValue.valueOnly(variantNo)).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendCmdRequest() {
        try {
            mconn.getClient().writeValue(cmdChangeRequestNodeId, DataValue.valueOnly(new Variant(true))).get();
        } catch (ExecutionException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
