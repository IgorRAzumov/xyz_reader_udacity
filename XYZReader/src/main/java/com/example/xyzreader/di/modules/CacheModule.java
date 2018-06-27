package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.cache.ICache;
import com.example.xyzreader.model.cache.RealmCache;
import com.example.xyzreader.model.entity.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Singleton
    @Provides
    public ICache realmCache(@Named("concurrent_list") List<Article> articles) {
        return new RealmCache(articles);
    }

    @Named("concurrent_list")
    @Provides
    public List<Article> concurrentList(@Named("list") List<Article> articles) {
        return Collections.synchronizedList(articles);
    }

    @Named(value = "list")
    @Provides
    public List<Article> list() {
        return new ArrayList<>();
    }
}
