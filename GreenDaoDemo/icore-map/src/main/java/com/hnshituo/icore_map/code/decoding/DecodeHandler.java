package com.hnshituo.icore_map.code.decoding;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.hnshituo.icore_map.code.camera.CameraManager;
import com.hnshituo.icore_map.code.camera.PlanarYUVLuminanceSource;

import java.util.Hashtable;

/**
 * @author Wzh
 * @date 2016/7/14  14:20
 */
final class DecodeHandler extends Handler {
    private final CaptureActivityHandler.OnCapture activity;
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();
    private boolean isRotate = false;

    DecodeHandler(CaptureActivityHandler.OnCapture activity, Hashtable<DecodeHintType, Object> hints) {
        this.multiFormatReader.setHints(hints);
        this.activity = activity;
    }

    public void handleMessage(Message message) {
        switch(message.what) {
            case 1048578:
                this.decode((byte[])message.obj, message.arg1, message.arg2);
                break;
            case 1048584:
                Looper.myLooper().quit();
        }

    }

    private void decode(byte[] data, int width, int height) {
        Result rawResult = null;
        byte[] rotatedData = new byte[data.length];

        for(int tmp = 0; tmp < height; ++tmp) {
            for(int source = 0; source < width; ++source) {
                rotatedData[source * height + height - tmp - 1] = data[source + tmp * width];
            }
        }

        PlanarYUVLuminanceSource var16 = CameraManager.get().buildLuminanceSource(rotatedData, height, width, this.isRotate);
        Bitmap bm = var16.renderCroppedGreyscaleBitmap();
        this.isRotate = !this.isRotate;
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(var16));

        try {
            rawResult = this.multiFormatReader.decodeWithState(bitmap);
        } catch (ReaderException var14) {
            ;
        } finally {
            this.multiFormatReader.reset();
        }

        Message message;
        if(rawResult != null) {
            message = Message.obtain(this.activity.getHandler(), 1048580, rawResult);
            Bundle bundle = new Bundle();
            bundle.putParcelable("barcode_bitmap", bm);
            message.setData(bundle);
            message.sendToTarget();
        } else {
            message = Message.obtain(this.activity.getHandler(), 1048579);
            message.sendToTarget();
        }

    }
}
