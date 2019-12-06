package com.mycompany.crossCutting.objects;

public class OeeObject {

    private final int productid;
    private final float acceptedCount;
    private final double idealcycletime;

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
