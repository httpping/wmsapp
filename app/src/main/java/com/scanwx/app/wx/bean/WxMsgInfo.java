package com.scanwx.app.wx.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * 微信信息bean
 */

@Entity(tableName = "wx_msg",indices = {@Index(value = {"name_md5","data_type"},unique = true),
        @Index(value = {"created_time"})})
public class WxMsgInfo {

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;


    /**
     * 排重key,根据文件名md5得出 唯一
     *
     */

    private String name_md5;


    /**
     * 微信用户
     */
    private String wx_user ;

    /**
     * 文件类型
     */
    private int file_type;
    /**
     * 文件数据类型，属于什么组
     */
    private int data_type;

    /**
     * 文件路径
     */
    private String file_path;



    /**
     * 标记，标签，用于区分数据群
     * @return
     */
    private String file_tag;


    /**
     * 原始文件路劲
     */
    private String origin_file_path;

    /**
     * 文件名
     */
    private String file_name;

    /**
     * 文件大小
     */
    private long file_size;

    /**
     * 缩略图,为页面数据提速使用，基础数据不提供
     */
    private int voice_time ;


    /**
     * 缩略图,为页面数据提速使用，基础数据不提供
     */
    private String thumbnail;


    /**
     * 文件用户，后面分析文件名分析出
     */
    private String file_user;


    /**
     * 原始文件md5
     */
    private String origin_file_md5;

    /**
     * 文件扩展名
     */
    private String file_extend_name;


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

    public String getName_md5() {
        return name_md5;
    }

    public String getOrigin_file_path() {
        return origin_file_path;
    }

    public void setOrigin_file_path(String origin_file_path) {
        this.origin_file_path = origin_file_path;
    }

    public String getOrigin_file_md5() {
        return origin_file_md5;
    }

    public void setOrigin_file_md5(String origin_file_md5) {
        this.origin_file_md5 = origin_file_md5;
    }

    public void setName_md5(String name_md5) {
        this.name_md5 = name_md5;
    }

    public String getWx_user() {
        return wx_user;
    }

    public void setWx_user(String wx_user) {
        this.wx_user = wx_user;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public int getVoice_time() {
        return voice_time;
    }

    public void setVoice_time(int voice_time) {
        this.voice_time = voice_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFile_user() {
        return file_user;
    }

    public void setFile_user(String file_user) {
        this.file_user = file_user;
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

    public String getFile_extend_name() {
        return file_extend_name;
    }

    public void setFile_extend_name(String file_extend_name) {
        this.file_extend_name = file_extend_name;
    }

    public String getFile_tag() {
        return file_tag;
    }

    public void setFile_tag(String file_tag) {
        this.file_tag = file_tag;
    }
}
