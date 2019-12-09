//package com.mycompany.databaseSetup;
//
//import com.mycompany.data.dataAccess.Connect.TestDatabase;
//
//public class TestDatabaseSetup {
//
//    private TestDatabase db;
//
//    public TestDatabaseSetup() {
//        this.db = new TestDatabase();
//    }
//
//    public TestDatabase getDb() {
//        return db;
//    }
//
//    public void tearDownDatabaseData() {
//        db.queryUpdate("DELETE FROM timeinstate; ");
//        db.queryUpdate("DELETE FROM finalbatchinformation; ");
//        db.queryUpdate("DELETE FROM productioninfo; ");
//        db.queryUpdate("DELETE FROM productionlist; ");
//        db.queryUpdate("DELETE FROM brewerymachine; ");
//
//        db.queryUpdate("ALTER SEQUENCE timeinstate_timeinstateid_seq RESTART; ");
//        db.queryUpdate("ALTER SEQUENCE brewerymachine_brewerymachineid_seq RESTART; ");
//        db.queryUpdate("ALTER SEQUENCE finalbatchinformation_finalbatchinformationid_seq RESTART; ");
//        db.queryUpdate("ALTER SEQUENCE productioninfo_productioninfoid_seq RESTART; ");
//        db.queryUpdate("ALTER SEQUENCE productionlist_productionlistid_seq RESTART; ");
//
//    }
//
//    public void setUpDatabaseForAllBatchReportData() {
//        db.queryUpdate("INSERT INTO brewerymachine( "
//                + "hostname, port) "
//                + "VALUES ('TestDatabase', 5432); ");
//        db.queryUpdate("INSERT INTO ProductionList "
//                + "(batchid, productid, productamount, deadline, speed, status, dateofcreation) "
//                + "VALUES(1, 1, 10000, '2019-12-06', 100, 'Completed', '2019-12-06'); ");
//        db.queryUpdate("INSERT INTO ProductionList "
//                + "(batchid, productid, productamount, deadline, speed, status, dateofcreation) "
//                + "VALUES(2, 3, 5000, '2019-12-06', 200, 'Completed', '2019-12-06'); ");
//        db.queryUpdate("INSERT INTO finalbatchinformation( "
//                + "productionlistid, brewerymachineid, deadline, dateofcreation, dateofcompletion, productid, totalcount, defectcount, acceptedcount) "
//                + "VALUES (1, 1, '2019-12-06', '2019-12-06', '2019-12-06', 1, 10000, 2000, 8000); ");
//        db.queryUpdate("INSERT INTO finalbatchinformation( "
//                + "productionlistid, brewerymachineid, deadline, dateofcreation, dateofcompletion, productid, totalcount, defectcount, acceptedcount) "
//                + "VALUES (2, 1, '2019-12-06', '2019-12-06', '2019-12-06', 3, 5000, 1000, 7000); ");
//        db.queryUpdate("INSERT INTO productioninfo( "
//                + "productionlistid, brewerymachineid, humidity, temperature) "
//                + "VALUES (1, 1, 23.3999996185303, 41); ");
//        db.queryUpdate("INSERT INTO productioninfo( "
//                + "productionlistid, brewerymachineid, humidity, temperature) "
//                + "VALUES (1, 1, 24.3999997185303, 36); ");
//        db.queryUpdate("INSERT INTO productioninfo( "
//                + "productionlistid, brewerymachineid, humidity, temperature) "
//                + "VALUES (1, 1, 23.4999997185303, 36); ");
//        db.queryUpdate("INSERT INTO productioninfo( "
//                + "productionlistid, brewerymachineid, humidity, temperature) "
//                + "VALUES (1, 1, 25.4999997185303, 40); ");
//        db.queryUpdate("INSERT INTO productioninfo( "
//                + "productionlistid, brewerymachineid, humidity, temperature) "
//                + "VALUES (1, 1, 24.4999997185303, 40); ");
//    }
//
//    public void setUpDatabaseForGetTimeInStates() {
//        db.queryUpdate("INSERT INTO brewerymachine( "
//                + "hostname, port) "
//                + "VALUES ('TestDatabase', 5432); ");
//        db.queryUpdate("INSERT INTO ProductionList "
//                + "(batchid, productid, productamount, deadline, speed, status, dateofcreation) "
//                + "VALUES(1, 1, 10000, '2019-12-06', 100, 'Completed', '2019-12-06'); ");
//        db.queryUpdate("INSERT INTO ProductionList "
//                + "(batchid, productid, productamount, deadline, speed, status, dateofcreation) "
//                + "VALUES(2, 3, 5000, '2019-12-06', 200, 'Completed', '2019-12-06'); ");
//        db.queryUpdate("INSERT INTO finalbatchinformation( "
//                + "productionlistid, brewerymachineid, deadline, dateofcreation, dateofcompletion, productid, totalcount, defectcount, acceptedcount) "
//                + "VALUES (1, 1, '2019-12-06', '2019-12-06', '2019-12-06', 1, 10000, 2000, 8000); ");
//        db.queryUpdate("INSERT INTO finalbatchinformation( "
//                + "productionlistid, brewerymachineid, deadline, dateofcreation, dateofcompletion, productid, totalcount, defectcount, acceptedcount) "
//                + "VALUES (2, 1, '2019-12-06', '2019-12-06', '2019-12-06', 3, 5000, 1000, 7000); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (1, 1, '10:43:31', 6); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (1, 1, '11:03:34', 17); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (1, 1, '11:03:47', 15); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (1, 1, '11:03:48', 4); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (2, 1, '11:05:22', 6); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (2, 1, '11:13:29', 17); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (2, 1, '11:13:33', 15); ");
//        db.queryUpdate("INSERT INTO timeinstate( "
//                + "productionlistid, brewerymachineid, starttimeinstate, machinestateid) "
//                + "VALUES (2, 1, '11:14:33', 15); ");
//    }
//}
