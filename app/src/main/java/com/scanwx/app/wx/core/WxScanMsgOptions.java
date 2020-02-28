package com.scanwx.app.wx.core;
/*

                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG

*/

import com.scanwx.app.wx.thread.MonitorThread;
import com.scanwx.app.wx.thread.MyThreadPoolExecutor;
import com.scanwx.app.wx.thread.ThreadManager;
import com.utils.sign.MD5Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.scanwx.app.AppApplication;
import com.scanwx.app.wx.bean.DataType;
import com.scanwx.app.wx.bean.FileNameUtil;
import com.scanwx.app.wx.bean.FileType;
import com.scanwx.app.wx.bean.WxAccountInfo;
import com.scanwx.app.wx.bean.WxMsgInfo;

/**
 * 项目名称: z
 * 类描述：
 * 创建时间:2018/11/12 9:52
 *
 * @author tanping
 */
public class WxScanMsgOptions {

    public static String distory = "tencent";

    /**
     * 微信消息
     */
    public static String distoryMsg = "MicroMsg";

    /**
     * 微信文件
     */
    public static String distoryFile = "Download";


    /**
     * 微信图片和视频
     */
    public static String distoryFileImageOrVoice = "video";


    /**
     * 微信语音
     */
    public static String distoryVoice2 = "voice2";


    /**
     * 微信额外图片
     */
    public static String distoryImage2 = "image2";

    /**
     * 搜索数量
     */
    public int scanSize ;

    /**
     * 微信收藏
     */
    public static String distoryFavorite = "favorite";


    public static String root = "/storage/emulated";

    public static String root1 = "/storage/emulated/0/";
    public static String root2 = "/storage/emulated/999/";



    public static final String ERROR_REMIND = "没有发现可用的文件存储器";

    public static final String ERROR_FILE_WIRTE = "写文件异常";

    private static WxScanMsgOptions instance;

    public WxScanMsgOptions(AppApplication app, ScanListener scanListener,ExecutorService pool) {
        this.appApplication = app;
        this.scanListener = scanListener;
        this.pool = pool;
    }

    public AppApplication appApplication;
    public ScanListener scanListener;
    public boolean isAllRefresh = false;


    public WxScanMsgOptions(AppApplication appApplication, ScanListener scanListener,boolean isAllRefresh) {
        this.appApplication = appApplication;
        this.scanListener = scanListener;
        this.isAllRefresh = isAllRefresh;
    }

    ExecutorService pool;
    /**
     * 索索
     *
     * @return
     */
    public  String[] searchTencent() {



        searchTencent(root1,isAllRefresh);

        searchTencent(root2,isAllRefresh);


        if (scanListener !=null){
//            scanListener.isEnd(this);
        }
        return null;

    }


