package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineTempData;
import com.mycompany.data.dataAccess.Connect.TestDatabase;
import com.mycompany.databaseSetup.TestDatabaseSetup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAllBatchReportData {

    private TestDatabase db = new TestDatabase();
    private TestDatabaseSetup testDatabaseSetup = new TestDatabaseSetup(db);
    private BatchDataHandler batchDataHandler = new BatchDataHandler(db);

    public TestAllBatchReportData() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testDatabaseSetup.tearDownDatabaseData();
    }

    @After
    public void tearDown() {
        testDatabaseSetup.tearDownDatabaseData();

    }

    @Test
    public void testGetMachineTempData() {
        System.out.println("\ntestGetMachineTempData");

        testDatabaseSetup.setUpDatabaseForAllBatchReportData();

        List<MachineTempData> actualList = batchDataHandler.getMachineTempData(1, 1);

        List<MachineTempData> expectedList = new ArrayList<>();
        expectedList.add(new MachineTempData(1, 36.0));
        expectedList.add(new MachineTempData(1, 40.0));
        expectedList.add(new MachineTempData(1, 41.0));

        System.out.println("####Expected####: " + Arrays.toString(expectedList.toArray()));
        System.out.println("####Actual####: " + Arrays.toString(actualList.toArray()));

        if (!actualList.isEmpty()) {
            assertEquals(Arrays.toString(expectedList.toArray()), Arrays.toString(actualList.toArray()));
        } else {
            fail("The lists are empty or the states do not exist in the database");
        }

    }

    @Test
    public void testGetMachineHumiData() {
        System.out.println("\ntestGetMachineHumiData");

        testDatabaseSetup.setUpDatabaseForAllBatchReportData();

        List<MachineHumiData> actualList = batchDataHandler.getMachineHumiData(1, 1);

        List<MachineHumiData> expectedList = new ArrayList<>();
        expectedList.add(new MachineHumiData(1, 23.3999996185303));
        expectedList.add(new MachineHumiData(1, 23.4999997185303));
        expectedList.add(new MachineHumiData(1, 24.3999997185303));
        expectedList.add(new MachineHumiData(1, 24.4999997185303));
        expectedList.add(new MachineHumiData(1, 25.4999997185303));

        System.out.println("####Expected####: " + Arrays.toString(expectedList.toArray()));
        System.out.println("####Actual####: " + Arrays.toString(actualList.toArray()));

        if (!actualList.isEmpty()) {
            assertEquals(Arrays.toString(expectedList.toArray()), Arrays.toString(actualList.toArray()));
        } else {
            fail("The lists are empty or the states do not exist in the database");
        }

    }

    @Test
    public void testGetBatchReportProductionData() {
        System.out.println("\ntestGetBatchReportProductionData");

        testDatabaseSetup.setUpDatabaseForAllBatchReportData();

        BatchReport actualBatchReport = batchDataHandler.getBatchReportProductionData(1, 1);

        BatchReport expectedBatchReport = new BatchReport(1, 1, "2019-12-06", "2019-12-06", "2019-12-06", "Wheat", 10000, 2000.0, 8000.0);

        System.out.println("####Expected####: " + expectedBatchReport.toString());
        System.out.println("####Actual####: " + actualBatchReport.toString());

        if (!actualBatchReport.equals(expectedBatchReport)) {
            assertEquals(expectedBatchReport.toString(), actualBatchReport.toString());
        } else {
            fail("Batch report object is not the same");
        }
    }
}
