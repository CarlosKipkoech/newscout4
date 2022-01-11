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

public class Business extends AppCompatActivity {

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
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);


        NewsApi client = NewsClient.getClient();
        Call<NewsBusinessesSearchResponse> call = client.getArticles("business");

        call.enqueue(new Callback<NewsBusinessesSearchResponse>() {

            @Override
            public void onResponse(Call<NewsBusinessesSearchResponse> call, Response<NewsBusinessesSearchResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    articles = response.body().getArticles();
                    NewsAdapter newsAdapter = new NewsAdapter(articles, Business.this); //pass mNews and activity
                    recyclerView.setAdapter(newsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Business.this));   // set layout manager as the current context
                    recyclerView.setHasFixedSize(true);//declare fixed size depending on array size(enhance perfomance)


                    ShowArticles();


                } else {
                    showUnsuccessfullMessage();
                }

            }

            @Override
            public void onFailure(Call<NewsBusinessesSearchResponse> call, Throwable t) {

                hideProgressBar();
                showFailureMessage();

            }
        });
        ImageViewNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business.this, MyNotes.class);
                startActivity(intent);
            }
        });


    }

    private void   showFailureMessage(){
        ErrorTextView.setText("Something went wrong,Check your internet connection");
        ErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfullMessage(){
        ErrorTextView.setText("Something went wrong,Try Again Later");
        ErrorTextView.setVisibility(View.VISIBLE);
    }

    private void  ShowArticles(){
        recyclerView.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}