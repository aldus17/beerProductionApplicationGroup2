package com.mycompany.domain.management.interfaces;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;

public interface IBatchReportGenerate {
    
    public PDDocument GeneratePDFDocument(); // TODO needs parameters
    public File createNewPDF(int batchID, int prodListID, int machineID);
}
