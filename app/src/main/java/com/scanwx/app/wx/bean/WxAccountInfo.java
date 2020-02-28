package com.scanwx.app.wx.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * 微信信息bean
 */

@Entity(tableName = "wx_account",indices = {@Index(value = {"wx_user_account"},unique = true),
        @Index(value = {"created_time"})})
public class WxAccountInfo {

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;



    /**
     * 微信用户
     */
    private String wx_user_account;

    /**
     * 别名
     */
    private String wx_user_alias ;

    /**
     * 微信头像
     */
    private String wx_user_avater ;


    /**
     * 原始文件路劲
     */
    private String origin_file_path;

    /**
     * 文件名
     */
    private String file_name;

    /**
     * 更新时间
     */
    private long created_time;

    /**
     * 扫描时间
     */
    private long update_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWx_user_account() {
        return wx_user_account;
    }

    public void setWx_user_account(String wx_user_account) {
        this.wx_user_account = wx_user_account;
    }

    public String getWx_user_alias() {
        return wx_user_alias;
    }

    public void setWx_user_alias(String wx_user_alias) {
        this.wx_user_alias = wx_user_alias;
    }

    public String getWx_user_avater() {
        return wx_user_avater;
    }

    public void setWx_user_avater(String wx_user_avater) {
        this.wx_user_avater = wx_user_avater;
    }

    public String getOrigin_file_path() {
        return origin_file_path;
    }

    public void setOrigin_file_path(String origin_file_path) {
        this.origin_file_path = origin_file_path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
}
