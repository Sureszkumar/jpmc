package com.jpmc.trading.report.service;

import com.jpmc.trading.report.domain.Amount;
import com.jpmc.trading.report.domain.Instruction;
import com.jpmc.trading.report.domain.InstructionType;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This is a persistence class which persist and maintain the instructions parsed and prints the required report
 *
 * @author Suresh
 * @since 15-8-2018
 */
public class ReportData {
    private static List<Instruction> instructions = new LinkedList<>();
    private static Map<LocalDate, Amount> dailyAmountMap = new HashMap<>();

    /**
     * 1.Persist the instruction in the memory
     * 2.Creates a map for the incoming and outgoing amount
     *
     * @param instruction
     */
    public void addAmount(Instruction instruction) {
        addInstruction(instruction);
        if (InstructionType.B == instruction.getType()) {
            LocalDate settlementDate = instruction.getEffectiveSettlementDate();
            if (!dailyAmountMap.containsKey(settlementDate)) {
                dailyAmountMap.put(settlementDate, new Amount());
            }
            dailyAmountMap.get(settlementDate).setOutgoing(dailyAmountMap.get(settlementDate).getOutgoing().add(instruction.getUsdValue()));
        } else if (InstructionType.S == instruction.getType()) {
            LocalDate incomingDate = instruction.getInstructionDate();
            if (!dailyAmountMap.containsKey(incomingDate)) {
                dailyAmountMap.put(incomingDate, new Amount());
            }
            dailyAmountMap.get(incomingDate).setIncoming(dailyAmountMap.get(incomingDate).getIncoming().add(instruction.getUsdValue()));
        } else {
            throw new IllegalArgumentException(String.format("Invalid instruction type '%s'specified in the input file", instruction.getType()));
        }
    }

    /**
     * Add the instruction to the memory if new or update the existing entity
     *
     * @param instruction
     */
    private void addInstruction(Instruction instruction) {
        Optional<Instruction> matchingObject = instructions.stream().
                filter(p -> (p.getEntity().equals(instruction.getEntity()) && p.getType().equals(instruction.getType()))).
                findFirst();
        if (matchingObject.isPresent()) {
            matchingObject.get().setUsdValue(matchingObject.get().getUsdValue().add(instruction.getUsdValue()));
        } else {
            instructions.add(instruction);
        }

    }

    /**
     * Prints the daily report on incoming and outgoing amount
     */
    public void printDailyReport() {
        System.out.println("---------------");
        System.out.println("Daily report");
        System.out.println("---------------");
        System.out.println("*************************************************************");
        dailyAmountMap.forEach((key, value)
                -> System.out.println("Date: " + key + ", Incoming: " + value.getIncoming() + ", Outgoing: " + value.getOutgoing())
        );
        System.out.println("*************************************************************");
    }

    /**
     *
     * Prints the ranking of the entity based on the type and amount
     */
    public void printRanking() {
        System.out.println("---------------");
        System.out.println("Ranking");
        System.out.println("---------------");
        System.out.println("*************************************************************");
        Comparator<Instruction> comparator = Comparator
                .comparing(Instruction::getType)
                .thenComparing(Comparator.comparing(Instruction::getUsdValue).reversed());
        instructions.stream()
                .sorted(comparator)
                .collect(Collectors.groupingBy(Instruction::getType, Collectors.toList()))
                .forEach((key, val) -> {
                    System.out.println("---------------");
                    System.out.println(key.getAlias());
                    System.out.println("---------------");
                    val.forEach(instruction -> System.out.println("Name : " + instruction.getEntity()
                            + ", Amount in USD : " + instruction.getUsdValue()));
                });
        System.out.println("*************************************************************");
    }
}
