package com.example.xyzreader.view.articleDetailScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IArticleDetailView extends MvpView {
    void init();

    void onLoadCompleted();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorLoadDataMessage();
}
