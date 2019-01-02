package com.ykbjson.lib.unity3dplugin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;
import com.ykbjson.lib.unity3dplugin.internal.IGetUnity3DCall;
import com.ykbjson.lib.unity3dplugin.internal.IOnUnity3DCall;

import java.lang.ref.SoftReference;

/**
 * Description：AndroidCall for Unity3D
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public class AndroidCall {
    private final String TAG = getClass().getName();

    private IOnUnity3DCall onUnity3DCall;
    private SoftReference<Activity> hostContext;

    /**
     * In order to further relax the restrictions of OnUnity3DCall, let Fragment implement OnUnity3DCall can also load Unity3D view, so added {@link IGetUnity3DCall}
     *       interface.
     *
     * @param iGetUnity3DCall
     */
    public AndroidCall(@NonNull IGetUnity3DCall iGetUnity3DCall) {
        this.onUnity3DCall = iGetUnity3DCall.getOnUnity3DCall();
        hostContext = new SoftReference<>((Activity) onUnity3DCall.gatContext());
    }

    public void destroy() {
        if (null != hostContext) {
            hostContext.clear();
        }
    }

    protected void checkConfiguration() {
        if (null == hostContext || null == hostContext.get()) {
            throw new RuntimeException(getClass().getSimpleName() + " must be call init(Context) method at first");
        }
    }

    @Nullable
    public Context getApplicationContext() {
        final Context context = getContext();
        return null == context ? null : context.getApplicationContext();
    }

    @Nullable
    public Context getContext() {
        return null == hostContext || null == hostContext.get() ? null : hostContext.get();
    }


    public void onVoidCall(@NonNull String param) {
        Log.d(TAG, "onVoidCall, param : " + param);
        onAndroidVoidCall(new CallInfo.Builder().build(param));
    }

    public Object onReturnCall(@NonNull String param) {
        Log.d(TAG, "onReturnCall, param : " + param);
        return onAndroidReturnCall(new CallInfo.Builder().build(param));
    }

    public void onAndroidVoidCall(@NonNull ICallInfo param) {
        checkConfiguration();
        if (null == onUnity3DCall) {
            return;
        }
        onUnity3DCall.onVoidCall(param);
    }

    public Object onAndroidReturnCall(@NonNull ICallInfo callInfo) {
        checkConfiguration();
        if (null == onUnity3DCall) {
            return null;
        }
        return onUnity3DCall.onReturnCall(callInfo);
    }
}
