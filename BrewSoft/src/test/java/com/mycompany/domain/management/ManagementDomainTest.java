/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.BeerTypes;
import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author PCATG
 */
public class ManagementDomainTest {
    
    private IBatchDataHandler batchDataHandler = new BatchDataHandler();
    
    public ManagementDomainTest() {
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
     * Test of createBatch method, of class ManagementDomain.
     */
    @Test
    public void testCreateBatch() {
        System.out.println("createBatch");
        Batch batch = null;
        ManagementDomain instance = new ManagementDomain();
        instance.createBatch(batch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of batchObjects method, of class ManagementDomain.
     */
    @Test
    public void testBatchObjects() {
        System.out.println("batchObjects");
        String searchKey = "";
        String searchValue = "";
        ManagementDomain instance = new ManagementDomain();
        List<Batch> expResult = null;
        List<Batch> result = instance.batchObjects(searchKey, searchValue);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calulateOEE method, of class ManagementDomain.
     */
    @Test
    public void testCalulateOEE() {
        System.out.println("calulateOEE");
        LocalDate searchDate = null;
        ManagementDomain instance = new ManagementDomain();
        double expResult = 0.0;
        double result = instance.calulateOEE(searchDate);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBeerTypes method, of class ManagementDomain.
     */
    @Test
    public void testGetBeerTypes() {
        System.out.println("getBeerTypes");
        ManagementDomain instance = new ManagementDomain();
        List<BeerTypes> expResult = null;
        List<BeerTypes> result = instance.getBeerTypes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void getDifferenceTimeInState() {
        System.out.println("getDifferenceTimeInState");
        ManagementDomain instance = new ManagementDomain();
        String value1 = "12:31:22";
        String value2 = "13:40:49";
        String expectedValue = "01:09:27";
        String actualValue = instance.getDifferenceTimeInState(value1,value2);
        
        assertEquals(expectedValue, actualValue);
        System.out.println("Expected: " + expectedValue + "\nActualValue: " + actualValue);
    }

    /**
     * Test of getTimeInStates method, of class ManagementDomain.
     */
    @Test
    public void testGetTimeInStates() {
        System.out.println("getTimeInStates");
        String prodListID = "410";
        ManagementDomain instance = new ManagementDomain();
        MachineState ms = batchDataHandler.getMachineState(prodListID);
        TreeMap<Integer, String> allTimeValues = new TreeMap<>();
        TreeMap<Integer, String> timeDifferenceMap = new TreeMap<>();
        System.out.println(instance.getDifferenceTimeInState("12:31:22", "13:40:49"));
//        for (int i = 0; i <= 19; i++) {
//            allTimeValues.put(i, Integer.valueOf(ms.getMachineStateID()) == i ? ms.getTimeInStates() : "");
//            if (Integer.valueOf(ms.getMachineStateID()) == 0) {
//                System.out.println("TEST: " + ms.getTimeInStates());
//            }
            
        }
      
//        expResult.put(0, "10:05:30");
//        expResult.put(1, "11:05:30");
//        expResult.put(2, "12:05:30");
//        expResult.put(3, "13:03:40");
//        expResult.put(4, "16:05:30");
//        expResult.put(5, "10:05:30");
//        expResult.put(6, "10:05:30");
//        expResult.put(7, "10:05:30");
//        expResult.put(8, "10:05:30");
//        expResult.put(9, "10:05:30");
//        expResult.put(10, "10:05:30");
//        expResult.put(11, "10:05:30");
//        expResult.put(12, "10:05:30");
//        expResult.put(13, "10:05:30");
//        expResult.put(14, "10:05:30");
//        expResult.put(15, "10:05:30");
//        expResult.put(16, "10:05:30");
//        expResult.put(17, "10:05:30");
//        expResult.put(18, "10:05:30");
//        expResult.put(19, "10:05:30");
    
//        for (int i = 0; i < allTimeValues.size(); i++) {
//            int firstValue = i;
//            int secondValue = i + 1;
//            
//            timeDifferenceMap.put(i, instance.getDifferenceTimeInState(allTimeValues.get(firstValue), allTimeValues.get(secondValue)));
//        }
        
        TreeMap<Integer, String> exp = new TreeMap<>();
        
//        assertEquals(exp, timeDifferenceMap);
        
    
  
        
        
    
}
