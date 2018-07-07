package com.example.xyzreader.model.api;

import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;



public interface IApiService {
    @GET("xyz-reader-json")
    Single<List<Article>> getArticles();
}
