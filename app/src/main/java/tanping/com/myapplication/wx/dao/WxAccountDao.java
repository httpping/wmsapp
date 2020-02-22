package tanping.com.myapplication.wx.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tanping.com.myapplication.wx.bean.WxAccountInfo;
import tanping.com.myapplication.wx.bean.WxMsgInfo;

@Dao
public interface WxAccountDao {

    @Query("SELECT * FROM wx_account")
    List<WxAccountInfo> findAll();


    @Query("SELECT * FROM wx_account where wx_user_account = :account limit 1")
    WxAccountInfo findWxByAccount(String account);


    /**
     * 冲突替换
     * @param wxAccountInfo
     */
    @Insert()
    void insert(WxAccountInfo wxAccountInfo);

    /**
     * 默认更新
     * @param wxAccountInfo
     */
    @Update
    void update(WxAccountInfo wxAccountInfo);

    @Delete
    void delete(WxAccountInfo wxAccountInfo);



}