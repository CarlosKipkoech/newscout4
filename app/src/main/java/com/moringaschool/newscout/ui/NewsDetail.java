package com.moringaschool.newscout.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.moringaschool.newscout.R;
import com.moringaschool.newscout.adapters.NewsPagerAdapter;
import com.moringaschool.newscout.models.Article;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetail extends AppCompatActivity {
   @BindView(R.id.viewPager)
    ViewPager mViewPager;
   private NewsPagerAdapter adapterViewPager;
   List<Article>  mArticles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);



        ButterKnife.bind(this);
        mArticles = Parcels.unwrap(getIntent().getParcelableExtra("articles"));
        int startingPosition = getIntent().getIntExtra("position",0);

        adapterViewPager = new NewsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mArticles);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}