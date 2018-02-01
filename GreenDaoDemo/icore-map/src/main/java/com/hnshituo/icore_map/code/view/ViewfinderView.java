package com.hnshituo.icore_map.code.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.hnshituo.icore_map.code.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Wzh
 * @date 2016/7/14  14:05
 */
public class ViewfinderView extends View {
    private static final int[] SCANNER_ALPHA = new int[]{128, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 100L;
    private static final int OPAQUE = 255;
    private final Paint paint = new Paint();
    private Bitmap resultBitmap;
    private int maskColor;
    private int resultColor;
    private int laserColor;
    private int resultPointColor;
    private int scannerAlpha;
    private int scannerXY;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    public ViewfinderView(Context context) {
        super(context);
        initColor();
    }

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColor();
    }

    private void initColor() {
        this.paint.setAntiAlias(true);
        this.maskColor = Color.parseColor("#60000000");
        this.resultColor = Color.parseColor("#e0000000");
        // TODO: 2017/8/25 修改扫描框的四个角和中间线的颜色
        this.laserColor = Color.parseColor("#FF474E");
        this.resultPointColor = Color.parseColor("#c0ffff00");
        this.scannerAlpha = 0;
        this.scannerXY = 0;
        this.possibleResultPoints = new HashSet(5);
    }

    public void onDraw(Canvas canvas) {
            Rect frame = CameraManager.get().getFramingRect();
            if(frame != null) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                this.paint.setColor(this.resultBitmap != null?this.resultColor:this.maskColor);
                canvas.drawRect(0.0F, 0.0F, (float)width, (float)frame.top, this.paint);
                canvas.drawRect(0.0F, (float)frame.top, (float)frame.left, (float)(frame.bottom + 1), this.paint);
                canvas.drawRect((float)(frame.right + 1), (float)frame.top, (float)width, (float)(frame.bottom + 1), this.paint);
                canvas.drawRect(0.0F, (float)(frame.bottom + 1), (float)width, (float)height, this.paint);
                if(this.resultBitmap != null) {
                    this.paint.setAlpha(255);
                    canvas.drawBitmap(this.resultBitmap, (float)frame.left, (float)frame.top, this.paint);
                } else {
                    int w = Math.round(TypedValue.applyDimension(1, 3.0F, this.getResources().getDisplayMetrics()));
                    int w2 = Math.round(TypedValue.applyDimension(1, 1.0F, this.getResources().getDisplayMetrics()));
                    int w3 = Math.round(TypedValue.applyDimension(1, 4.0F, this.getResources().getDisplayMetrics()));
                    int h = Math.round(TypedValue.applyDimension(1, 15.0F, this.getResources().getDisplayMetrics()));
                    this.paint.setColor(Color.parseColor("#ffffff"));
                    this.paint.setAlpha(250);
                    canvas.drawRect((float)frame.left, (float)frame.top, (float)frame.right, (float)(frame.top + w2), this.paint);
                    canvas.drawRect((float)frame.left, (float)(frame.bottom - w2), (float)frame.right, (float)frame.bottom, this.paint);
                    canvas.drawRect((float)frame.left, (float)frame.top, (float)(frame.left + w2), (float)frame.bottom, this.paint);
                    canvas.drawRect((float)(frame.right - w2), (float)frame.top, (float)frame.right, (float)frame.bottom, this.paint);
                    this.paint.setColor(this.laserColor);
                    this.paint.setAlpha(250);
                    canvas.drawRect((float)frame.left, (float)frame.top, (float)(frame.left + h), (float)(frame.top + w), this.paint);
                    canvas.drawRect((float)frame.left, (float)(frame.top + w), (float)(frame.left + w), (float)(frame.top + h), this.paint);
                    canvas.drawRect((float)(frame.right - w), (float)(frame.top + w), (float)frame.right, (float)(frame.top + h), this.paint);
                    canvas.drawRect((float)frame.left, (float)(frame.bottom - w), (float)(frame.left + h), (float)frame.bottom, this.paint);
                    canvas.drawRect((float)(frame.right - h), (float)frame.top, (float)frame.right, (float)(frame.top + w), this.paint);
                    canvas.drawRect((float)frame.left, (float)(frame.bottom - h), (float)(frame.left + w), (float)(frame.bottom - w), this.paint);
                    canvas.drawRect((float)(frame.right - w), (float)(frame.bottom - h), (float)frame.right, (float)(frame.bottom - w), this.paint);
                    canvas.drawRect((float)(frame.right - h), (float)(frame.bottom - w), (float)frame.right, (float)frame.bottom, this.paint);
                    this.paint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
                    this.scannerAlpha = (this.scannerAlpha + 1) % SCANNER_ALPHA.length;
                    this.scannerXY += w3;
                    if(this.scannerXY < frame.top || this.scannerXY + w3 > frame.bottom) {
                        this.scannerXY = frame.top;
                    }

                    RectF oval = new RectF((float)(frame.left + w3), (float)this.scannerXY, (float)(frame.right - w3), (float)(this.scannerXY + w3));
                    canvas.drawOval(oval, this.paint);
                    Collection currentPossible = this.possibleResultPoints;
                    Collection currentLast = this.lastPossibleResultPoints;
                    ResultPoint point;
                    Iterator var13;
                    if(currentPossible.isEmpty()) {
                        this.lastPossibleResultPoints = null;
                    } else {
                        this.possibleResultPoints = new HashSet(5);
                        this.lastPossibleResultPoints = currentPossible;
                        this.paint.setAlpha(255);
                        this.paint.setColor(this.resultPointColor);
                        var13 = currentPossible.iterator();

                        while(var13.hasNext()) {
                            point = (ResultPoint)var13.next();
                            canvas.drawCircle((float)frame.left + point.getX(), (float)frame.top + point.getY(), 6.0F, this.paint);
                        }
                    }

                    if(currentLast != null) {
                        this.paint.setAlpha(127);
                        this.paint.setColor(this.resultPointColor);
                        var13 = currentLast.iterator();

                        while(var13.hasNext()) {
                            point = (ResultPoint)var13.next();
                            canvas.drawCircle((float)frame.left + point.getX(), (float)frame.top + point.getY(), 3.0F, this.paint);
                        }
                    }

                    this.postInvalidateDelayed(100L, frame.left, frame.top, frame.right, frame.bottom);
                }

            }
    }

    public void drawViewfinder() {
            this.resultBitmap = null;
            this.invalidate();
    }

    public void drawResultBitmap(Bitmap barcode) {
            this.resultBitmap = barcode;
            this.invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
            this.possibleResultPoints.add(point);
    }
}
