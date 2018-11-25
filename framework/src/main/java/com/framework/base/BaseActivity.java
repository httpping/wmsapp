package com.framework.base;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tp.base.R;
import com.utils.sreen.DensityUtil;

import org.greenrobot.eventbus.EventBus;


/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY ="intent_bundle";

    /**
     * Material Design风格AppBarLayout控件
     */
    public AppBarLayout appBarLayout;
    /**
     * Material Design风格Toolbar控件
     */
    public Toolbar toolbar;


    /**
     *  表示自定义contentView
     * @return
     */
     public  boolean isDefaultContentView(){
         return  true;
     }

    /**
     * 获取content fragment
      * @return
     */
    public abstract Fragment getContentFragment();

    public void onToolbarNavigationClick() {
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        super.attachBaseContext(LanguageUtil.changeAppLanguage(newBase, BaseApplication.getInstance().getLocaleLanguage()));
//        super.attachBaseContext(LanguageUtil.changeAppLanguage(newBase, BaseApplication.getInstance().getLocaleLanguage(),BaseApplication.getInstance().getCountry()));
    }

    void changeAppLanguage() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeAppLanguage();
        //注册eventbus
        if (isRegisterEventBus()){
            try {
                EventBus.getDefault().register(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //默认布局
        if (isDefaultContentView()) {
            setContentView(R.layout.activity_ysbase_fragment);
            appBarLayout  = findViewById(R.id.appBarLayout);
            toolbar = findViewById(R.id.toolbar);

            Fragment fragment =  getContentFragment();
            //替换添加
            if (fragment !=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_content,fragment).commit();
            }
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onToolbarNavigationClick();
                }
            });
        }

        /**
         * 阴影去除
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (appBarLayout != null) {
                StateListAnimator stateListAnimator = new StateListAnimator();
                stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(appBarLayout, "elevation", DensityUtil.dip2px(this,2)));
                appBarLayout.setStateListAnimator(stateListAnimator);
            }
        }
        /**
         * 6.0 以上开启沉浸式
         */
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
            // 设置状态栏字体黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/

     }


    /***
     * 点击屏幕其他地方，使 etName 失去焦点（EditText）
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isInterceptClearFocus() && ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前焦点所在的控件；
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect vr = new Rect();
                view.getGlobalVisibleRect(vr);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                Rect tr = new Rect();
                if(toolbar != null){
                    toolbar.getGlobalVisibleRect(tr);
                }
                // 判断点击的点是否落在当前焦点所在的 view 上,并且不在toolBar上；
                if (!vr.contains(rawX, rawY) && !tr.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Nullable
    @Override
    public Uri getReferrer() {

        if (Build.VERSION.SDK_INT >= 22) {
            return super.getReferrer();
        }
        return getReferrerCompatible();
    }
    /**
     * Returns the referrer on devices running SDK versions lower than 22.
     */
    private Uri getReferrerCompatible() {
        Intent intent = this.getIntent();
        Uri referrerUri = intent.getParcelableExtra(Intent.EXTRA_REFERRER);
        if (referrerUri != null) {
            return referrerUri;
        }
        String referrer = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (referrer != null) {
            // Try parsing the referrer URL; if it's invalid, return null
            try {
                return Uri.parse(referrer);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 是否需要拦截事件，并清除EditText焦点
     *
     * @return true 需要拦截，否则不拦截
     * @author dingpeihua
     * @date 2017/8/19 17:38
     * @version 1.0
     */
    protected boolean isInterceptClearFocus() {
        return true;
    }


    /**
     * 是否注册eventbus 默认不注册
     * @return
     */
    public boolean isRegisterEventBus(){
        return false ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isRegisterEventBus()){
            try {
                EventBus.getDefault().unregister(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}