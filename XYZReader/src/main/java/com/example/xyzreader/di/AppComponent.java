package com.example.xyzreader.di;

import com.example.xyzreader.di.modules.AppModule;
import com.example.xyzreader.di.modules.ImageLoaderModule;
import com.example.xyzreader.di.modules.RepoModule;
import com.example.xyzreader.presenter.ArticlesListActivityPresenter;
import com.example.xyzreader.view.ArticlesListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageLoaderModule.class})
public interface AppComponent {
    void inject(ArticlesListActivity articlesListActivity);

    void inject(ArticlesListActivityPresenter articlesListActivityPresenter);

}
