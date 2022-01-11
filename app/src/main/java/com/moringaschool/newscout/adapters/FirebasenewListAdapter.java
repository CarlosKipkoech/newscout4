package com.moringaschool.newscout.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.moringaschool.newscout.R;
import com.moringaschool.newscout.adapters.FirebaseArticleViewHolder;
import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.ui.Business;
import com.moringaschool.newscout.ui.NewsDetail;
import com.moringaschool.newscout.util.ItemTouchHelperAdapter;
import com.moringaschool.newscout.util.OnStartDragListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebasenewListAdapter extends FirebaseRecyclerAdapter<Article, FirebaseArticleViewHolder> implements ItemTouchHelperAdapter  {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Article> mArticles = new ArrayList<>();



    public FirebasenewListAdapter(FirebaseRecyclerOptions<Article> options,
                                  Query ref,
                                  OnStartDragListener onStartDragListener,
                                  Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mArticles.add(dataSnapshot.getValue(Article.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseArticleViewHolder firebasearticleViewHolder, int position, @NonNull Article model) {
        firebasearticleViewHolder.bindArticle(model);
        firebasearticleViewHolder.mArticleImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(firebasearticleViewHolder);
                }
                return false;
            }
        });

        firebasearticleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(mContext, NewsDetail.class);
                intent.putExtra("position", firebasearticleViewHolder.getAdapterPosition());
                intent.putExtra("articles", Parcels.wrap(mArticles));
                mContext.startActivity(intent);
            }
        });
    }



    @NonNull
    @Override
    public FirebaseArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_drag, parent, false);
        return new FirebaseArticleViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mArticles, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }
    @Override
    public void stopListening() { super.stopListening(); mRef.removeEventListener(mChildEventListener); }

    @Override
    public void onItemDismiss(int position){
        mArticles.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Article article : mArticles) {
            int index = mArticles.indexOf(article);
            DatabaseReference ref = getRef(index);
            article.setIndex(Integer.toString(index));
            ref.setValue(article);
        }


    }

}


