package com.example.xyzreader.model.cache;

import com.example.xyzreader.model.entity.Article;

import java.util.List;

public interface ICache {
    void updateArticlesCache(List<Article> articles);
}
