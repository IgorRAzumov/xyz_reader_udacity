package com.example.xyzreader.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.xyzreader.view.fragments.articleDetailFragment.IArticleDetailFragment;

@InjectViewState
public class ArticleDetailFragmentPresenter extends MvpPresenter<IArticleDetailFragment> {
    private static final String COMMA_WITH_BACKSPACE = ", ";

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }


    public void completeCreateColors(int bodyTextColor, int titleTextColor, int rgb) {
        getViewState().onLoadCompleted(bodyTextColor, titleTextColor, rgb);
    }

    public void shareButtonClick(String author, String articleTitle, String preShareString) {
        getViewState().startShareAction(createArticleShareString(author, articleTitle, preShareString));
    }

    private String createArticleShareString(String author, String articleTitle, String preShareString) {
        return preShareString +
                author +
                COMMA_WITH_BACKSPACE +
                articleTitle;
    }
}
