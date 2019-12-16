//package com.mycompany.data.dataAccess;
//
//import com.mycompany.crossCutting.objects.MachineState;
//import com.mycompany.databaseSetup.TestDatabaseSetup;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import static org.hamcrest.Matchers.containsInAnyOrder;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.fail;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class TestTimeInStatesData {
//
//    private TestDatabaseSetup testDatabaseSetup = new TestDatabaseSetup();
//    private BatchDataHandler batchDataHandler = new BatchDataHandler(testDatabaseSetup.getDb());
//
//    public TestTimeInStatesData() {
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
//    public void testGetMachineState() {
//        System.out.println("\ntestGetMachineState");
//
//        // Setup database with data
//        testDatabaseSetup.setUpDatabaseForGetTimeInStates();
//
//        // Create list with actualList data from the method getMachineState()
//        List<MachineState> actualList = batchDataHandler.getMachineState(1, 1);
//
//        // Expected list output
//        List<MachineState> expectedList = new ArrayList<>();
//        MachineState ms1 = new MachineState(6, "10:43:31");
//        MachineState ms2 = new MachineState(17, "11:03:34");
//        MachineState ms3 = new MachineState(15, "11:03:47");
//        MachineState ms4 = new MachineState(4, "11:03:48");
//        MachineState ms5 = new MachineState(6, "11:05:22"); // First state time of the next batch
//
//        // Sort for precise assertion
//        Collections.sort(actualList, Comparator.comparing(MachineState::getTimeInState));
//        System.out.println("####Expected objects####: " + ms1.toString()
//                + " " + ms2.toString()
//                + " " + ms3.toString()
//                + " " + ms4.toString()
//                + " " + ms5.toString());
//        System.out.println("####Actual####: " + Arrays.toString(actualList.toArray()));
//
//        // Test
//        if (!actualList.isEmpty()) {
//            assertThat(actualList, containsInAnyOrder(ms1, ms2, ms3, ms4, ms5));
//        } else {
//            fail("The lists are empty or the states do not exist in the database");
//        }
//    }
//}
