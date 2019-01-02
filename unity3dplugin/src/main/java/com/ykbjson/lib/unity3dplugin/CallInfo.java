package com.ykbjson.lib.unity3dplugin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;

/**
 * Description：Carrier for Android and Unity3D interaction
 * Creator：yankebin
 * CreatedAt：2018/12/1
 */
public class CallInfo implements ICallInfo {
    private String callModelName;
    private String callMethodName;
    private JSONObject callMethodParams = new JSONObject();
    private CallInfo child;
    private CallInfo parent;
    private boolean unityCall = true;
    private boolean needCallMethodParams = true;

    public CallInfo() {
    }

    CallInfo(@Nullable Builder builder) {
        this();
        if (null != builder) {
            unityCall = builder.unityCall;
            needCallMethodParams = builder.needCallMethodParams;
            callModelName = builder.callModelName;
            callMethodName = builder.callMethodName;
            callMethodParams = builder.callMethodParams;
            setChild(builder.child);
            setParent(builder.parent);
        }
    }

    public CallInfo setCallModelName(@Nullable String callModelName) {
        this.callModelName = callModelName;
        return this;
    }

    public CallInfo setCallMethodName(@NonNull String callMethodName) {
        this.callMethodName = callMethodName;
        return this;
    }

    public CallInfo setCallMethodParams(@Nullable JSONObject callMethodParams) {
        if (null != callMethodParams) {
            this.callMethodParams.putAll(callMethodParams);
        }
        return this;
    }

    public CallInfo setChild(@Nullable CallInfo child) {
        this.child = child;
        if (null != child) {
            child.setParent(this);
        }

        return this;
    }

    public CallInfo setParent(@Nullable CallInfo parent) {
        this.parent = parent;
        if (null != parent) {
            parent.setChild(this);
        }
        return this;
    }

    public CallInfo setUnityCall(boolean unityCall) {
        this.unityCall = unityCall;
        return this;
    }

    public CallInfo setNeedCallMethodParams(boolean needCallMethodParams) {
        this.needCallMethodParams = needCallMethodParams;
        return this;
    }

    public CallInfo addCallMethodParam(@NonNull String key, @Nullable Object value) {
        this.callMethodParams.put(key, value);
        return this;
    }

    @Override
    @Nullable
    public String getCallModelName() {
        return callModelName;
    }

    @Override
    @NonNull
    public String getCallMethodName() {
        return callMethodName;
    }

    @Override
    @Nullable
    public JSONObject getCallMethodParams() {
        return callMethodParams;
    }

    @Override
    @Nullable
    public CallInfo getChild() {
        return child;
    }

    @Override
    @Nullable
    public CallInfo getParent() {
        return parent;
    }

    @Override
    public boolean isUnityCall() {
        return unityCall;
    }

    @Override
    public boolean isNeedCallMethodParams() {
        return needCallMethodParams;
    }

    @NonNull
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public void send() {
        Unity3DCall.doUnity3DVoidCall(this);
    }

    public static class Builder {
        private String callModelName;
        private String callMethodName;
        private JSONObject callMethodParams = new JSONObject();
        private CallInfo child;
        private CallInfo parent;
        private boolean unityCall;
        private boolean needCallMethodParams = true;

        public Builder callModelName(@Nullable String callModelName) {
            this.callModelName = callModelName;
            return this;
        }

        public Builder callMethodName(@NonNull String callMethodName) {
            this.callMethodName = callMethodName;
            return this;
        }

        public Builder addCallMethodParam(@NonNull String key, @Nullable Object value) {
            this.callMethodParams.put(key, value);
            return this;
        }

        public Builder child(@Nullable CallInfo child) {
            this.child = child;
            return this;
        }

        public Builder parent(@Nullable CallInfo parent) {
            this.parent = parent;
            return this;
        }

        public Builder unityCall(boolean unityCall) {
            this.unityCall = unityCall;
            return this;
        }

        public Builder needCallMethodParams(boolean needCallMethodParams) {
            this.needCallMethodParams = needCallMethodParams;
            return this;
        }

        public CallInfo build() {
            return new CallInfo(this);
        }

        public CallInfo build(@Nullable String param) {
            return JSONObject.parseObject(param, CallInfo.class);
        }
    }
}
