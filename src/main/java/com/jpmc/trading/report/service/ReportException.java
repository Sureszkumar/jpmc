package com.jpmc.trading.report.service;

/**
 * Custom exception for Report to handle the exceptions
 */
public class ReportException extends Exception {

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
