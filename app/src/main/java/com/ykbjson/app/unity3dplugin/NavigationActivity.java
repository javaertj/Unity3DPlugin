package com.ykbjson.app.unity3dplugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.ykbjson.lib.unity3dplugin.base.impl.BaseActivity;

/**
 * Description：导航页
 * <BR/>
 * Creator：yankebin
 * <BR/>
 * CreatedAt：2019/2/12
 */
public class NavigationActivity extends BaseActivity {

    @Override
    public int contentViewLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    public void onViewCreated(@NonNull Bundle params, @NonNull View contentView) {

    }

    @Override
    public void onVieDestroyed() {

    }

    public void loadUnityPlayWithActivity(View view) {
        startActivity(new Intent(this, MainUnityPlayerActivity.class));
    }

    public void loadUnityPlayWithFragment(View view) {
        startActivity(new Intent(this, MainUnityPlayerFragmentHostActivity.class));
    }

}
