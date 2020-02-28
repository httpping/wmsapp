package com.scanwx.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.framework.base.BaseActivity;
import com.scanwx.app.demolist.DemoFragment;
import com.scanwx.app.wx.bean.WxMsgInfo;
import com.scanwx.app.wx.core.ScanListener;
import com.scanwx.app.wx.core.WxScanMsgOptions;
import com.scanwx.app.wx.thread.MonitorThread;
import com.scanwx.app.wx.thread.MyThreadPoolExecutor;
import com.scanwx.app.wx.thread.ThreadManager;
import com.utils.log.NetLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutorService;

import tanping.com.myapplication.R;

public class TestActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 1;


    boolean isrun = false;
    long startTime ;

    TextView showTime ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        showTime = findViewById(R.id.show_time);
        findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanBtn();
            }
        });
    }

    public void scanBtn(){
        startTime = System.currentTimeMillis();
        showTime.setText("进行中");
        // 如果未获得外部存储读写权限，则申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }else {
            scan();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 允许权限，将文件的路径写在下面的方法里；
//                    WxScanMsgOptions.searchTencent();
                    scan();
                } else {
                    // 拒绝权限

                }
        }
    }

    public static  final String tag  = "XXXXX";
    public void scan(){
        AppApplication app = (AppApplication) getApplication();


        final long startTime = System.currentTimeMillis();

        ExecutorService pool = ThreadManager.getSingletonPool(8, 100, new MonitorThread() {
            @Override
            public void beforeExecute(MyThreadPoolExecutor executor, Thread t, Runnable r) {
                NetLog.d("开始 ： "+System.currentTimeMillis());
            }

            @Override
            public void afterExecute(MyThreadPoolExecutor executor, Runnable r, Throwable t) {
                NetLog.d("开始-结束 ： "+ (System.currentTimeMillis()-startTime));
                if (t!=null) {
                    t.printStackTrace();
                }
            }

            @Override
            public void terminated(MyThreadPoolExecutor executor) {

            }
        });

        ExecutorService runPool = ThreadManager.getPrototypePool(8, 16, new MonitorThread() {

            @Override
            public void afterExecute(MyThreadPoolExecutor executor, Runnable r, Throwable t) {
                if (t != null) {
                    t.printStackTrace();
                }
            }

            @Override
            public void terminated(MyThreadPoolExecutor executor) {
                NetLog.d("runPool开始-结束 ： "+ (System.currentTimeMillis()-startTime));
                mHandler.sendEmptyMessage(1);

            }
        });

        pool.submit(new Runnable() {
            @Override
            public void run() {
                wxScanMsgOptions = new WxScanMsgOptions(app, new ScanListener() {
                    @Override
                    public boolean change(WxScanMsgOptions wxScanMsgOptions, WxMsgInfo msgInfo, WxMsgInfo wxMsgInfo, int size) {
                        NetLog.d("开始-结束 WxScanMsgOptions size ： "+wxScanMsgOptions.scanSize+"");

                        return false;
                    }

                }
                        ,runPool);

                wxScanMsgOptions.isAllRefresh = true;
                try {
                    wxScanMsgOptions.searchTencent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runPool.shutdown();
            }
        });


    }

    public void subEnd(){
        long cur = System.currentTimeMillis() - startTime;
        cur = cur/1000;
        int size = 0;
        if (wxScanMsgOptions!=null){
            size = wxScanMsgOptions.scanSize;
        }

        showTime.setText("花费："+cur+" 秒 , size : "+size);
    }


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            subEnd();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    WxScanMsgOptions wxScanMsgOptions;
}
