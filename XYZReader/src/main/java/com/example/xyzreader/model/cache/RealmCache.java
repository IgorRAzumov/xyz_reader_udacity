package com.example.xyzreader.model.cache;

import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Single;

public class RealmCache implements ICache {
    @Override
    public void updateArticlesCache(List<Article> articles) {

    }

    @Override
    public Single<List<Article>> getCache() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
