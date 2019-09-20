package training.supportbank;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    LocalDate date;
    String narrative;
    String fromName;
    String toName;
    BigDecimal amountSent;

    public Transaction(String fromName, String toName, String narrative, BigDecimal amountSent) {
        this.narrative = narrative;
        this.fromName = fromName;
        this.toName = toName;
        this.amountSent = amountSent;
    }

}
