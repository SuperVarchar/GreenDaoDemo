package com.hnshituo.icore_map.view.listview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.view.view.SlideView;

import java.util.Date;


/**
 * 自定义上拉刷新 下拉加载 ListView
 * @author Wzh
 * @date 2016/7/5  11:02
 */
public class XListView extends ListView implements AbsListView.OnScrollListener {
    private float mLastY = -1.0F;
    private Scroller mScroller;
    private OnScrollListener mScrollListener;
    private XListView.IXListViewListener mListViewListener;
    private XListViewHeader mHeaderView;
    private View mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight = 50;
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false;
    private XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;
    private int mTotalItemCount;
    private int mScrollBack;
    private SlideView mFocusedSlideView;

    public XListView(Context context) {
        super(context);
        this.initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initWithContext(context);
    }

    private void initWithContext(Context context) {
        this.mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);
        this.mHeaderView = new XListViewHeader(context);
        this.mHeaderView.setEnabled(false);
        this.mHeaderViewContent = this.mHeaderView.findViewById(R.id.head_contentLayout);
        this.mHeaderTimeView = (TextView)this.mHeaderView.findViewById(R.id.head_lastUpdatedTextView);
        Drawable drawable = this.getResources().getDrawable(R.drawable.ico_timerecorad_load);
        drawable.setBounds(0, 0, (int)this.getResources().getDimension(R.dimen.base10dp),(int)this.getResources().getDimension(R.dimen.base10dp));
        this.mHeaderTimeView.setCompoundDrawables(drawable, (Drawable)null, (Drawable)null, (Drawable)null);
        this.setRefreshTime("\t" + (new Date()).toLocaleString());
        this.addHeaderView(this.mHeaderView);
        this.mFooterView = new XListViewFooter(context);
        this.mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                XListView.this.mHeaderViewHeight = Math.round(TypedValue.applyDimension(1, (float)XListView.this.mHeaderViewHeight, XListView.this.getResources().getDisplayMetrics()));
                XListView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public void setAdapter(ListAdapter adapter) {
        if(!this.mIsFooterReady && this.mEnablePullLoad) {
            this.mIsFooterReady = true;
            this.addFooterView(this.mFooterView);
        }

        super.setAdapter(adapter);
    }

    public void setPullRefreshEnable(boolean enable) {
        this.mEnablePullRefresh = enable;
        if(!this.mEnablePullRefresh) {
            this.mHeaderViewContent.setVisibility(INVISIBLE);
            this.mPullRefreshing = true;
        } else {
            this.mHeaderViewContent.setVisibility(VISIBLE);
            this.mPullRefreshing = false;
        }

    }

