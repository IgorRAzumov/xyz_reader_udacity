package com.example.xyzreader.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.xyzreader.App;
import com.example.xyzreader.R;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.image.IImageLoader;
import com.example.xyzreader.presenter.ArticlesListActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ArticlesListActivity extends MvpAppCompatActivity
        implements IArticlesListActivityView {
    @BindView(R.id.rv_act_articles_list_articles)
    RecyclerView articlesRecycler;

    @InjectPresenter
    ArticlesListActivityPresenter presenter;

    @Inject
    IImageLoader<ImageView> imageLoader;

    private ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);
        ButterKnife.bind(this);

        articlesRecycler.setLayoutManager(new LinearLayoutManager(this));
        articlesRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        App.getInstance().getAppComponent().inject(this);
    }

    @ProvidePresenter
    public ArticlesListActivityPresenter createPresenter() {
        ArticlesListActivityPresenter presenter = new ArticlesListActivityPresenter(
                AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void init() {
        articlesAdapter = new ArticlesAdapter(presenter, imageLoader);
        articlesRecycler.setAdapter(articlesAdapter);
    }

    @Override
    public void loadCompeted() {
        articlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorLoadMessage() {

    }


}
