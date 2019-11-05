/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author PCATG
 */
public class MachineSubscribeDataHandlerTest {
    
    public MachineSubscribeDataHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertProductionInfoData method, of class MachineSubscribeDataHandler.
     */
    @org.junit.Test
    public void testInsertProductionInfoData() {
        System.out.println("insertProductionInfoData");
        int machineID = 0;
        int batchID = 0;
        MachineSubscribeDataHandler instance = new MachineSubscribeDataHandler();
        boolean expResult = false;
        boolean result = instance.insertProductionInfoData(machineID, batchID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProductionInfoData method, of class MachineSubscribeDataHandler.
     */
    @org.junit.Test
    public void testUpdateProductionInfoData() {
        System.out.println("updateProductionInfoData");
        int machineID = 0;
        int batchID = 0;
        MachineSubscribeDataHandler instance = new MachineSubscribeDataHandler();
        boolean expResult = false;
        boolean result = instance.updateProductionInfoData(machineID, batchID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertFinalBatchInformationData method, of class MachineSubscribeDataHandler.
     */
    @org.junit.Test
    public void testInsertFinalBatchInformationData() {
        System.out.println("insertFinalBatchInformationData");
        MachineSubscribeDataHandler instance = new MachineSubscribeDataHandler();
        instance.insertFinalBatchInformationData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateFinalBatchInfo method, of class MachineSubscribeDataHandler.
     */
    @org.junit.Test
    public void testUpdateFinalBatchInfo() {
        System.out.println("updateFinalBatchInfo");
        MachineSubscribeDataHandler instance = new MachineSubscribeDataHandler();
        boolean expResult = false;
        boolean result = instance.updateFinalBatchInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertFinalBatchInfo method, of class MachineSubscribeDataHandler.
     */
    @org.junit.Test
    public void testInsertFinalBatchInfo() {
        System.out.println("insertFinalBatchInfo");
        MachineSubscribeDataHandler instance = new MachineSubscribeDataHandler();
        boolean expResult = false;
        boolean result = instance.insertFinalBatchInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFinalBatchInfo method, of class MachineSubscribeDataHandler.
     */
    @org.junit.Test
    public void testReadFinalBatchInfo() {
        System.out.println("readFinalBatchInfo");
        MachineSubscribeDataHandler instance = new MachineSubscribeDataHandler();
        boolean expResult = false;
        boolean result = instance.readFinalBatchInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
