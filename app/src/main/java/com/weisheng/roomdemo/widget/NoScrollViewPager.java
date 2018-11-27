package com.weisheng.roomdemo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenguang on 2018/1/7.
 */

public class NoScrollViewPager extends ViewPager {
    public boolean isCanSliding() {
        return canSliding;
    }

    public void setNotSliding(boolean canSliding) {
        this.canSliding = canSliding;
    }

    private boolean canSliding = false;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
