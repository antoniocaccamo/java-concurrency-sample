package me.antoniocaccamo.sample.concurrency.beans;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter @Setter
public class BankAccount {

    private int accNumber;
    private String name;
    private String email;
    private String accType;

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("accNumber", accNumber)
                .append("name", name)
                .append("email", email)
                .append("accType", accType)
                .toString();
    }
}
