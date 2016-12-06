package com.seryozhka.getnews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seryozhka.getnews.ArticleListActivity;
import com.seryozhka.getnews.R;
import com.seryozhka.getnews.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by seryozhka on 24.11.16.
 */
public class ArticleListAdapter extends BaseAdapter {

    List<Article> articleList;
    Context ctx;
    private final LayoutInflater lInflater;

    public ArticleListAdapter(List<Article> articleList, Context ctx){
        this.articleList = articleList;
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void changeArticleList(List<Article> articleList){
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.item_article, null);
        }

        TextView newsTitle = (TextView)view.findViewById(R.id.newsTitle);
        TextView newsDescription = (TextView)view.findViewById(R.id.newsDescription);
        ImageView newsImage = (ImageView)view.findViewById(R.id.newsImage);
        newsTitle.setText(articleList.get(position).getTitle());
        newsDescription.setText(articleList.get(position).getDescription());
        //newsImage.setImageResource(R.drawable.news_image);


        Picasso.with(ctx)
                .load(articleList.get(position).getImageUrl())
                .placeholder(R.drawable.news_image)
                .error(R.drawable.news_image)
                .into(newsImage);



        view.setTag(position);
        view.setOnClickListener(articleListListener);

        return view;
    }

    View.OnClickListener articleListListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String articleTitle = articleList.get((Integer) v.getTag()).getTitle();
            //Log.d("SERZH", "categoryName= "+categoryName);
            ((ArticleListActivity)ctx).goToArticleActivity(articleTitle);
        }
    };
}