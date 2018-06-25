package com.example.xyzreader.view.adapters.articlesListAdapter;

public interface IArticleRowView {
    void setArticleName(String title);

    void setArticleAuthor(String date);

    void setImage(String thumbUrl, float aspectRatio);

    void setTag(int pos);
}
