package com.hnshituo.icore_map.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.hnshituo.icore_map.R;


/**
 * @author Wzh
 * @date 2016/7/5  14:45
 */
public class SlideView extends LinearLayout {
    private Context mContext;
    private LinearLayout mViewContent;
    private Scroller mScroller;
    private SlideView.OnSlideListener mOnSlideListener;
    private int mHolderWidth = 0;
    private int mLastX = 0;
    private int mLastY = 0;
    private static final int TAN = 2;
    private boolean isSlide = true;
    private boolean isShare = false;
    private boolean isDelete = false;
    private OnClickListener listener;
    private boolean isListener;
    private boolean isDOWN;
    private int inching = 0;
    private int[] thread;
    @SuppressLint({"HandlerLeak"})
    private Handler handler;

    public SlideView(Context context) {
        super(context);
        this.thread = new int[]{0, this.inching, 0};
        this.handler = new Handler() {
            public void handleMessage(Message msg) {
                SlideView.this.smoothScrollTo(msg.what, 0);
            }
        };
        this.initView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.thread = new int[]{0, this.inching, 0};
        this.handler = new Handler() {
            public void handleMessage(Message msg) {
                SlideView.this.smoothScrollTo(msg.what, 0);
            }
        };
        this.initView();
    }

    private void initView() {
        this.mContext = this.getContext();
        this.mScroller = new Scroller(this.mContext);
        this.setOrientation(HORIZONTAL);
        View.inflate(this.mContext, R.layout.layout_slide_view_merge,this);
        this.mViewContent = (LinearLayout)this.findViewById(R.id.view_content);
        this.mHolderWidth = Math.round(TypedValue.applyDimension(1, (float)this.mHolderWidth, this.getResources().getDisplayMetrics()));

    }

    public void setDeleteText(CharSequence text) {
        ((TextView)this.findViewById(R.id.delete)).setText(text);
        this.isDelete = true;
    }

    public void setDeleteOnClickListener(OnClickListener l) {
            this.findViewById(R.id.delete).setOnClickListener(l);
            if(l == null) {
                this.isDelete = false;
            } else {
                this.isDelete = true;
            }

    }

    public void setShareText(CharSequence text) {
            ((TextView)this.findViewById(R.id.share)).setText(text);
            this.isShare = true;

    }

    public void setShareOnClickListener(OnClickListener l) {
            this.findViewById(R.id.share).setOnClickListener(l);
            if(l == null) {
                this.isShare = false;
            } else {
                this.isShare = true;
            }

    }

    public void setContentView(View view) {
            this.mViewContent.addView(view);

    }

    public void setOnSlideListener(SlideView.OnSlideListener onSlideListener) {
            this.mOnSlideListener = onSlideListener;

    }

    public void shrink() {
        if(this.getScrollX() != 0) {
            this.smoothScrollTo(0, 0);
        }

    }

    public boolean isSlide() {
        return this.isSlide;
    }

    public void setSlide(boolean isSlide) {
            this.isSlide = isSlide;
            if(!isSlide) {
                this.isShare = false;
                this.isDelete = false;
            }

    }

