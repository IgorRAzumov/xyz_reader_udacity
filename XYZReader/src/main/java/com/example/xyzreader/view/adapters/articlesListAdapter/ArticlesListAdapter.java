package com.example.xyzreader.view.adapters.articlesListAdapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.model.image.IImageLoader;
import com.example.xyzreader.presenter.IArticlesListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ArticleViewHolder> {
    private IArticlesListPresenter presenter;
    private IImageLoader<ImageView, Maybe<Bitmap>> imageLoader;

    public ArticlesListAdapter(IArticlesListPresenter presenter,
                               IImageLoader<ImageView, Maybe<Bitmap>> imageLoader) {
        this.presenter = presenter;
        this.imageLoader = imageLoader;

    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.articles_list_item, parent, false);
        view.setOnClickListener(createItemOnClickListener());
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.itemView.setTag(position);
        presenter.bindArticleListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getArticlesCount();
    }

    private View.OnClickListener createItemOnClickListener() {
        return view -> presenter.onArticleClick((int) view.getTag());
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements IArticleRowView {
        @BindView(R.id.tv_articles_list_item_title)
        TextView articleName;
        @BindView(R.id.tv_articles_list_item_author)
        TextView articleAuthor;
        @BindView(R.id.iv_articles_list_item_image)
        ImageView articleImage;
        @BindView(R.id.cl_articles_list_item_thumbnail_root_card_ly)
        ConstraintLayout rootCardLayout;

        ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setArticleName(String title) {
            articleName.setText(title);
        }

        @Override
        public void setArticleAuthor(String author) {
            articleAuthor.setText(author);
        }

        @Override
        public void setImage(String thumbUrl, double aspectRatio) {
            String posterRatio = String.valueOf(1) + ":" + String.valueOf(1.5);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootCardLayout);
            constraintSet.setDimensionRatio(articleImage.getId(), posterRatio);
            constraintSet.applyTo(rootCardLayout);

            imageLoader.loadInto(thumbUrl, articleImage);
        }
    }
}
