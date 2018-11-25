

package com.framework.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 20;
    private int firstVisibleItem;
    private int totalItemCount;
    private int visibleItemCount;
    private int mScrolledDistance;
    private boolean mControlsVisible = true;
    private static final long TIME = 150L;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();


        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();

        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] mFirstVisible = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            firstVisibleItem = mFirstVisible[0];
        } else {
            throw new RuntimeException(
                    "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }

        //记录上下滑动触发时间
        final long time = System.currentTimeMillis();

        if (firstVisibleItem == 0) {
            if (!mControlsVisible) {
                mControlsVisible = true;
            }
            if (time - callbackTime >= TIME) {
                onShow();
            }
            changeLastCallbackTime(time);
        } else {
            if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                if (time - callbackTime >= TIME) {
                    onHide();
                }
                mControlsVisible = false;
                mScrolledDistance = 0;
                changeLastCallbackTime(time);
            } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                if (time - callbackTime >= TIME) {
                    onShow();
                }
                mControlsVisible = true;
                mScrolledDistance = 0;
                changeLastCallbackTime(time);
            }
        }
        if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
            mScrolledDistance += dy;
        }
    }

    long callbackTime;

    private void changeLastCallbackTime(long time) {
        callbackTime = time;
    }

    public abstract void onHide();

    public abstract void onShow();
}