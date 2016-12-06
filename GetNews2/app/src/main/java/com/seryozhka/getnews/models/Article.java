package com.seryozhka.getnews.models;

/**
 * Created by seryozhka on 23.11.16.
 */
public class Article {
    private String title;
    private String content;
    private String description;
    private int categoryID;
    private String imageUrl;

    public Article(String title, String content, String description, int categoryID, String imageUrl){
        this.title = title;
        this.content = content;
        this.description = description;
        this.categoryID = categoryID;
        this.imageUrl = imageUrl;
    }

    public Article(String title, String content, String description, int categoryID){
        this(title, content, description, categoryID, "http://lorempixel.com/300/300/cats/");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}