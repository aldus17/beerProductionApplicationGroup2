package com.mycompany.domain.management.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class CreatePDF {
    
    private PDDocument document;
    
    private List<Double> temp = new ArrayList<>();
    
    public void CreateNewPDF() {
        
        document = new PDDocument();
        
        document.addPage(AddChartToDocument("Temprature for Batch", temp, "Time", "Temprature"));
    }
    
    
    public PDPage AddChartToDocument(String chartName, List<Double> data,
            String nameOfXAxis, String nameOfYAxis) {
        
        CreateChart chart = new CreateChart();
        
        PDPage pdfChart = new PDPage(PDRectangle.A4);
        pdfChart.setRotation(90);
        
        float pageWidth = pdfChart.getMediaBox().getWidth();
        float pageHeight = pdfChart.getMediaBox().getHeight();
        
        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document, 
                    chart.createChart(chartName, data, nameOfXAxis, nameOfYAxis,
                            (int) pageWidth,
                            (int) pageHeight));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
            contentStream.drawImage(chartImage, 0, 0);
        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pdfChart;
    }
    
    public void SaveandClose() {
        try {
            document.save("Test.pdf"); // TODO: Changes Path or it will save it in project folder.
            document.close();
        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
