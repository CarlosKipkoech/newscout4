package com.moringaschool.newscout.network;

import com.moringaschool.newscout.models.NewsBusinessesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
   @GET("everything")
   Call<NewsBusinessesSearchResponse> getArticles(
           @Query("q") String keyword
   );
}
