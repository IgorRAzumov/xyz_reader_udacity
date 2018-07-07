package com.example.xyzreader.di;

import com.example.xyzreader.di.modules.TestRepoModule;
import com.example.xyzreader.presenter.ArticleDetailActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = {TestRepoModule.class})
public interface TestComponent {
    void inject(ArticleDetailActivityPresenter presenter);
}
