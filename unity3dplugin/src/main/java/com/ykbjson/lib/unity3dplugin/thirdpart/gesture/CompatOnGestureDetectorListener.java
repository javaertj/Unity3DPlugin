package com.ykbjson.lib.unity3dplugin.thirdpart.gesture;

import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.lang.ref.WeakReference;

/**
 * Desription：Callback class that receives basic gestures and zoom gestures
 * Creator：yankebin
 * CreatedAt：2018/12/2
 */
public class CompatOnGestureDetectorListener extends GestureDetector.SimpleOnGestureListener
        implements ScaleGestureDetector.OnScaleGestureListener {
    protected WeakReference<CompatOnGestureCallback> mCallback;
    protected float mLastScaleFactor = 1.0F;
    protected float mMaxScale = 5;
    protected float mMinScale = 0.1f;

    public CompatOnGestureDetectorListener(@NonNull CompatOnGestureCallback callback) {
        this.mCallback = new WeakReference<>(callback);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


    /*
     * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
     * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
     * <p>
     * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，
     * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行，
     * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间
     * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /*
     * 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
     * <p>
     * 轻击一下屏幕，立刻抬起来，才会有这个触发
     * <p>
     * 从名字也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /*
     *用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (null == mCallback || null == mCallback.get()) {
            return false;
        }
        mCallback.get().onHorizontalMove(distanceX);
        mCallback.get().onVerticalMove(distanceY);

        return true;
    }

    /*
     *用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /*
     *用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (null == mCallback || null == mCallback.get()) {
            return false;
        }
        final float currentScaleFactor = detector.getScaleFactor() * mLastScaleFactor;
        //当放大倍数大于5或者缩小倍数小于0.1倍 就不伸缩图片 返回true取消处理伸缩手势事件
        if (currentScaleFactor > mMaxScale || currentScaleFactor < mMinScale) {
            mLastScaleFactor = currentScaleFactor;
            return true;
        }

        mCallback.get().onScale(currentScaleFactor, detector.getCurrentSpanX(), currentScaleFactor,
                detector.getCurrentSpanY());
        mLastScaleFactor = currentScaleFactor;//保存上一次的伸缩值
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}