package com.hnshituo.icore_map.code.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

/**
 * @author Wzh
 * @date 2016/7/14  14:00
 */
class PreviewCallback implements Camera.PreviewCallback {
    private final CameraConfigurationManager configManager;
    private final boolean useOneShotPreviewCallback;
    private Handler previewHandler;
    private int previewMessage;

    PreviewCallback(CameraConfigurationManager configManager, boolean useOneShotPreviewCallback) {
        this.configManager = configManager;
        this.useOneShotPreviewCallback = useOneShotPreviewCallback;
    }

    void setHandler(Handler previewHandler, int previewMessage) {
        this.previewHandler = previewHandler;
        this.previewMessage = previewMessage;
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        Point cameraResolution = this.configManager.getCameraResolution();
        if(!this.useOneShotPreviewCallback) {
            camera.setPreviewCallback((Camera.PreviewCallback)null);
        }

        if(this.previewHandler != null) {
            Message message = this.previewHandler.obtainMessage(this.previewMessage, cameraResolution.x, cameraResolution.y, data);
            message.sendToTarget();
            this.previewHandler = null;
        }

    }
}
