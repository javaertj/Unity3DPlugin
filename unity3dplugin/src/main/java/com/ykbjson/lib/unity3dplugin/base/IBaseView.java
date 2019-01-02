package com.ykbjson.lib.unity3dplugin.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description：Basic interface of all {@link Activity}
 * or
 * {@link Fragment}
 * or
 * {@link android.app.Fragment}
 * <p>
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */
public interface IBaseView {

    /**
     * Return the layout resource
     *
     * @return Layout Resource
     */
    @LayoutRes
    int contentViewLayoutId();

    /**
     * Call after {@link Activity#onCreate(Bundle)}
     * or
     * {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * or
     * {@link android.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     *
     * @param params
     * @param contentView
     */
    void onViewCreated(@NonNull Bundle params, @NonNull View contentView);

    /**
     * Call after
     * {@link Activity#onDestroy()} (Bundle)}
     * or
     * {@link Fragment#onDestroyView()}
     * or
     * {@link android.app.Fragment#onDestroyView()}
     */
    void onVieDestroyed();
}
