package com.example.xyzreader.view.articlesListScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IArticlesListView extends MvpView {
    void init();

    void onLoadCompeted();

    @StateStrategyType(SkipStrategy.class)
    void showErrorLoadMessage();

    @StateStrategyType(SkipStrategy.class)
    void startDetailScreen(int position);

    @StateStrategyType(SkipStrategy.class)
    void showEmptyDataMessage();
}
