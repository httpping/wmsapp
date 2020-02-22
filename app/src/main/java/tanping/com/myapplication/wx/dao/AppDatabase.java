package tanping.com.myapplication.wx.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import tanping.com.myapplication.wx.bean.WxAccountInfo;
import tanping.com.myapplication.wx.bean.WxMsgInfo;

@Database(entities = {WxMsgInfo.class,WxAccountInfo.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WxMsgDao wxMsgDao();

    public abstract WxAccountDao wxAccountDao();

}