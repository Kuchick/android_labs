package com.seryozhka.getnews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.seryozhka.getnews.DataClass;
import com.seryozhka.getnews.models.Article;
import com.seryozhka.getnews.models.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by seryozhka on 23.11.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsDB";

    private final static String CATEGORIES_TABLE = "Categories";
    private final static String CATEGORIES_ID = "id";
    private final static String CATEGORIES_NAME = "name";

    private final static String ARTICLES_TABLE = "Articles";
    private final static String ARTICLES_ID = "id";
    private final static String ARTICLES_TITLE = "TITLE";
    private final static String ARTICLES_CONTENT = "CONTENT";
    private final static String ARTICLES_DESCRIPTION = "DESCRIPTION";
    private final static String ARTICLES_CATEGORIES_KEY = CATEGORIES_TABLE + "_id";

    private final static String CREATE_CATEGORIES = "create table " + CATEGORIES_TABLE + " ( "
            + CATEGORIES_ID + " integer primary key autoincrement, "
            + CATEGORIES_NAME + " text);";

    private final static String CREATE_ARTICLES = "create table " + ARTICLES_TABLE + " ( "
            + ARTICLES_ID + " integer primary key autoincrement, "
            + ARTICLES_TITLE + " text, "
            + ARTICLES_CONTENT + "  text, "
            + ARTICLES_DESCRIPTION + " text, "
            + ARTICLES_CATEGORIES_KEY + " integer, "
            + "FOREIGN KEY(" + ARTICLES_CATEGORIES_KEY + ") REFERENCES " + CATEGORIES_TABLE + "(" + CATEGORIES_ID + ")" + "ON DELETE CASCADE" + ");";

    private final static String REMOVE_CATEGORY_BY_NAME = "DELETE FROM " + CATEGORIES_TABLE + " WHERE " +
            CATEGORIES_NAME + " = ?";

    private final static String REMOVE_CATEGORY =  "DELETE FROM " + CATEGORIES_TABLE;
//    private final static String SELECT_NEWS_BY_TITLE = "SELECT * FROM " + ARTICLES_TABLE +
//            " INNER JOIN " + CATEGORIES_TABLE +
//            " ON " + ARTICLES_TABLE + "." + ARTICLES_CATEGORIES_KEY + " = " + CATEGORIES_TABLE + " . " + CATEGORIES_ID + " WHERE " + CATEGORIES_TABLE + "." + CATEGORIES_NAME + " = ? ORDER BY length(" + ARTICLES_TITLE + ");";

