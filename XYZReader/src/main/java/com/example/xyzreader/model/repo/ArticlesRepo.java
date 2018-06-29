package com.example.xyzreader.model.repo;

import com.example.xyzreader.NetworkStatus;
import com.example.xyzreader.model.api.IApiService;
import com.example.xyzreader.model.cache.ICache;
import com.example.xyzreader.model.entity.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                        for (Article article : articles) {
                            String publisherDate = article.getPublishedDate();
                         //   article.setPublishedDate(formatDate(publisherDate));
                        }
                        articlesCache.updateArticlesCache(articles)
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                        return articles;
                    });
        } else {
            return articlesCache.getArticles();
        }
    }

  /*  private String formatDate(String publisherDate) {
        Date pubDate =


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format()



    }*/
}
