package com.example.xyzreader.model.repo;

import com.example.xyzreader.model.api.IApiService;
import com.example.xyzreader.model.cache.ICache;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ArticleRepo {
    private IApiService api;
    private ICache articlesCache;

    public ArticleRepo(ICache cache, IApiService api) {
        this.api = api;
        articlesCache = cache;
    }


    public Single<List<Article>> getArticles() {
        return api.getArticles()
                .subscribeOn(Schedulers.io())
                .map(articles -> {
                    articlesCache.updateArticlesCache(articles);
                    return articles;
                });

    }
}
