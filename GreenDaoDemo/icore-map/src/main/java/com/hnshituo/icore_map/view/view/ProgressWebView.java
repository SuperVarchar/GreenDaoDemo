package com.hnshituo.icore_map.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 带进度条的WebView
 * @author Wzh
 * @date 2016/7/30  10:15
 */
public class ProgressWebView extends WebView {
    private ProgressBar progressBar;

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    public ProgressWebView(Context context) {
        super(context);
        this.init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    private void init(Context context) {
        this.progressBar = new ProgressBar(context, (AttributeSet)null, 16842872);
        this.progressBar.setLayoutParams(new LayoutParams(-1, 3, 0, 0));
        this.addView(this.progressBar);
        this.setWebChromeClient(null);
    }

    public void setProgressBarDrawable(int drawable) {
        this.progressBar.setProgressDrawable(this.getResources().getDrawable(drawable));
        this.progressBar.setMinimumHeight(3);
    }

    public void setWebChromeClient(final WebChromeClient client) {
            super.setWebChromeClient(new WebChromeClient() {
                public void onReceivedTitle(WebView view, String title) {
                    if(client != null) {
                        client.onReceivedTitle(view, title);
                    }

                    super.onReceivedTitle(view, title);
                }

                public void onReceivedIcon(WebView view, Bitmap icon) {
                    if(client != null) {
                        client.onReceivedIcon(view, icon);
                    }

                    super.onReceivedIcon(view, icon);
                }

                public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
                    if(client != null) {
                        client.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
                    }

                    super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
                }

                public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                    if(client != null) {
                        client.onReceivedTouchIconUrl(view, url, precomposed);
                    }

                    super.onReceivedTouchIconUrl(view, url, precomposed);
                }

                public void onRequestFocus(WebView view) {
                    if(client != null) {
                        client.onRequestFocus(view);
                    }

                    super.onRequestFocus(view);
                }

                public void onProgressChanged(WebView view, int newProgress) {
                    if(newProgress == 100) {
                        ProgressWebView.this.progressBar.setVisibility(GONE);
                    } else {
                        if(ProgressWebView.this.progressBar.getVisibility() == GONE) {
                            ProgressWebView.this.progressBar.setVisibility(VISIBLE);
                        }

                        ProgressWebView.this.progressBar.setProgress(newProgress);
                    }

                    if(client != null) {
                        client.onProgressChanged(view, newProgress);
                    }

                    super.onProgressChanged(view, newProgress);
                }
            });
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams)this.progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        this.progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
