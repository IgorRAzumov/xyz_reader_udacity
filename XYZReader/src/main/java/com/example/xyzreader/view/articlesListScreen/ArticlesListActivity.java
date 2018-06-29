package com.example.xyzreader.view.articlesListScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    IImageLoader<ImageView,Maybe<Bitmap>> imageLoader;

    private ArticlesListAdapter articlesListAdapter;

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
        initArticlesRecycler();
    }

    @Override
    public void onLoadCompeted() {
        progressBar.setVisibility(View.INVISIBLE);
        articlesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorLoadMessage() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startDetailScreen(int position) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(getString(R.string.selected_article_key), position);
        startActivity(intent);
    }

    private void initArticlesRecycler() {
        articlesListAdapter = new ArticlesListAdapter(presenter, imageLoader);
        articlesRecycler.setLayoutManager(new LinearLayoutManager(this));
        articlesRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        articlesRecycler.setAdapter(articlesListAdapter);
    }
}
