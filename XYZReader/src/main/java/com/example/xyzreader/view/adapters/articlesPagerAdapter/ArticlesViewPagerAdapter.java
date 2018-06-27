package com.example.xyzreader.view.adapters.articlesPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xyzreader.presenter.IArticlePagePresenter;
import com.example.xyzreader.view.fragments.articleDetailFragment.ArticleDetailFragment;


public class ArticlesViewPagerAdapter extends FragmentStatePagerAdapter {
    private IArticlePagePresenter presenter;

    public ArticlesViewPagerAdapter(FragmentManager fm, IArticlePagePresenter presenter) {
        super(fm);
        this.presenter = presenter;
    }

    @Override
    public Fragment getItem(int position) {
        ArticleDetailFragment fragment = ArticleDetailFragment.newInstance();
        presenter.bindArticlePage(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return presenter.getArticlesCount();
    }
}
