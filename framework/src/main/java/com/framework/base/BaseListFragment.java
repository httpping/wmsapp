package com.framework.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.framework.utils.HidingScrollListener;
import com.framework.utils.IOrientation;
import com.tp.base.R;

import java.util.List;



/**
 * 下拉刷新 和上拉加载
 *
 * @author tanping
 * @version 1.0
 * @date 2018/04/03
 * @since 1.0
 */
public abstract class BaseListFragment extends BaseFragment {

    public RecyclerView mRecyclerView ;
    public SwipeRefreshLayout mSwipeRefresh;
    public FloatingActionButton mFloatActionButton;
    public int page = 1;
    public int pageSize = 20;

    @Override
    public int getContentResId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefresh =   findViewById(R.id.base_swipeRefreshLayout);
        mRecyclerView =   findViewById(R.id.base_recyclerView);
        mFloatActionButton = findViewById(R.id.base_float_action_button);
        addFloatEvent();
        if (mRecyclerView!=null){
            mRecyclerView.addOnScrollListener(new HidingScrollListener() {
                @Override
                public void onHide() {
                    onListScrolled(IOrientation.SCROLL_ORIENTATION_UP);
                }

                @Override
                public void onShow() {
                    onListScrolled(IOrientation.SCROLL_ORIENTATION_DOWN);
                }
            });
        }
    }

    /**
     * 默认的recycle view 上下滑动处理
     * @param orientation
     */
    public void onListScrolled(int orientation){
        if (mFloatActionButton == null){
            return;
        }
        if(!isStartFloatActionButton()){
            return;
        }
        if (orientation == IOrientation.SCROLL_ORIENTATION_UP){
            animateOut(mFloatActionButton);
        }else if (orientation == IOrientation.SCROLL_ORIENTATION_DOWN){
            animateIn(mFloatActionButton);
        }
    }

    /**
     *  瀑布流bug 空行
     */
    public void  handlerBug(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if (!recyclerView.canScrollVertically(-1)) {
                        handler.postDelayed(notifyData, 5);
                        return;
                    }
                }
            }
        });
    }

    /**
     * 是否预加载
     *
     * @author dingpeihua
     * @date 2016/4/25 8:55
     * @version 1.0
     */
    private boolean isPreLoading;
    private Handler handler = new Handler();
    private Runnable notifyData = new Runnable() {
        @Override
        public void run() {
            if (mRecyclerView.getAdapter() != null) {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    };

  /**
     * request Checke net
     */
    public void requestChecknet(){
      /*  if (NetworkUtil.isConnected(true)) {
        }else{
            multiStatusView.showNoNetWork();
            multiStatusView.showOnNetTop();
        }
*/
    }




    /**
     * 处理 loadmore 结果
     * @param adapter a
     * @param result r
     * @param visibleMoreEnd true 不显示加载结束的no more view
     */
    public void loadMoreState(BaseQuickAdapter adapter, List<?> result, boolean visibleMoreEnd){
        if (result !=null){
            if (result.size() != 0 ){
                adapter.loadMoreComplete();
            }else {
                adapter.loadMoreEnd(visibleMoreEnd);
            }
        }else {
            adapter.notifyLoadMoreToLoading();
        }
    }


    private void animateOut(final View button) {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).translationY(button.getHeight() + getMarginBottom(button)).withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                        }

                        @Override
                        public void onAnimationCancel(View view) {
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            view.setVisibility(View.GONE);
                        }
                    }).start();
        } else {

        }
    }
    private int getMarginBottom(View v) {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    private void animateIn(View button) {
        button.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).translationY(0)
                     .withLayer().setListener(null)
                    .start();
        } else {

        }
    }

    /**
     * 增加返回按钮事件
     */
    public void addFloatEvent(){
        if (mFloatActionButton == null){
            return;
        }
        if(isStartFloatActionButton()){
            mFloatActionButton.setVisibility(View.VISIBLE);
        }else{
            mFloatActionButton.setVisibility(View.GONE);
        }
        mFloatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerView!=null){
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });
    }

    /**
     * 默认不开启回到顶部悬停按钮
     * @return
     */
    public boolean isStartFloatActionButton(){
        return false;
    }

}