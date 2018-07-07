package com.example.xyzreader;

import com.example.xyzreader.di.TestComponent;
import com.example.xyzreader.di.modules.TestRepoModule;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;
import com.example.xyzreader.presenter.ArticleDetailActivityPresenter;
import com.example.xyzreader.view.articleDetailScreen.IArticleDetailView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

public class ArticleDetailActivityPresenterUnitTest {
    private static final Integer ARTICLE_ID = 1;
    private static final String ARTICLE_TITLE = "test title";
    private static final String ARTICLE_AUTHOR = "test author";
    private static final String ARTICLE_BODY = "test body";
    private static final String ARTICLE_THUMB = "test thumb";
    private static final String ARTICLE_PHOTO = "test photo";
    private static final double ARTICLE_ASPECT_RATIO = 1.5;
    private static final String ARTICLE_PUB_DATE = "12/17/2016T00:00:00:000";
    private static final long SCHEDULER_SEC_DELAY = 1;
    private static final String TEST_ERROR = "test_error";
    private static final String ARTICLE_ERROR = "error";

    @Mock
    IArticleDetailView articleDetailView;

    private ArticleDetailActivityPresenter presenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        testScheduler = new TestScheduler();
        presenter = Mockito.spy(new ArticleDetailActivityPresenter(testScheduler));
    }

    @Test
    public void onFirstViewAttach() {
        injectArticlesList(provideTestArticlesList());
        presenter.attachView(articleDetailView);
        Mockito.verify(articleDetailView).init();
    }

    @Test
    public void loadArticlesComplete() {
        injectArticlesList(provideTestArticlesList());
        presenter.attachView(articleDetailView);
        testScheduler.advanceTimeBy(SCHEDULER_SEC_DELAY, TimeUnit.SECONDS);
        Mockito.verify(articleDetailView).onLoadCompleted();
    }

    @Test
    public void loadArticlesEmptyData() {
        injectArticlesList(provideEmptyTestArticlesList());
        presenter.attachView(articleDetailView);
        testScheduler.advanceTimeBy(SCHEDULER_SEC_DELAY, TimeUnit.SECONDS);
        Mockito.verify(articleDetailView).showEmptyDataMessage();
    }

    @Test
    public void loadArticlesError() {
        injectErrorWhenLoadArticles(new RuntimeException(ARTICLE_ERROR));
        presenter.attachView(articleDetailView);
        testScheduler.advanceTimeBy(SCHEDULER_SEC_DELAY, TimeUnit.SECONDS);
        Mockito.verify(articleDetailView).showErrorLoadDataMessage();
    }

    private void injectArticlesList(List<Article> articles) {
        TestComponent component = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ArticlesRepo articlesRepo() {
                        ArticlesRepo repo = super.articlesRepo();
                        Mockito.when(repo.getArticles())
                                .thenReturn(Single.just(articles));
                        return repo;
                    }
                }).build();

        component.inject(presenter);
    }


    private void injectErrorWhenLoadArticles(RuntimeException exception) {
        TestComponent component = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ArticlesRepo articlesRepo() {
                        ArticlesRepo repo = super.articlesRepo();
                        Mockito.when(repo.getArticles())
                                .thenReturn(Single.error(new RuntimeException(TEST_ERROR)));
                        return repo;
                    }
                }).build();

        component.inject(presenter);
    }

    private List<Article> provideTestArticlesList() {
        List<Article> articles = new ArrayList<>();
        Article article = new Article(ARTICLE_ID, ARTICLE_TITLE, ARTICLE_AUTHOR,
                ARTICLE_BODY, ARTICLE_THUMB, ARTICLE_PHOTO, ARTICLE_ASPECT_RATIO, ARTICLE_PUB_DATE);
        articles.add(article);
        return articles;
    }

    private List<Article> provideEmptyTestArticlesList() {
        return new ArrayList<>();
    }
}
