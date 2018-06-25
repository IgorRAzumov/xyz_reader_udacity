package com.example.xyzreader.model.cache;

import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Single;

public interface ICache {
    void updateArticlesCache(List<Article> articles);

    Single<List<Article>> getCache();

    boolean isEmpty();
}
