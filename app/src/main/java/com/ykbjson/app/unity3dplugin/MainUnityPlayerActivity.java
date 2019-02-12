package com.ykbjson.app.unity3dplugin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.ykbjson.lib.unity3dplugin.CallInfo;
import com.ykbjson.lib.unity3dplugin.UnityPlayerActivity;
import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;
import com.ykbjson.lib.unity3dplugin.internal.IOnUnity3DCall;

import butterknife.BindView;

/**
 * Description：An example for unity3d
 * <BR/>
 * Creator：yankebin
 * <BR/>
 * CreatedAt：2019/1/2
 */
public class MainUnityPlayerActivity extends UnityPlayerActivity {

    private boolean isPause;
    @BindView(R.id.buttonPause)
    Button buttonPause;

    @Override
    public int contentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onVieDestroyed() {

    }

    //unity3d发送过来的消息，不需要返回值
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

    //unity3d发送过来的消息，需要返回值
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
        return super.generateIOnUnity3DCallDelegate(unityPlayer, bundle);
    }

    //在xml文件里指定的onClick
    public void setPause(View view) {
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
        Toast.makeText(this, "来自Unity的消息: " + message, Toast.LENGTH_SHORT).show();
    }
}
