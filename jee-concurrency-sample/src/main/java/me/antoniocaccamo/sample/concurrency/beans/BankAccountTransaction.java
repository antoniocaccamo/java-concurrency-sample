package me.antoniocaccamo.sample.concurrency.beans;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class BankAccountTransaction {

    private int accNumber;
    private double amount;
    private Date txDate;
    private String txType;
    private int txId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("accNumber", accNumber)
                .append("amount", amount)
                .append("txDate", txDate)
                .append("txType", txType)
                .append("txId", txId)
                .toString();
    }
}
