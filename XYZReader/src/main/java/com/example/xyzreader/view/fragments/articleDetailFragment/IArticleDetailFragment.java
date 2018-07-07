package com.example.xyzreader.view.fragments.articleDetailFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IArticleDetailFragment extends MvpView {
    void init();

    void onLoadCompleted(int bodyTextColor, int titleTextColor, int rgb);

    @StateStrategyType(SkipStrategy.class)
    void startShareAction(String articleInfo);
}
