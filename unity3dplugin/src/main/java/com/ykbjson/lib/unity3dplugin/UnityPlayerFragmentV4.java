package com.ykbjson.lib.unity3dplugin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.unity3d.player.UnityPlayer;
import com.ykbjson.lib.unity3dplugin.base.impl.BaseFragmentV4;
import com.ykbjson.lib.unity3dplugin.internal.IGetUnity3DCall;
import com.ykbjson.lib.unity3dplugin.internal.IOnUnity3DCall;
import com.ykbjson.lib.unity3dplugin.internal.IUnityPlayerContainer;

/**
 * Description：The base Unity3D fragment, the Activity holding this Fragment needs to implement the {@link IGetUnity3DCall} interface and implement {@link IGetUnity3DCall#getOnUnity3DCall()}
 * 方法返回此Fragment的实例
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public abstract class UnityPlayerFragmentV4 extends BaseFragmentV4 implements IOnUnity3DCall, IUnityPlayerContainer {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    public void setUnityPlayer(@NonNull UnityPlayer mUnityPlayer) {
        this.mUnityPlayer = mUnityPlayer;
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull Bundle params, @NonNull View contentView) {
        if (null != mUnityPlayer) {
            final ViewGroup unityContainer = contentView.findViewById(unityPlayerContainerId());
            unityContainer.addView(mUnityPlayer);
            mUnityPlayer.requestFocus();
        }
    }

    @Nullable
    @Override
    public Context gatContext() {
        return getActivity();
    }
}
