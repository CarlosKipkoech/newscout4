package com.moringaschool.newscout.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.newscout.R;
import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.ui.NewsDetail;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article>  mArticles;
    private  Context context;



    public NewsAdapter(List<Article> mArticles, Context context) {
        this.mArticles = mArticles;
        this.context = context;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        Article articleList = mArticles.get(position);
        holder.textViewNewsTitle.setText(articleList.getTitle());
        holder.textViewNewsDescription.setText(articleList.getDescription());
        Picasso.get().load(articleList.getUrlToImage()).into(holder.NewsImageView);

    }
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewNewsTitle;
        public TextView textViewNewsDescription;

//        @BindView(R.id.NewsImageView) ImageView  NewsImageView;
        public ImageView NewsImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
            NewsImageView = (ImageView)  itemView.findViewById(R.id.NewsImageView);
            textViewNewsTitle = (TextView) itemView.findViewById(R.id.textViewNewsTitle);
            textViewNewsDescription =(TextView)  itemView.findViewById(R.id.textViewNewsDescription);

        }

        @Override
        public void onClick(View v) {
        int itemPosition = getLayoutPosition();
        Intent intent = new Intent(context, NewsDetail.class);
        intent.putExtra("position",itemPosition);
        intent.putExtra("articles", Parcels.wrap(mArticles));
        context.startActivity(intent);
        }
    }
}

