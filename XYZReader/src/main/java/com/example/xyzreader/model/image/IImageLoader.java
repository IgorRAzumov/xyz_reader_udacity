package com.example.xyzreader.model.image;

public interface IImageLoader<T> {
    void loadInto(String url, T container);
}
