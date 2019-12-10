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
//public class GetLatestBatchIDTest {
//    
//    public GetLatestBatchIDTest() {
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
//    }
//    
//    @After
//    public void tearDown() {
//        TestDatabase db = new TestDatabase();
//        db.queryUpdate("DELETE FROM Productionlist;");
//    }
//
//
//
//    /**
//     * Test of getLatestBatchID method, when no products in Database.
//     */
//    @Test
//    public void testGetLatestBatchID_Null() {
//        TestDatabase db = new TestDatabase();
//        BatchDataHandler instance = new BatchDataHandler(db);
//        Integer actual = instance.getLatestBatchID();
//        if(actual == null){
//            assertNull(actual);
//        } else {
//            fail("The database was not empty when testing");
//        }
//
//    }
//    
//     /**
//     * Test of getLatestBatchID method.
//     */
//    @Test
//    public void testGetLatestBatchID() {
//        TestDatabase db = new TestDatabase();
//        BatchDataHandler instance = new BatchDataHandler(db);
//        Batch batch_1 = new Batch(1, 1, 100, "2019-12-03", 100f);
//        Batch batch_2 = new Batch(20, 1, 100, "2019-12-03", 100f);
//        Batch batch_3 = new Batch(50, 1, 100, "2019-12-03", 100f);
//        instance.insertBatchToQueue(batch_1);
//        instance.insertBatchToQueue(batch_2);
//        instance.insertBatchToQueue(batch_3);
//        Integer actual = instance.getLatestBatchID();
//        Integer wanted = batch_3.getBatchID();
//        if(actual.equals(wanted)){
//            assertEquals(wanted, actual);
//        } else {
//            fail("The test did not return the correct value");
//        }
//        
//    }
//    
//    
//
//    
//    
//}