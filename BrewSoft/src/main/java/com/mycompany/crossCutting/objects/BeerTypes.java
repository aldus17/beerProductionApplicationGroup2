package com.mycompany.crossCutting.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BeerTypes {
    
    private StringProperty indexnumber;
    private StringProperty typeName;
    private StringProperty productionSpeed;
    
    public BeerTypes(String indexnumber, String typeName) {
        this(indexnumber, typeName, null);
    }

    public BeerTypes(String indexnumber, String typeName, String productionSpeed) {
        this.indexnumber = new SimpleStringProperty(indexnumber);
        this.typeName = new SimpleStringProperty(typeName);
        this.productionSpeed = new SimpleStringProperty(productionSpeed);
    }
    
    public String getProductionSpeed() {
        return productionSpeed.getValue();
    }
}
