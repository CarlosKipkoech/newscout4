package com.moringaschool.newscout.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ArticleListFragment extends Fragment {
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


    public ArticleListFragment (){

    }

    @Override
    public void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        NewsApi client = NewsClient.getClient();
        Call<NewsBusinessesSearchResponse> call = client.getArticles("sports");

        call.enqueue(new Callback<NewsBusinessesSearchResponse>() {

            @Override
            public void onResponse(Call<NewsBusinessesSearchResponse> call, Response<NewsBusinessesSearchResponse> response) {

                if (response.isSuccessful()) {
                    articles = response.body().getArticles();
                    NewsAdapter newsAdapter = new NewsAdapter(articles, getActivity()); //pass mNews and activity
                    recyclerView.setAdapter(newsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));   // set layout manager as the current context
                    recyclerView.setHasFixedSize(true);//declare fixed size depending on array size(enhance perfomance)

                }

            }

            @Override
            public void onFailure(Call<NewsBusinessesSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }


        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

}





