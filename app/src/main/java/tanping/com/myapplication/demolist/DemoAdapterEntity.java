package tanping.com.myapplication.demolist;
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


import com.gb.bind.adapter.GbMultiItemEntity;

/**
 * 类描述：
 * 创建人：Created by tanping
 * 创建时间:2018/7/25 14:15
 * @author tanping
 */
public class DemoAdapterEntity<T> implements GbMultiItemEntity   {

    public static final int TYPE_ONE =1;
    public static final int TYPE_TWO =2;
    public static final int TYPE_THREE =3;

    public int type = 0;
    public T value;

    @Override
    public int getItemType() {
        return type;
    }


    @Override
    public Object getItemValue() {
        return value;
    }

}
