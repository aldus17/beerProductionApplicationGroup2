///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.domain.breweryWorker;
//
//import com.mycompany.crossCutting.objects.Batch;
//import com.mycompany.data.dataAccess.BatchDataHandler;
//import com.mycompany.data.dataAccess.Connect.TestDatabase;
//import com.mycompany.data.dataAccess.MachineSubscribeDataHandler;
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
//public class SendProductionDataTest {
//    
//    TestDatabase db = new TestDatabase();
//    
//    public SendProductionDataTest() {
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
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    // Work in progres
//    /**
//     * Test of sendProductionData method, of class MachineSubscriber.
//     */
//    @Test
//    public void testSendProductionData() {
//        System.out.println("sendProductionData");
//        MachineSubscribeDataHandler msdh = new MachineSubscribeDataHandler(db);
//        BatchDataHandler instance = new BatchDataHandler(db);
//        Batch insertedBatch = new Batch(1, 1, 100, "2019-12-03", 100.0f);
//        
//        msdh.insertProductionInfo(1, 1, 25f, 20f);
//        instance.insertBatchToQueue(insertedBatch);
//        
//        db.query("SELECT ", values)
//        
//    }
//
//}
