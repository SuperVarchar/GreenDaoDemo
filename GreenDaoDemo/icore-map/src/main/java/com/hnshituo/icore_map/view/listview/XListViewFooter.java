package com.hnshituo.icore_map.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnshituo.icore_map.R;


/**
 * 脚布局
 * @author Wzh
 * @date 2016/7/5  11:56
 */
public class XListViewFooter extends LinearLayout {

    private Context context;
    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;

    public XListViewFooter(Context context) {
        super(context);
        this.initView(context);
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public void setState(int state) {
        this.mHintView.setVisibility(INVISIBLE);
        this.mProgressBar.setVisibility(INVISIBLE);
        this.mHintView.setVisibility(INVISIBLE);
        if(state == 1) {
            this.mHintView.setVisibility(VISIBLE);
            this.mHintView.setText(context.getString(R.string.xlistview_footer_hint_ready));
        } else if(state == 2) {
            this.mProgressBar.setVisibility(VISIBLE);
        } else {
            this.mHintView.setVisibility(VISIBLE);
            this.mHintView.setText(context.getString(R.string.xlistview_footer_hint_normal));
        }

    }

    public void setBottomMargin(int height) {
        if(height >= 0) {
            LayoutParams lp = (LayoutParams)this.mContentView.getLayoutParams();
            lp.bottomMargin = height;
            this.mContentView.setLayoutParams(lp);
        }
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams)this.mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    public void normal() {
        this.mHintView.setVisibility(VISIBLE);
        this.mProgressBar.setVisibility(GONE);
    }

    public void loading() {
        this.mHintView.setVisibility(GONE);
        this.mProgressBar.setVisibility(VISIBLE);
    }

    public void hide() {
        LayoutParams lp = (LayoutParams)this.mContentView.getLayoutParams();
        lp.height = 0;
        this.mContentView.setLayoutParams(lp);
        this.mContentView.setVisibility(GONE);
    }

    public void show() {
        LayoutParams lp = (LayoutParams)this.mContentView.getLayoutParams();
        lp.height = -2;
        this.mContentView.setLayoutParams(lp);
        this.mContentView.setVisibility(VISIBLE);
    }

    private void initView(Context context) {
        this.context = context;
        RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_listview_footer,null);
        this.addView(moreView);
        moreView.setLayoutParams(new LayoutParams(-1, -2));
        this.mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        this.mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        this.mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
    }
}
