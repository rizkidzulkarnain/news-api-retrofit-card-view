package com.tokopedia.test.api;

import com.tokopedia.test.generalclass.config;
import com.tokopedia.test.model.Article;
import com.tokopedia.test.model.ArticleResponse;
import com.tokopedia.test.model.News;
import com.tokopedia.test.model.SourceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsClient {
    @GET("/v2/everything")
    Call<News> getArticles(
        @Query("q") String q,
        @Query("from") String from,
        @Query("to") String to,
        @Query("sortBy") String sortBy,
        @Query("apiKey") String apiKey
//        @Query("language") String language
    );

    @GET("articles")
    Call<ArticleResponse> getArticles2(
            @Query("apiKey") String apiKey,
            @Query("source") String sources,
            @Query("sortBy") String sortBy) ; //top, latest, popular

    @GET("sources")
    Call<SourceResponse> getSource(
            @Query("language") String countryCode);
}
