package com.jpmc.trading.report;

import com.jpmc.trading.report.service.ReportException;
import com.jpmc.trading.report.service.ReportService;
import org.junit.Test;

import java.io.File;

public class ReportServiceTest {
    ReportService reportService = new ReportService();

    @Test
    public void testReport() throws ReportException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("valid-instruction-file").getFile());
        reportService.generateInstructionReport(file);
    }
}
