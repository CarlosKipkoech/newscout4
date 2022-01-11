package com.moringaschool.newscout.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.newscout.R;
import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.models.Constants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsDetailFragment extends Fragment implements  View.OnClickListener{

    @BindView(R.id.articleImageView)
    ImageView mArticleImageView;
    @BindView(R.id.NewsTitleTextView)
    TextView mNewsTitleTextView;
    @BindView(R.id.authorTextView) TextView mAuthorTextView;
    @BindView(R.id.ContentTextView) TextView mContentTextView;
    @BindView(R.id.descriptionTextView) TextView mDescriptionTextView;
    @BindView(R.id.saveNewsBtn)
    Button mSaveNewsBtn;

    private Article mArticle;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public static NewsDetailFragment newInstance(Article article) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("articles", Parcels.wrap(article));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = Parcels.unwrap(getArguments().getParcelable("articles"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news_detail,container,false);
        ButterKnife.bind(this,view);
        Picasso.get().load(mArticle.getUrlToImage()).into(mArticleImageView);
        mNewsTitleTextView.setText(mArticle.getTitle());
        mAuthorTextView.setText(mArticle.getAuthor());
        mDescriptionTextView.setText(mArticle.getDescription());
        mContentTextView.setText(mArticle.getContent());
        mSaveNewsBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
          if(v == mSaveNewsBtn){
              FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
              String uid = user.getUid();
              DatabaseReference ArticleRef = FirebaseDatabase.getInstance()
                      .getReference(Constants.FIREBASE_CHILD_ARTICLE).child(uid);

              DatabaseReference pushRef = ArticleRef.push();
              String pushId = pushRef.getKey();
              mArticle.setPushId(pushId);
              pushRef.setValue(mArticle);

              Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();

              Intent webIntent = new Intent(Intent.ACTION_VIEW,
                      Uri.parse(mArticle.getUrl()));
              startActivity(webIntent);
          }
    }
}