package com.example.xyzreader.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.xyzreader.model.entity.Article;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IArticlesListActivityView extends MvpView {
    void loadCompeted();

    void showErrorLoadMessage();

    void init();


}
