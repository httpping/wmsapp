package tanping.com.myapplication;

import android.app.Application;
 import android.arch.persistence.room.Room;

import tanping.com.myapplication.wx.dao.AppDatabase;

public class AppApplication extends Application {

    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wx_msg_info.db")
                           .allowMainThreadQueries()
                           .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }


}