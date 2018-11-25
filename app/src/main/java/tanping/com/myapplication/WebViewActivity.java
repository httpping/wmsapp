package tanping.com.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.utils.file.FilePathUtils;
import com.utils.ui.ToastUtil;

import tanping.com.myapplication.bean.JsRequest;
import tanping.com.myapplication.bean.JsResponse;
import tanping.com.myapplication.utils.FileOptions;
import tanping.com.myapplication.web.BaseBrowserActivity;
import tanping.com.myapplication.web.JavaJsOptions;
public class WebViewActivity extends BaseBrowserActivity  {

    private static final int REQUEST_EXTERNAL_STORAGE = 1000;
    RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Must be done during an initialization phase like onCreate
   /*     rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        JsResponse result = FileOptions.getInstance(this).writeFile("abc.json","谭平最帅");
                        ToastUtil.showToast(this,result.code+"");

                        result = FileOptions.getInstance(this).readFile("abc.json");
                        ToastUtil.showToast(this,result.code+" :" + result.data);
                    } else {
                        // Oups permission denied
                        finish();
                    }
                });*/



        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

//        if (permission != PackageManager.PERMISSION_GRANTED ||) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);
//        }


        webView.addJavascriptInterface(new JavaJsOptions(this),"tp");


//        webView.loadUrl("https://www.baidu.com/");

        webView.loadUrl("file:///android_asset/test.html");


//        String path = getSDcardRootPath();
//        ToastUtil.showToast(this,path);

//        FilePathUtils.getInstance(this).getVaildStoragePriorityOut();

    }

    public static boolean hasStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getSDcardRootPath() {
        if (hasStorage()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }



    /* 改写物理按键返回的逻辑 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 返回上一页面
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE){
            JsResponse result = null;



            result = FileOptions.getInstance(this).writeFile("abc3.json","测试吧");
            ToastUtil.showToast(this,result.code+"");

            result = FileOptions.getInstance(this).readFile("abc1.json");
            ToastUtil.showToast(this,result.code+" :" + result.data);
        }
    }


}
