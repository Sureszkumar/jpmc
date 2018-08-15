package com.jpmc.trading.report.app;

import com.jpmc.trading.report.service.ReportService;

import java.io.File;

/**
 *
 * This class is the main class which generates the trading report
 * based on the input transaction file.
 *
 * command line argument[0] is the instruction file path.
 *
 * @author Suresh
 * @since 15-08-2018
 */
public class Application {

    private static ReportService reportService = new ReportService();

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                File file = new File(args[0]);
                reportService.generateInstructionReport(file);
            } else {
                System.out.println("No file given");
            }
        } catch (Exception e) {
            System.out.println("Exception while generating report : " + e.getMessage());
        }

    }
}
