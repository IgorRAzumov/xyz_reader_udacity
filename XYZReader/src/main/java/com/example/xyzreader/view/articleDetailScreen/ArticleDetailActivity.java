package com.example.xyzreader.view.articleDetailScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.xyzreader.App;
import com.example.xyzreader.R;
import com.example.xyzreader.presenter.ArticleDetailActivityPresenter;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.ArticlesViewPagerAdapter;
import com.example.xyzreader.view.fragments.articleDetailFragment.ArticleDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ArticleDetailActivity extends MvpAppCompatActivity implements IArticleDetailView,
        ArticleDetailFragment.OnFragmentInteractionListener {
    @BindView(R.id.vp_act_article_detail_content)
    ViewPager articlesViewPager;
    @BindView(R.id.pb_act_article_detail_progress)
    ProgressBar progressBar;

    @InjectPresenter
    ArticleDetailActivityPresenter presenter;

    private int selectedPosition;
    private ArticlesViewPagerAdapter articleViewPagerAdapter;

    @ProvidePresenter
    public ArticleDetailActivityPresenter createPresenter() {
        ArticleDetailActivityPresenter presenter = new ArticleDetailActivityPresenter(
                AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_detail);
        ButterKnife.bind(this);
        App.getInstance().getAppComponent().inject(this);

        if (savedInstanceState == null) {
            Intent startIntent = getIntent();
            if (!startIntent.hasExtra(getString(R.string.selected_article_key))) {
                throw new RuntimeException(this.toString() + getString(R.string.error_start_intent_data));
            }
            selectedPosition = getIntent().getIntExtra(
                    getString(R.string.selected_article_key),
                    getResources().getInteger(R.integer.selected_article_default_value));
        }
    }


    @Override
    public void init() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompleted() {
        initViewPager();
        progressBar.setVisibility(View.GONE);
    }

    private void initViewPager() {
        articleViewPagerAdapter = new ArticlesViewPagerAdapter(getSupportFragmentManager(), presenter);
        articlesViewPager.setAdapter(articleViewPagerAdapter);
        articlesViewPager.addOnPageChangeListener(createViewPagerListener());
        articlesViewPager.setCurrentItem(selectedPosition);
    }

    @Override
    public void showErrorLoadDataMessage() {

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

    @Override
    public void shareArticle(String articleBody) {
        Intent shareArticleIntent = new Intent();
        shareArticleIntent.setAction(Intent.ACTION_SEND);
        shareArticleIntent.putExtra(Intent.EXTRA_TEXT, articleBody);
        shareArticleIntent.setType(getString(R.string.intent_type_text_plain));

        if (shareArticleIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareArticleIntent);
        }
    }
}
