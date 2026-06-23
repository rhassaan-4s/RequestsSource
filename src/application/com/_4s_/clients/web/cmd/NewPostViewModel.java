package com._4s_.clients.web.cmd;

import org.hibernate.validator.constraints.NotBlank;

public class NewPostViewModel {
    @NotBlank
    private String text;

    public NewPostViewModel() {
    }

    public NewPostViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
