package com.mycompany.crossCutting.objects;

public class BeerTypes {

    private int indexnumber;
    private String typeName;
    private float productionSpeed;

    public BeerTypes(int indexnumber, String typeName) {
        this(indexnumber, typeName, 0.0f);
    }

    public BeerTypes(int indexnumber, String typeName, float productionSpeed) {
        this.indexnumber = indexnumber;
        this.typeName = typeName;
        this.productionSpeed = productionSpeed;
    }

    public float getProductionSpeed() {
        return productionSpeed;
    }

    public int getIndexNumber() {
        return indexnumber;
    }
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public String toString() {
        return this.typeName;
    }
}
