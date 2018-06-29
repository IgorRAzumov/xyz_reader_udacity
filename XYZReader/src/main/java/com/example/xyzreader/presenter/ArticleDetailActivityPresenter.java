package com.example.xyzreader.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.IArticlePageView;
import com.example.xyzreader.view.articleDetailScreen.IArticleDetailView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ArticleDetailActivityPresenter extends MvpPresenter<IArticleDetailView>
        implements IArticlePagePresenter {
    @Inject
    ArticlesRepo articlesRepo;

    private List<Article> articlesList;
    private Scheduler scheduler;


    public ArticleDetailActivityPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    @Override
    public void bindArticlePage(int pos, IArticlePageView pageView) {
        if (articlesList != null) {
            Article article = articlesList.get(pos);
            pageView.setData(article.getTitle(), article.getAuthor(), article.getPublishedDate(),
                    article.getBody(),article.getPhoto());
        }
    }

    @Override
    public int getArticlesCount() {
        return articlesList == null ? 0 : articlesList.size();
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        articlesRepo.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(articles -> {
                    articlesList = articles;
                    getViewState().onLoadCompleted();
                }, throwable -> {
                    getViewState().showErrorLoadDataMessage();
                });
    }


}
