package com.example.xyzreader.di;

import com.example.xyzreader.App;
import com.example.xyzreader.di.modules.AppModule;
import com.example.xyzreader.di.modules.ImageLoaderModule;
import com.example.xyzreader.di.modules.RepoModule;
import com.example.xyzreader.presenter.ArticleDetailActivityPresenter;
import com.example.xyzreader.presenter.ArticlesListActivityPresenter;
import com.example.xyzreader.view.articleDetailScreen.ArticleDetailActivity;
import com.example.xyzreader.view.articlesListScreen.ArticlesListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageLoaderModule.class})
public interface AppComponent {
    void inject(App application);

    void inject(ArticlesListActivity articlesListActivityActivity);

    void inject(ArticlesListActivityPresenter articlesListActivityPresenter);

    void inject(ArticleDetailActivityPresenter presenter);

    void inject(ArticleDetailActivity articleDetailActivity);
}
