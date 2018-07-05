package com.example.xyzreader.view.customViews;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

public class AspectRatioImageView extends AppCompatImageView {
        private float mAspectRatio = 1.5f;

    public AspectRatioImageView(Context context) {
        super(context);
    }


    public void setAspectRatio(float aspectRatio) {
            mAspectRatio = aspectRatio;
            requestLayout();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = getMeasuredWidth();
            setMeasuredDimension(measuredWidth, (int) (measuredWidth / mAspectRatio));
        }
    }
