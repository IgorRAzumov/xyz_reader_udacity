package com.example.xyzreader;

import android.support.annotation.NonNull;

import com.example.xyzreader.di.TestComponent;
import com.example.xyzreader.di.modules.ApiModule;
import com.example.xyzreader.model.entity.Article;
import com.example.xyzreader.model.repo.ArticlesRepo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class ArticlesRepoInstrumentedTest {
    private static final Integer ARTICLE_ID = 1;
    private static final String ARTICLE_TITLE = "test title";
    private static final String ARTICLE_AUTHOR = "test author";
    private static final String ARTICLE_BODY = "test body";
    private static final String ARTICLE_THUMB = "test thumb";
    private static final String ARTICLE_PHOTO = "test photo";
    private static final double ARTICLE_ASPECT_RATIO = 1.5;
    private static final String ARTICLE_PUB_DATE = "12/17/2016T00:00:00:000";

    private static MockWebServer mockWebServer;

    @Inject
    ArticlesRepo articlesRepo;

    @BeforeClass
    public static void setupClass() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        mockWebServer.shutdown();
    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule() {
                    @Override
                    public String baseUrl() {
                        return mockWebServer.url("/").toString();
                    }
                }).build();

        component.inject(this);
    }

    @Test
    public void getArticles() {
        mockWebServer.enqueue(createArticlesResponse());
        TestObserver<List<Article>> articlesObserver = new TestObserver<>();
        articlesRepo.getArticles().subscribe(articlesObserver);

        articlesObserver.awaitTerminalEvent();
        articlesObserver.assertValueCount(1);

        Article article = articlesObserver.values().get(0).get(0);

        assertEquals(article.getId(), ARTICLE_ID);
        assertEquals(article.getAuthor(), ARTICLE_AUTHOR);
        assertEquals(article.getTitle(), ARTICLE_TITLE);
        assertEquals(article.getPublishedDate(), ARTICLE_PUB_DATE);
        assertEquals(article.getBody(), ARTICLE_BODY);
        assertEquals(article.getPhoto(), ARTICLE_PHOTO);
        assertEquals(article.getThumb(), ARTICLE_THUMB);
        assertThat(article.getAspectRatio(), equalTo(ARTICLE_ASPECT_RATIO));
    }

    @NonNull
    private MockResponse createArticlesResponse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[{");
        stringBuilder.append("\"id\":");
        stringBuilder.append(ARTICLE_ID);
        stringBuilder.append(",\"title\":\"");
        stringBuilder.append(ARTICLE_TITLE);
        stringBuilder.append("\",\"author\":\"");
        stringBuilder.append(ARTICLE_AUTHOR);
        stringBuilder.append("\",\"body\":\"");
        stringBuilder.append(ARTICLE_BODY);
        stringBuilder.append("\",\"thumb\":\"");
        stringBuilder.append(ARTICLE_THUMB);
        stringBuilder.append("\",\"photo\":\"");
        stringBuilder.append(ARTICLE_PHOTO);
        stringBuilder.append("\",\"aspect_ratio\":");
        stringBuilder.append(ARTICLE_ASPECT_RATIO);
        stringBuilder.append(",\"published_date\":\"");
        stringBuilder.append(ARTICLE_PUB_DATE);
        stringBuilder.append("\"}]");

        return new MockResponse().setBody(stringBuilder.toString());
    }
}
