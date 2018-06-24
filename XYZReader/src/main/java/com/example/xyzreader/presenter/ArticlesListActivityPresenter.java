package com.example.xyzreader.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticleRepo;
import com.example.xyzreader.view.IArticleRowView;
import com.example.xyzreader.view.IArticlesListActivityView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
@InjectViewState
public class ArticlesListActivityPresenter extends MvpPresenter<IArticlesListActivityView>
        implements IArticlesListPresenter {
    @Inject
    ArticleRepo articleRepo;

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

    @SuppressLint("CheckResult")
    private void loadArticles() {
        articleRepo.getArticles()
                .observeOn(scheduler)
                .subscribe(articles -> {
                    articlesList = articles;
                    getViewState().loadCompeted();
                }, throwable -> {
                    getViewState().showErrorLoadMessage();
                });
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
}
