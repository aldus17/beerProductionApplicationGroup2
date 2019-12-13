//package com.mycompany.domain.management;
//
//import com.mycompany.crossCutting.objects.MachineState;
//import com.mycompany.data.dataAccess.BatchDataHandler;
//import com.mycompany.data.interfaces.IBatchDataHandler;
//import com.mycompany.databaseSetup.TestDatabaseSetup;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class TestMachineStateCalculationFunctionality {
//
//    private TestDatabaseSetup testDatabaseSetup = new TestDatabaseSetup();
//    private IBatchDataHandler batchDataHandler = new BatchDataHandler(testDatabaseSetup.getDb());
//
//    public TestMachineStateCalculationFunctionality() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        testDatabaseSetup.tearDownDatabaseData();
//    }
//
//    @After
//    public void tearDown() {
//        testDatabaseSetup.tearDownDatabaseData();
//
//    }
//
//    /**
//     * Test of getDifferenceTimeInState method, of class ManagementDomain.
//     */
//    @Test
//    public void getDifferenceTimeInState() {
//        System.out.println("\ngetDifferenceTimeInState");
//
//        // Instantiate class ManagementDomain to use method
//        ManagementDomain instance = new ManagementDomain();
//
//        // Values 
//        String value1 = "12:31:22";
//        String value2 = "13:40:49";
//
//        // Actual and expected values
//        String expectedValue = "01:09:27";
//        String actualValue = instance.getDifferenceTimeInState(value1, value2);
//
//        System.out.println("####Expected####: " + expectedValue + "\n####ActualValue####: " + actualValue);
//
//        // Test
//        if (!expectedValue.startsWith("-") && !expectedValue.isEmpty()) {
//            assertEquals(expectedValue, actualValue);
//        } else {
//            fail("String is either negative or is empty, test failed");
//        }
//
//    }
//
//    /**
//     * Test of getAdditionTimeInState method, of class ManagementDomain.
//     */
//    @Test
//    public void getAdditionTimeInState() {
//        System.out.println("\ngetAdditionTimeInState");
//
//        // Instantiate class ManagementDomain to use method
//        ManagementDomain instance = new ManagementDomain();
//
//        // Get first difference result value
//        String value1 = "12:31:22";
//        String value2 = "13:40:49";
//        String diff1 = instance.getDifferenceTimeInState(value1, value2);
//
//        // Get second difference result value
//        String value3 = "13:45:00";
//        String value4 = "14:00:00";
//        String diff2 = instance.getDifferenceTimeInState(value3, value4);
//
//        // Expected and actual result of adding the two difference values
//        String expectedValue1 = "01:24:27";
//        String actualValue1 = instance.getAdditionTimeInState(diff1, diff2);
//
//        // Test Days values
//        String value5 = "12:50:22";
//        String value6 = "13:35:26";
//        String expectedValue2 = "01:02:20:48";
//        String actualValue2 = instance.getAdditionTimeInState(value5, value6);
//
//        System.out.println("Test minutes and seconds");
//        System.out.println("####Expected####: " + expectedValue1 + "\n####ActualValue####: " + actualValue1);
//
//        // Test minutes and seconds
//        if (!expectedValue1.startsWith("-") && !expectedValue1.isEmpty()) {
//            assertEquals(expectedValue1, actualValue1);
//        } else {
//            fail("String is either negative or is empty, test failed");
//        }
//
//        System.out.println("Test days, minutes and seconds");
//        System.out.println("####Expected####: " + expectedValue2 + "\n####ActualValue####: " + actualValue2);
//
//        // Test days, minutes and seconds
//        if (!expectedValue1.startsWith("-") && !expectedValue1.isEmpty()) {
//            assertEquals(expectedValue2, actualValue2);
//        } else {
//            fail("String is either negative or is empty, test failed");
//        }
//    }
//
//    /**
//     * Test of getTimeInStates method, of class ManagementDomain.
//     */
//    @Test
//    public void testGetTimeInStates() {
//        System.out.println("\ngetTimeInStates");
//
//        // Setup database for test data
//        testDatabaseSetup.setUpDatabaseForGetTimeInStates();
//        int prodListID = 1;
//        int machineID = 1;
//
//        // Instantiate ManagementDomain with the testDatabase
//        ManagementDomain instance = new ManagementDomain(testDatabaseSetup.getDb());
//        List<MachineState> ms = batchDataHandler.getMachineState(prodListID, machineID);
//
//        // Actual Map and expected Map values
//        Map<Integer, String> timeDifferenceMap = new TreeMap<>();
//        timeDifferenceMap = instance.getTimeInStates(prodListID, machineID);
//        Map<Integer, String> expectedMap = new TreeMap<>();
//        expectedMap.put(4, "00:01:34");
//        expectedMap.put(6, "00:20:03");
//        expectedMap.put(15, "00:00:01");
//        expectedMap.put(17, "00:00:13");
//
//        String expectedValue = expectedMap.toString();
//        String actualValue = timeDifferenceMap.toString();
//
//        System.out.println("####Expected####: " + expectedValue + "\n####Actual####: " + actualValue);
//
//        // Test
//        if (!timeDifferenceMap.isEmpty()) {
//
//            assertEquals(expectedMap, timeDifferenceMap);
//        } else {
//            fail("No values in map, test failed");
//        }
//    }
//}
