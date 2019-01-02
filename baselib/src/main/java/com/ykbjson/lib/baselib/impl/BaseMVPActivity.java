package com.ykbjson.lib.baselib.impl;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ykbjson.lib.baselib.IBaseMvpView;


/**
 * Desription：mvp模式activity基类
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */

public abstract class BaseMVPActivity<T extends BaseMVPPresenter> extends BaseActivity implements IBaseMvpView<T> {
    protected T presenter;

    @Override
    public void onStart() {
        super.onStart();
        if (null!=presenter){
            presenter.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null!=presenter){
            presenter.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (null!=presenter){
            presenter.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public Context gatContext() {
        return this;
    }
}
