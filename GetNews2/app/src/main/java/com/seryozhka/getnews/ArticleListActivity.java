package com.seryozhka.getnews;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.seryozhka.getnews.adapters.ArticleListAdapter;
import com.seryozhka.getnews.database.DBHelper;
import com.seryozhka.getnews.models.Article;
import com.seryozhka.getnews.models.Category;

import java.util.Arrays;
import java.util.List;

/**
 * Created by seryozhka on 24.11.16.
 */
public class ArticleListActivity extends AppCompatActivity {

    private ArticleListAdapter adapter;
    //DBHelper dbHelper;


    public void goToArticleActivity(String articleTitle){
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("articleTitle", articleTitle);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item4:
                DBHelper dbHelper = new DBHelper(this);


                List<Category> categoryList = Arrays.asList(DataClass.c1, DataClass.c2, DataClass.c3, DataClass.c4);

                for (int i = 0; i < categoryList.size(); i++){
                    //Log.d("SERZH", categoryList.get(i).getName()+"_____"+getIntent().getStringExtra("categoryName"));
                    if (categoryList.get(i).getName().equals(getIntent().getStringExtra("categoryName"))){

                        //Log.d("SERZH", "i= "+Integer.toString(i));
                        List<Article> answer = dbHelper.orderByTitle(categoryList.get(i));
                        Log.d("SRZH", answer.toString());
                        adapter.changeArticleList(answer);
                        adapter.notifyDataSetChanged();
                    }
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("_SERZH", "ActivityTwo: onCreate()");
        setContentView(R.layout.activity_article_list);


        //dbHelper = new DBHelper(this);
        //Log.d("SERZH", getIntent().getStringExtra("categoryName"));
        List<Category> categoryList = Arrays.asList(DataClass.c1, DataClass.c2, DataClass.c3, DataClass.c4);

        for (int i = 0; i < categoryList.size(); i++){
            //Log.d("SERZH", categoryList.get(i).getName()+"_____"+getIntent().getStringExtra("categoryName"));
            if (categoryList.get(i).getName().equals(getIntent().getStringExtra("categoryName"))){

                //Log.d("SERZH", "i= "+Integer.toString(i));
                adapter = new ArticleListAdapter(categoryList.get(i).getArticleList(), this);
            }
        }


        //adapter = new ArticleListAdapter(null, this);
        ListView listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("articleTitle");
        Intent intent = new Intent();
        intent.putExtra("articleTitle", name);
        setResult(RESULT_OK, intent);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////LIFE_CYCLE//////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("_SERZH", "ActivityTwo: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("_SERZH", "ActivityTwo: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("_SERZH", "ActivityTwo: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("_SERZH", "ActivityTwo: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("_SERZH", "ActivityTwo: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("_SERZH", "ActivityTwo: onDestroy()");
    }
}