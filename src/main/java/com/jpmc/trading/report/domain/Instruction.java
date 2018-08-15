package com.jpmc.trading.report.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class Instruction {

    private String entity;

    private InstructionType type;

    private BigDecimal agreedFix;

    private String currency;

    private LocalDate instructionDate;

    private LocalDate requestedSettlementDate;

    private LocalDate effectiveSettlementDate;

    private int units;

    private BigDecimal pricePerUnit;

    private BigDecimal usdValue;

    public Instruction(String entity, InstructionType type, BigDecimal agreedFix, String currency, LocalDate instructionDate,
                       LocalDate requestedSettlementDate, int units, BigDecimal pricePerUnit) {
        this.entity = entity;
        this.type = type;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.requestedSettlementDate = requestedSettlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.agreedFix = agreedFix;
        long daysToIncrement = 0;
        if (currency.equals("AED") || currency.equals("SAR")) {
            if (requestedSettlementDate.getDayOfWeek() == FRIDAY) {
                daysToIncrement = 2;
            } else if (requestedSettlementDate.getDayOfWeek() == SATURDAY) {
                daysToIncrement = 1;
            }
        } else {
            if (requestedSettlementDate.getDayOfWeek() == SATURDAY) {
                daysToIncrement = 2;
            } else if (requestedSettlementDate.getDayOfWeek() == SUNDAY) {
                daysToIncrement = 1;
            }
        }
        this.effectiveSettlementDate = requestedSettlementDate.plusDays(daysToIncrement);
        this.usdValue = (pricePerUnit.multiply(BigDecimal.valueOf(units)).multiply(agreedFix)).setScale(2, RoundingMode.CEILING);
    }


    public InstructionType getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public LocalDate getRequestedSettlementDate() {
        return requestedSettlementDate;
    }

    public int getUnits() {
        return units;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public BigDecimal getAgreedFix() {
        return agreedFix;
    }

    public LocalDate getEffectiveSettlementDate() {
        return effectiveSettlementDate;
    }

    public String getEntity() {
        return entity;
    }

    public BigDecimal getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(BigDecimal usdValue) {
        this.usdValue = usdValue;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "entity='" + entity + '\'' +
                ", type=" + type +
                ", agreedFix=" + agreedFix +
                ", currency='" + currency + '\'' +
                ", instructionDate=" + instructionDate +
                ", requestedSettlementDate=" + requestedSettlementDate +
                ", effectiveSettlementDate=" + effectiveSettlementDate +
                ", units=" + units +
                ", pricePerUnit=" + pricePerUnit +
                ", usdValue=" + usdValue +
                '}';
    }
}
