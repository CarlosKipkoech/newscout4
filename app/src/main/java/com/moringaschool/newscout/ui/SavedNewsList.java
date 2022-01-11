package com.moringaschool.newscout.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moringaschool.newscout.R;
import com.moringaschool.newscout.adapters.FirebaseArticleViewHolder;
import com.moringaschool.newscout.adapters.FirebasenewListAdapter;
import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.models.Constants;
import com.moringaschool.newscout.util.ItemTouchHelperAdapter;
import com.moringaschool.newscout.util.OnStartDragListener;
import com.moringaschool.newscout.util.SimpleItemTouchHelperCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedNewsList extends AppCompatActivity implements OnStartDragListener{

    private DatabaseReference mArticleReference;
    private FirebasenewListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;


    @BindView(R.id.newsItemRecyclerView)
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_news);
        ButterKnife.bind(this);



        setUpFirebaseAdapter();


    }


private void setUpFirebaseAdapter(){
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();


    Query query = FirebaseDatabase.getInstance()
            .getReference(Constants.FIREBASE_CHILD_ARTICLE)
            .child(uid)
            .orderByChild(Constants.FIREBASE_QUERY_INDEX);

    FirebaseRecyclerOptions<Article> options =
            new FirebaseRecyclerOptions.Builder<Article>()
                    .setQuery(query, Article.class)
                    .build();

    mFirebaseAdapter = new FirebasenewListAdapter(options,
         query, this, this);

    mArticleReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_ARTICLE).child(uid);


    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(mFirebaseAdapter);
    recyclerView.setHasFixedSize(true);
    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
    mItemTouchHelper = new ItemTouchHelper(callback);
    mItemTouchHelper.attachToRecyclerView(recyclerView);
}

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening(); }
}