package com.mycompany.domain.management.pdf;

import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.MachineData;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class CreatePDF {

    private PDDocument document;

    private List<Double> temperatureDataList = new ArrayList<>();
    private List<Double> humidityDataList = new ArrayList<>();
    private IBatchDataHandler batchDataHandler;

    // https://www.tutorialspoint.com/pdfbox/pdfbox_quick_guide.htm
    public void createNewPDF(int batchID, int prodListID, int machineID) {
        batchDataHandler = new BatchDataHandler();
        BatchReport batchRep = batchDataHandler.getBatchReportProductionData(batchID, machineID);
        MachineData machineData = batchDataHandler.getMachineData(prodListID, machineID);
        List<MachineData> mdl = new ArrayList<>();
        for (Object object : machineData.getMachineDataObjList()) {
            mdl.add((MachineData) object);
        }
        for (MachineData md : mdl) {
            temperatureDataList.add(md.getTemperature());
            humidityDataList.add(md.getHumidity());
        }
        
        try {
            document = new PDDocument();

            document.addPage(addPageWithBatchInfo(batchRep));
            document.addPage(addXYChartToDocument("Temprature for Batch", temperatureDataList, "Time", "Temprature"));
            document.addPage(addCategoryChartToDocument("Humidity for Batch", humidityDataList, temperatureDataList, "Temperature", "Humidity"));

            document.save("BatchReport.pdf"); // TODO: Changes Path or it will save it in project folder.
            document.close();
        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private PDPage addPageWithBatchInfo(BatchReport batchReport) {
        //Retrieving the pages of the document 
        PDPage page = new PDPage();

        String header = "Batch Report";
        String batchIDText = "Batch ID: " + batchReport.getBatchID();
        String machineIDText = "Machine ID: " + batchReport.getBreweryMachineID();
        String deadlineText = "Deadline: " + batchReport.getDeadline();
        String dateOfCreationText = "Date of creation: " + batchReport.getDateOfCreation();
        String dateOfCompletionText = "Date of completion: " + batchReport.getDateOfCompletion();
        String productTypeText = "Product type: " + batchReport.getProductType();
        String totalcountText = "Total product count: " + batchReport.getTotalCount();
        String acceptCountText = "Accepted product count: " + batchReport.getAcceptedCount();
        String defectCountText = "Defect product count: " + batchReport.getDefectCount();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            Text text = new Text();
            text.createText(contentStream, PDType1Font.TIMES_BOLD, 24, 225, 750, header);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 700, batchIDText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 650, machineIDText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 600, deadlineText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 550, dateOfCreationText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 500, dateOfCompletionText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 450, productTypeText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 400, totalcountText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 350, acceptCountText);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 25, 300, defectCountText);

            System.out.println("BatchInfo added");

        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return page;
    }

    // https://github.com/knowm/XChart
    private PDPage addXYChartToDocument(String chartName, List<Double> data,
            String nameOfXAxis, String nameOfYAxis) {

        Chart chart = new Chart();

        PDPage pdfChart = new PDPage(PDRectangle.A4);
        pdfChart.setRotation(90);

        float pageWidth = pdfChart.getMediaBox().getWidth();
        float pageHeight = pdfChart.getMediaBox().getHeight();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document,
                    chart.createXYChart(chartName, data, nameOfXAxis, nameOfYAxis,
                            (int) pageWidth,
                            (int) pageHeight));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

            contentStream.drawImage(chartImage, 0, 0);
            System.out.println("XYChart added");
        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pdfChart;
    }

    private PDPage addCategoryChartToDocument(String chartName, List<Double> xData, List<Double> yData,
            String nameOfXAxis, String nameOfYAxis) {

        Chart chart = new Chart();

        PDPage pdfChart = new PDPage(PDRectangle.A4);
        pdfChart.setRotation(90);

        float pageWidth = pdfChart.getMediaBox().getWidth();
        float pageHeight = pdfChart.getMediaBox().getHeight();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document,
                    chart.createCategoryChart(chartName, xData, yData, nameOfXAxis, nameOfYAxis,
                            (int) pageWidth,
                            (int) pageHeight));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
            contentStream.drawImage(chartImage, 0, 0);
            System.out.println("CategoryChart added");
        } catch (IOException ex) {
            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pdfChart;
    }

//    public void saveandClose() {
//        try {
//            document.save("Test.pdf"); // TODO: Changes Path or it will save it in project folder.
//            document.close();
//        } catch (IOException ex) {
//            Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void main(String[] args) {
        CreatePDF c = new CreatePDF();
        c.createNewPDF(8, 195, 1);
        //c.AddChartToDocument(null, null, null, null);
    }

}
