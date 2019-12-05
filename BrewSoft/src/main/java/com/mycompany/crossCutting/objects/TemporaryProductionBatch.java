package com.mycompany.crossCutting.objects;

import java.time.LocalDate;

public class TemporaryProductionBatch {

    private final int productionListId;
    private final float acceptedCount;
    private final float defectCount;
    private float totalAmount;
    private LocalDate DateForStop;

    public TemporaryProductionBatch(int productionListId, float acceptedCount, float defectCount) {
        this.productionListId = productionListId;
        this.acceptedCount = acceptedCount;
        this.defectCount = defectCount;
    }

    public TemporaryProductionBatch(int productionListId, float acceptedCount, float defectCount, float totalAmount) {
        this.productionListId = productionListId;
        this.acceptedCount = acceptedCount;
        this.defectCount = defectCount;
        this.totalAmount = totalAmount;
    }

    public int getProductionListId() {
        return productionListId;
    }

    public float getAcceptedCount() {
        return acceptedCount;
    }

    public float getDefectCount() {
        return defectCount;
    }

    public LocalDate getDateForStop() {
        return DateForStop;
    }

    public float getTotalAmount() {
        return totalAmount;
    }
}
