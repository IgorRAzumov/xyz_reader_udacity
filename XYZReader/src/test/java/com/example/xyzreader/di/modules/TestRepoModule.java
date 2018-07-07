package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.repo.ArticlesRepo;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestRepoModule {
    @Provides
    public ArticlesRepo articlesRepo() {
        return Mockito.mock(ArticlesRepo.class);
    }
}
