package com.ykbjson.lib.unity3dplugin.thirdpart.gesture;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Description：Callback interface for basic gestures and zoom gestures
 * Creator：yankebin
 * CreatedAt：2018/12/2
 */
public interface CompatOnGestureCallback {

    void onHorizontalMove(float distanceX);

    void onVerticalMove(float distanceY);

    void onScale(float scaleX, float spanX, float scaleY, float spanY);

    @NonNull
    Context getContext();
}
