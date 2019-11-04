package com.mycompany.domain.management.pdf;

import java.awt.image.BufferedImage;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class CreateChart {

    public CreateChart() {
    }
    
    public BufferedImage createChart(String chartName, List<Double> data,
            String nameOfXAxis, String nameOfYAxis, int width, int height) {
        
        XYChart chart = new XYChartBuilder().xAxisTitle(nameOfXAxis)
                .yAxisTitle(nameOfYAxis).width(width).height(height)
                .theme(ChartTheme.Matlab).build();
        
        XYSeries series = chart.addSeries(chartName, data);
        series.setMarker(SeriesMarkers.NONE);
        
        return BitmapEncoder.getBufferedImage(chart);
    }
}
