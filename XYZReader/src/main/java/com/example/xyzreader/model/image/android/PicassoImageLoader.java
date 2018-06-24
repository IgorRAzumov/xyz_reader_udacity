package com.example.xyzreader.model.image.android;

import android.widget.ImageView;

import com.example.xyzreader.R;
import com.example.xyzreader.model.image.IImageLoader;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements IImageLoader<ImageView> {
    @Override
    public void loadInto(String url, ImageView container) {
        Picasso.get()
                .load(url)
                .error(R.drawable.empty_detail)
                .into(container);
    }
}
