package com.ykbjson.lib.baselib.impl;


import com.ykbjson.lib.baselib.IBaseMvpPresenter;
import com.ykbjson.lib.baselib.IBaseMvpView;

/**
 * Desription：mvp模式presenter基类
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */
public abstract class BaseMVPPresenter<V extends IBaseMvpView> implements IBaseMvpPresenter {

    protected V mView;

    public BaseMVPPresenter(V view) {
        if (null == view) {
            throw new NullPointerException("View can not be null");
        }
        this.mView = view;
        //noinspection unchecked
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
