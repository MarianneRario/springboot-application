package com.caramellow.location.locationweb.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReportUtilImpl implements ReportUtil{

    @Override
    public void generatePieChart(
            String path, List<Object[]> data) {

        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Object[] obj: data) {
            System.out.println("mima obj0: " + obj[0].toString());
            System.out.println("mima obj1: " + obj[1].toString());
            // obj[1].toString() -> is the actual count of the obj in the db count(type)
            dataset.setValue(obj[0].toString(), new Double(obj[1].toString()));
        }
        

        // create the pie chart report
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Location Type Report", dataset );

        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(path), chart, 300, 300);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
