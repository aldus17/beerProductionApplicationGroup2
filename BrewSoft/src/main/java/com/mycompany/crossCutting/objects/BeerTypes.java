package com.mycompany.crossCutting.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BeerTypes {
    
    StringProperty indexnumber;
    StringProperty typeName;

    public BeerTypes(String indexnumber, String typeName) {
        this.indexnumber = new SimpleStringProperty(indexnumber);
        this.typeName = new SimpleStringProperty(typeName);
    }
    
}
