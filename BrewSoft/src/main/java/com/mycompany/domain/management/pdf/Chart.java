package com.mycompany.domain.management.pdf;

import java.awt.image.BufferedImage;
import java.util.List;

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

    public BufferedImage createXYChart(String chartName, List<Double> data,
            String nameOfXAxis, String nameOfYAxis, int width, int height) {

        XYChart xyChart = new XYChartBuilder()
                .xAxisTitle(nameOfXAxis)
                .yAxisTitle(nameOfYAxis)
                .width(width)
                .height(height)
                .theme(ChartTheme.Matlab)
                .build();
        XYSeries series = xyChart.addSeries(chartName, data);
        series.setMarker(SeriesMarkers.CIRCLE);

        return BitmapEncoder.getBufferedImage(xyChart);
    }

    public BufferedImage createCategoryChart(String chartName, List<Double> xData, List<Double> yData,
            String nameOfXAxis, String nameOfYAxis, int width, int height) {

        CategoryChart categoryChart = new CategoryChartBuilder()
                .width(width)
                .height(height)
                .title(chartName)
                .xAxisTitle(nameOfXAxis)
                .yAxisTitle(nameOfYAxis)
                .build();
        // Use below for styling purpose
        categoryChart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        categoryChart.getStyler().setHasAnnotations(true);
        // categoryChart.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Stick);

        categoryChart.addSeries(chartName, xData, yData);


        return BitmapEncoder.getBufferedImage(categoryChart);
    }
}
