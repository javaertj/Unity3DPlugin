package com.ykbjson.lib.baselib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Desription：MVP视图接口
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */

public interface IBaseMvpView<T> {
    @Nullable
    Context gatContext();

    void setPresenter(@NonNull T presenter);
}
