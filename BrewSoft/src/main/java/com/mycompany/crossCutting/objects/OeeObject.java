package com.mycompany.crossCutting.objects;

public class OeeObject {
    private int productid;
    private float acceptedCount;
    private double idealcycletime;

    public OeeObject(int productid, float acceptedCount, double idealcycletime) {
        this.productid = productid;
        this.acceptedCount = acceptedCount;
        this.idealcycletime = idealcycletime;
    }

    public int getProductid() {
        return productid;
    }

    public float getAcceptedCount() {
        return acceptedCount;
    }

    public double getIdealcycletime() {
        return idealcycletime;
    }
    
}
