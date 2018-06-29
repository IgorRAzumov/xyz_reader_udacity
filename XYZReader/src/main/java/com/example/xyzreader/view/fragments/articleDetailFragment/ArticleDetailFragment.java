package com.example.xyzreader.view.fragments.articleDetailFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ArticleDetailFragment extends MvpAppCompatFragment implements IArticlePageView,
        IArticleDetailFragmentView {
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

    @SuppressLint("CheckResult")
    @Override
    public void init() {
        progressBar.setVisibility(View.VISIBLE);
        final int noColor = getResources().getInteger(R.integer.fr_article_detail_no_detected_color);
        imageLoader.loadIntoWithResult(getArgumentsBundle().getString(ARTICLE_IMAGE_URL), articleImage)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((Bitmap bitmap) -> {
                    Palette.from(bitmap).generate(palette -> {
                        Palette.Swatch swatch = palette.getDarkMutedSwatch();
                        if (swatch != null) {
                            presenter.completeCreateColors(swatch.getBodyTextColor(),
                                    swatch.getTitleTextColor(), swatch.getRgb());
                        } else {
                            presenter.completeCreateColors(noColor, noColor, noColor);
                        }

                    });
                }, throwable -> {
                    presenter.completeCreateColors(noColor, noColor, noColor);
                }, () -> {
                    presenter.completeCreateColors(noColor, noColor, noColor);
                });
    }

    @NonNull
    private Bundle getArgumentsBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new RuntimeException(TAG + getString(R.string.error_fr_article_detail_call_set_data));
        }
        return bundle;
    }

    @Override
    public void showArticleInfo(int bodyTextColor, int titleTextColor, int rgb) {
        metaBarLinear.setBackgroundColor(rgb);
        titleText.setTextColor(titleTextColor);
        authorText.setTextColor(bodyTextColor);
        publisherDateText.setTextColor(bodyTextColor);

        progressBar.setVisibility(View.GONE);
        Bundle bundle = getArgumentsBundle();
        titleText.setText(bundle.getString(ARTICLE_TITLE_KEY));
        authorText.setText(bundle.getString(ARTICLE_AUTHOR_KEY));
        publisherDateText.setText(bundle.getString(ARTICLE_PUBLISHER_DATE_KEY));
        bodyText.setText(Html.fromHtml((bundle.getString(ARTICLE_BODY_KEY)).replaceAll(
                getString(R.string.new_string_regex), getString(R.string.new_string_replacment))));
    }

    @OnClick(R.id.fab_fr_article_detail_share_article)
    public void shareButtonClick() {
        presenter.shareButtonClick();
    }

    @Override
    public void startShareAction() {
        onFragmentInteractionListener.shareArticle(getArgumentsBundle().getString(ARTICLE_BODY_KEY));
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

    public interface OnFragmentInteractionListener {
        void shareArticle(String article);
    }
}
