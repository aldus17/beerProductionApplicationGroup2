package com.mycompany.domain.management.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.coobird.thumbnailator.Thumbnails;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Chart {

    public Chart() {
    }

    /**
     * The method creates a xy coordinat graph based on the data. This graph is
     * converted to a bufferedImage.
     *
     * @param chartName of  type String
     * @param data of type List<Double>
     * @param nameOfXAxis of type String
     * @param nameOfYAxis of  type String
     * @param imageWidth of  type int
     * @param imageHeight of  type int
     *
     * @return returns a BufferedImage of a XYChart.
     */
    public BufferedImage createXYChart(String chartName, List<Double> data,
            String nameOfXAxis, String nameOfYAxis, int imageWidth, int imageHeight) {

        XYChart xyChart = new XYChartBuilder()
                .xAxisTitle(nameOfXAxis)
                .yAxisTitle(nameOfYAxis)
                .theme(ChartTheme.Matlab)
                .build();
        
        // Fill in with data
        XYSeries series = xyChart.addSeries(chartName, data);
        series.setMarker(SeriesMarkers.CIRCLE);
        
        // Create image with specified width and height
        BufferedImage bi = BitmapEncoder.getBufferedImage(xyChart);
        try {
            bi = Thumbnails.of(bi).size(imageWidth, imageHeight).asBufferedImage();
        } catch (IOException ex) {
            Logger.getLogger(Chart.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bi;
    }

    /**
     * The method creates a histodiagram based on the data. This graph is
     * converted to a bufferedImage.
     *
     * @param chartName, type String
     * @param xData, type List<Double>
     * @param yData, type List<Double>
     * @param nameOfXAxis, type String
     * @param nameOfYAxis, type String
     * @param imageWidth, type int
     * @param imageHeight, type int
     * 
     * @return returns a BufferedImage of a histodiagram
     */
    public BufferedImage createCategoryChart(String chartName, List<Double> xData, List<Double> yData,
            String nameOfXAxis, String nameOfYAxis, int imageWidth, int imageHeight) {

        CategoryChart categoryChart = new CategoryChartBuilder()
                .title(chartName)
                .xAxisTitle(nameOfXAxis)
                .yAxisTitle(nameOfYAxis)
                .build();
        
        // Use below for styling purpose
        categoryChart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        categoryChart.getStyler().setHasAnnotations(true);
        
        // Fill in with data
        categoryChart.addSeries(chartName, xData, yData);
        
        // Create image with specified width and height
        BufferedImage bi = BitmapEncoder.getBufferedImage(categoryChart);
        try {
            bi = Thumbnails.of(bi).size(imageWidth, imageHeight).asBufferedImage();
        } catch (IOException ex) {
            Logger.getLogger(Chart.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bi;

    }
}
