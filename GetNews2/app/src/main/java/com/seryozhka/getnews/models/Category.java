package com.seryozhka.getnews.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seryozhka on 23.11.16.
 */
public class Category {
    private String name;
    private List<Article> articleList;
    private boolean isInteresting;

    public Category(String name, boolean isInteresting, List<Article> articleList){
        this.name = name;
        this.isInteresting = isInteresting;
        this.articleList = articleList;
    }

    public Category(String name, List<Article> articleList) {
        this(name, true, articleList);
    }

    public static List<Category> generateList(List<String> stringList){
        List<Category> result = new ArrayList<>();
        for (String string : stringList) {
            result.add(new Category(string, null));
        }
        //Log.d("SERZH", result.toString());
        return result;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public boolean isInteresting() {
        return isInteresting;
    }

    public void setInteresting(boolean interesting) {
        isInteresting = interesting;
    }
}