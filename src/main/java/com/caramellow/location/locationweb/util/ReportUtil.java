package com.caramellow.location.locationweb.util;

import java.util.List;

public interface ReportUtil {

    /**
     * @param path -> on the file system where we want the final jpeg to reside
     * @param data -> object that will be returned by findTypeCount() in LocationRepository
     */
    void generatePieChart(String path, List<Object[]> data);
}
