package com.ykbjson.app.unity3dplugin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.ykbjson.lib.unity3dplugin.CallInfo;
import com.ykbjson.lib.unity3dplugin.base.impl.BaseFragmentV4;
import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;
import com.ykbjson.lib.unity3dplugin.internal.IOnUnity3DCall;
import com.ykbjson.lib.unity3dplugin.internal.IUnityPlayerContainer;

import butterknife.BindView;

/**
 * Description：加载UnityPlay的Fragment
 * <BR/>
 * Creator：yankebin
 * <BR/>
 * CreatedAt：2019/2/12
 */
public class MainUnityPlayFragment extends BaseFragmentV4 implements IOnUnity3DCall, IUnityPlayerContainer, View.OnClickListener {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    private boolean isPause;
    @BindView(R.id.buttonPause)
    Button buttonPause;

    @Override
    public int contentViewLayoutId() {
        return R.layout.fragment_show_unity_palyer;
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull Bundle params, @NonNull View contentView) {
        if (null != mUnityPlayer) {
            final ViewGroup unityContainer = contentView.findViewById(unityPlayerContainerId());
            unityContainer.addView(mUnityPlayer);
            mUnityPlayer.requestFocus();
        }
        buttonPause.setOnClickListener(this);
    }

    @Override
    public void onVieDestroyed() {

    }

    @Override
    public void onVoidCall(@NonNull ICallInfo callInfo) {
        switch (callInfo.getCallMethodName()) {
            case "showToast":
                showToast(callInfo.getCallMethodParams().getString("message"));
                break;

            default:
                break;
        }
    }

    @Override
    public Object onReturnCall(@NonNull ICallInfo callInfo) {
        return null;
    }

    @Nullable
    @Override
    public Context gatContext() {
        return getActivity();
    }

    @Override
    public int unityPlayerContainerId() {
        return R.id.unityPlayerContainer;
    }

    public void setUnityPlayer(@NonNull UnityPlayer mUnityPlayer) {
        this.mUnityPlayer = mUnityPlayer;
    }

    @Override
    public void onClick(View view) {
        isPause = !isPause;
        buttonPause.setText(isPause ? "继续" : "暂停");
        CallInfo.Builder
                .create()
                .callModelName("Ball")//对应的unity组件挂在的script文件指定的名字,本demo中对应BallController
                .callMethodName("SetPause")//对应的unity组件挂在的script文件里的方法名字，本demo中对应BallController的SetPause方法
                .addCallMethodParam("isPause", isPause)////对应的unity组件挂在的script文件里的方法需要的参数
                .build()
                .send();
    }

    /**
     * 显示一个toast
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(getActivity(), "来自Unity的消息: " + message, Toast.LENGTH_SHORT).show();
    }

    public static MainUnityPlayFragment instantiate(Context context, String name, Bundle param, UnityPlayer unityPlayer) {
        final MainUnityPlayFragment mainUnityPlayFragment = (MainUnityPlayFragment) Fragment.instantiate(context, name, param);
        mainUnityPlayFragment.setUnityPlayer(unityPlayer);
        return mainUnityPlayFragment;
    }
}
