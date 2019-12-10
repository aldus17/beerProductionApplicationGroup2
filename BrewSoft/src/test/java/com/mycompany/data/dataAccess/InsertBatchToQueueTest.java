/////*
//// * To change this license header, choose License Headers in Project Properties.
//// * To change this template file, choose Tools | Templates
//// * and open the template in the editor.
//// */
//package com.mycompany.data.dataAccess;
//
//import com.mycompany.crossCutting.objects.Batch;
//import com.mycompany.crossCutting.objects.MachineState;
//import com.mycompany.data.dataAccess.Connect.SimpleSet;
//import com.mycompany.data.dataAccess.Connect.TestDatabase;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
///**
// *
// * @author Glumby
// */
//public class InsertBatchToQueueTest {
//    
//    TestDatabase db = new TestDatabase();
//
//    public InsertBatchToQueueTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//
//    }
//
//    @Before
//    public void setUp() {
//        db.queryUpdate("DELETE FROM Productionlist;");
//    }
//
//    @After
//    public void tearDown() {
//        db.queryUpdate("DELETE FROM Productionlist;");
//    }
//
//    /**
//     * Test of insertBatchToQueue method, of class BatchDataHandler.
//     */
//    @Test
//    public void testInsertBatchToQueue() {
//        // Block that inserts to test database //
//        Batch insertedBatch = new Batch(1, 1, 100, "2019-12-03", 100.0f);
//        BatchDataHandler instance = new BatchDataHandler(db);
//        instance.insertBatchToQueue(insertedBatch);
//
//        //Block that retrieves the inserted from test database //
//        SimpleSet batchSet = db.query("SELECT * FROM productionlist");
//        Batch retrievedBatch = null;
//        for (int i = 0; i < batchSet.getRows(); i++) {
//            retrievedBatch = new Batch(
//                    (int) batchSet.get(i, "batchid"),
//                    (int) batchSet.get(i, "productid"),
//                    (int) batchSet.get(i, "productamount"),
//                    String.valueOf(batchSet.get(i, "deadline")),
//                    Float.parseFloat(String.valueOf(batchSet.get(i, "speed")))
//            );
//        }
// 
//        assertEquals(insertedBatch.getBatchID(), retrievedBatch.getBatchID());
//        assertEquals(insertedBatch.getType(), retrievedBatch.getType());
//        assertEquals(insertedBatch.getTotalAmount(), retrievedBatch.getTotalAmount());
//        assertEquals(insertedBatch.getDeadline(), retrievedBatch.getDeadline());
//        assertEquals(insertedBatch.getSpeedforProduction(), retrievedBatch.getSpeedforProduction(), 1f);
//    }
//}
//
