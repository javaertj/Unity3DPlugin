package com.ykbjson.lib.unity3dplugin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unity3d.player.UnityPlayer;
import com.ykbjson.lib.unity3dplugin.internal.IOnUnity3DCall;

/**
 * Desription：The basic Unity3D Activity is mainly for the {@link UnityPlayerFragmentV4} service. Let Fragment support showing Unity3D
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public abstract class UnityPlayerFragmentV4Activity extends UnityPlayerActivity {
    protected UnityPlayerFragmentV4 mUnityPlayerFragment;

    @Override
    protected void initUnityPlayer(@NonNull Bundle bundle) {
        mUnityPlayer = new UnityPlayer(this);
        mUnityPlayerFragment = generateUnityPlayerFragment(mUnityPlayer, bundle);
        if (null == getSupportFragmentManager().findFragmentByTag(mUnityPlayerFragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().add(unityPlayerContainerId(),
                    mUnityPlayerFragment, mUnityPlayerFragment.getClass().getName()).commit();
        }
    }

    @NonNull
    @Override
    public IOnUnity3DCall getOnUnity3DCall() {
        return mUnityPlayerFragment;
    }

    @NonNull
    protected abstract UnityPlayerFragmentV4 generateUnityPlayerFragment(@NonNull UnityPlayer unityPlayer,
                                                                       @Nullable Bundle bundle);
}
