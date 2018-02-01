package com.hnshituo.icore_map.code.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.util.TypedValue;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Wzh
 * @date 2016/7/14  13:58
 */
public class CameraManager {
    private static CameraManager cameraManager;
    static final int SDK_INT;
    private final Context context;
    private final CameraConfigurationManager configManager;
    public Camera camera;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;
    private final PreviewCallback previewCallback;
    private final AutoFocusCallback autoFocusCallback;
    private Camera.Parameters parameter;
    private int size = 200;
    private int pinTop = 182;

    static {
        int sdkInt;
        try {
            sdkInt = Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException var2) {
            sdkInt = 10000;
        }

        SDK_INT = sdkInt;
    }

    public static void init(Context context) {
        if(cameraManager == null) {
            cameraManager = new CameraManager(context);
        }

    }

    public static CameraManager get() {
        return cameraManager;
    }

    private CameraManager(Context context) {
        this.context = context;
        this.configManager = new CameraConfigurationManager(context);
        this.useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) > 3;
        this.previewCallback = new PreviewCallback(this.configManager, this.useOneShotPreviewCallback);
        this.autoFocusCallback = new AutoFocusCallback();
    }

    public void openDriver(SurfaceHolder holder) throws IOException {
        if(this.camera == null) {
            this.camera = Camera.open();
            if(this.camera == null) {
                throw new IOException();
            }

            this.camera.setPreviewDisplay(holder);
            if(!this.initialized) {
                this.initialized = true;
                this.configManager.initFromCameraParameters(this.camera);
            }

            this.configManager.setDesiredCameraParameters(this.camera);
            FlashlightManager.enableFlashlight();
        }

    }

    public void closeDriver() {
        if(this.camera != null) {
            FlashlightManager.disableFlashlight();
            this.camera.release();
            this.camera = null;
        }

    }

    public void startPreview() {
        if(this.camera != null && !this.previewing) {
            this.camera.startPreview();
            this.previewing = true;
        }

    }

    public void stopPreview() {
        if(this.camera != null && this.previewing) {
            if(!this.useOneShotPreviewCallback) {
                this.camera.setPreviewCallback((Camera.PreviewCallback)null);
            }

            this.camera.stopPreview();
            this.previewCallback.setHandler((Handler)null, 0);
            this.autoFocusCallback.setHandler((Handler)null, 0);
            this.previewing = false;
        }

    }

    public void requestPreviewFrame(Handler handler, int message) {
        if(this.camera != null && this.previewing) {
            this.previewCallback.setHandler(handler, message);
            if(this.useOneShotPreviewCallback) {
                this.camera.setOneShotPreviewCallback(this.previewCallback);
            } else {
                this.camera.setPreviewCallback(this.previewCallback);
            }
        }

    }

    public void requestAutoFocus(Handler handler, int message) {
        if(this.camera != null && this.previewing) {
            this.autoFocusCallback.setHandler(handler, message);
            try {
                this.camera.autoFocus(this.autoFocusCallback);
            } catch (Exception e) {
            }
        }

    }

    public Rect getFramingRect() {
        Point screenResolution = this.configManager.getScreenResolution();
        if(screenResolution == null){
            return null;
        }

        if(this.framingRect == null) {
            if(this.camera == null) {
                return null;
            }

            this.size = Math.round(TypedValue.applyDimension(1, (float)this.size, this.context.getResources().getDisplayMetrics()));
            this.pinTop = Math.round(TypedValue.applyDimension(1, (float)this.pinTop, this.context.getResources().getDisplayMetrics()));
            this.framingRect = new Rect(screenResolution.x / 2 - this.size / 2, this.pinTop, screenResolution.x / 2 + this.size / 2, this.size + this.pinTop);
        }

        return this.framingRect;
    }

    public Rect getFramingRectInPreview() {
        if(this.framingRectInPreview == null) {
            Rect rect = new Rect(this.getFramingRect());
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            rect.left = rect.left * cameraResolution.y / screenResolution.x;
            rect.right = rect.right * cameraResolution.y / screenResolution.x;
            rect.top = rect.top * cameraResolution.x / screenResolution.y;
            rect.bottom = rect.bottom * cameraResolution.x / screenResolution.y;
            this.framingRectInPreview = rect;
        }

        return this.framingRectInPreview;
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height, boolean b) {
        Rect rect = this.getFramingRectInPreview();
        int previewFormat = this.configManager.getPreviewFormat();
        String previewFormatString = this.configManager.getPreviewFormatString();
        switch(previewFormat) {
            case 16:
            case 17:
                if(b) {
                    data = this.rotateData(data, width, height);
                    return new PlanarYUVLuminanceSource(data, height, width, height - rect.bottom, rect.left, rect.height(), rect.width());
                }

                return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
            default:
                if("yuv420p".equals(previewFormatString)) {
                    if(b) {
                        data = this.rotateData(data, width, height);
                        return new PlanarYUVLuminanceSource(data, height, width, height - rect.bottom, rect.left, rect.height(), rect.width());
                    } else {
                        return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported picture format: " + previewFormat + '/' + previewFormatString);
                }
        }
    }

    private byte[] rotateData(byte[] data, int width, int height) {
        byte[] rotatedData = new byte[data.length];

        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
        }

        return rotatedData;
    }

    public void openLight() {
        if(this.camera != null) {
            this.parameter = this.camera.getParameters();
            this.parameter.setFlashMode("torch");
            this.camera.setParameters(this.parameter);
        }

    }

    public void offLight() {
        if(this.camera != null) {
            this.parameter = this.camera.getParameters();
            this.parameter.setFlashMode("off");
            this.camera.setParameters(this.parameter);
        }

    }

    public void takePicture(Camera.PictureCallback jpeg){
        if(this.camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();
            int height = 0;
            int width = 0;

            Collections.sort(sizeList, new Comparator<Camera.Size>() {
                @Override
                public int compare(Camera.Size lhs, Camera.Size rhs) {
                    return lhs.width - rhs.width;
                }
            });

            for (int i = 0; i < sizeList.size(); i++) {
                Camera.Size size = sizeList.get(i);
                if(height < size.height){
                    height = size.height;
                }
                if(width < size.width){
                    width = size.width;
                }

                if(width > 2048 && i > 0){
                    width = sizeList.get(i-1).width;
                    height = sizeList.get(i-1).height;
                    break;
                }
            }



            parameters.setPictureSize(width,height);
            camera.setParameters(parameters);

            this.camera.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {

                }
            }, null, jpeg);
        }
    }
}
