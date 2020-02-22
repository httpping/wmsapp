package tanping.com.myapplication.wx.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tanping on 2018/3/19.
 */

public class FileNameUtil {

    public  static boolean  isGif(String url){
        if (url == null)
            return  false;

        int index = url.toLowerCase().indexOf(".gif");
        return index > 0;

    }


    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    public static String getExtendName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index<=0){
            return "";
        }
        String suffix = fileName.substring(index + 1);
        return suffix;
    }


    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        String[] suffix = fileName.split("\\.");
        return suffix[0];
    }



    public static boolean isEmpty(String str){
        return str == null || str.trim().equals("");
    }
    public static boolean isNotEmpty(String str){
        return str != null && !str.trim().equals("") && !str.trim().equals("null");
    }




    public static boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }
    public static boolean isNotEmpty(List list){
        return list != null && list.size() != 0;
    }


    /**
     * 获取微信用户信息
     * @param files
     * @return
     */
    public  static List<File> getWxUserFileDir(File[] files){
        List<File> result = new ArrayList<>();
        for (File file: files){
            if (file.isDirectory()){
                String name = file.getName();

                // 32位md5
                if (name.length()==32){
                    result.add(file);
                }
            }

        }

        return result;
    }


    /**
     * 获取指定目录内所有文件路径
     *
     * @param dirPath 需要查询的文件目录
     * @param _type   查询类型，比如mp3什么的
     */
    public static List<File> getAllFiles(String dirPath, String _type, long lastModifyDate, List<File> results) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if (files == null) {//判断权限
            return null;
        }

        for (File _file : files) {//遍历目录
            if (_file.isFile() && (_type == null || _file.getName().endsWith(_type))) {

                // 修改时间增值
                long last =_file.lastModified();
                if (lastModifyDate == 0 || lastModifyDate<= last) {
                    results.add(_file);
                }
            } else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath(), _type, lastModifyDate, results);
            }
        }
        return results;
    }


}
