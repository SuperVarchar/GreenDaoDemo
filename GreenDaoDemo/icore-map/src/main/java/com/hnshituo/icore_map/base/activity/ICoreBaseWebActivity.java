package com.hnshituo.icore_map.base.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.view.view.ProgressWebView;

import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;

/**
 * 公用的打开网页的Activity
 *
 * @author Wzh
 * @date 2016/7/30  10:08
 */
public class ICoreBaseWebActivity extends ICoreBaseActivity {

    ProgressWebView webview;
    private String title;
    private String url;
    public static final String TITLE_TO_INTENT = "title";
    public static final String URL_TO_INTENT = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra(TITLE_TO_INTENT);
        url = getIntent().getStringExtra(URL_TO_INTENT);
        setContentView(R.layout.activity_web);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        initData();
    }

    @Override
    protected void sessionTimeOutDeal() {

    }

    public void initData() {
        clearCookie();
        setBackButton();
        setTitleText(title, null);
        synCookies(url);
        initWebView();
        webview.loadUrl(url);// 加载网页
    }

    private void initWebView() {
        webview.setProgressBarDrawable(R.drawable.progressbar_mini);
        WebSettings settings = webview.getSettings();
        settings.setAllowFileAccess(true);
        // 如果访问的页面中有Javascript，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(true);
        //settings.setUseWideViewPort(true);
        //settings.setLoadWithOverviewMode(true);
         settings.setSupportZoom(true);// 支持缩放
         /*settings.setBuiltInZoomControls(false);// 设置支持缩放
         settings.setNeedInitialFocus(true);//*/
        webview.setBackgroundColor(getResources().getColor(R.color.white)); // 设置背景色
        webview.setHorizontalScrollBarEnabled(false);// 设置是否显示水平滚动条
        webview.setVerticalScrollBarEnabled(false);// 设置是否显示垂直滚动条
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                synCookies(url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }

    @Override
    protected void onDestroy() {
        clearCookie();
        webview.reload();
        webview.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (webview.canGoBack()) {
            webview.goBack();// 返回前一个页面
        } else {
            super.onBackPressedSupport();
        }
    }

    /**
     * 设置Cookie
     *
     * @param url
     */
    public void synCookies(String url) {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        List<Cookie> cookies = OkHttpUtils.getInstance().getCookieStore().getCookies();
        for (Cookie cookie : cookies) {
            StringBuffer sb = new StringBuffer();
            sb.append(cookie.name() + "=" + cookie.value() + ";");
            sb.append("domain=" + cookie.domain() + ";");
            sb.append("path=" + cookie.path() + ";");
            cookieManager.setCookie(url, sb.toString());
        }
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 清除Cookie
     */
    private void clearCookie() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie();
    }

}
