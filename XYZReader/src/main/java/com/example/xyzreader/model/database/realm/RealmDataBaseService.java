package com.example.xyzreader.model.database.realm;

import com.example.xyzreader.model.database.IDataBaseService;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDataBaseService implements IDataBaseService {

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
            emitter.onSuccess(realmArticles);
        });
    }

}
