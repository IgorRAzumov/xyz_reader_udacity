package com.example.xyzreader.presenter;

import com.example.xyzreader.view.IArticleRowView;
import com.example.xyzreader.view.IArticlesListActivityView;

public interface IArticlesListPresenter {

    void bindArticleListRow(int pos, IArticleRowView rowView);

    int getArticlesCount();
}
