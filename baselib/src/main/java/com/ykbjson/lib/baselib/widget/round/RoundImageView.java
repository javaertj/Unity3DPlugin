package com.ykbjson.lib.baselib.widget.round;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Description：圆形ImageView
 * Creator：yankebin
 * CreatedAt：2018/12/27
 */
public class RoundImageView extends ImageView {

    private RoundViewDelegate delegate;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new RoundViewDelegate(this, context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate.isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setBackgroundColor(int color) {
        if (null != delegate) {
            delegate.setBackgroundColor(color);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        } else {
            delegate.setBgSelector();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null != delegate) {
            delegate.onDraw(canvas);
        }
        super.onDraw(canvas);
    }

    /**
     * use delegate to set attr
     */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

}