    public void setPullLoadEnable(boolean enable) {
        this.mEnablePullLoad = enable;
        if(!this.mEnablePullLoad) {
            this.mPullLoading = true;
            if(this.mIsFooterReady) {
                this.mIsFooterReady = false;
                this.removeFooterView(this.mFooterView);
            }

            this.mFooterView.hide();
            this.mFooterView.setOnClickListener(null);
        } else {
            this.mPullLoading = false;
            if(!this.mIsFooterReady) {
                this.mIsFooterReady = true;
                this.addFooterView(this.mFooterView);
            }

            this.mFooterView.show();
            this.mFooterView.setState(0);
            this.mFooterView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    XListView.this.startLoadMore();
                }
            });
        }

    }

    public void onLoad() {
        this.stopRefresh();
        this.stopLoadMore();
        this.setRefreshTime("\t" + (new Date()).toLocaleString());
    }

    public void startRefresh() {
        if(!this.mPullRefreshing) {
            this.mPullRefreshing = true;
            this.mHeaderView.setVisiableHeight(this.mHeaderViewHeight);
            this.mHeaderView.setState(2);
            if(this.mListViewListener != null) {
                this.mListViewListener.onRefresh();
            }
            this.resetHeaderHeight();
        }

    }

    private void stopRefresh() {
        if(this.mPullRefreshing) {
            this.mPullRefreshing = false;
            this.resetHeaderHeight();
        }

    }

    private void stopLoadMore() {
        if(this.mPullLoading) {
            this.mPullLoading = false;
            this.mFooterView.setState(0);
        }
    }

    private void setRefreshTime(String time) {
        this.mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling() {
        if(this.mScrollListener instanceof XListView.OnXScrollListener) {
            XListView.OnXScrollListener l = (XListView.OnXScrollListener)this.mScrollListener;
            l.onXScrolling(this);
        }

    }

    private void updateHeaderHeight(float delta) {
        this.mHeaderView.setVisiableHeight((int)delta + this.mHeaderView.getVisiableHeight());
        if(this.mEnablePullRefresh && !this.mPullRefreshing) {
            if(this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight) {
                this.mHeaderView.setState(1);
            } else {
                this.mHeaderView.setState(0);
            }
        }

        this.setSelection(0);
    }

    private void resetHeaderHeight() {
        int height = this.mHeaderView.getVisiableHeight();
        if(height != 0) {
            if(!this.mPullRefreshing || height > this.mHeaderViewHeight) {
                int finalHeight = 0;
                if(this.mPullRefreshing && height > this.mHeaderViewHeight) {
                    finalHeight = this.mHeaderViewHeight;
                }

                this.mScrollBack = 0;
                this.mScroller.startScroll(0, height, 0, finalHeight - height, 400);
                this.invalidate();
            }
        }
    }

    private void updateFooterHeight(float delta) {
        int height = this.mFooterView.getBottomMargin() + (int)delta;
        if(this.mEnablePullLoad && !this.mPullLoading) {
            if(height > 50) {
                this.mFooterView.setState(1);
            } else {
                this.mFooterView.setState(0);
            }
        }
        this.mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = this.mFooterView.getBottomMargin();
        if(bottomMargin > 0) {
            this.mScrollBack = 1;
            this.mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, 400);
            this.invalidate();
        }

    }
    private void startLoadMore() {
        this.mPullLoading = true;
        this.mFooterView.setState(2);
        if(this.mListViewListener != null) {
            this.mListViewListener.onLoadMore();
        }

    }

    public boolean onTouchEvent(MotionEvent ev) {
        if(this.mListViewListener != null) {
            if(this.mLastY == -1.0F) {
                this.mLastY = ev.getRawY();
            }
            switch(ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.mLastY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    this.mLastY = -1.0F;
                    if(this.getFirstVisiblePosition() == 0) {
                        if(this.mEnablePullRefresh && this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight && !this.mPullRefreshing) {
                            this.mPullRefreshing = true;
                            this.mHeaderView.setState(2);
                            if(this.mListViewListener != null) {
                                this.mListViewListener.onRefresh();
                            }
                        }

                        this.resetHeaderHeight();
                    } else if(this.getLastVisiblePosition() == this.mTotalItemCount - 1) {
                        if(this.mEnablePullLoad && this.mFooterView.getBottomMargin() > 50 && !this.mPullLoading) {
                            this.startLoadMore();
                        }

                        this.resetFooterHeight();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = ev.getRawY() - this.mLastY;
                    this.mLastY = ev.getRawY();
                    if(this.getFirstVisiblePosition() == 0 && (this.mHeaderView.getVisiableHeight() > 0 || x > 0.0F) && !this.mPullRefreshing) {
                        this.updateHeaderHeight(x / 1.8F);
                        this.invokeOnScrolling();
                    } else if(this.getLastVisiblePosition() == this.mTotalItemCount - 1 && (this.mFooterView.getBottomMargin() > 0 || x < 0.0F) && !this.mPullLoading) {
                        this.updateFooterHeight(-x / 1.8F);
                    }
            }
        }

        switch(ev.getAction()) {
            case 0:
                int x1 = (int)ev.getX();
                int y = (int)ev.getY();
                int position = this.pointToPosition(x1, y);
                if(position != -1) {
                    try {
                        MessageItem data = (MessageItem)this.getItemAtPosition(position);
                        if(data != null) {
                            this.mFocusedSlideView = data.getSlideView();
                        }
                    } catch (Exception var6) {

                    }
                }
            default:
                if(this.mFocusedSlideView != null) {
                    this.mFocusedSlideView.onRequireTouchEvent(ev);
                }

                return super.onTouchEvent(ev);
        }
    }

    public void computeScroll() {
        if(this.mScroller.computeScrollOffset()) {
            if(this.mScrollBack == 0) {
                this.mHeaderView.setVisiableHeight(this.mScroller.getCurrY());
            } else {
                this.mFooterView.setBottomMargin(this.mScroller.getCurrY());
            }

            this.postInvalidate();
            this.invokeOnScrolling();
        }

        super.computeScroll();
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mScrollListener = l;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mTotalItemCount = totalItemCount;
        if(this.mScrollListener != null) {
            this.mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

    }

    public void setXListViewListener(XListView.IXListViewListener l) {
        this.mListViewListener = l;
    }

    public interface IXListViewListener {
        void onRefresh();

        void onLoadMore();
    }

    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View var1);
    }
}
