package com.mycompany.domain.management.pdf;

import com.mycompany.crossCutting.objects.BatchReport;
import com.mycompany.crossCutting.objects.MachineHumiData;
import com.mycompany.crossCutting.objects.MachineTempData;
import com.mycompany.data.dataAccess.BatchDataHandler;
import com.mycompany.data.interfaces.IBatchDataHandler;
import com.mycompany.domain.management.ManagementDomain;
import com.mycompany.domain.management.interfaces.IBatchReportGenerate;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Override
    public PDDocument createNewPDF(int batchID, int prodListID, int machineID) throws NullPointerException {
        batchDataHandler = new BatchDataHandler();
        BatchReport batchRep = batchDataHandler.getBatchReportProductionData(batchID, machineID);
        List<MachineTempData> mtd = new ArrayList<>();
        List<MachineHumiData> mhd = new ArrayList<>();
        mtd = batchDataHandler.getMachineTempData(prodListID, machineID);
        mhd = batchDataHandler.getMachineHumiData(prodListID, machineID);
        ManagementDomain managementDomain = new ManagementDomain();
        Map<Integer, String> machineStatesMap = managementDomain.getTimeInStates(prodListID, machineID);

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

        document.addPage(addPageWithBatchInfo(batchRep, machineStatesMap));
        document.addPage(addXYChartToDocument("Temprature for Batch", temperatureDataList, "Point", "Temprature"));
        document.addPage(addCategoryChartToDocument("Humidity for Batch", countDouble, humidityDataList, "Point", "Humidity"));

        return document;
    }

    private PDPage addPageWithBatchInfo(BatchReport batchReport, Map<Integer, String> timeInStatesMap) {
        //Retrieving the pages of the document
        PDPage page = new PDPage();

        // Batch info
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
        // Time in states
        String norecord = "No record";
        String idle = "Idle: " + norecord;
        String execute = "Execute: " + norecord;
        String held = "Held: " + norecord;
        String completed = "Completed: " + norecord;
        String aborted = "Aborted: " + norecord;
        String stopped = "Stopped: " + norecord;

        // Machine states for batch
        for (Map.Entry<Integer, String> en : timeInStatesMap.entrySet()) {
            if (en.getKey() == 4) {
                idle = "Idle: " + en.getValue();
            }
            if (en.getKey() == 2) {
                stopped = "Stopped: " + en.getValue();
            }
            if (en.getKey() == 6) {
                execute = "Execute: " + en.getValue();
            }
            if (en.getKey() == 9) {
                aborted = "Aborted: " + en.getValue();
            }
            if (en.getKey() == 11) {
                held = "Held: " + en.getValue();
            }
            if (en.getKey() == 17) {
                completed = "Completed: " + en.getValue();
            }
        }

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
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 350, 700, idle);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 350, 650, execute);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 350, 600, held);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 350, 550, completed);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 350, 500, aborted);
            text.createText(contentStream, PDType1Font.TIMES_ROMAN, 14, 350, 450, stopped);
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return page;
    }

    private PDPage addXYChartToDocument(String chartName, List<Double> data,
            String nameOfXAxis, String nameOfYAxis) {

        Chart chart = new Chart();

        PDPage pdfChart = new PDPage(PDRectangle.A4);
        pdfChart.setRotation(90);

        float pageWidth = pdfChart.getMediaBox().getWidth();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document,
                    chart.createXYChart(chartName, data, nameOfXAxis, nameOfYAxis,
                            (int) PDRectangle.A4.getHeight(), (int) PDRectangle.A4.getWidth()));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 100));

            contentStream.drawImage(chartImage, -100, 0);
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

        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdfChart)) {
            PDImageXObject chartImage = JPEGFactory.createFromImage(document,
                    chart.createCategoryChart(chartName, xData, yData, nameOfXAxis, nameOfYAxis,
                            (int) PDRectangle.A4.getHeight(), (int) PDRectangle.A4.getWidth()));
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
            contentStream.drawImage(chartImage, 0, 0);
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
//            c.savePDF(c.createNewPDF(100, 449, 1), "TestMain", "S:\\git\\brewSoft_Group2\\BrewSoft");
            c.savePDF(c.createNewPDF(101, 450, 1), "TestMain", "D:\\git\\brewSoft_Group2\\BrewSoft");
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
