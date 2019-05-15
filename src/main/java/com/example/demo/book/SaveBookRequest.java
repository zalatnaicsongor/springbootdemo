package com.example.demo.book;

import javax.validation.constraints.NotNull;

public class SaveBookRequest {
    @NotNull
    private String author;

    @NotNull
    private String title;

    public SaveBookRequest(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
