//package com.mycompany.data.dataAccess;
//
//import com.mycompany.crossCutting.objects.BatchReport;
//import com.mycompany.crossCutting.objects.MachineHumiData;
//import com.mycompany.crossCutting.objects.MachineTempData;
//import com.mycompany.databaseSetup.TestDatabaseSetup;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class TestAllBatchReportData {
//
//    private TestDatabaseSetup testDatabaseSetup = new TestDatabaseSetup();
//    private BatchDataHandler batchDataHandler = new BatchDataHandler(testDatabaseSetup.getDb());
//
//    public TestAllBatchReportData() {
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
//    @Test
//    public void testGetMachineTempData() {
//        System.out.println("\ntestGetMachineTempData");
//
//        // Setup database with data
//        testDatabaseSetup.setUpDatabaseForAllBatchReportData();
//
//        // Fill list with the actual data recieved from the getMachineTempData() method
//        List<MachineTempData> actualList = batchDataHandler.getMachineTempData(1, 1);
//
//        // Expected list output
//        List<MachineTempData> expectedList = new ArrayList<>();
//        expectedList.add(new MachineTempData(1, 36.0));
//        expectedList.add(new MachineTempData(1, 40.0));
//        expectedList.add(new MachineTempData(1, 41.0));
//
//        System.out.println("####Expected####: " + Arrays.toString(expectedList.toArray()));
//        System.out.println("####Actual####: " + Arrays.toString(actualList.toArray()));
//
//        // Test
//        if (!actualList.isEmpty()) {
//            assertEquals(Arrays.toString(expectedList.toArray()), Arrays.toString(actualList.toArray()));
//        } else {
//            fail("The lists are empty or the states do not exist in the database");
//        }
//
//    }
//
//    @Test
//    public void testGetMachineHumiData() {
//        System.out.println("\ntestGetMachineHumiData");
//
//        // Setup database with data
//        testDatabaseSetup.setUpDatabaseForAllBatchReportData();
//
//        // Fill list with the actual data recieved from the getMachineHumiData() method
//        List<MachineHumiData> actualList = batchDataHandler.getMachineHumiData(1, 1);
//
//        // Expected list output based.
//        List<MachineHumiData> expectedList = new ArrayList<>();
//        expectedList.add(new MachineHumiData(1, round(23.3999996185303, 3)));
//        expectedList.add(new MachineHumiData(1, round(23.4999997185303, 3)));
//        expectedList.add(new MachineHumiData(1, round(24.3999997185303, 3)));
//        expectedList.add(new MachineHumiData(1, round(24.4999997185303, 3)));
//        expectedList.add(new MachineHumiData(1, round(25.4999997185303, 3)));
//
//        System.out.println("####Expected####: " + Arrays.toString(expectedList.toArray()));
//        System.out.println("####Actual####: " + Arrays.toString(actualList.toArray()));
//
//        // Test
//        if (!actualList.isEmpty()) {
//            assertEquals(Arrays.toString(expectedList.toArray()), Arrays.toString(actualList.toArray()));
//        } else {
//            fail("The lists are empty or the states do not exist in the database");
//        }
//
//    }
//
//    @Test
//    public void testGetBatchReportProductionData() {
//        System.out.println("\ntestGetBatchReportProductionData");
//        
//        // Setup database with data
//        testDatabaseSetup.setUpDatabaseForAllBatchReportData();
//
//        // Create BatchReport object from the method to extract the actual data
//        BatchReport actualBatchReport = batchDataHandler.getBatchReportProductionData(1, 1);
//
//        // Create BatchReport object with the expected attribute data. 
//        BatchReport expectedBatchReport = new BatchReport(1, 1, "2019-12-06", "2019-12-06", "2019-12-06", "Wheat", 10000, 2000.0, 8000.0);
//
//        System.out.println("####Expected####: " + expectedBatchReport.toString());
//        System.out.println("####Actual####: " + actualBatchReport.toString());
//
//        // Test
//        if (!actualBatchReport.equals(expectedBatchReport)) {
//            assertEquals(expectedBatchReport.toString(), actualBatchReport.toString());
//        } else {
//            fail("Batch report object is not the same");
//        }
//    }
//
//    /**
//     * Helper method for rounding decimal points exact
//     * @param value the decimal number to round
//     * @param places how many places of the pointer
//     * 
//     * @return returns the rounded double value.
//     */
//    public static double round(double value, int places) {
//        if (places < 0) {
//            throw new IllegalArgumentException();
//        }
//        BigDecimal bd = BigDecimal.valueOf(value);
//        bd = bd.setScale(places, RoundingMode.HALF_UP);
//        return bd.doubleValue();
//    }
//
//}
