package com.ykbjson.lib.unity3dplugin.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Description：Interface that can receive Unity3D messages
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public interface IOnUnity3DCall {
    /**
     * Android calls Unity3D, no return value
     *
     * @param callInfo Carrier for Android and Unity3D interaction
     */
    void onVoidCall(@NonNull ICallInfo callInfo);

    /**
     * Android calls Unity3D, has a return value
     *
     * @param callInfo Carrier for Android and Unity3D interaction
     */
    Object onReturnCall(@NonNull ICallInfo callInfo);

    /**
     * Get Context
     *
     * @return {@link Context}
     */
    @Nullable
    Context gatContext();
}
