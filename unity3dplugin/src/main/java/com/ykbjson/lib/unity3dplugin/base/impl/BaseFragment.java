package com.ykbjson.lib.unity3dplugin.base.impl;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ykbjson.lib.unity3dplugin.base.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：app包下Fragment作为基类封装
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */
@Deprecated
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected Unbinder unbinder;
    protected View mainContentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null == mainContentView) {
            mainContentView = inflater.inflate(contentViewLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, mainContentView);
            if (null == savedInstanceState) {
                savedInstanceState = new Bundle();
            }
            savedInstanceState.putAll(getArguments());
            onViewCreated(savedInstanceState, mainContentView);
        } else {
            unbinder = ButterKnife.bind(this, mainContentView);
        }
        return mainContentView;
    }

    @Override
    public void onDetach() {
        mainContentView = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (mainContentView != null && mainContentView.getParent() != null) {
            ((ViewGroup) mainContentView.getParent()).removeView(mainContentView);
        }
        super.onDestroyView();

        onVieDestroyed();
    }
}
