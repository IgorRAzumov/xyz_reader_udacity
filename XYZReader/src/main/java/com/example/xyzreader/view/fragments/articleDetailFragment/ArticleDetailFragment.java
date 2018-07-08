package com.example.xyzreader.view.fragments.articleDetailFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.xyzreader.App;
import com.example.xyzreader.R;
import com.example.xyzreader.model.image.IImageLoader;
import com.example.xyzreader.presenter.ArticleDetailFragmentPresenter;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.IArticlePageView;
import com.example.xyzreader.view.articleDetailScreen.ArticleDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ArticleDetailFragment extends MvpAppCompatFragment implements IArticlePageView,
        IArticleDetailFragment {
    private static final String TAG = ArticleDetailFragment.class.getSimpleName();
    private static final String ARTICLE_TITLE_KEY = "article-title-key";
    private static final String ARTICLE_AUTHOR_KEY = "article-author-key";
    private static final String ARTICLE_PUBLISHER_DATE_KEY = "article-publisher-date-key";
    private static final String ARTICLE_BODY_KEY = "article-body-key";
    private static final String ARTICLE_IMAGE_URL = "article-image-url-key";

    @BindView(R.id.tv_fr_article_detail_cont_title)
    TextView titleText;
    @BindView(R.id.tv_fr_article_detail_cont_author)
    TextView authorText;
    @BindView(R.id.tv_fr_article_detail_cont_publish_date)
    TextView publisherDateText;
    @BindView(R.id.tv_fr_article_detail_cont_body)
    TextView bodyText;
    @BindView(R.id.iv_fr_article_detail_ctl_image)
    ImageView articleImage;
    @BindView(R.id.pb_fr_article_detail_progress)
    ProgressBar progressBar;
    @BindView(R.id.lly_fr_article_detail_cont_meta_bar)
    LinearLayout metaBarLinear;
    @BindView(R.id.fab_fr_article_detail_share_article)
    FloatingActionButton shareButton;
    @BindView(R.id.ctl_fr_article_detail_collapsing_tb_lly)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tb_fr_article_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.abl_fr_article_detail_app_bar)
    AppBarLayout appBarLayout;

    @Inject
    IImageLoader<ImageView, Maybe<Bitmap>> imageLoader;

    @InjectPresenter
    ArticleDetailFragmentPresenter presenter;

    private OnFragmentInteractionListener onFragmentInteractionListener;
    private Unbinder unbinder;

    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance() {
        return new ArticleDetailFragment();
    }

    @Override
    public void setData(String title, String author, String publishedDate, String body,
                        String imageUrl) {
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putString(ARTICLE_TITLE_KEY, title);
        args.putString(ARTICLE_AUTHOR_KEY, author);
        args.putString(ARTICLE_PUBLISHER_DATE_KEY, publishedDate);
        args.putString(ARTICLE_BODY_KEY, body);
        args.putString(ARTICLE_IMAGE_URL, imageUrl);
        setArguments(args);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_activity_implement_fragment_callback));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void init() {
        showLoading(View.VISIBLE);
        loadArticleImage();
    }

    private void showLoading(int visible) {
        progressBar.setVisibility(visible);
    }

    @Override
    public void onLoadCompleted(int bodyTextColor, int titleTextColor, int rgb) {
        showLoading(View.GONE);
        initToolbar();
        initAppBarLayout();
        initShareButton();
        setColors(bodyTextColor, titleTextColor, rgb);
        showArticleInfo();
    }

    @Override
    public void startShareAction(String articleInfo) {
        onFragmentInteractionListener.shareArticle(articleInfo);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("CheckResult")
    private void loadArticleImage() {
        final int noColor = getResources().getInteger(R.integer.fr_article_detail_no_detected_color);
        imageLoader.loadIntoWithResult(getArgumentsBundle().getString(ARTICLE_IMAGE_URL), articleImage)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((Bitmap bitmap) -> Palette.from(bitmap).generate(palette -> {
                    Palette.Swatch swatch = palette.getDarkMutedSwatch();
                    if (swatch != null) {
                        presenter.completeCreateColors(swatch.getBodyTextColor(),
                                swatch.getTitleTextColor(), swatch.getRgb());
                    } else {
                        presenter.completeCreateColors(noColor, noColor, noColor);
                    }

                }), throwable -> presenter.completeCreateColors(noColor, noColor, noColor), () -> presenter.completeCreateColors(noColor, noColor, noColor));
    }

    private void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(getString(R.string.empty_string));
            }
        }
    }

    private void initAppBarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getArgumentsBundle().getString(ARTICLE_TITLE_KEY));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(getString(R.string.empty_string));
                    isShow = false;
                }
            }
        });
    }

    private void initShareButton() {
        shareButton.setVisibility(View.VISIBLE);
        shareButton.setOnClickListener(view -> {
            Activity activity = getActivity();
            if (activity != null) {
                ((ArticleDetailActivity) getActivity()).showErrorLoadDataMessage();
                presenter.shareButtonClick(authorText.getText().toString(),
                        titleText.getText().toString(), getString(R.string.share_pre_string));
            }
        });
    }

    private void setColors(int bodyTextColor, int titleTextColor, int rgb) {
        metaBarLinear.setBackgroundColor(rgb);
        titleText.setTextColor(titleTextColor);
        authorText.setTextColor(bodyTextColor);
        publisherDateText.setTextColor(bodyTextColor);
    }

    private void showArticleInfo() {
        Bundle bundle = getArgumentsBundle();
        titleText.setText(bundle.getString(ARTICLE_TITLE_KEY));
        authorText.setText(bundle.getString(ARTICLE_AUTHOR_KEY));
        publisherDateText.setText(bundle.getString(ARTICLE_PUBLISHER_DATE_KEY));
        bodyText.setText(Html.fromHtml((bundle.getString(ARTICLE_BODY_KEY))));
    }

    @NonNull
    private Bundle getArgumentsBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new RuntimeException(TAG + getString(R.string.error_fr_article_detail_call_set_data));
        }
        return bundle;
    }

    public interface OnFragmentInteractionListener {
        void shareArticle(String article);
    }

}
