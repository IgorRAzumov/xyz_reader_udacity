package com.example.xyzreader.model.cache;

import com.example.xyzreader.model.entity.Article;

import java.util.List;


import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface ICache {
    Single<List<Article>> getArticlesCache();

    Completable updateArticlesCache(List<Article> articles);

    Single<List<Article>> getArticles();

    boolean isEmpty();
}
