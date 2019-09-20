package training.supportbank;

import java.math.BigDecimal;

public class Account {
    String name;
    BigDecimal balance = new BigDecimal(0.0);

    public Account(String name) {
        this.name = name;
    }

    public void decreaseBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }


}
