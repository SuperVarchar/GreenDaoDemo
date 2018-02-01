package com.hnshituo.icore_map.base.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.util.MyToast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;


/**
 * 上传图片选择基类
 * @author Wzh
 * @date 2016/10/28  15:42
 */

@RuntimePermissions
public abstract class BaseUploadImageActivity extends ICoreBaseActivity {

    protected File CameraFile;

    @NeedsPermission(Manifest.permission.CAMERA)
    public void showCamera(int captureCode) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getIamgeFile()));
        startActivityForResult(intent, captureCode);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void showPic(int pickCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, pickCode);
    }
    public void openPic(int captureCode) {
        BaseUploadImageActivityPermissionsDispatcher.showPicWithCheck(this, captureCode);
    }
    public void openCamera(int captureCode) {
        BaseUploadImageActivityPermissionsDispatcher.showCameraWithCheck(this, captureCode);
    }
//    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
//    void showRationaleForCamera(final PermissionRequest request) {
////        StyleDialog.showRationaleDialog(this, R.string.camera, request);
//        request.proceed();
//    }
//
//    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    void showRationaleForPic(final PermissionRequest request) {
////        StyleDialog.showRationaleDialog(this, R.string.pic, request);
//        request.proceed();
//    }

//    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
//    void showDeniedForCamera() {
//        MyToast.show(App.application,"拍照权限已经被允许");
//    }
//
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        MyToast.show(ICoreMapClient.application,"拍照权限已经被禁止且设置为不再询问,请手动在权限管理中设置");
    }
//    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    void showDeniedForPic() {
//        MyToast.show(App.application,"读写权限已经被允许");
//    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForPic() {
        MyToast.show(ICoreMapClient.application,"读写权限已经被禁止且设置为不再询问,请手动在权限管理中设置");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseUploadImageActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    public abstract void operateImage(int requestCode, int resultCode, Intent data);


    /**
     * 拍照或者选择照片的返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        operateImage(requestCode,resultCode,data);
    }
    /**
     * 解析选择图片的url
     */
    protected String getImageUrl(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    return picturePath;
                }
            }
        }
        return null;
    }


    /**
     * 存放拍照图片的路径
     */
    public File getIamgeFile() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        File file = new File(Environment.getExternalStorageDirectory(), dateFormat.format(date) + ".jpg");
        this.CameraFile = file;
        return file;
    }
}
