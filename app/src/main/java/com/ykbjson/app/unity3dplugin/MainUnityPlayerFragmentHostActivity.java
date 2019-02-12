package com.ykbjson.app.unity3dplugin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unity3d.player.UnityPlayer;
import com.ykbjson.lib.unity3dplugin.UnityPlayerActivity;
import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;
import com.ykbjson.lib.unity3dplugin.internal.IOnUnity3DCall;

/**
 * Description：加载UnityPlay的Fragmen依赖的Activity
 * <BR/>
 * Creator：yankebin
 * <BR/>
 * CreatedAt：2019/2/12
 */
public class MainUnityPlayerFragmentHostActivity extends UnityPlayerActivity {
    @Override
    public int contentViewLayoutId() {
        return R.layout.activity_unity_player_fragment_host;
    }

    @Override
    public void onVieDestroyed() {

    }

    @Override
    public void onVoidCall(@NonNull ICallInfo callInfo) {
    }

    @Override
    public Object onReturnCall(@NonNull ICallInfo callInfo) {
        return null;
    }

    @Override
    public int unityPlayerContainerId() {
        return R.id.unityPlayerContainer;
    }

    @Nullable
    @Override
    protected IOnUnity3DCall generateIOnUnity3DCallDelegate(@NonNull UnityPlayer unityPlayer, @Nullable Bundle bundle) {
        return MainUnityPlayFragment.instantiate(this, MainUnityPlayFragment.class.getName(), bundle, unityPlayer);
    }

}