    /**
     * 索索
     *
     * @return
     */
    public  boolean searchTencent(String rootDir,boolean isAllRefresh) {

        //获取微信的消息根目录
        rootDir = rootDir + distory +"/"+distoryMsg;

        File file = new File(rootDir);
        if (!file.exists()){
            return false;
        }

        File[]  files = file.listFiles();

        //1、获取微信用户

        /**
         * 切换微信用户的
         */
        List<File> wxUsers = FileNameUtil.getWxUserFileDir(files);
        if (wxUsers.size() == 0){
            return false;
        }

        //download 的数据信息全部给最新更新的微信账号
        File lastWxUser = null;
        if (wxUsers.size()>1){
            long time =-1;
            for (File wxUser : wxUsers){
                if (wxUser.lastModified()> time){
                    time = wxUser.lastModified();
                    lastWxUser = wxUser;
                }

                WxAccountInfo wxAccountInfo = updateAndSaveWxAccount(wxUser);
                searchWxUserDir(wxAccountInfo,isAllRefresh);

            }
        }else {
            lastWxUser = wxUsers.get(0);
            WxAccountInfo wxAccountInfo = updateAndSaveWxAccount(lastWxUser);
            searchWxUserDir(wxAccountInfo,isAllRefresh);
        }

        final File curWx = lastWxUser;
        final String frootDir = rootDir;
        pool.submit(new Runnable() {
            @Override
            public void run() {
                //搜索公用的下载数据
                try {
                    searchDownload(curWx,frootDir,isAllRefresh);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        return true;


    }


    /**
     * wx账号信息
     * @param wxUser
     * @return
     */
    public WxAccountInfo updateAndSaveWxAccount(File wxUser){


        WxAccountInfo wxAccountInfo = new WxAccountInfo();
        String wxName = wxUser.getName();

        WxAccountInfo temp = appApplication.getAppDatabase().wxAccountDao().findWxByAccount(wxName);
        if (temp !=null){
            wxAccountInfo = temp;
        }

        wxAccountInfo.setWx_user_account(wxName);
        wxAccountInfo.setFile_name(wxName);
        wxAccountInfo.setCreated_time(wxUser.lastModified());
        wxAccountInfo.setUpdate_time(System.currentTimeMillis());
        wxAccountInfo.setOrigin_file_path(wxUser.getAbsolutePath());

        if (temp==null) {
            appApplication.getAppDatabase().wxAccountDao().insert(wxAccountInfo);
        }else {
            appApplication.getAppDatabase().wxAccountDao().update(wxAccountInfo);
        }

        return wxAccountInfo;
    }

    /**
     * 搜索下载目录，微信用户公用，下载目录
     * @param wxUser
     * @param path
     * @param isAllRefresh
     * @return
     */
    public boolean searchDownload(File wxUser,String path, boolean isAllRefresh){
        String wxName = wxUser.getName();
        String downloadDir = path +"/" + distoryFile;
        analysisType(wxName,downloadDir,DataType.FILEE,null,getLasModifyData(wxName,isAllRefresh));
        return true;
    }

    /**
     * 搜索微信信息
     * @param wxAccountInfo
     * @return
     */
    public boolean searchWxUserDir(WxAccountInfo wxAccountInfo,boolean isAllRefresh){

        final String wxName = wxAccountInfo.getFile_name();

        final String rootDir = wxAccountInfo.getOrigin_file_path();


        pool.submit(new Runnable() {
            @Override
            public void run() {
                //1、搜藏
                try {
                    sacnFavorite(isAllRefresh, wxName, rootDir, distoryFavorite, DataType.FAVORITE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                //语音
                try {
                    scanVoice(isAllRefresh, wxName, rootDir, distoryVoice2, DataType.VOICE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                //文件
                try {
                    scanWxFile(isAllRefresh, wxName, rootDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });






        return true;
    }

    /**
     * 搜索微信文件
     * @param isAllRefresh
     * @param wxName
     * @param rootDir
     */
    private void scanWxFile(boolean isAllRefresh, String wxName, String rootDir) {
        //distoryImage2
        String wxistoryFileImageOrVoice = rootDir +"/" + distoryFileImageOrVoice;
        analysisType(wxName,wxistoryFileImageOrVoice,DataType.FILEE,null,getLasModifyData(wxName,isAllRefresh));

        //额外的图片
        wxistoryFileImageOrVoice = rootDir +"/" + distoryImage2;
        analysisType(wxName,wxistoryFileImageOrVoice,DataType.FILEE,null,getLasModifyData(wxName,isAllRefresh));
    }

    /**
     * 搜索微信 语音
     * @param isAllRefresh
     * @param wxName
     * @param rootDir
     * @param distoryVoice2
     * @param voice
     */
    public void scanVoice(boolean isAllRefresh, String wxName, String rootDir, String distoryVoice2, int voice) {
        String wxVoice2 = rootDir + "/" + distoryVoice2;
        analysisType(wxName, wxVoice2, voice, null, getLasModifyData(wxName, isAllRefresh));
    }


    /**
     * 搜索微信收藏
     * @param isAllRefresh
     * @param wxName
     * @param rootDir
     * @param distoryFavorite
     * @param favorite
     */
    private void sacnFavorite(boolean isAllRefresh, String wxName, String rootDir, String distoryFavorite, int favorite) {
        String favoriteDir = rootDir + "/" + distoryFavorite;
        analysisType(wxName, favoriteDir, favorite, null, getLasModifyData(wxName, isAllRefresh));
    }


    /**
     * 获取微信名最后的搜索时间
     * @param wxName
     * @return
     */
    public long getLasModifyData(String wxName,boolean isAllRefresh){
        if (isAllRefresh){
            return 0;
        }


        //从数据库获取最后更新时间
        WxMsgInfo result = appApplication.getAppDatabase().wxMsgDao().findWxMsgLastScanTime(wxName);
        if (result!=null ){
            return result.getUpdate_time();
        }

        return 0;
    }


    /**
     * 分析
     * @param wxName
     * @param path
     * @param type
     * @param _type
     * @param lastModifyDate
     * @return
     */
    public boolean analysisType(String wxName , String path ,int type,String _type, long lastModifyDate){
        List<File> favoriteFile = new ArrayList<>();
        FileNameUtil.getAllFiles(path,null,lastModifyDate,favoriteFile);
        analysisFileResult(favoriteFile,wxName,type);
        return true;
    }


    /**
     * 分析归档
     * @param files
     * @param wxUser
     * @param dataType
     * @return
     */
    public   boolean analysisFileResult(List<File> files, String wxUser, int dataType){

        scanSize +=files.size();

        for (File file : files){

            int fileType = FileType.queryType(file.getName());
            if (fileType<0){
                continue;
            }

            WxMsgInfo wxMsgInfo = new WxMsgInfo();
            wxMsgInfo.setWx_user(wxUser);
            wxMsgInfo.setData_type(dataType);
            wxMsgInfo.setFile_extend_name(FileNameUtil.getExtendName(file.getName()));

            wxMsgInfo.setName_md5(MD5Util.MD516(FileNameUtil.getFileName(file.getName())));
            wxMsgInfo.setFile_type(fileType);
            wxMsgInfo.setFile_size(file.lastModified());
            wxMsgInfo.setFile_path(file.getAbsolutePath());
            wxMsgInfo.setCreated_time(file.lastModified());
            wxMsgInfo.setUpdate_time(System.currentTimeMillis());

            if (fileType == FileType.Null){
                //无类型文件
                continue;
            }else if (fileType == FileType.IMG){
                // 做转换吗？

            }else if (fileType == FileType.Voice){
                //语音是否要转化语音生成新文件存储
            }

            try {
               /* //存数据库
                WxMsgInfo oldMsg = appApplication.getAppDatabase().wxMsgDao().findMsgByNameAndDataType(wxMsgInfo.getName_md5(), wxMsgInfo.getData_type());

                boolean changeResult = true;
                if (scanListener !=null){
                    changeResult = scanListener.change(this,wxMsgInfo,oldMsg,scanSize);
                }
                if (changeResult && oldMsg!=null) {
                    appApplication.getAppDatabase().wxMsgDao().insert(wxMsgInfo);
                    scanSize++;
                }*/

            }catch (Exception e){
                e.printStackTrace();
            }
        }



        return true;

    }


}
