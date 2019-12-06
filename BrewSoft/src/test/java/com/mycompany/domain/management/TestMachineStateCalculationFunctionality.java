/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.domain.management;

import com.mycompany.crossCutting.objects.MachineState;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.dataAccess.Connect.TestDatabase;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class TestMachineStateCalculationFunctionality {

    private TestDatabase db = new TestDatabase();
    private IBatchDataHandler batchDataHandler = new BatchDataHandler(db);

    public TestMachineStateCalculationFunctionality() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        db = new TestDatabase();
        db.queryUpdate("DELETE FROM timeinstate; ");
        db.queryUpdate("DELETE FROM finalbatchinformation; ");
        db.queryUpdate("DELETE FROM productionlist; ");
        db.queryUpdate("DELETE FROM brewerymachine; ");
        db.queryUpdate("ALTER SEQUENCE timeinstate_timeinstateid_seq RESTART; ");
        db.queryUpdate("ALTER SEQUENCE brewerymachine_brewerymachineid_seq RESTART; ");
        db.queryUpdate("ALTER SEQUENCE finalbatchinformation_finalbatchinformationid_seq RESTART; ");
        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART; ");
    }

    @After
    public void tearDown() {
        db = new TestDatabase();
        db.queryUpdate("DELETE FROM timeinstate; ");
        db.queryUpdate("DELETE FROM finalbatchinformation; ");
        db.queryUpdate("DELETE FROM productionlist; ");
        db.queryUpdate("DELETE FROM brewerymachine; ");
        db.queryUpdate("ALTER SEQUENCE timeinstate_timeinstateid_seq RESTART; ");
        db.queryUpdate("ALTER SEQUENCE brewerymachine_brewerymachineid_seq RESTART; ");
        db.queryUpdate("ALTER SEQUENCE finalbatchinformation_finalbatchinformationid_seq RESTART; ");
        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART; ");

    }

    /**
     * Test of getDifferenceTimeInState method, of class ManagementDomain.
     */
    @Test
    public void getDifferenceTimeInState() {
        System.out.println("getDifferenceTimeInState");
        ManagementDomain instance = new ManagementDomain();
        String value1 = "12:31:22";
        String value2 = "13:40:49";
        String expectedValue = "01:09:27";
        String actualValue = instance.getDifferenceTimeInState(value1, value2);

        System.out.println("####Expected####: " + expectedValue + "\n####ActualValue####: " + actualValue);

        if (!expectedValue.startsWith("-") && !expectedValue.isEmpty()) {
            assertEquals(expectedValue, actualValue);
        } else {
            fail("String is either negative or is empty, test failed");
        }

    }

    /**
     * Test of getAdditionTimeInState method, of class ManagementDomain.
     */
//    @Test
//    public void getAdditionTimeInState() {
//
//    }
    /**
     * Test of getTimeInStates method, of class ManagementDomain.
     */
    @Test
    public void testGetTimeInStates() {
        System.out.println("getTimeInStates");
        setUpDatabaseForGetTimeInStates();
        int prodListID = 1;
        int machineID = 1;

        ManagementDomain instance = new ManagementDomain(db);
        List<MachineState> ms = batchDataHandler.getMachineState(prodListID, machineID);
        System.out.println(Arrays.toString(ms.toArray()));

        Map<Integer, String> timeDifferenceMap = new TreeMap<>();
        timeDifferenceMap = instance.getTimeInStates(prodListID, machineID);
        Map<Integer, String> expectedMap = new TreeMap<>();
        expectedMap.put(4, "00:01:34");
        expectedMap.put(6, "00:20:03");
        expectedMap.put(15, "00:00:01");
        expectedMap.put(17, "00:00:13");

        String expectedValue = expectedMap.toString();
        String actualValue = timeDifferenceMap.toString();

        System.out.println("####Expected####: " + expectedValue + "\n####Actual####: " + actualValue);
        assertEquals(expectedValue, actualValue);
        if (!timeDifferenceMap.isEmpty()) {

            assertEquals(expectedMap, timeDifferenceMap);
        } else {

            fail("No values in map, test failed");
        }
    }

    private void setUpDatabaseForGetTimeInStates() {
        db.queryUpdate("INSERT INTO brewerymachine( "
                + "hostname, port) "
                + "VALUES ('TestDatabase', 5432); ");
        db.queryUpdate("INSERT INTO ProductionList "
                + "(batchid, productid, productamount, deadline, speed, status, dateofcreation) "
                + "VALUES(1, 1, 10000, '2019-12-06', 100, 'Completed', '2019-12-06')");
        db.queryUpdate("INSERT INTO ProductionList "
                + "(batchid, productid, productamount, deadline, speed, status, dateofcreation) "
                + "VALUES(2, 3, 5000, '2019-12-06', 200, 'Completed', '2019-12-06'); ");
        db.queryUpdate("INSERT INTO finalbatchinformation( "
                + "productionlistid, brewerymachineid, deadline, dateofcreation, dateofcompletion, productid, totalcount, defectcount, acceptedcount) "
                + "VALUES (1, 1, '2019-12-06', '2019-12-06', '2019-12-06', 1, 10000, 2000, 8000); ");
        db.queryUpdate("INSERT INTO finalbatchinformation( "
                + "productionlistid, brewerymachineid, deadline, dateofcreation, dateofcompletion, productid, totalcount, defectcount, acceptedcount) "
                + "VALUES (2, 1, '2019-12-06', '2019-12-06', '2019-12-06', 3, 5000, 1000, 7000); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (1, 1, '10:43:31', 6); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (1, 1, '11:03:34', 17); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (1, 1, '11:03:47', 15); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (1, 1, '11:03:48', 4); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (2, 1, '11:05:22', 6); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (2, 1, '11:13:29', 17); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (2, 1, '11:13:33', 15); ");
        db.queryUpdate("INSERT INTO timeinstate( "
                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
                + "VALUES (2, 1, '11:14:33', 15); ");
    }
}
