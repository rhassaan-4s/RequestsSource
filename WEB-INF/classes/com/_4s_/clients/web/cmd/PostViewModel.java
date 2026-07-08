package com._4s_.clients.web.cmd;

public class PostViewModel {
    private long id;
    private String text;
    private String authorEmail;

    public PostViewModel() {
    }

    public PostViewModel(long id, String text, String authorEmail) {
        this.id = id;
        this.text = text;
        this.authorEmail = authorEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}
