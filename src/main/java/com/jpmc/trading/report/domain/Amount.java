package com.jpmc.trading.report.domain;

import java.math.BigDecimal;

public class Amount {
    private BigDecimal incoming = BigDecimal.ZERO;
    private BigDecimal outgoing = BigDecimal.ZERO;
    public BigDecimal getIncoming() {
        return incoming;
    }

    public void setIncoming(BigDecimal incoming) {
        this.incoming = incoming;
    }

    public BigDecimal getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(BigDecimal outgoing) {
        this.outgoing = outgoing;
    }
}
