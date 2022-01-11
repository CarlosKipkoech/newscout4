package com.moringaschool.newscout.models;

import com.moringaschool.newscout.BuildConfig;

import org.parceler.Parcel;

@Parcel
public class Constants {
    public static final String NEWS_BASE_URL = "https://newsapi.org/v2/";
    public static final String NEWS_API_KEY = BuildConfig.NEWS_API_KEY;
    public static final String FIREBASE_CHILD_ARTICLE = "Article";
    public static final String FIREBASE_QUERY_INDEX = "index";
}