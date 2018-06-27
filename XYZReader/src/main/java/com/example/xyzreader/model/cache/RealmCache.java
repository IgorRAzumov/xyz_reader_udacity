package com.example.xyzreader.model.cache;

import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmCache implements ICache {
    private final List<Article> cashArticles;

    public RealmCache(List<Article> articles) {
        cashArticles = articles;
    }

    @Override
    public Single<List<Article>> getArticlesCache() {
        return Single.just(cashArticles);
    }

    @Override
    public boolean isEmpty() {
        return cashArticles == null || cashArticles.isEmpty();
    }

    @Override
    public Completable updateArticlesCache(List<Article> articles) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(innerRealm -> {
                RealmResults<Article> realmResults = realm
                        .where(Article.class)
                        .findAll();
                if (!realmResults.isEmpty()) {
                    realmResults.deleteAllFromRealm();
                }
                realm.insertOrUpdate(articles);
            });
            realm.close();
            updateInnerCash(articles);
        });

    }

    @Override
    public Single<List<Article>> getArticles() {
        return Single.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            List<Article> realmArticles = realm.copyFromRealm(
                    realm
                            .where(Article.class)
                            .findAll());
            if (!realmArticles.isEmpty()) {
                updateInnerCash(realmArticles);
            }
            emitter.onSuccess(realmArticles);
        });
    }

    private void updateInnerCash(List<Article> articles) {
        synchronized (cashArticles) {
            cashArticles.clear();
            cashArticles.addAll(articles);
        }
    }
}
