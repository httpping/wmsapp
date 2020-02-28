package com.scanwx.app.wx.bean;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.scanwx.app.wx.bean.DataType.FAVORITE;
import static com.scanwx.app.wx.bean.DataType.FILEE;
import static com.scanwx.app.wx.bean.DataType.VOICE;

/**
 * 所有微信数据的分类
 * 1、文件
 * 2、语音
 * 3、收藏
 */
@IntDef({
        FILEE,VOICE,FAVORITE
})
@Retention(RetentionPolicy.CLASS)
public @interface DataType {


    /**
     * 文件类型
     */
    public  final static  int FILEE = 1;

    /**
     * 语音
     */
    public  final static  int VOICE = 2;

    /**
     * 收藏
     */
    public  final static  int FAVORITE = 3;



}
