package com.example.xyzreader.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;
import com.example.xyzreader.view.adapters.articlesListAdapter.IArticleRowView;
import com.example.xyzreader.view.articlesListScreen.IArticlesListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ArticlesListActivityPresenter extends MvpPresenter<IArticlesListView>
        implements IArticlesListPresenter {
    @Inject
    ArticlesRepo articlesRepo;

    private List<Article> articlesList;
    private Scheduler scheduler;

    public ArticlesListActivityPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadArticles();
    }

    @Override
    public void bindArticleListRow(int pos, IArticleRowView rowView) {
        if (articlesList != null) {
            Article article = articlesList.get(pos);
            rowView.setArticleName(article.getTitle());
            rowView.setArticleAuthor(article.getAuthor());
            rowView.setImage(article.getThumb(), article.getAspectRatio());
        }
    }

    @Override
    public int getArticlesCount() {
        return articlesList == null ? 0 : articlesList.size();
    }

    @Override
    public void onArticleClick(int position) {
        getViewState().startDetailScreen(position);
    }

    public void retryLoad() {
        loadArticles();
    }

    @SuppressLint("CheckResult")
    private void loadArticles() {
        articlesRepo.getArticles()
                .observeOn(scheduler)
                .subscribeOn(Schedulers.io())
                .subscribe(articles -> {
                    articlesList = articles;
                    if (articles.size() == 0) {
                        getViewState().showEmptyDataMessage();
                    } else {
                        getViewState().onLoadCompeted();
                    }
                }, throwable -> {
                    getViewState().showErrorLoadMessage();
                });
    }
}
