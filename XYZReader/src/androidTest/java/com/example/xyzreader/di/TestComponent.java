package com.example.xyzreader.di;


import com.example.xyzreader.ArticlesRepoInstrumentedTest;
import com.example.xyzreader.di.modules.RepoModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {RepoModule.class})
public interface TestComponent {
    void inject(ArticlesRepoInstrumentedTest articlesRepoInstrumentedTest);
}
