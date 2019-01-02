package com.ykbjson.lib.unity3dplugin.thirdpart.gesture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Desription：GestureDetector with basic gestures and zoom gestures
 * Creator：yankebin
 * CreatedAt：2018/12/2
 */
public class CompatGestureDetector {
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    public CompatGestureDetector(@NonNull Context context, @NonNull CompatOnGestureCallback gestureCallback) {
        this(context, new CompatOnGestureDetectorListener(gestureCallback));
    }

    public CompatGestureDetector(@NonNull Context context, @NonNull CompatOnGestureDetectorListener gestureDetectorListener) {
        mGestureDetector = new GestureDetector(context, gestureDetectorListener);
        mScaleGestureDetector = new ScaleGestureDetector(context, gestureDetectorListener);
    }

    public GestureDetector getGestureDetector() {
        return mGestureDetector;
    }

    public ScaleGestureDetector getScaleGestureDetector() {
        return mScaleGestureDetector;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || mScaleGestureDetector.onTouchEvent(event);
    }
}