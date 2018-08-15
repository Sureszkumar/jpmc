package com.jpmc.trading.report.service;

import com.jpmc.trading.report.domain.Instruction;
import com.jpmc.trading.report.domain.InstructionType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 *
 * Service class which is responsible for parsing the instruction input file
 * and stores in the memory and process to print to report.
 *
 *
 * @author Suresh
 * @since 15-08-2018
 */
public class ReportService {

    private ReportData reportData = new ReportData();
    private static final String COMMA_SEPERATOR = ",";
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd MMM yyyy").toFormatter(Locale.ENGLISH);

    public void generateInstructionReport(File file) throws ReportException {
        parseInstructionFile(file);
        reportData.printDailyReport();
        System.out.println();
        reportData.printRanking();
    }

    /**
     * Reads the instruction file line by line ,create instruction entity
     * based on it and store it to memory using ReportData
     * @see ReportData
     *
     * @param file Instruction input file
     * @throws ReportException in case of invalid instruction data in the file
     */
    private void parseInstructionFile(File file) throws ReportException {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                reportData.addAmount(createInstruction(line));
            }
        } catch (Exception e) {
            throw new ReportException("Exception while parsing the instruction file", e);
        }
    }

    /**
     * Creates Instrucntion entity based on the text.
     *
     * @param instructionEntity
     * @return
     */
    private Instruction createInstruction(String instructionEntity) {
        String[] elements = instructionEntity.split(COMMA_SEPERATOR);
        return new Instruction(elements[0], InstructionType.valueOf(elements[1]), new BigDecimal(elements[2]), elements[3], LocalDate.parse(elements[4], formatter),
                LocalDate.parse(elements[5], formatter), Integer.valueOf(elements[6]), new BigDecimal(elements[7]));
    }

}