//    private final static String SELECT_NEWS_BY_CONTENT = "SELECT * FROM " + ARTICLES_TABLE +
//            " INNER JOIN " + CATEGORIES_TABLE +
//            " ON " + ARTICLES_TABLE + "." + ARTICLES_CATEGORIES_KEY + " = " + CATEGORIES_TABLE + " . " + CATEGORIES_ID + " WHERE " + CATEGORIES_TABLE + "." + CATEGORIES_NAME + " = ? ORDER BY length(" + ARTICLES_CONTENT + ");";
//
//    private final static String SELECT_NEWS_BY_TITLE = "SELECT * FROM " + ARTICLES_TABLE +
//        " WHERE " + ARTICLES_CATEGORIES_KEY+ " = ? ORDER BY length(" + ARTICLES_TITLE + ");";

    private final static String GET_GATEGORIES = "SELECT * FROM " + CATEGORIES_TABLE;

    private final static String GET_ARTICLES = "SELECT * FROM " + ARTICLES_TABLE +
            " WHERE " + ARTICLES_CATEGORIES_KEY + " = ?";

    private final static String INNER_JOIN = "SELECT * FROM " + ARTICLES_TABLE +
            " INNER JOIN " + CATEGORIES_TABLE +
            " ON " + ARTICLES_TABLE + "." + ARTICLES_CATEGORIES_KEY + " = " + CATEGORIES_TABLE + " . " + CATEGORIES_ID;

    private final static String SELECT_NEWS_BY_TITLE = "SELECT * FROM " + ARTICLES_TABLE +
            " INNER JOIN " + CATEGORIES_TABLE + " ON " + ARTICLES_TABLE + "." + ARTICLES_CATEGORIES_KEY + " = " + CATEGORIES_TABLE + " . " + CATEGORIES_ID +
            " WHERE " + CATEGORIES_TABLE + " . " + CATEGORIES_NAME + " = ?" +
            " ORDER BY length(" + ARTICLES_TITLE + ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper() {
        super(null, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORIES);
        db.execSQL(CREATE_ARTICLES);
        saveCategories(Arrays.asList(DataClass.c1, DataClass.c2, DataClass.c3, DataClass.c4), db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void saveCategories(List<Category> categories, SQLiteDatabase db) {

        for (Category category: categories) {
            ContentValues values = new ContentValues();
            values.put(CATEGORIES_NAME, category.getName());
            long id = db.insert(CATEGORIES_TABLE, null, values);

            for (Article article: category.getArticleList()) {
                createArticle(id, article, db);
            }
        }
    }

    private void createArticle(long categoryId, Article article,SQLiteDatabase db) {
        //SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ARTICLES_TITLE, article.getTitle());
        values.put(ARTICLES_CONTENT, article.getContent());
        values.put(ARTICLES_DESCRIPTION, article.getDescription());
        values.put(ARTICLES_CATEGORIES_KEY, categoryId);

        db.insert(ARTICLES_TABLE, null, values);
    }

    public void getLogs(){

    }


    public List<String> innerJoin(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(INNER_JOIN, new String[]{});
        List<String> answer = new ArrayList<String>();


        if (cursor != null) {
            //Log.d("SERZHIK", String.valueOf(cursor.moveToFirst()));
            if (cursor.moveToFirst()) {
                //String str;
                do {
                    String str = "";
                    for (String cn : cursor.getColumnNames()) {
                        //str = str.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                        str = str.concat(cursor.getString(cursor.getColumnIndex(cn)) + " ");
                    }
                    answer.add(str);
                    //Log.d("SERZHIK", str + "sometext");
                } while (cursor.moveToNext());
            }
        } else {
            //Log.d("SERZHIK", "Cursor is null");
        }

        cursor.close();

        return answer;
    }

    public List<Article> orderByTitle(Category category){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(SELECT_NEWS_BY_TITLE, new String[]{category.getName()});
        List<Article> answer = new ArrayList<Article>();


        if (cursor != null) {
            Log.d("SERZHIKor", String.valueOf(cursor.moveToFirst()));
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex(ARTICLES_TITLE));
                    String content = cursor.getString(cursor.getColumnIndex(ARTICLES_CONTENT));
                    String description = cursor.getString(cursor.getColumnIndex(ARTICLES_DESCRIPTION));
                    String categoryID = cursor.getString(cursor.getColumnIndex(ARTICLES_CATEGORIES_KEY));
                    Article article = new Article(title, content, description, Integer.parseInt(categoryID));

                    answer.add(article);
                    Log.d("SERZHIKor", "ORDER BY: " + title + ' ' +content);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d("SERZHIKor", "Cursor is null");
        }

        cursor.close();
        return answer;
    }


    public List<Category> getCategories(){
        List<Category> answer = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(GET_GATEGORIES, new String[]{});

        if (cursor != null) {
            Log.d("getCat", String.valueOf(cursor.moveToFirst()));
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(CATEGORIES_NAME));
                    //int categoryID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CATEGORIES_ID)));
                    String categoryID = cursor.getString(cursor.getColumnIndex(CATEGORIES_ID));
                    List<Article> articleList = getArticlesFromCategory(categoryID, db);

                    Category category = new Category(name, articleList);

                    answer.add(category);
                    //Log.d("getCAt", "ORDER BY: " + title + ' ' +content);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d("getCat", "Cursor is null");
        }

        cursor.close();

        return answer;
    }

    private List<Article> getArticlesFromCategory(String category_id, SQLiteDatabase db){
        List<Article> answer = new ArrayList<Article>();

        Cursor cursor = db.rawQuery(GET_ARTICLES, new String[]{category_id});

        if (cursor != null) {
            Log.d("getArt", String.valueOf(cursor.moveToFirst()));
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex(ARTICLES_TITLE));
                    String description = cursor.getString(cursor.getColumnIndex(ARTICLES_DESCRIPTION));
                    String content = cursor.getString(cursor.getColumnIndex(ARTICLES_CONTENT));

                    Article article = new Article(title, content, description, Integer.parseInt(category_id));

                    answer.add(article);
                    //Log.d("getCAt", "ORDER BY: " + title + ' ' +content);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d("getCat", "Cursor is null");
        }

        cursor.close();
        return answer;
    }

    public void removeCategoryByName(String name){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(REMOVE_CATEGORY_BY_NAME, new String[]{name});
    }


}