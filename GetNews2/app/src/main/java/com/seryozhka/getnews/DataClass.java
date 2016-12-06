package com.seryozhka.getnews;

import com.seryozhka.getnews.models.Article;
import com.seryozhka.getnews.models.Category;

import java.util.Arrays;

/**
 * Created by seryozhka on 25.11.16.
 */
public class DataClass {
    public static Article a1 = new Article("Title1", "Content1", "Description1", 1, "http://lorempixel.com/300/300/cats/");
    public static Article a2 = new Article("Title222", "Content2", "Description2", 1, "http://lorempixel.com/300/300/cats/");
    public static Article a3 = new Article("Title33", "Content3", "Description3", 1, "http://lorempixel.com/300/300/cats/");
    public static Article a4 = new Article("Title44444", "Content4", "Description4", 2, "http://lorempixel.com/300/300/cats/");
    public static Article a5 = new Article("Title55", "Content5", "Description5", 2, "http://lorempixel.com/300/300/cats/");
    public static Article a6 = new Article("Title666", "Content6", "Description6", 2, "http://lorempixel.com/300/300/cats/");
    public static Article a7 = new Article("Title77", "Content7", "Description7", 3, "http://lorempixel.com/300/300/cats/");
    public static Article a8 = new Article("Title8888", "Content8", "Description8", 3, "http://lorempixel.com/300/300/cats/");
    public static Article a9 = new Article("Title9", "Content9", "Description9", 3, "http://lorempixel.com/300/300/cats/");
    public static Article a10 = new Article("Title1000", "Content10", "Description10", 4, "http://lorempixel.com/300/300/cats/");
    public static Article a11 = new Article("Title111", "Content11", "Description11", 4, "http://lorempixel.com/300/300/cats/");
    public static Article a12 = new Article("Title12222", "Content12", "Description12", 4, "http://lorempixel.com/300/300/cats/");
    public static Category c1 = new Category("Category1", Arrays.asList(a1,a2,a3));
    public static Category c2 = new Category("Category2", Arrays.asList(a4,a5,a6));
    public static Category c3 = new Category("Category3", Arrays.asList(a7,a8,a9));
    public static Category c4 = new Category("Category4", Arrays.asList(a10,a11,a12));

//    public static void writeInDatabase(Context ctx){
//        DBHelper dbHelper = new DBHelper(ctx);
//        dbHelper.saveCategories(Arrays.asList(c1, c2, c3, c4));
//    }
}