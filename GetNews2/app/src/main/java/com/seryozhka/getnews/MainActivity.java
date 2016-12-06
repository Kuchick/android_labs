package com.seryozhka.getnews;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seryozhka.getnews.adapters.CategoryListAdapter;
import com.seryozhka.getnews.database.DBHelper;
import com.seryozhka.getnews.models.Article;
import com.seryozhka.getnews.models.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("native-lib");
    }

    private CategoryListAdapter adapter;
    private List<Category> categoryList;
    private boolean menuEditPressed = false;
    DBHelper dbHelper;

    public String lastViewedArticle;
    public TextView lastViewedArticleView;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void goToArticleListActivity(String categoryName){
        Intent intent = new Intent(this, ArticleListActivity.class);
        intent.putExtra("categoryName", categoryName);
        startActivityForResult(intent, 1);
    }

//    public void saveLastViewedArticle(String name){
//        lastViewedArticle = name;
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        lastViewedArticleView.setText(lastViewedArticle);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        //categoryList = Category.generateList(Arrays.asList("Спорт", "Музыка", "Кулинария", "Погода", "Экономика", "Политика", "IT", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        categoryList = dbHelper.getCategories();

        adapter = new CategoryListAdapter(categoryList, this);

        ListView listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(adapter);
        lastViewedArticleView = (TextView)findViewById(R.id.last_viewed_article);
        lastViewedArticleView.setText("");
        //DataClass.writeInDatabase(getApplicationContext());

        //dbHelper = new DBHelper(this);
        //dbHelper.saveCategories(new ArrayList<Category>() {new Category("Sport", new ArrayList<Article>()){}});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuEditPressed){
            menu.findItem(R.id.item1).setTitle(getString(R.string.menu_item2));
        } else {
            menu.findItem(R.id.item1).setTitle(getString(R.string.menu_item1));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                if (menuEditPressed){
                    adapter.applyChanges();
                    adapter.notifyDataSetChanged();
                }
                menuEditPressed ^= true;

                adapter.setCheckboxVisibility(menuEditPressed);
                adapter.notifyDataSetChanged();

                break;
            case R.id.item2:
                //Toast.makeText(this, "Sort by java - 0.0017\nSort by C++ - 0.0004", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, Integer.toString(addictionNative(5, 7)), Toast.LENGTH_SHORT).show();
                List<String> newsContent = new ArrayList<>();
                for (Category category: categoryList) {
                    for (Article article: category.getArticleList()) {
                        newsContent.add(article.getContent());
                    }

                }

                String[] news = new String[newsContent.size()];
                for (int i = 0; i < newsContent.size(); i++){
                    news[i] = newsContent.get(i);
                }

                long start = System.currentTimeMillis();
                int resJava = sortJava(news, "t8");
                long timeJava = System.currentTimeMillis() - start;
                Log.d("Native", Integer.toString(resJava));

                start = System.currentTimeMillis();
                int resNative = sortNative(news, "t8");
                long timeNative = System.currentTimeMillis() - start;
                Log.d("Native", Integer.toString(resNative));
                Toast.makeText(this, String.format("C++ result - " + timeNative + "\nJava result - " + timeJava), Toast.LENGTH_LONG).show();





                start = System.nanoTime();
                resJava = sortJava(news, "t8");
                timeJava = System.nanoTime() - start;
                Log.d("Native", Integer.toString(resJava));

                start = System.nanoTime();
                resNative = sortNative(news, "t8");
                timeNative = System.nanoTime() - start;
                Log.d("Native", Integer.toString(resNative));
                Toast.makeText(this, String.format("C++ result - " + timeNative + "\nJava result - " + timeJava), Toast.LENGTH_LONG).show();
                break;
            case R.id.item3:
                Intent intent = new Intent(this, InnerJoinActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("articleTitle");
        lastViewedArticle = name;
        lastViewedArticleView.setText(lastViewedArticle);

    }

    private int sortJava(String[] news, String key) {
        int index = 0;
        int occurences = 0;

        for (int i = 0; i < news.length; i++) {
            int occ = findOccurences(news[i], key);
            if (occ > occurences) {
                index = i;
                occurences = occ;
            }
        }

        return index;
    }

    private int findOccurences(String article, String key) {
        int count = 0;
        if (article.length() < key.length()) {
            return 0;
        }

        for (int i = 0; i < article.length() - key.length() + 1; i++) {
            for (int j = 0; j < key.length(); j++) {
                if (article.charAt(i + j) != key.charAt(j)) {
                    break;
                } else if (key.length() == j + 1) {
                    count++;
                }
            }
        }

        return count;
    }

    //private native int addictionNative(int a, int b);
    private native int sortNative(String[] news, String key);
}