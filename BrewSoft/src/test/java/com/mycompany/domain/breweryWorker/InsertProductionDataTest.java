///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.domain.breweryWorker;
//
//import com.mycompany.crossCutting.objects.Batch;
//import com.mycompany.data.dataAccess.BatchDataHandler;
//import com.mycompany.data.dataAccess.Connect.SimpleSet;
//import com.mycompany.data.dataAccess.Connect.TestDatabase;
//import com.mycompany.data.dataAccess.MachineSubscribeDataHandler;
//import java.util.ArrayList;
//import java.util.function.Consumer;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author jacob
// */
//public class InsertProductionDataTest {
//
//    TestDatabase db = new TestDatabase();
//    BatchDataHandler bdh = new BatchDataHandler(db);
//    MachineSubscribeDataHandler msdh = new MachineSubscribeDataHandler(db);
//    private int actualProductionlistID = 0, actualMachineID = 0;
//    private float actualHumidity = 0f, actualTemperature = 0f;
//
//    public InsertProductionDataTest() {
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
//        db.queryUpdate("DELETE FROM productioninfo;");
//        db.queryUpdate("DELETE FROM Productionlist;");
//
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART;");
//        db.queryUpdate("ALTER SEQUENCE productioninfo_productioninfoid_seq RESTART;");
//        Batch batch = new Batch(1, 2, 20000, "2019-12-08", 100.0f);
//        bdh.insertBatchToQueue(batch);
//    }
//
//    @After
//    public void tearDown() {
//        db.queryUpdate("DELETE FROM productioninfo;");
//        db.queryUpdate("DELETE FROM Productionlist;");
//
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART;");
//        db.queryUpdate("ALTER SEQUENCE productioninfo_productioninfoid_seq RESTART;");
//    }
//
//    /**
//     * Test of sendProductionData method, of class MachineSubscriber.
//     */
//    @Test
//    public void testInsertProductionData() {
//
//        int expectedProductionlistID = 1;
//        int expectedMachineID = 1;
//        float expectedHumidity = 25.0f;
//        float expectedTemperature = 20.0f;
//        msdh.insertProductionInfo(expectedProductionlistID, expectedMachineID, expectedHumidity, expectedTemperature);
//
//        SimpleSet set = db.query("SELECT * FROM productioninfo WHERE productionlistid = " + expectedProductionlistID + "; ");
//        for (int i = 0; i < set.getRows(); i++) {
//            actualProductionlistID = (int) set.get(i, "productionlistid");
//            actualMachineID = (int) set.get(i, "brewerymachineid");
//            actualHumidity = Float.parseFloat(String.valueOf(set.get(i, "humidity")));
//            actualTemperature = Float.parseFloat(String.valueOf(set.get(i, "temperature")));
//        }
//        if (actualProductionlistID == expectedProductionlistID) {
//            assertEquals(expectedProductionlistID, actualProductionlistID);
//            assertEquals(expectedMachineID, actualMachineID);
//            assertEquals(expectedHumidity, actualHumidity, 0f);
//            assertEquals(expectedTemperature, actualTemperature, 0f);
//
//        } else {
//            fail();
//        }
//
//    }
//
//}
