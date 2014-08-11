package com.qpp.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: gary,
 * Date: 12-7-27
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
public class People {

    @NotNull
    @Size(min = 4,max = 5)
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @NotNull
    @Size(min = 4,max = 5)
    @Email
    private String email;
}
