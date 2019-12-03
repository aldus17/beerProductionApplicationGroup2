package com.mycompany.domain.management.pdf;

import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineTempData;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.domain.management.interfaces.IBatchReportGenerate;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class PDF implements IBatchReportGenerate {

    private PDDocument document;

    private List<Double> temperatureDataList = new ArrayList<>();
    private List<Double> humidityDataList = new ArrayList<>();
    private IBatchDataHandler batchDataHandler;

    public PDF() {

    }

    // https://www.tutorialspoint.com/pdfbox/pdfbox_quick_guide.htm
    @Override
    public PDDocument createNewPDF(int batchID, int prodListID, int machineID) {
        batchDataHandler = new BatchDataHandler();
        BatchReport batchRep = batchDataHandler.getBatchReportProductionData(batchID, machineID);
        MachineTempData machineTempData = batchDataHandler.getMachineTempData(prodListID, machineID);
        MachineHumiData machineHumiData = batchDataHandler.getMachineHumiData(prodListID, machineID);

        List<MachineTempData> mtd = new ArrayList<>();
        List<MachineHumiData> mhd = new ArrayList<>();

        machineTempData.getMachineTempDataObjList().forEach((object) -> {
            mtd.add((MachineTempData) object);
        });
        machineHumiData.getMachineHumiDataObjList().forEach((object) -> {
            mhd.add((MachineHumiData) object);
        });

        for (MachineTempData md : mtd) {
            temperatureDataList.add(md.getTemperature());

        }
        double count = 0.0;
        List<Double> countDouble = new ArrayList<>();
        for (MachineHumiData md : mhd) {

            humidityDataList.add(md.getHumidity());
            count++;
            countDouble.add(count);
        }

        document = new PDDocument();

        document.addPage(addPageWithBatchInfo(batchRep));
        document.addPage(addXYChartToDocument("Temprature for Batch", temperatureDataList, "Point", "Temprature"));
        document.addPage(addCategoryChartToDocument("Humidity for Batch", countDouble, humidityDataList, "Point", "Humidity"));

        return document;
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
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
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
        // float pageHeight = pdfChart.getMediaBox().getHeight();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document,
                    chart.createXYChart(chartName, data, nameOfXAxis, nameOfYAxis,
                            (int) PDRectangle.A4.getHeight(), (int) PDRectangle.A4.getWidth()));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 100));

            contentStream.drawImage(chartImage, -100, 0);
            System.out.println("XYChart added");
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pdfChart;
    }

    private PDPage addCategoryChartToDocument(String chartName, List<Double> xData, List<Double> yData,
            String nameOfXAxis, String nameOfYAxis) {

        Chart chart = new Chart();

        PDPage pdfChart = new PDPage(PDRectangle.A4);
        pdfChart.setRotation(90);

        float pageWidth = pdfChart.getMediaBox().getWidth();
        // float pageHeight = pdfChart.getMediaBox().getHeight();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document,
                    chart.createCategoryChart(chartName, xData, yData, nameOfXAxis, nameOfYAxis,
                            (int) PDRectangle.A4.getHeight(), (int) PDRectangle.A4.getWidth()));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
            contentStream.drawImage(chartImage, 0, 0);
            System.out.println("CategoryChart added");
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pdfChart;
    }

    @Override
    public void savePDF(PDDocument document, String fileName, String directory) throws IOException {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd_MM_yyyy__HH_mm_ss");
        String formattedDate = myDateObj.format(myFormatObj);

        document.save(directory + "\\" + fileName + formattedDate + ".pdf"); // TODO: Changes Path or it will save it in project folder.
        document.close();

    }

    public static void main(String[] args) {
        PDF c = new PDF();

        try {
            c.savePDF(c.createNewPDF(100, 449, 1), "TestMain", "S:\\git\\brewSoft_Group2\\BrewSoft");
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
