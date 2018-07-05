package com.example.xyzreader.model.repo;

import com.example.xyzreader.NetworkStatus;
import com.example.xyzreader.model.api.IApiService;
import com.example.xyzreader.model.database.IDataBaseService;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ArticlesRepo {
    private IApiService api;
    private IDataBaseService dataBaseService;
    private final List<Article> articlesList;

    public ArticlesRepo(IDataBaseService dataBase, IApiService api, List<Article> articles) {
        articlesList = articles;
        this.api = api;
        dataBaseService = dataBase;
    }


    public Single<List<Article>> getArticles() {
        if (!articlesList.isEmpty()) {
            return Single.just(articlesList);
        } else if (NetworkStatus.isOnline()) {
            return api
                    .getArticles()
                    .map(articles -> {
                        dataBaseService
                                .updateArticlesCache(articles)
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                        return updateArticlesList(articles);
                    });
        } else {
            return dataBaseService
                    .getArticles()
                    .map(this::updateArticlesList);
        }
    }

    private List<Article> updateArticlesList(List<Article> articles) {
        if (!articles.isEmpty()) {
            articlesList.clear();
            articlesList.addAll(articles);
        }
        return articles;
    }
}
