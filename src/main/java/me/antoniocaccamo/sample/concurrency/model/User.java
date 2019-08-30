package me.antoniocaccamo.sample.concurrency.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter @Setter
public class User {

    private Integer id;

    private String email;

    private String name;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("email", email)
                .append("name", name)
                .toString();
    }
}
