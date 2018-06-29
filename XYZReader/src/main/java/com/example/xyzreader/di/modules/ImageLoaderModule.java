package com.example.xyzreader.di.modules;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.xyzreader.model.image.IImageLoader;
import com.example.xyzreader.model.image.android.ImageLoaderGlide;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Maybe;

@Module
public class ImageLoaderModule {
    @Provides
    public IImageLoader<ImageView, Maybe<Bitmap>> glideImageLoader() {
        return new ImageLoaderGlide();
    }
}
