package com.framework.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IOrientation {
    int DEFAULT_PAGE_SIZE = 20;
    int DEFAULT_CURRENT_PAGE = 1;
    /**
     * List滑动方向常量，向上
     */
    int SCROLL_ORIENTATION_UP = 0x001002;
    /**
     * List滑动方向常量，向下
     */
    int SCROLL_ORIENTATION_DOWN = 0x001003;

    @IntDef({SCROLL_ORIENTATION_UP, SCROLL_ORIENTATION_DOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }
}