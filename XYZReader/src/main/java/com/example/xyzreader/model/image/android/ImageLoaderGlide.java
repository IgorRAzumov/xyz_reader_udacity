package com.example.xyzreader.model.image.android;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xyzreader.model.image.IImageLoader;

import io.reactivex.Maybe;

public class ImageLoaderGlide implements IImageLoader<ImageView, Maybe<Bitmap>> {


    @Override
    public void loadInto(String url, ImageView container) {
        GlideApp
                .with(container.getContext())
                .load(url)
                .centerCrop()
                .into(container);
    }

    public Maybe<Bitmap> loadIntoWithResult(@Nullable String url, ImageView container) {
        return Maybe.create(emitter -> GlideApp
                .with(container.getContext())
                .asBitmap()
                .load(url)
                .centerCrop()
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {

                        emitter.onComplete();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        emitter.onSuccess(resource);
                        return false;
                    }
                })
                .into(container));
    }
}
