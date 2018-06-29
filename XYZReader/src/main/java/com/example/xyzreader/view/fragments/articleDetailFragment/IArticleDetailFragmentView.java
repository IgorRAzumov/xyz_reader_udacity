package com.example.xyzreader.view.fragments.articleDetailFragment;

import com.arellomobile.mvp.MvpView;

public interface IArticleDetailFragmentView extends MvpView {
    void init();

    void showArticleInfo(int bodyTextColor, int titleTextColor, int rgb);

    void startShareAction();
}
