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
//        testDatabaseSetup.setUpDatabaseForGetTimeInStates();
//
//        List<MachineState> actualList = batchDataHandler.getMachineState(1, 1);
//
//        List<MachineState> expectedList = new ArrayList<>();
//        expectedList.add(new MachineState(6, "10:43:31"));
//        expectedList.add(new MachineState(17, "11:03:34"));
//        expectedList.add(new MachineState(15, "11:03:47"));
//        expectedList.add(new MachineState(4, "11:03:48"));
//        expectedList.add(new MachineState(6, "11:05:22")); // First state time of the next batch
//
//        // Sort for precise assertion
//        Collections.sort(expectedList, Comparator.comparing(MachineState::getTimeInState));
//        Collections.sort(actualList, Comparator.comparing(MachineState::getTimeInState));
//        System.out.println("####Expected####: " + Arrays.toString(expectedList.toArray()));
//        System.out.println("####Actual####: " + Arrays.toString(actualList.toArray()));
//
//        if (!actualList.isEmpty()) {
////            assertEquals(Arrays.toString(expectedList.toArray()), Arrays.toString(actualList.toArray()));
//            assertThat(actualList, containsInAnyOrder(new MachineState(6, "10:43:31"), new MachineState(17, "11:03:34"), new MachineState(15, "11:03:47"), new MachineState(4, "11:03:48"), new MachineState(6, "11:05:22")));
//        } else {
//            fail("The lists are empty or the states do not exist in the database");
//        }
//    }
//}
