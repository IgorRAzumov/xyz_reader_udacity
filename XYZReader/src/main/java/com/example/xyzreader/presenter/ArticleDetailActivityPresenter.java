package com.example.xyzreader.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.IArticlePageView;
import com.example.xyzreader.view.articleDetailScreen.IArticleDetailView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ArticleDetailActivityPresenter extends MvpPresenter<IArticleDetailView>
        implements IArticlePagePresenter {
    private static final int END_OLD_DATA_GRIG_CALENDAR_YEAR = 2;
    private static final int END_OLD_DATA_GRIG_CALENDAR_MONTH = 1;
    private static final int END_OLD_DATA_GRIG_CALENDAR_DAY = 1;
    private static final String INBOX_DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.sss";
    private static final String OUTBOX_DATA_FORMAT = "yyyy-MM-dd";
    private static final String OLD_OUTBOX_DATA_FORMAT = "yyyy";
    private static final String ARTICLE_BODY_REGEX = "(\r\n|\n)";
    private static final String ARTICLE_BODY_REPLACEMENT = "<br />";


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
        getViewState().init();
        loadData();
    }

    @Override
    public void bindArticlePage(int pos, IArticlePageView pageView) {
        if (articlesList != null) {
            Article article = articlesList.get(pos);
            pageView.setData(article.getTitle(), article.getAuthor(), article.getPublishedDate(),
                    article.getBody(), article.getPhoto());
        }
    }

    @Override
    public int getArticlesCount() {
        return articlesList == null ? 0 : articlesList.size();
    }

    @SuppressLint("CheckResult")
    public void loadData() {
        articlesRepo.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .map(articles -> {
                    for (Article article : articles) {
                        article.setPublishedDate(formatDateString(article.getPublishedDate()));
                        article.setBody(formatBodyString(article.getBody()));
                    }
                    return articles;
                })
                .subscribe(articles -> {
                    articlesList = articles;
                    if (articles.size() == 0) {
                        getViewState().showEmptyDataMessage();
                    } else {
                        getViewState().onLoadCompleted();
                    }
                }, throwable -> getViewState().showErrorLoadDataMessage());
    }

    private String formatBodyString(String body) {
        return body.replaceAll(ARTICLE_BODY_REGEX, ARTICLE_BODY_REPLACEMENT);
    }

    private String formatDateString(String date) {
        Locale defaultLocale = Locale.getDefault();
        SimpleDateFormat dateFormat = new SimpleDateFormat(INBOX_DATA_FORMAT, defaultLocale);
        SimpleDateFormat outputFormat = new SimpleDateFormat(OUTBOX_DATA_FORMAT, defaultLocale);
        SimpleDateFormat oldOutputFormat = new SimpleDateFormat(OLD_OUTBOX_DATA_FORMAT, defaultLocale);
        Date parseDate;
        String returnedDate;
        GregorianCalendar endOldData = new GregorianCalendar(END_OLD_DATA_GRIG_CALENDAR_YEAR,
                END_OLD_DATA_GRIG_CALENDAR_MONTH, END_OLD_DATA_GRIG_CALENDAR_DAY);
        try {
            parseDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            parseDate = new Date();
        }
        if (!parseDate.before(endOldData.getTime())) {
            returnedDate = outputFormat.format(parseDate);

        } else {
            returnedDate = oldOutputFormat.format(parseDate);
        }
        return returnedDate;
    }
}
