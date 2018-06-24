package com.example.xyzreader.di.modules;

import android.widget.ImageView;

import com.example.xyzreader.model.image.IImageLoader;
import com.example.xyzreader.model.image.android.PicassoImageLoader;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageLoaderModule {
    @Provides
    public IImageLoader<ImageView> picassoImageLoader() {
        return new PicassoImageLoader();
    }
}
