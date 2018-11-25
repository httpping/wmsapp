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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.framework.base.BaseListFragment;
import com.framework.remote.ApiManager;
import com.framework.remote.config.RequestParam;
import com.framework.remote.config.XSubscriber;
import com.tp.cache.CacheManager;
import com.utils.log.NetLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tanping.com.myapplication.bean.BannerBean;
import tanping.com.myapplication.bean.GoodsBean;

import static tanping.com.myapplication.demolist.DemoAdapterEntity.TYPE_ONE;
import static tanping.com.myapplication.demolist.DemoAdapterEntity.TYPE_TWO;


/**
 * 类描述：
 * 创建人：Created by tanping
 * 创建时间:2018/7/31 10:29
 */
public class DemoFragment extends BaseListFragment implements BaseQuickAdapter.RequestLoadMoreListener  {

    private DemoAdapter adapter;
    List<DemoAdapterEntity> mData = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        multiStatusView.showContent();
        //限制
        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) ;
        mRecyclerView.setLayoutManager(manager);
//
        adapter = new DemoAdapter(mData);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this,mRecyclerView);

        testData();

        String key = "data-demo";
//        CacheManager.delete(key);
        CacheManager.init(getActivity().getApplicationContext());
        CacheManager.put("data-demo",mData);
        List data =  CacheManager.get("data-demo");

        data =  CacheManager.get("data-demo23");

        long count = CacheManager.count();
        testNet();


    }



    public void testNet(){

        RequestParam param = new RequestParam();
        param.put("key1","tanping");

        ApiManager.Api().initialization(param.createRequestBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new XSubscriber<String>(getActivity(),true) {

                    @Override
                    public void showDialog() {
                        multiStatusView.showLoading();
                    }

                    @Override
                    public void onNext(String s) {
                        multiStatusView.showContent();
                        NetLog.d("s : "+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        NetLog.d("x");
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        testNet();
    }

    /**
     * 测试数据
     */
    public void testData(){
        for (int i=0;i<10;i++){
            DemoAdapterEntity bean = new DemoAdapterEntity();
            //任意类型
            bean.type = (int) (Math.random()*3) + 1 ;

            if (bean.type == TYPE_ONE){
                BannerBean bannerBean = new BannerBean();
                bannerBean.bannerName =" bannner :" + i;
                bean.value = bannerBean;
            }else if (bean.type == TYPE_TWO){
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.price = (float) (Math.random()*100);
                bean.value = goodsBean;
            }else {
                bean.value = "value:" + bean.type;
            }
            mData.add(bean);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mData.size()>50){
                    adapter.loadMoreEnd();
                    return;
                }
                testData();
                adapter.loadMoreComplete();

            }
        },5200);

    }

    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().post(TriggerInfo.created("DemoAdapter",getActivity()));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
