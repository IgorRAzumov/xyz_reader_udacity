package com.example.xyzreader.presenter;

import com.example.xyzreader.view.adapters.articlesListAdapter.IArticleRowView;

public interface IArticlesListPresenter {

    void bindArticleListRow(int position, IArticleRowView rowView);

    int getArticlesCount();

    void onArticleClick(int position);
}
