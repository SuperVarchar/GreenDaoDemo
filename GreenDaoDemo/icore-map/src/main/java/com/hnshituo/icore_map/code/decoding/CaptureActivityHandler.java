package com.hnshituo.icore_map.code.decoding;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hnshituo.icore_map.code.camera.CameraManager;
import com.hnshituo.icore_map.code.view.ViewfinderResultPointCallback;
import com.hnshituo.icore_map.code.view.ViewfinderView;

import java.util.Vector;

/**
 * @author Wzh
 * @date 2016/7/14  14:19
 */
public class CaptureActivityHandler extends Handler {
    public static final int auto_focus = 1048577;
    public static final int decode = 1048578;
    public static final int decode_failed = 1048579;
    public static final int decode_succeeded = 1048580;
    public static final int encode_failed = 1048581;
    public static final int encode_succeeded = 1048582;
    public static final int launch_product_query = 1048583;
    public static final int quit = 1048584;
    public static final int restart_preview = 1048585;
    public static final int return_scan_result = 1048592;
    private final CaptureActivityHandler.OnCapture activity;
    private final DecodeThread decodeThread;
    private CaptureActivityHandler.State state;

    public CaptureActivityHandler(CaptureActivityHandler.OnCapture activity, Vector<BarcodeFormat> decodeFormats, String characterSet) {
        this.activity = activity;
        this.decodeThread = new DecodeThread(activity, decodeFormats, characterSet, new ViewfinderResultPointCallback(activity.getViewfinderView()));
        this.decodeThread.start();
        this.state = CaptureActivityHandler.State.SUCCESS;
        CameraManager.get().startPreview();
        this.restartPreviewAndDecode();
    }

    public void handleMessage(Message message) {
        switch(message.what) {
            case 1048577:
                if(this.state == CaptureActivityHandler.State.PREVIEW) {
                    CameraManager.get().requestAutoFocus(this, 1048577);
                }
                break;
            case 1048579:
                this.state = CaptureActivityHandler.State.PREVIEW;
                CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), 1048578);
                break;
            case 1048580:
                this.state = CaptureActivityHandler.State.SUCCESS;
                Bundle bundle = message.getData();
                Bitmap barcode = bundle == null?null:(Bitmap)bundle.getParcelable("barcode_bitmap");
                this.activity.handleDecode((Result)message.obj, barcode);
                break;
            case 1048583:
                String url = (String)message.obj;
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.addFlags(524288);
                this.activity.startActivity(intent);
                break;
            case 1048585:
                this.restartPreviewAndDecode();
                break;
            case 1048592:
                this.activity.setResult(-1, (Intent)message.obj);
                this.activity.finish();
        }

    }

    public void quitSynchronously() {
        this.state = CaptureActivityHandler.State.DONE;
        CameraManager.get().stopPreview();
        Message quit = Message.obtain(this.decodeThread.getHandler(), 1048584);
        quit.sendToTarget();

        try {
            this.decodeThread.join();
        } catch (InterruptedException var3) {
        }

        this.removeMessages(1048580);
        this.removeMessages(1048579);
    }

    private void restartPreviewAndDecode() {
        if(this.state == CaptureActivityHandler.State.SUCCESS) {
            this.state = CaptureActivityHandler.State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), 1048578);
            CameraManager.get().requestAutoFocus(this, 1048577);
            this.activity.drawViewfinder();
        }

    }

    public interface OnCapture {
        void handleDecode(Result var1, Bitmap var2);

        void setResult(int var1, Intent var2);

        void finish();

        void startActivity(Intent var1);

        Handler getHandler();

        ViewfinderView getViewfinderView();

        void drawViewfinder();
    }

    private static enum State {
        PREVIEW,
        SUCCESS,
        DONE;

        private State() {
        }
    }
}
