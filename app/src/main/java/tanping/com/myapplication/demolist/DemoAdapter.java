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

import android.graphics.Color;

import com.chad.library.adapter.base.BaseViewHolder;
import com.gb.bind.adapter.GBBaseBindAdapter;
import com.gb.bind.annotations.BindItem;

import java.util.List;

import tanping.com.myapplication.R;
import tanping.com.myapplication.bean.BannerBean;
import tanping.com.myapplication.bean.GoodsBean;

import static tanping.com.myapplication.demolist.DemoAdapterEntity.TYPE_ONE;
import static tanping.com.myapplication.demolist.DemoAdapterEntity.TYPE_THREE;
import static tanping.com.myapplication.demolist.DemoAdapterEntity.TYPE_TWO;


/**
 * 项目名称: YOSHOP
 * 类描述：
 * 创建人：Created by tanping
 * 创建时间:2018/7/25 14:15
 * @author tanping
 */
public class DemoAdapter extends GBBaseBindAdapter<DemoAdapterEntity,BaseViewHolder>  {
    public DemoAdapter(List<DemoAdapterEntity> data) {
        super(data);
    }

    /**
     * banner 测试类型
     * @param helper
     * @param bean
     * @throws Exception
     */
    @BindItem(type = TYPE_ONE,layout = R.layout.item_one)
    public void one(BaseViewHolder helper, DemoAdapterEntity bean, int postition){
        BannerBean bannerBean = (BannerBean) bean.value;
        helper.itemView.setBackgroundColor(Color.parseColor("#80ff0000"));
    }

    /**
     * 商品测试类型
     * @param helper
     * @param bean
     * @throws Exception
     */
    @BindItem(type = TYPE_TWO,layout = R.layout.item_two)
    public void two(BaseViewHolder helper, DemoAdapterEntity bean) throws Exception {
        GoodsBean goodsBean = (GoodsBean) bean.value;
        helper.itemView.setBackgroundColor(Color.parseColor("#80999999"));
    }

    /**
     * 测试类型3
     * @param helper
     * @param bean
     * @throws Exception
     */
    @BindItem(type= TYPE_THREE,layout = R.layout.item_three)
    public void three(BaseViewHolder helper, DemoAdapterEntity bean) throws Exception {
        String value = (String) bean.value;
        helper.setText( R.id.textView,"type：" + TYPE_THREE+" - "+value);
    }


    @Override
    public boolean isOpenAutoBindView() {
        return  true;
    }



}
