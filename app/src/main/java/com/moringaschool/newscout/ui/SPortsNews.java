package com.moringaschool.newscout.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.newscout.R;
import com.moringaschool.newscout.adapters.NewsAdapter;
import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.models.NewsBusinessesSearchResponse;
import com.moringaschool.newscout.network.NewsApi;
import com.moringaschool.newscout.network.NewsClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SPortsNews extends AppCompatActivity {

    @BindView(R.id.newsItemRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imageViewNotesBtn)
    ImageView ImageViewNotesBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ErrortextView)
    TextView ErrorTextView;
    public List<Article> articles;
    //private  NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news);


    }
}