    public void onRequireTouchEvent(MotionEvent event) {
            int x = (int)event.getX();
            int y = (int)event.getY();
            int scrollX = this.getScrollX();
            int newScrollX;
            switch(event.getAction()) {
                case 0:
                    this.isListener = true;
                    this.isDOWN = true;
                    View newScrollX2 = this.findViewById(R.id.delete);
                    LinearLayout deltaY1 = (LinearLayout)this.findViewById(R.id.view_layout);
                    View newScrollX3 = this.findViewById(R.id.share);
                    if(this.isShare && this.isDelete) {
                        newScrollX2.setVisibility(VISIBLE);
                        newScrollX3.setVisibility(VISIBLE);
                        this.mHolderWidth = Math.round(TypedValue.applyDimension(1, 160.0F, this.getResources().getDisplayMetrics()));
                    } else if(this.isDelete) {
                        newScrollX2.setVisibility(VISIBLE);
                        newScrollX3.setVisibility(GONE);
                        this.mHolderWidth = Math.round(TypedValue.applyDimension(1, 80.0F, this.getResources().getDisplayMetrics()));
                    } else if(this.isShare) {
                        newScrollX2.setVisibility(GONE);
                        newScrollX3.setVisibility(VISIBLE);
                        this.mHolderWidth = Math.round(TypedValue.applyDimension(1, 80.0F, this.getResources().getDisplayMetrics()));
                    } else {
                        newScrollX2.setVisibility(GONE);
                        newScrollX3.setVisibility(GONE);
                        this.mHolderWidth = 0;
                        this.isSlide = false;
                    }

                    deltaY1.setLayoutParams(new LayoutParams(this.mHolderWidth, -1));
                    if(!this.mScroller.isFinished()) {
                        this.mScroller.abortAnimation();
                    }

                    if(this.mOnSlideListener != null) {
                        this.mOnSlideListener.onSlide(this, 1);
                    }
                    break;
                case 2:
                    if(this.isDOWN && x > this.getLeft() && x < this.getRight() && y > this.getTop() && y < this.getBottom()) {
                        if(this.isSlide) {
                            newScrollX = (x - this.mLastX) * 2;
                            int deltaY = y - this.mLastY;
                            if(Math.abs(newScrollX) >= Math.abs(deltaY) * 2) {
                                int newScrollX1 = scrollX - newScrollX;
                                if(newScrollX != 0) {
                                    if(newScrollX1 < -this.inching) {
                                        newScrollX1 = -this.inching;
                                    } else if(newScrollX1 > this.mHolderWidth) {
                                        newScrollX1 = this.mHolderWidth;
                                    }

                                    this.scrollTo(newScrollX1, 0);
                                }

                                if(newScrollX1 > 10) {
                                    this.isListener = false;
                                }
                            }
                        }
                        break;
                    }
                case 1:
                    if(this.isDOWN) {
                        newScrollX = 0;
                        if(scrollX < 0) {
                            (new Thread(new Runnable() {
                                public void run() {
                                    for(int i = 0; i < SlideView.this.thread.length; ++i) {
                                        SlideView.this.handler.sendEmptyMessage(SlideView.this.thread[i]);

                                        try {
                                            int scrollX = SlideView.this.getScrollX();
                                            int delta = SlideView.this.thread[i] - scrollX;
                                            Thread.sleep((long)(Math.abs(delta) * 3));
                                        } catch (InterruptedException var4) {
                                            ;
                                        }
                                    }

                                }
                            })).start();
                        } else {
                            if((double)scrollX - (double)this.mHolderWidth * 0.5D > 0.0D) {
                                newScrollX = this.mHolderWidth;
                            }

                            this.smoothScrollTo(newScrollX, 0);
                        }

                        if(this.mOnSlideListener != null) {
                            this.mOnSlideListener.onSlide(this, newScrollX == 0?0:2);
                        }

                        if(x > this.getLeft() && x < this.getRight() && y > this.getTop() && y < this.getBottom() && this.listener != null && this.isListener && newScrollX == 0) {
                            this.listener.onClick(this);
                        }

                        this.isDOWN = false;
                    }
            }

            this.mLastX = x;
            this.mLastY = y;
    }

    public void setOnClickListener(OnClickListener l) {
            this.listener = l;
    }

    public int getInching() {
        return this.inching;
    }

    public void setInching(int inching) {
        this.inching = inching;
    }

    private void smoothScrollTo(int destX, int destY) {
            int scrollX = this.getScrollX();
            int delta = destX - scrollX;
            this.mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
            this.invalidate();
    }

    public void computeScroll() {
            if(this.mScroller.computeScrollOffset()) {
                this.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
                this.postInvalidate();
            }
    }

    public interface OnSlideListener {
        int SLIDE_STATUS_OFF = 0;
        int SLIDE_STATUS_START_SCROLL = 1;
        int SLIDE_STATUS_ON = 2;

        void onSlide(View var1, int var2);
    }
}
