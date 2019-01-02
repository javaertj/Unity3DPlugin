package com.ykbjson.lib.unity3dplugin.internal;

import android.support.annotation.IdRes;

/**
 * Description：Container that can display Unity3dPlayer
 * Creator：yankebin
 * CreatedAt：2018/12/19
 */
public interface IUnityPlayerContainer {
    /**
     * The layout id of display {@link com.unity3d.player.UnityPlayer}
     *
     * @return The layout id
     */
    @IdRes
    int unityPlayerContainerId();
}
