package tanping.com.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.framework.base.BaseActivity;

import java.util.List;

import tanping.com.myapplication.demolist.DemoFragment;
import tanping.com.myapplication.wx.bean.DataType;
import tanping.com.myapplication.wx.bean.WxMsgInfo;
import tanping.com.myapplication.wx.core.ScanListener;
import tanping.com.myapplication.wx.core.WxScanMsgOptions;

public class MainActivity extends BaseActivity {

    private final int REQUEST_CODE = 1;



    @Override
    public Fragment getContentFragment() {
        return new DemoFragment();
    }

    @Override
    public boolean isDefaultContentView() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // 如果未获得外部存储读写权限，则申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }else {
//            WxScanMsgOptions.searchTencent();
            scan();
        }
//        WxScanMsgOptions.getInstance(this);
//        WxScanMsgOptions.searchTencent();


    }


    public void roomTest(){
        WxMsgInfo wxMsgInfo = new WxMsgInfo();
        wxMsgInfo.setName_md5("234323"+System.currentTimeMillis());
        wxMsgInfo.setWx_user("33");

        AppApplication app = (AppApplication) getApplication();

        try {
            app.getAppDatabase().wxMsgDao().insert(wxMsgInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        List<WxMsgInfo> result = app.getAppDatabase().wxMsgDao().findWxMsg();

        List<WxMsgInfo> favs = app.getAppDatabase().wxMsgDao().findWxDataTypeMsg(DataType.FAVORITE);

        List<WxMsgInfo> voics = app.getAppDatabase().wxMsgDao().findWxDataTypeMsg(DataType.VOICE);

        List<WxMsgInfo> files = app.getAppDatabase().wxMsgDao().findWxDataTypeMsg(DataType.FILEE);

        List<WxMsgInfo> totalinfos = app.getAppDatabase().wxMsgDao().totalInfo();
        Log.i("tag","rest");

    }


    @Override
    protected void onResume() {
        super.onResume();

        roomTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // 请求运行时权限的回调方法
// requestCode：用于识别申请权限的请求码
// permissions：请求的权限
// grantResults：对应权限的请求结果
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

        new Thread(new Runnable() {
            @Override
            public void run() {

                WxScanMsgOptions wxScanMsgOptions = new WxScanMsgOptions(app, new ScanListener() {
                    @Override
                    public void change(WxScanMsgOptions wxScanMsgOptions, WxMsgInfo msgInfo, WxMsgInfo wxMsgInfo, int size) {
                        Log.d(tag,wxScanMsgOptions.scanSize+"");


                    }

                }
                );

                wxScanMsgOptions.searchTencent();
            }
        }).start();

    }
}
