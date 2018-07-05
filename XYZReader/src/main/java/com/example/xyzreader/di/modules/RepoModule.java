package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.api.IApiService;
import com.example.xyzreader.model.database.IDataBaseService;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class, DataBaseModule.class})
public class RepoModule {
    @Singleton
    @Provides
    public ArticlesRepo articleRepo(IDataBaseService cache, IApiService apiService,
                                    @Named("concurrent_list")  List<Article> articleList) {
        return new ArticlesRepo(cache, apiService, articleList);
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
