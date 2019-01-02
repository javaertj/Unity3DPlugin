package com.ykbjson.lib.unity3dplugin.internal;

import android.support.annotation.NonNull;

/**
 * Description：In order to allow any class that holds an Activity, such as a Fragment, to display Unity3D resources, an interface is provided to allow the Activity to be implemented.
 *   Then return any {@link IOnUnity3DCall} via the {@link #getOnUnity3DCall()} method.
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public interface IGetUnity3DCall {
    /**
     * IOnUnity3DCall
     *
     * @return {@link IOnUnity3DCall}
     */
    @NonNull
    IOnUnity3DCall getOnUnity3DCall();
}
