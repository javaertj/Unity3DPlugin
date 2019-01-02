package com.ykbjson.lib.baselib.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.ykbjson.lib.baselib.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：Activity基类
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    protected View mainContentView;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContentView =  getLayoutInflater().inflate(contentViewLayoutId(),
                (ViewGroup) getWindow().getDecorView(), false);
        setContentView(mainContentView);
        unbinder = ButterKnife.bind(this, mainContentView);
        Bundle bundle = getIntent().getExtras();
        if (null == bundle) {
            bundle = new Bundle();
        }
        if (null != savedInstanceState) {
            bundle.putAll(savedInstanceState);
        }
        onViewCreated(bundle, mainContentView);
    }

    @Override
    protected void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        mainContentView = null;
        super.onDestroy();
        onVieDestroyed();
    }
}
