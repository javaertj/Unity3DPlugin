package com.ykbjson.lib.unity3dplugin.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Description：Carrier for Android and Unity3D interaction
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public interface ICallInfo extends Serializable {

    @NonNull
    String getCallMethodName();

    @NonNull
    String getCallModelName();

    @Nullable
    JSONObject getCallMethodParams();

    @Nullable
    ICallInfo getParent();

    @Nullable
    ICallInfo getChild();

    boolean isUnityCall();

    boolean isNeedCallMethodParams();

    void send();
}
