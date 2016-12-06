package com.seryozhka.getnews;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seryozhka.getnews.models.Article;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by seryozhka on 27.11.16.
 */
public class FragmentArticle extends Fragment {

    public TextView articleTitleView;
    public TextView articleContentView;
    //public ImageView imageView;
    public RecisingImage imageView;
    public Button btn_next;
    public String articleTitle;
    public Article article;
    public static int current_article;
    List<Article> articleList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, null);



    }

    @Override
    public void onStart() {
        super.onStart();
        articleTitle = getActivity().getIntent().getStringExtra("articleTitle");
        articleTitleView = (TextView)getActivity().findViewById(R.id.articleTitle);
        articleContentView = (TextView)getActivity().findViewById(R.id.articleContent);
        btn_next = (Button)getActivity().findViewById(R.id.nextArticle);
        //imageView = (ImageView)getActivity().findViewById(R.id.articleImage);
        imageView = (RecisingImage) getActivity().findViewById(R.id.articleImage);

        articleList = Arrays.asList(DataClass.a1, DataClass.a2, DataClass.a3, DataClass.a4,
                DataClass.a5, DataClass.a6, DataClass.a7, DataClass.a8,
                DataClass.a9, DataClass.a10, DataClass.a11, DataClass.a12);

        for (int i = 0; i < articleList.size(); i++){
            //Log.d("SERZH", categoryList.get(i).getName()+"_____"+getIntent().getStringExtra("categoryName"));
            if (articleList.get(i).getTitle().equals(articleTitle)){

                //Log.d("SERZH", "i= "+Integer.toString(i));
                article = articleList.get(i);
                current_article = i;
                break;
            }
        }

        articleTitleView.setText(article.getTitle());
        articleContentView.setText(article.getContent());
        btn_next.setOnClickListener(btnNextListener);
        ((ArticleActivity)getActivity()).saveDataToIntent(article.getTitle());



        Picasso.with(getActivity())
                .load(article.getImageUrl())
                .placeholder(R.drawable.news_image)
                .error(R.drawable.news_image)
                .into(imageView);

    }

    View.OnClickListener btnNextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToNextArticle();
        }
    };

    public void goToNextArticle(){
        if ((current_article + 1) == articleList.size()){
            current_article = 0;
        } else {
            current_article += 1;
        }

        article = articleList.get(current_article);
        articleTitleView.setText(article.getTitle());
        articleContentView.setText(article.getContent());
        ((ArticleActivity)getActivity()).saveDataToIntent(article.getTitle());
    }

    public void goToPreviousArticle(){
        if ((current_article - 1) == -1){
            current_article = articleList.size() - 1;
        } else {
            current_article -= 1;
        }

        article = articleList.get(current_article);
        articleTitleView.setText(article.getTitle());
        articleContentView.setText(article.getContent());
        ((ArticleActivity)getActivity()).saveDataToIntent(article.getTitle());
    }
}