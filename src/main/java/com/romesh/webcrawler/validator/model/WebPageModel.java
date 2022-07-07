package com.romesh.webcrawler.validator.model;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "webpage")
public class WebPageModel {
    public WebPageModel() {
    }

    public WebPageModel(String id, String title, String pages) {
        this.id = id;
        this.title = title;
        this.pages = pages;
    }

    public WebPageModel(String title, String pages) {
        this.title = title;
        this.pages = pages;
    }
    private String id;

    private String title;

    private String pages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }
}
