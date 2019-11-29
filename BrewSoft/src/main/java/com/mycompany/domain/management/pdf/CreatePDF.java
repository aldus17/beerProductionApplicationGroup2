package com.mycompany.domain.management.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class CreatePDF {

    private PDDocument document;

    private List<Double> temperatureDataList = new ArrayList<>();
    private List<Double> humidityDataList = new ArrayList<>();

    // https://www.tutorialspoint.com/pdfbox/pdfbox_quick_guide.htm
    public void createNewPDF() {
        temperatureDataList.add(20.5);
        temperatureDataList.add(22.5);
        document = new PDDocument();

        document.addPage(addChartToDocument("Temprature for Batch", temperatureDataList, "Time", "Temprature"));

        //document.addPage(new PDPage());
    }

    public void createPage1() {

    }

    public void createPage2() {

    }

    public void createPage3() {

    }

    public void addProductionInfoData() {

    }

    public void addingDocumentAttributes() {
        //Creating a blank page
        PDPage blankPage = new PDPage();

        //Adding the blank page to the document
        document.addPage(blankPage);

        //Creating the PDDocumentInformation object 
        PDDocumentInformation pdd = document.getDocumentInformation();

        //Setting the author of the document
        pdd.setAuthor("Refslevbæk Bryghus A/S");

        // Setting the title of the document
        pdd.setTitle("Batch Report");

        //Setting the creator of the document 
        pdd.setCreator("BrewSoft");

        //Setting the subject of the document 
        pdd.setSubject("Batch Report");

        //Setting the created date of the document 
        Calendar date = new GregorianCalendar();
        pdd.setCreationDate(date);
        //Setting the modified date of the document 
        pdd.setModificationDate(date);

        //Setting keywords for the document 
        pdd.setKeywords("report, batchreport");
    }

    public void addingDocumentAttributes(PDPage page, String author, String title, String creator, String subject, String... keywords) {
        //Adding the blank page to the document
        document.addPage(page);

        //Creating the PDDocumentInformation object 
        PDDocumentInformation pdd = document.getDocumentInformation();

        //Setting the author of the document
        pdd.setAuthor("Refslevbæk Bryghus A/S");

        // Setting the title of the document
        pdd.setTitle("Batch Report");

        //Setting the creator of the document 
        pdd.setCreator("Refslevbæk Bryghus A/S");

        //Setting the subject of the document 
        pdd.setSubject("Batch Report");

        //Setting the created date of the document 
        Calendar date = new GregorianCalendar();
        pdd.setCreationDate(date);
        //Setting the modified date of the document 
        pdd.setModificationDate(date);

        //Setting keywords for the document 
        pdd.setKeywords("report, batchreport");
    }

    // https://github.com/knowm/XChart
    public PDPage addChartToDocument(String chartName, List<Double> data,
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

    public void saveandClose() {
        try {
            document.save("Test.pdf"); // TODO: Changes Path or it will save it in project folder.
            document.close();
        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        CreatePDF c = new CreatePDF();
        c.createNewPDF();
        c.addingDocumentAttributes();
        //c.AddChartToDocument(null, null, null, null);
        c.saveandClose();
    }

}
