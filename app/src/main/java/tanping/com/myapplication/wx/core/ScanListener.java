package tanping.com.myapplication.wx.core;

import tanping.com.myapplication.wx.bean.WxMsgInfo;

/**
 * 浏览监听
 */
public interface ScanListener {

    /**
     * Old Msg存在，不执行更新和 插入操作节约扫描时间
     * 改变 false 则过滤掉不存储数据库
     * @param wxScanMsgOptions
     * @param msgInfo
     * @param oldMsg 数据库中的
     * @param size
     */
    default boolean change(WxScanMsgOptions wxScanMsgOptions, WxMsgInfo msgInfo, WxMsgInfo oldMsg, int size){
        if (oldMsg!=null){
            return false;
        }
        return true;
    }

    /**
     * 接收
     * @param wxScanMsgOptions
     */
    default boolean isEnd(WxScanMsgOptions wxScanMsgOptions){
        return true;
    }
}
