package com.ykbjson.lib.unity3dplugin;

import android.support.annotation.NonNull;
import android.util.Log;

import com.unity3d.player.UnityPlayer;
import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;

/**
 * Description：Call Unity3D
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public class Unity3DCall {
    /**
     * Call Unity3d
     *
     * @param callInfo Carrier for Android and Unity3D interaction
     */
    public static void doUnity3DVoidCall(@NonNull ICallInfo callInfo) {
        if(BuildConfig.DEBUG) {
            Log.i("Unity3DCall", callInfo.toString());
        }
        UnityPlayer.UnitySendMessage(callInfo.getCallModelName(), callInfo.getCallMethodName(),
                callInfo.isNeedCallMethodParams() ? callInfo.toString() : "");
    }
}
