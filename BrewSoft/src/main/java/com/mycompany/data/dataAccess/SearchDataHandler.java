package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.SearchData;
import com.mycompany.data.dataAccess.Connect.DatabaseConnection;
import com.mycompany.data.dataAccess.Connect.SimpleSet;
import com.mycompany.data.interfaces.ISearchDataHandler;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathias
 */
public class SearchDataHandler implements ISearchDataHandler {
    
    private DatabaseConnection dbConnection;
    
    public SearchDataHandler() {
        dbConnection = new DatabaseConnection();
    }
    
    @Override
    public List<Batch> getBatchList(SearchData searchDataObj) {
        List<Batch> batchList = new ArrayList();
        
        String whereClause = "";
        float batchID;
        String doc = searchDataObj.getDateOfCompletion();
        batchID = searchDataObj.getBatchID();
        if(!doc.isEmpty()) {
            whereClause += " dateofcompletion = ?";
        }
        if(batchID != 0.0f) {
            whereClause += " batchid = ?";
        }
        SimpleSet set = dbConnection.query("SELECT * FROM finalbatchinformation WHERE" + whereClause, Date.valueOf(doc), batchID);
        
        for (int i = 0; i < set.getRows(); i++) {
            Batch b = new Batch(
                    String.valueOf(set.get(i, "finalBatchInformationID")),
                    String.valueOf(set.get(i, "productionListID")),
                    String.valueOf(set.get(i, "BreweryMachineID")),
                    String.valueOf(set.get(i, "deadline")),
                    String.valueOf(set.get(i, "dateOfCreation")),
                    String.valueOf(set.get(i, "dateOfCompletion")),
                    String.valueOf(set.get(i, "productID")),
                    String.valueOf(set.get(i, "totalCount")),
                    String.valueOf(set.get(i, "defectCount")),
                    String.valueOf(set.get(i, "acceptedCount")));

            batchList.add(b);
        }
        
        return batchList;
    }

}
