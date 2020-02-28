package com.scanwx.app.wx.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.scanwx.app.wx.bean.WxAccountInfo;
import com.scanwx.app.wx.bean.WxMsgInfo;

@Database(entities = {WxMsgInfo.class,WxAccountInfo.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WxMsgDao wxMsgDao();

    public abstract WxAccountDao wxAccountDao();

}