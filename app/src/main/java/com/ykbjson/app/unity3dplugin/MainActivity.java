package com.ykbjson.app.unity3dplugin;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ykbjson.lib.unity3dplugin.CallInfo;
import com.ykbjson.lib.unity3dplugin.Unity3DCall;
import com.ykbjson.lib.unity3dplugin.UnityPlayerActivity;
import com.ykbjson.lib.unity3dplugin.internal.ICallInfo;

import butterknife.BindView;

/**
 * An example for unity3d
 */
public class MainActivity extends UnityPlayerActivity {

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

    public void setPause(View view) {
        isPause = !isPause;
        buttonPause.setText(isPause ? "继续" : "暂停");
        Unity3DCall.doUnity3DVoidCall(CallInfo.Builder
                .create()
                .callModelName("Ball")//对应的unity组件挂在的script文件指定的名字
                .callMethodName("SetPause")//对应的unity组件挂在的script文件里的方法名字
                .addCallMethodParam("isPause", isPause)////对应的unity组件挂在的script文件里的方法需要的参数
                .build());
    }

    /**
     * 显示一个toast
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(this, "来自Unity的消息: "+message, Toast.LENGTH_SHORT).show();
    }
}
