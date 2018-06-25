package com.example.xyzreader.view.fragments.articleDetailFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.xyzreader.R;
import com.example.xyzreader.view.adapters.articlesPagerAdapter.IArticlePageView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArticleDetailFragment extends MvpAppCompatFragment implements IArticlePageView {
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance() {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_activity_implement_fragment_callback));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setAuthor(String author) {

    }

    @Override
    public void setBody(String body) {

    }

    @Override
    public void setpublisherDate(String publishedDate) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
