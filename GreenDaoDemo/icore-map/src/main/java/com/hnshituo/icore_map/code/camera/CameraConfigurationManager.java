package com.hnshituo.icore_map.code.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author Wzh
 * @date 2016/7/14  13:58
 */
class CameraConfigurationManager {
    private static final int TEN_DESIRED_ZOOM = 27;
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private final Context context;
    private Point screenResolution;
    private Point cameraResolution;
    private int previewFormat;
    private String previewFormatString;

    CameraConfigurationManager(Context context) {
        this.context = context;
    }

    void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        this.previewFormat = parameters.getPreviewFormat();
        this.previewFormatString = parameters.get("preview-format");
        WindowManager manager = (WindowManager)this.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        this.screenResolution = new Point(display.getWidth(), display.getHeight());
        Point screenResolutionForCamera = new Point();
        screenResolutionForCamera.x = this.screenResolution.x;
        screenResolutionForCamera.y = this.screenResolution.y;
        if(this.screenResolution.x < this.screenResolution.y) {
            screenResolutionForCamera.x = this.screenResolution.y;
            screenResolutionForCamera.y = this.screenResolution.x;
        }

        this.cameraResolution = getCameraResolution(parameters, screenResolutionForCamera);
    }

    void setDesiredCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(this.cameraResolution.x, this.cameraResolution.y);
        this.setFlash(parameters);
        this.setZoom(parameters);
        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);
    }

    protected void setDisplayOrientation(Camera camera, int angle) {
        try {
            Method downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{Integer.TYPE});
            if(downPolymorphic != null) {
                downPolymorphic.invoke(camera, new Object[]{Integer.valueOf(angle)});
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    Point getCameraResolution() {
        return this.cameraResolution;
    }

    Point getScreenResolution() {
        return this.screenResolution;
    }

    int getPreviewFormat() {
        return this.previewFormat;
    }

    String getPreviewFormatString() {
        return this.previewFormatString;
    }

    private static Point getCameraResolution(Camera.Parameters parameters, Point screenResolution) {
        String previewSizeValueString = parameters.get("preview-size-values");
        if(previewSizeValueString == null) {
            previewSizeValueString = parameters.get("preview-size-value");
        }

        Point cameraResolution = null;
        if(previewSizeValueString != null) {
            cameraResolution = findBestPreviewSizeValue(previewSizeValueString, screenResolution);
        }

        if(cameraResolution == null) {
            cameraResolution = new Point(screenResolution.x >> 3 << 3, screenResolution.y >> 3 << 3);
        }

        return cameraResolution;
    }

    private static Point findBestPreviewSizeValue(CharSequence previewSizeValueString, Point screenResolution) {
        int bestX = 0;
        int bestY = 0;
        int diff = 2147483647;
        String[] var8;
        int var7 = (var8 = COMMA_PATTERN.split(previewSizeValueString)).length;

        for(int var6 = 0; var6 < var7; ++var6) {
            String previewSize = var8[var6];
            previewSize = previewSize.trim();
            int dimPosition = previewSize.indexOf(120);
            if(dimPosition >= 0) {
                int newX;
                int newY;
                try {
                    newX = Integer.parseInt(previewSize.substring(0, dimPosition));
                    newY = Integer.parseInt(previewSize.substring(dimPosition + 1));
                } catch (NumberFormatException var13) {
                    continue;
                }

                int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);
                if(newDiff == 0) {
                    bestX = newX;
                    bestY = newY;
                    break;
                }

                if(newDiff < diff) {
                    bestX = newX;
                    bestY = newY;
                    diff = newDiff;
                }
            }
        }

        return bestX > 0 && bestY > 0?new Point(bestX, bestY):null;
    }

    private static int findBestMotZoomValue(CharSequence stringValues, int tenDesiredZoom) {
        int tenBestValue = 0;
        String[] var6;
        int var5 = (var6 = COMMA_PATTERN.split(stringValues)).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String stringValue = var6[var4];
            stringValue = stringValue.trim();

            double value;
            try {
                value = Double.parseDouble(stringValue);
            } catch (NumberFormatException var10) {
                return tenDesiredZoom;
            }

            int tenValue = (int)(10.0D * value);
            if(Math.abs((double)tenDesiredZoom - value) < (double) Math.abs(tenDesiredZoom - tenBestValue)) {
                tenBestValue = tenValue;
            }
        }

        return tenBestValue;
    }

    private void setFlash(Camera.Parameters parameters) {
        if(Build.MODEL.contains("Behold II") && CameraManager.SDK_INT == 3) {
            parameters.set("flash-value", 1);
        } else {
            parameters.set("flash-value", 2);
        }

        parameters.set("flash-mode", "off");
    }

    private void setZoom(Camera.Parameters parameters) {
        String zoomSupportedString = parameters.get("zoom-supported");
        if(zoomSupportedString == null || Boolean.parseBoolean(zoomSupportedString)) {
            int tenDesiredZoom = 27;
            String maxZoomString = parameters.get("max-zoom");
            if(maxZoomString != null) {
                try {
                    int takingPictureZoomMaxString = (int)(10.0D * Double.parseDouble(maxZoomString));
                    if(tenDesiredZoom > takingPictureZoomMaxString) {
                        tenDesiredZoom = takingPictureZoomMaxString;
                    }
                } catch (NumberFormatException var13) {
                    ;
                }
            }

            String takingPictureZoomMaxString1 = parameters.get("taking-picture-zoom-max");
            if(takingPictureZoomMaxString1 != null) {
                try {
                    int motZoomValuesString = Integer.parseInt(takingPictureZoomMaxString1);
                    if(tenDesiredZoom > motZoomValuesString) {
                        tenDesiredZoom = motZoomValuesString;
                    }
                } catch (NumberFormatException var12) {
                    ;
                }
            }

            String motZoomValuesString1 = parameters.get("mot-zoom-values");
            if(motZoomValuesString1 != null) {
                tenDesiredZoom = findBestMotZoomValue(motZoomValuesString1, tenDesiredZoom);
            }

            String motZoomStepString = parameters.get("mot-zoom-step");
            if(motZoomStepString != null) {
                try {
                    double motZoomStep = Double.parseDouble(motZoomStepString.trim());
                    int tenZoomStep = (int)(10.0D * motZoomStep);
                    if(tenZoomStep > 1) {
                        tenDesiredZoom -= tenDesiredZoom % tenZoomStep;
                    }
                } catch (NumberFormatException var11) {
                    ;
                }
            }

            if(maxZoomString != null || motZoomValuesString1 != null) {
                parameters.set("zoom", String.valueOf((double)tenDesiredZoom / 10.0D));
            }

            if(takingPictureZoomMaxString1 != null) {
                parameters.set("taking-picture-zoom", tenDesiredZoom);
            }

        }
    }
}
