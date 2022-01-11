package com.moringaschool.newscout.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.newscout.R;
import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.models.Constants;
import com.moringaschool.newscout.models.NewsBusinessesSearchResponse;
import com.moringaschool.newscout.ui.NewsDetail;
import com.moringaschool.newscout.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseArticleViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {


    View mView;
    Context mContext;
    public ImageView mArticleImageView;

    public FirebaseArticleViewHolder (@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindArticle(Article article){
        mArticleImageView = (ImageView) mView.findViewById(R.id.NewsImageView);
      ImageView  NewsImageView = (ImageView)  itemView.findViewById(R.id.NewsImageView);
        TextView textViewNewsTitle = (TextView) itemView.findViewById(R.id.textViewNewsTitle);
        TextView textViewNewsDescription =(TextView)  itemView.findViewById(R.id.textViewNewsDescription);
        Picasso.get().load(article.getUrlToImage()).into(NewsImageView);
        textViewNewsTitle.setText(article.getTitle());
        textViewNewsDescription.setText(article.getDescription());


    }


    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
