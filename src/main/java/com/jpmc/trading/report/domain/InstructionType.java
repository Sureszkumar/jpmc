package com.jpmc.trading.report.domain;

public enum InstructionType {

    B("Outgoing"), S("Incoming");

    private String alias;

    InstructionType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
