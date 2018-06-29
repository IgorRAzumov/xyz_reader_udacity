package com.example.xyzreader.model.image;

public interface IImageLoader<T,K> {
    void loadInto(String url, T container);
    K loadIntoWithResult(String url, T container);
}
