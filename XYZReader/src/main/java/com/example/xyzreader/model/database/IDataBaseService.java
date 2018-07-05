package com.example.xyzreader.model.database;

import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IDataBaseService {
    Completable updateArticlesCache(List<Article> articles);

    Single<List<Article>> getArticles();
}
