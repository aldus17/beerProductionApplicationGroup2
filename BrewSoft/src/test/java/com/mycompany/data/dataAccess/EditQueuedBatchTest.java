//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.data.dataAccess;
//
//import com.mycompany.crossCutting.objects.Batch;
//import com.mycompany.crossCutting.objects.BeerTypes;
//import com.mycompany.crossCutting.objects.MachineState;
//import com.mycompany.data.dataAccess.Connect.SimpleSet;
//import com.mycompany.data.dataAccess.Connect.TestDatabase;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
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
//public class EditQueuedBatchTest {
//    
//    TestDatabase db = new TestDatabase();
//    
//    public EditQueuedBatchTest() {
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
//        TestDatabase db = new TestDatabase();
//        db.queryUpdate("DELETE FROM Productionlist;");
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART;");
//    }
//    
//    @After
//    public void tearDown() {
//        db.queryUpdate("DELETE FROM Productionlist;");
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART;");
//        
//    }
//
//    
//
//    /**
//     * Test of editQueuedBatch method, of class BatchDataHandler.
//     */
//    @Test
//    public void testEditQueuedBatch() {
//        //Block that inserts batch
//        BatchDataHandler instance = new BatchDataHandler(db);
//        Batch batch_1 = new Batch(1, 1, 100, "2019-12-03", 100);
//        instance.insertBatchToQueue(batch_1);
//        
//        //Block that alters the batch in the database, based on productionlistID 1
//        Batch batch_edit = new Batch(1, 1, 5, 200, "2019-12-03", 25.0f, String.valueOf(LocalDate.now()));
//        instance.editQueuedBatch(batch_edit);
//        
//        //Block that retrieves the inserted from test database //
//        SimpleSet batchSet = db.query("SELECT * FROM productionlist");
//        Batch retrievedBatch = null;
//        for (int i = 0; i < batchSet.getRows(); i++) {
//            retrievedBatch = new Batch(
//                    (int) batchSet.get(i, "productionlistid"),
//                    (int) batchSet.get(i, "batchid"),
//                    (int) batchSet.get(i, "productid"),
//                    (int) batchSet.get(i, "productamount"),
//                    String.valueOf(batchSet.get(i, "deadline")),
//                    Float.parseFloat(String.valueOf(batchSet.get(i, "speed"))),
//                    String.valueOf(batchSet.get(i, "dateofcreation"))
//            );
//        } 
//        assertEquals(batch_edit, retrievedBatch);
//        }
//    }
//
//
//    
