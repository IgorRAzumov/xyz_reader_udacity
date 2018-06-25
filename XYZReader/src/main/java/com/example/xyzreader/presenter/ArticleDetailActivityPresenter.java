package com.example.xyzreader.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.IArticlePageView;
import com.example.xyzreader.view.articleDetailScreen.IArticleDetailView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;

@InjectViewState
public class ArticleDetailActivityPresenter extends MvpPresenter<IArticleDetailView>
        implements IArticlePagePresenter {
    @Inject
    ArticlesRepo articlesRepo;

    private int selectedArticleId;
    private List<Article> articlesList;
    private Scheduler scheduler;


    public ArticleDetailActivityPresenter(Scheduler scheduler, int selectedArticleId) {
        this.scheduler = scheduler;
        this.selectedArticleId = selectedArticleId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    @Override
    public void bindArticlePage(int pos, IArticlePageView pageView) {
        if (articlesList != null) {
            Article article = articlesList.get(pos);
            pageView.setTitle(article.getTitle());
            pageView.setAuthor(article.getAuthor());
            pageView.setBody(article.getBody());
            pageView.setpublisherDate(article.getPublishedDate());
        }
    }

    @Override
    public int getArticlesCount() {
        return articlesList == null ? 0 : articlesList.size();
    }



    
}
