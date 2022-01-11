package com.moringaschool.newscout.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringaschool.newscout.models.Article;
import com.moringaschool.newscout.ui.NewsDetailFragment;

import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private List<Article> mArticles;

    public NewsPagerAdapter(@NonNull FragmentManager fm,int behaviour,List<Article> articles) {
        super(fm,behaviour);
        mArticles= articles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(mArticles.get(position
        ));
    }

    @Override
    public int getCount() {
      return mArticles.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mArticles.get(position).getTitle();
    }
}
