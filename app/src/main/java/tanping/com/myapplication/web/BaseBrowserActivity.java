package tanping.com.myapplication.web;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.framework.base.BaseActivity;
import com.tp.cache.StringUtil;
import com.utils.log.NetLog;

import tanping.com.myapplication.R;

/**
 * WebView 基础操作
 *
 * @author tanping
 * @version 1.0
 * @date 2018/8/18 13:54
 */
public abstract class BaseBrowserActivity extends BaseActivity {
    public WebView webView;
    public ProgressBar progressBar;
    /**
     * Toolbar显示标题
     */
    private String title;
    private String url;

    @Override
    public boolean isDefaultContentView() {
        return false;
    }
    @Override
    public Fragment getContentFragment() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tp_fragment_browser);
        webView = findViewById(R.id.webView1);
        progressBar = findViewById(R.id.progressBar);

        initWebView(webView);



        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // 获得网页的加载进度
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }

            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                super.shouldOverrideUrlLoading(view, request);

                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                webView.loadUrl(url);
                return true;
            }
        });

    }

    protected void initWebView(WebView webView) {
        //除线上环境外，开启chrome,android 调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.clearCache(true);
        WebSettings webSetting = webView.getSettings();
        //启用javascript支持 用于访问页面中的javascript
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);

        dealJavascriptLeak(webView);
        //设置脚本是否允许自动打开弹窗
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置在WebView内部是否允许访问文件
        webSetting.setAllowFileAccess(true);
        //设定true时会将viewport的meta tag启动。如果我们没有强制设定宽度，那么就会使用可视范围的最大视野，其意思就是荧幕的大小
        webSetting.setUseWideViewPort(true);
        //默认flase，为true表示当内容大于viewport时，系统将会自动缩小内容以适应屏幕宽度
        webSetting.setLoadWithOverviewMode(true);
        //使所有列的宽度不超过屏幕宽度。默认NARROW_COLUMNS
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //让WebView支持DOM storage API。默认false
        webSetting.setDomStorageEnabled(true);
        //让WebView支持缩放。false表示不支持
        webSetting.setSupportZoom(true);
        //启用WebView内置缩放功能。false表示关闭
        webSetting.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSetting.setDisplayZoomControls(false);
        webSetting.setDatabaseEnabled(true);

        //设置h5的缓存打开
        webSetting.setAppCacheEnabled(true);
        //缓存路径
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //http/https混合加载 防止有些网站出错
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        if (StringUtil.isNotEmpty(url)) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }

    public String getUrl() {
        return url;
    }

    /**
     * 获取是否可以返回上一页
     *
     * @author dingpeihua
     * @date 2018/8/18 11:29
     * @version 1.0
     */
    public final boolean canGoBack() {
        return webView != null && webView.canGoBack();
    }

    /**
     * 执行返回上一页
     */
    public final void goBack() {
        if (canGoBack()) {
            webView.goBack();
        }
    }

    /**
     * 重新加载当前url
     */
    public final void reload() {
        if (webView != null) {
            webView.reload();
        }
    }

    /**
     * 加载下一个url
     */
    public final void getForward() {
        if (webView != null && webView.canGoForward()) {
            NetLog.d("加载下一个url");
            webView.goForward();
        }
    }

    /**
     * 处理webview漏洞，删除危险API
     *
     * @param webView
     */
    public static void dealJavascriptLeak(WebView webView) {
        if (webView == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
