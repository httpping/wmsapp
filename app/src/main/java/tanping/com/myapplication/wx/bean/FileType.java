package tanping.com.myapplication.wx.bean;


/**
 * 所有文件的分类
 */
public class FileType {


    /**
     * 无后缀
     */
    public  final static  int Null = 1;

    /**
     * jpg、png 等， 图片
     */
    public  final static  int IMG = 2;

    /**
     * 文档
     */
    public  final static  int DOC = 3;

    /**
     * 音频
     */
    public  final static  int Voice = 3;

    /**
     * 视频
     */
    public  final static  int video = 3;


    /**
     * 主要支持的文件格式
     */
    public static final String[] nulls ={""};
    public static final String[] IMGS ={"jpg","png"};
    public static final String[] DOCS ={"pdf","xls","txt","doc"};
    public static final String[] Voices ={"amr","silk","mp3"};
    public static final String[] videos ={"mp4"};


    /**
     * 获取文件类型，是否在搜索范围内
     * @param filename
     * @return
     */
    public static int queryType(String filename){

        String extend = FileNameUtil.getExtendName(filename);

        for (String str :nulls){
            if (extend.equalsIgnoreCase(str)){
                return Null;
            }
        }
        for (String str :IMGS){
            if (extend.equalsIgnoreCase(str)){
                return IMG;
            }
        }

        for (String str :DOCS){
            if (extend.equalsIgnoreCase(str)){
                return DOC;
            }
        }

        for (String str :Voices){
            if (extend.equalsIgnoreCase(str)){
                return Voice;
            }
        }

        for (String str :videos){
            if (extend.equalsIgnoreCase(str)){
                return video;
            }
        }

        return -1;

    }


}
