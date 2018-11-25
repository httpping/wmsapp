package com.framework.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @Created by tanping
 *
 * 多状态View操作，以后重构用
 *
 */
public class MultiStatusView extends FrameLayout {

    public static final int VIEW_STATE_UNKNOWN = -1;
    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_ERROR = 1;
    public static final int VIEW_STATE_EMPTY = 2;
    public static final int VIEW_STATE_LOADING = 3;
    public static final int VIEW_STATE_NO_NETWORK = 4;
    public static final int VIEW_STATE_PROCESS = 5;
    public static final int VIEW_STATE_NO_NETWORK_TIP = 6;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VIEW_STATE_UNKNOWN, VIEW_STATE_CONTENT, VIEW_STATE_ERROR, VIEW_STATE_EMPTY, VIEW_STATE_LOADING, VIEW_STATE_NO_NETWORK, VIEW_STATE_PROCESS,VIEW_STATE_NO_NETWORK_TIP})
    public @interface ViewState {
    }

    //状态view
    public HashMap<Integer,View> mStatusView = new HashMap<>();

    public MultiStatusView(Context context ) {
        super(context);
        initView();
    }
    public MultiStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        createDefaultStatus();
    }


    /**
     * 显示状态
     * @param status
     */

    public int curStatus = -100;

    public void show(@ViewState int status){
        View view = getStatusView(status);
        if (view!=null && curStatus != status){
//            hideAll();
            removeAllViews();//全部删除
            view.setVisibility(VISIBLE);
            // 延迟加载view,缩小加载时间
            if (view.getParent() == null){
                addView(view);
            }
            curStatus = status;
            bringChildToFront(view);
        }
    }

    public void add(@ViewState int status,View view){
        mStatusView.put(status,view);
    }
    public void add(@ViewState int status, @LayoutRes int rid){
        View view = LayoutInflater.from(getContext()).inflate(rid,null);
        add(status,view);
    }


    /**
     * 显示状态
     * @param
     */
    public void showLoading(){
       show(VIEW_STATE_LOADING);
    }

    /**
     * 显示状态
     * @param
     */
    public void showContent(){
        show(VIEW_STATE_CONTENT);
    }


    /**
     * 显示状态
     * @param
     */
    public void showEmpty(){
        show(VIEW_STATE_EMPTY);
    }

    /**
     * 显示状态
     * @param
     */
    public void showError(){
        show(VIEW_STATE_ERROR);
    }

    /**
     * 显示状态
     * @param
     */
    public void showNoNetWork(){
        show(VIEW_STATE_NO_NETWORK);
    }

    /**
     * no net tip
     */
    public void showOnNetTop(){
        View view =getStatusView(VIEW_STATE_NO_NETWORK_TIP);
        if (view == null){
            return;
        }
        view.setVisibility(VISIBLE);
        removeView(view);
        addView(view);
        bringChildToFront(view);
    }

    /**
     * 移除掉狀態
     * @param status
     */
    public void removeStatus(@ViewState int status){
        View view =getStatusView(status);
        if (view != null) {
            removeView(view);
        }
    }

    /**
     * 隐藏所有 status view
     */
    public void hideAll(){
        Iterator<Integer> it = mStatusView.keySet().iterator();
        while (it.hasNext()){
            Integer key = it.next();
            View view =mStatusView.get(key);
            if (view !=null){
                view.setVisibility(GONE);
            }
        }
    }


    public View getStatusView(@ViewState int status){
        return  mStatusView.get(status);
    }


    private void createDefaultStatus(){
//        add(VIEW_STATE_LOADING,  R.layout.progress_scroll_loading_view);
//        add(VIEW_STATE_NO_NETWORK, R.layout.error_scroll_no_network_center_layout);
//        add(VIEW_STATE_NO_NETWORK_TIP, R.layout.cart_nonet_tip_view);

    }

    /**
     * 查找View
     * @param viewId
     * @param <VIEW>
     * @return
     */
    final public <VIEW extends View> VIEW findView(@IdRes int viewId) {
        Iterator<Integer> it = mStatusView.keySet().iterator();
        while (it.hasNext()){
            Integer key = it.next();
            View view = mStatusView.get(key);
            if (view !=null ){
                VIEW v = view.findViewById(viewId);
                if (v != null){
                    return v;
                }
            }
        }
        return  null;
    }

}