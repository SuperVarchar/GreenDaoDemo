package com.hnshituo.icore_map.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by liuba
 * time:2017/4/15 09:53
 * describe --自动对齐的文本View
 */

public class AutoAlignTextView extends AppCompatTextView {
    float mLineY;
    int mViewWidth;
    boolean isSpace = true;

    public AutoAlignTextView(Context context) {
        super(context);
    }

    public AutoAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoAlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        mViewWidth = getMeasuredWidth();
        String text = getText().toString();
        int length =text.length();//文本长度
        mLineY = getTextSize();
        float drawY = mLineY;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();//暂未用到
        drawY += paddingTop;
        mViewWidth = mViewWidth - paddingLeft - paddingRight;
        int spaceWidth = 0;
        int colonWidth = 0;//冒号的宽度
//是否存在冒号 包括中英 冒号   因为一般来说  冒号是跟在最后一个字符后面的  所以在计算宽度的时候需要除去
        if (text.contains(":")) {
            colonWidth = (int) textPaint.measureText(":");
        } else if (text.contains("：")) {
            colonWidth = (int) textPaint.measureText("：");
        }
        int spaceLenght = 1;
        if (colonWidth != 0) {
            spaceLenght = 2;
        }
        //计算每个空格的宽度
        spaceWidth = (int) ((mViewWidth - textPaint.measureText(text) - colonWidth) / (length - spaceLenght));
        int drawX = paddingLeft;
        for (int i = 0; i < length; i++) {//循环画每一个字
            String chars = text.substring(i, (i + 1));
            if (i != 0) {
                drawX = paddingLeft + (int) textPaint.measureText(text.substring(0, i));
                //获取字的宽度 大小
                if (isSpace) {//是否需要填充空格 这里默认需要
                    if (colonWidth != 0 && i == (length - 1)) {
                        drawX += ((i - 1) * spaceWidth);
                    } else {
                        drawX += (i * spaceWidth);
                    }
                }
            }
            canvas.drawText(chars, drawX, drawY, textPaint);
        }
    }
}
