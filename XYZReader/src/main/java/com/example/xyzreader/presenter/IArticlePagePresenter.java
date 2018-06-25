package com.example.xyzreader.presenter;

import com.example.xyzreader.view.adapters.articlesPagerAdapter.IArticlePageView;

public interface IArticlePagePresenter {
   void bindArticlePage(int pos, IArticlePageView pageView);

    int getArticlesCount();
}
