package com.mycompany.domain.management.interfaces;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;

public interface IBatchReportGenerate {

//    public PDDocument GeneratePDFDocument(); // TODO needs parameters
    public PDDocument createNewPDF(int batchID, int prodListID, int machineID);
    public void savePDF(PDDocument document, String fileName, String directory) throws IOException;

}
