package com.framework.remote.config.base;

import android.content.Context;

import com.tp.cache.StringUtil;

/**
 * 网络返回结果封装
 * @param <T>
 */
public class NetResult<T> extends NetBaseBean {

    public int statusCode;
    public T result;
    public String msg;
    public int flag ;

    public boolean isSuccess() {
        if (statusCode == 200){
            return  true;
        }

        return false;
    }

    public void showError(Context context){
        if(StringUtil.isNotEmpty(msg)){
//            ToastUtil.show(msg);
        }else{
//            ToastUtil.showLongMessage(R.string.request_failed);
        }
    }

}
