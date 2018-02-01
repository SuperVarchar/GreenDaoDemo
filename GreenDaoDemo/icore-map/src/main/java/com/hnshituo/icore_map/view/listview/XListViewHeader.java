package com.hnshituo.icore_map.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hnshituo.icore_map.R;


/**
 * 头布局
 * @author Wzh
 * @date 2016/7/5  11:59
 */
public class XListViewHeader extends LinearLayout {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private View mProgressBar;
    private TextView mHintTextView;
    private int mState = 0;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private Context context;

    public XListViewHeader(Context context) {
        super(context);
        this.initView(context);
    }

    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutParams lp = new LayoutParams(-1, 0);
        this.mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_listview_head,null);
        this.addView(this.mContainer, lp);
        this.setGravity(80);
        this.mArrowImageView = (ImageView)this.findViewById(R.id.head_arrowImageView);
        this.mHintTextView = (TextView)this.findViewById(R.id.head_tipsTextView);
        this.mProgressBar = this.findViewById(R.id.head_progressBar);
        this.mRotateUpAnim = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
        this.mRotateUpAnim.setDuration(180L);
        this.mRotateUpAnim.setFillAfter(true);
        this.mRotateDownAnim = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
        this.mRotateDownAnim.setDuration(180L);
        this.mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        if(state != this.mState) {
            if(state == 2) {
                this.mArrowImageView.clearAnimation();
                this.mArrowImageView.setVisibility(INVISIBLE);
                this.mProgressBar.setVisibility(VISIBLE);
            } else {
                this.mArrowImageView.setVisibility(INVISIBLE);
                this.mProgressBar.setVisibility(INVISIBLE);
            }

            switch(state) {
                case 0:
                    if(this.mState == 1) {
                        this.mArrowImageView.startAnimation(this.mRotateDownAnim);
                    }

                    if(this.mState == 2) {
                        this.mArrowImageView.clearAnimation();
                    }

                    this.mHintTextView.setText(context.getString(R.string.pull_to_refresh_pull_label));
                    break;
                case 1:
                    if(this.mState != 1) {
                        this.mArrowImageView.clearAnimation();
                        this.mArrowImageView.startAnimation(this.mRotateUpAnim);
                        this.mHintTextView.setText(context.getString(R.string.pull_to_refresh_release_label));
                    }
                    break;
                case 2:
                    this.mHintTextView.setText(context.getString(R.string.pull_to_refresh_refreshing_label));
            }

            this.mState = state;
        }
    }

    public void setVisiableHeight(int height) {
        if(height < 0) {
            height = 0;
        }

        LayoutParams lp = (LayoutParams)this.mContainer.getLayoutParams();
        lp.height = height;
        this.mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return this.mContainer.getHeight();
    }
}
