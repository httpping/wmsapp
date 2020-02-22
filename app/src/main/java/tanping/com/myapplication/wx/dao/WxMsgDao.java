package tanping.com.myapplication.wx.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tanping.com.myapplication.wx.bean.WxMsgInfo;

@Dao
public interface WxMsgDao {

    @Query("SELECT * FROM wx_msg ")
    List<WxMsgInfo> findWxMsg();


    @Query("SELECT * FROM wx_msg where data_type = :dataType")
    List<WxMsgInfo> findWxDataTypeMsg(int dataType);

    @Delete
    int delete(WxMsgInfo wxMsgInfo);


    /**
     * 获取最后的扫描时间
     * @param wxUser
     * @return
     */
    @Query("SELECT * FROM wx_msg where wx_user = :wxUser order by update_time desc limit 1")
    WxMsgInfo findWxMsgLastScanTime(String wxUser);


    @Query("SELECT * FROM wx_msg group by wx_user,data_type")
    List<WxMsgInfo> totalInfo();

    @Insert
    void insert(WxMsgInfo wxMsgInfo);


    /**
     * 查询微信文件是否存在
     * @param nameMd5
     * @param dataType
     * @return
     */
    @Query("SELECT * FROM wx_msg where  name_md5= :nameMd5 and  data_type = :dataType limit 1")
    WxMsgInfo findMsgByNameAndDataType(String nameMd5 , int dataType);

    /**
     * 默认更新
     * @param wxMsgInfo
     */
    @Update
    void update(WxMsgInfo wxMsgInfo);

}