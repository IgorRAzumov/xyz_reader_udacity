package com.example.xyzreader.view.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;



public class FabScrollBehavior extends FloatingActionButton.Behavior {
    private final LinearInterpolator linearInterpolator;

    public FabScrollBehavior(Context context, AttributeSet attrs) {
        super();
        linearInterpolator = new LinearInterpolator();
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0) {
            CoordinatorLayout.LayoutParams layoutParams
                    = (CoordinatorLayout.LayoutParams) child.getLayoutParams();

            int fab_bottomMargin = layoutParams.bottomMargin;
            child.animate()
                    .translationY(child.getHeight() + fab_bottomMargin)
                    .setInterpolator(linearInterpolator)
                    .start();
        } else if (dyConsumed < 0) {
            child.animate()
                    .translationY(0).setInterpolator(linearInterpolator).start();
        }
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


}

