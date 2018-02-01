package com.hnshituo.icore_map.code.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

/**
 * @author Wzh
 * @date 2016/7/14  12:49
 */
class AutoFocusCallback implements Camera.AutoFocusCallback {
    private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
    private Handler autoFocusHandler;
    private int autoFocusMessage;

    AutoFocusCallback() {
    }

    void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
        this.autoFocusHandler = autoFocusHandler;
        this.autoFocusMessage = autoFocusMessage;
    }

    public void onAutoFocus(boolean success, Camera camera) {
        if (this.autoFocusHandler != null) {
            Message message = this.autoFocusHandler.obtainMessage(this.autoFocusMessage, Boolean.valueOf(success));
            this.autoFocusHandler.sendMessageDelayed(message, 1500L);
            this.autoFocusHandler = null;
        }

    }
}