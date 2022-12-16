package com.example.lx.data;

import java.io.Serializable;

public class BookItem implements Serializable {


    public BookItem(String title, String author, String translator, String publisher, String isbn,String pubTime,String note,String label,String website) {
        this.title = title;
        this.author = author;
        this.pubTime = pubTime;
        this.isbn = isbn;
        this.publisher = publisher;
        this.translator = translator;
        this.note = note;
        this.label = label;
        this.website = website;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }
    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPubTime() {
        return pubTime;
    }
    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    private String title;
    private String translator;
    private String author;
    private String publisher;
    private String isbn;
    private String pubTime;
    private String note;
    private String label;
    private String website;
}