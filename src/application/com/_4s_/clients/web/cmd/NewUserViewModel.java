package com._4s_.clients.web.cmd;

import org.hibernate.validator.constraints.NotBlank;

// TODO: matching passwords
public class NewUserViewModel {
    @NotBlank
    private String username;
    @NotBlank
    private String password1;
    @NotBlank
    private String password2;

    public NewUserViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
