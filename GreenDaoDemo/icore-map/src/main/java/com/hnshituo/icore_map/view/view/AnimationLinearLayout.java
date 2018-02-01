package com.hnshituo.icore_map.view.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 带缩放动画的布局
 * @author Wzh
 * @date 2016/7/4  20:26
 */
public class AnimationLinearLayout extends LinearLayout {
    private float f = 10.0F;
    private TextView tv;
    private boolean down = false;
    private boolean isClickEvent = true;
    private final RectF roundRect = new RectF();
    private float rect_adius = 0.0F;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private boolean isAnimationEffect = true;
    private OnClickListener l;

    public AnimationLinearLayout(Context context) {
        super(context);
        this.init(context);
    }

    public AnimationLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public AnimationLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    public void setTextSize(float size) {
        this.tv.setTextSize(size);
    }

    public void setTextSize(int unit, float size) {
        this.tv.setTextSize(unit, size);
    }

    public void setTextColor(int color) {
        this.tv.setTextColor(color);
    }

    public void setTextColor(ColorStateList colors) {
        this.tv.setTextColor(colors);
    }

    public final void setText(int resid) {
        this.tv.setText(resid);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    public final void setText(int resid, TextView.BufferType type) {
        this.tv.setText(resid, type);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    public final void setText(CharSequence text) {
        this.tv.setText(text);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        this.tv.setText(text, type);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    private void init(Context context) {
        this.maskPaint.setAntiAlias(true);
        this.maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.zonePaint.setAntiAlias(true);
        this.zonePaint.setColor(-1);
        float density = context.getResources().getDisplayMetrics().density;
        this.rect_adius *= density;
        this.tv = new TextView(context);

    }

    public void setRectAdius(float adius) {
        this.rect_adius = adius;
        this.invalidate();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = this.getWidth();
        int h = this.getHeight();
        this.roundRect.set(0.0F, 0.0F, (float)w, (float)h);
    }

    public void draw(Canvas canvas) {
        canvas.saveLayer(this.roundRect, this.zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(this.roundRect, this.rect_adius, this.rect_adius, this.zonePaint);
        canvas.saveLayer(this.roundRect, this.maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

    public boolean isClickEvent() {
        return this.isClickEvent;
    }

    public void setClickEvent(boolean isClickEvent) {
        this.isClickEvent = isClickEvent;
    }

    public boolean isAnimationEffect() {
        return this.isAnimationEffect;
    }

    public void setAnimationEffect(boolean isAnimationEffect) {
        this.isAnimationEffect = isAnimationEffect;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!this.isClickEvent) {
            return super.onTouchEvent(event);
        } else {
            ScaleAnimation acaleAnimation = null;
            Rect viewRect = new Rect();
            this.getLocalVisibleRect(viewRect);
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(!this.down) {
                        acaleAnimation = new ScaleAnimation(1.0F, this.getF(), 1.0F, this.getF(), 1, 0.5F, 1, 0.5F);
                        acaleAnimation.setDuration(100L);
                        acaleAnimation.setFillAfter(true);
                        if(this.isAnimationEffect) {
                            this.startAnimation(acaleAnimation);
                        }

                        this.down = true;
                    }
                case MotionEvent.ACTION_UP:
                    this.clearAnimation(acaleAnimation,down,true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.down = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                default:
                    this.clearAnimation(acaleAnimation,false, false);
            }
            return super.onTouchEvent(event);
        }
    }

    public float getF() {
        return 1.0F - (float) Math.round(TypedValue.applyDimension(1, this.f, this.getResources().getDisplayMetrics())) / (float)this.getWidth();
    }

    private void clearAnimation(ScaleAnimation acaleAnimation, final boolean b, boolean up) {
            if(this.down) {
                this.down = false;
                acaleAnimation = new ScaleAnimation(this.getF(), 1.0F, this.getF(), 1.0F, 1, 0.5F, 1, 0.5F);
                acaleAnimation.setDuration(100L);
                if(up) {
                    acaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationStart(Animation paramAnimation) {
                        }

                        public void onAnimationRepeat(Animation paramAnimation) {
                        }

                        public void onAnimationEnd(Animation paramAnimation) {
                            if(AnimationLinearLayout.this.l != null && b) {
                                AnimationLinearLayout.this.l.onClick(AnimationLinearLayout.this);
                            }

                        }
                    });
                }

                if(this.isAnimationEffect) {
                    this.startAnimation(acaleAnimation);
                } else if(up && this.l != null && b) {
                    this.l.onClick(this);
                }

        }
    }
}
