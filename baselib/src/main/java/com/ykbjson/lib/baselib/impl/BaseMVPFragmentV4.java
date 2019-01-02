package com.ykbjson.lib.baselib.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ykbjson.lib.baselib.IBaseMvpView;

/**
 * Desription：mvp模式v4包下fragment基类
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */

public abstract class BaseMVPFragmentV4<T extends BaseMVPPresenter> extends BaseFragmentV4 implements IBaseMvpView<T> {
    protected T presenter;

    @Override
    public void onStart() {
        super.onStart();
        if (null != presenter) {
            presenter.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != presenter) {
            presenter.resume();
        }
    }

    @Override
    public void onDestroyView() {
        if (null != presenter) {
            presenter.destroy();
        }
        super.onDestroyView();
    }

    @Override
    public void setPresenter(@NonNull T presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public Context gatContext() {
        return getActivity();
    }
}
