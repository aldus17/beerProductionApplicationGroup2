///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.domain.management;
//
//import com.mycompany.crossCutting.objects.Batch;
//import com.mycompany.crossCutting.objects.BeerTypes;
//import com.mycompany.crossCutting.objects.SearchData;
//import com.mycompany.data.dataAccess.BatchDataHandler;
//import com.mycompany.data.dataAccess.Connect.SimpleSet;
//import com.mycompany.data.dataAccess.Connect.TestDatabase;
//import com.mycompany.data.dataAccess.SearchDataHandler;
//import com.mycompany.data.interfaces.IBatchDataHandler;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Glumby
// */
//public class CreateBatchTest {
//
//    TestDatabase db = new TestDatabase();
//    ManagementDomain managementDomain = new ManagementDomain(new BatchDataHandler(db), new SearchDataHandler(), new BatchDataHandler(db));
//    BatchDataHandler batchDataHandler = new BatchDataHandler(db);
//
//    public CreateBatchTest() {
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
//        db.queryUpdate("DELETE FROM Productionlist;");
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART;");
//    }
//
//    @After
//    public void tearDown() {
//        db.queryUpdate("DELETE FROM Productionlist;");
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART;");
//    }
//
//    /**
//     * Test of createBatch method with empty database
//     */
//    @Test
//    public void testCreateBatch() {
//        int expectedBatchID = 0;
//        managementDomain.createBatch(new Batch(2, 8000, "2019-12-08", 100.0f));
//        SimpleSet set = db.query("SELECT * FROM productionlist ORDER BY productionlistID DESC limit 1");
//        Batch batch = null;
//        for (int i = 0; i < set.getRows(); i++) {
//            batch = new Batch(
//                    (int) set.get(i, "batchid"),
//                    (int) set.get(i, "productid"),
//                    (int) set.get(i, "productamount"),
//                    String.valueOf(set.get(i, "deadline")),
//                    Float.parseFloat(String.valueOf(set.get(i, "speed")))
//            );
//        }
//        assertEquals(expectedBatchID, batch.getBatchID());
//    }
//
//    /**
//     * Test of createBatch method with non empty database
//     */
//    @Test
//    public void testCreateBatch_Mid() {
//        batchDataHandler.insertBatchToQueue(new Batch(800, 2, 20000, "2019-12-08", 100.0f));
//        int expectedBatchID = 801;
//        managementDomain.createBatch(new Batch(2, 8000, "2019-12-08", 100.0f));
//
//        SimpleSet set = db.query("SELECT * FROM productionlist ORDER BY productionlistID DESC limit 1");
//        Batch batch = null;
//        for (int i = 0; i < set.getRows(); i++) {
//            batch = new Batch(
//                    (int) set.get(i, "batchid"),
//                    (int) set.get(i, "productid"),
//                    (int) set.get(i, "productamount"),
//                    String.valueOf(set.get(i, "deadline")),
//                    Float.parseFloat(String.valueOf(set.get(i, "speed")))
//            );
//        }
//        assertEquals(expectedBatchID, batch.getBatchID());
//    }
//
//    /**
//     * Test of createBatch method edge case
//     */
//    @Test
//    public void testCreateBatch_EdgeCase() {
//        //Insert a dummy batch directly to the empty database.
//        batchDataHandler.insertBatchToQueue(new Batch(65535, 2, 20000, "2019-12-08", 100.0f));
//
//        //Create a new batch using create batch method.
//        managementDomain.createBatch(new Batch(2, 8000, "2019-12-08", 100.0f));
//
//        //Get the created batch from the database.
//        SimpleSet set = db.query("SELECT * FROM productionlist ORDER BY productionlistID DESC limit 1");
//        Batch batch = null;
//        for (int i = 0; i < set.getRows(); i++) {
//            batch = new Batch(
//                    (int) set.get(i, "batchid"),
//                    (int) set.get(i, "productid"),
//                    (int) set.get(i, "productamount"),
//                    String.valueOf(set.get(i, "deadline")),
//                    Float.parseFloat(String.valueOf(set.get(i, "speed")))
//            );
//        }
//        //Assert that the id of the batch pulled from the database is 0.
//        int expectedBatchID = 0;
//        assertEquals(expectedBatchID, batch.getBatchID());
//    }
//
//}
