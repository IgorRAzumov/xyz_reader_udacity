package com.example.xyzreader.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.xyzreader.view.fragments.articleDetailFragment.IArticleDetailFragmentView;

@InjectViewState
public class ArticleDetailFragmentPresenter extends MvpPresenter<IArticleDetailFragmentView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }


    public void completeCreateColors(int bodyTextColor, int titleTextColor, int rgb) {
        getViewState().showArticleInfo(bodyTextColor,titleTextColor,rgb);
    }


    public void shareButtonClick(){
        getViewState().startShareAction();
    }
}
