package com.example.xyzreader.model.repo;

import com.example.xyzreader.NetworkStatus;
import com.example.xyzreader.model.api.IApiService;
import com.example.xyzreader.model.cache.ICache;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ArticlesRepo {
    private IApiService api;
    private ICache articlesCache;

    public ArticlesRepo(ICache cache, IApiService api) {
        this.api = api;
        articlesCache = cache;
    }


    public Single<List<Article>> getArticles() {
        if (!articlesCache.isEmpty()) {
            return articlesCache.getArticlesCache();
        } else if (NetworkStatus.isOnline()) {
            return api.getArticles()
                    .map(articles -> {
                        articlesCache.updateArticlesCache(articles)
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                        return articles;
                    });
        } else {
            return articlesCache.getArticles();
        }
    }
}
