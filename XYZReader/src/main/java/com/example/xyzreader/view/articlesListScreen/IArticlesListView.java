package com.example.xyzreader.view.articlesListScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IArticlesListView extends MvpView {
    void init();

    void onLoadCompeted();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorLoadMessage();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startDetailScreen(int position);
}
