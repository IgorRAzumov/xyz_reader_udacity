package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.api.IApiService;
import com.example.xyzreader.model.cache.ICache;
import com.example.xyzreader.model.repo.ArticleRepo;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule {
    @Provides
    public ArticleRepo articleRepo(ICache cache, IApiService apiService) {
        return new ArticleRepo(cache, apiService);
    }
}
