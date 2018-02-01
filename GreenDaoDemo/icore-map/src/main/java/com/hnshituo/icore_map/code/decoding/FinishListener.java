package com.hnshituo.icore_map.code.decoding;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * @author Wzh
 * @date 2016/7/14  14:10
 */
public final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {
    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.run();
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.run();
    }

    public void run() {
        this.activityToFinish.finish();
    }
}