package com.example.xyzreader.view.articlesListScreen;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.xyzreader.App;
import com.example.xyzreader.R;
import com.example.xyzreader.model.image.IImageLoader;
import com.example.xyzreader.presenter.ArticlesListActivityPresenter;
import com.example.xyzreader.view.adapters.articlesListAdapter.ArticlesListAdapter;
import com.example.xyzreader.view.articleDetailScreen.ArticleDetailActivity;
import com.example.xyzreader.view.widgets.SpacingItemDecorator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ArticlesListActivity extends MvpAppCompatActivity
        implements IArticlesListView {
    @BindView(R.id.rv_act_articles_list_articles)
    RecyclerView articlesRecycler;
    @BindView(R.id.pb_act_articles_list_progress)
    ProgressBar progressBar;

    @InjectPresenter
    ArticlesListActivityPresenter presenter;

    @Inject
    IImageLoader<ImageView, Maybe<Bitmap>> imageLoader;

    @ProvidePresenter
    public ArticlesListActivityPresenter createPresenter() {
        ArticlesListActivityPresenter presenter = new ArticlesListActivityPresenter(
                AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        App.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void init() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadCompeted() {
        progressBar.setVisibility(View.INVISIBLE);
        initArticlesRecycler();
    }

    @Override
    public void showErrorLoadMessage() {
        progressBar.setVisibility(View.INVISIBLE);
        showMessage(R.string.error_load_articles);
    }

    @Override
    public void showEmptyDataMessage() {
        progressBar.setVisibility(View.INVISIBLE);
        showMessage(R.string.empty_load_articles_result);
    }


    @Override
    public void startDetailScreen(int position) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(getString(R.string.selected_article_key), position);
        startActivity(intent);
    }

    private void showMessage(int empty_load_articles_result) {
        Snackbar
                .make(articlesRecycler, getString(empty_load_articles_result), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry_load), view -> presenter.retryLoad())
                .show();
    }

    private void initArticlesRecycler() {
        Resources resources = getResources();
        int spanCount = (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                ? resources.getInteger(R.integer.articles_list_span_portrait) :
                resources.getInteger(R.integer.articles_list_span_portrait);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(spanCount,
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        resources.getDimension(R.dimen.fr_article_detail_cont_horiz_margin),
                        resources.getDisplayMetrics())),
                true);

        ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(presenter, imageLoader);
        articlesRecycler.setLayoutManager(new StaggeredGridLayoutManager(spanCount,
                StaggeredGridLayoutManager.VERTICAL));
        articlesRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        articlesRecycler.addItemDecoration(itemDecorator);
        articlesRecycler.setAdapter(articlesListAdapter);
    }
}
