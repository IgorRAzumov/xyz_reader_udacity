package com.example.xyzreader.view.articleDetailScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.xyzreader.App;
import com.example.xyzreader.R;
import com.example.xyzreader.presenter.ArticleDetailActivityPresenter;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.ArticlesViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ArticleDetailActivity extends MvpAppCompatActivity implements IArticleDetailView {
    @BindView(R.id.vp_act_article_detail_content)
    ViewPager articlesViewPager;

    @InjectPresenter
    ArticleDetailActivityPresenter presenter;

    private ArticlesViewPagerAdapter articleViewPagerAdapter;

    @ProvidePresenter
    public ArticleDetailActivityPresenter createPresenter() {
        Intent startIntent = getIntent();
        if (startIntent.hasExtra(getString(R.string.selected_article_key))) {
            throw new RuntimeException(this.toString() + getString(R.string.error_start_intent_data));
        }

        ArticleDetailActivityPresenter presenter = new ArticleDetailActivityPresenter(
                AndroidSchedulers.mainThread(), startIntent.getIntExtra(
                getString(R.string.selected_article_key),
                getResources().getInteger(R.integer.selected_article_default_value)));
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_detail);
        ButterKnife.bind(this);
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void init() {
        articleViewPagerAdapter = new ArticlesViewPagerAdapter(getSupportFragmentManager());
        articlesViewPager.setAdapter(articleViewPagerAdapter);
        articlesViewPager.addOnPageChangeListener(createViewPagerListener());
    }

    @NonNull
    private ViewPager.OnPageChangeListener createViewPagerListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }
}
