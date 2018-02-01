package com.hnshituo.icore_map.base.upload;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.fragmention.SupportFragment;
import com.hnshituo.icore_map.okhttp.callback.BaseCallBack;
import com.hnshituo.icore_map.okhttp.callback.GsonCallback;
import com.hnshituo.icore_map.okhttp.callback.RequestCallFactory;
import com.hnshituo.icore_map.okhttp.request.RequestCall;
import com.hnshituo.icore_map.util.FileUtils;
import com.hnshituo.icore_map.view.dialog.StyleDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hnshituo.icore_map.R.string.me;


/**
 * Created by Administrator on 2017/1/4.
 */

public class UploadFragmentPresenter {
    /**
     *拍照上传请求码
     */
    public static final int CAPTURECODE = 1003;
    /**
     * 图库上传请求码
     */
    public static final int PICKCODE = 1004;
    /**
     * 请求成功结果码
     */
    private static final int RESULT_OK = -1;
    /**
     * 上传的IVew接口
     */
    private UploadIView mIview;

    private Context mContext;
    /**
     * 相机文件
     */
    protected File CameraFile;
    /**
     * 本地文件路径
     */
    String localFilePath = null;
    /**
     * 上传成功返回的对象
     */
    UploadInfo uploadInfo = null;
    /**
     * 标示
     */
    public Object mTag = -1;

    public UploadFragmentPresenter(UploadIView iview, Context context) {
        mIview = iview;
        mContext = context;
    }
    public void chooseBitmap(final SupportFragment fragment, Object tag) {
        mTag = tag;
        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("图库");
        StyleDialog.showIphoneDialog(mContext, list, "取消", null, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(getIamgeFile()));
                        fragment.startActivityForResult(intent, CAPTURECODE);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        fragment.startActivityForResult(intent2, PICKCODE);
                        break;
                }
            }
        });
    }

    /**
     * 文件的类型划分
     * @return
     */
    private Intent createGetContentIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    /**
     * 选择文件界面
     * @param fragment
     * @param tag
     */
    public void chooseFile(final SupportFragment fragment, Object tag){
        mTag = tag;
        Intent target = createGetContentIntent();
        Intent intent = Intent.createChooser(
                target, mContext.getString(R.string.choose_file));
        try {
            fragment.startActivityForResult(intent, Constant.RequestCode.FILE_CHOOSE);
        } catch (ActivityNotFoundException e) {
        }


    }


    /**
     * 获取图片路径并上传
     * @param requestCode
     * @param resultCode
     * @param data
     */

    public void upload(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURECODE) {
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                String filePath = CameraFile.getAbsolutePath();
                localFilePath = filePath;
                upload(filePath);
            } else if (requestCode == PICKCODE) {
                String filePath = getImageUrl(data);
                localFilePath = filePath;
                upload(filePath);
            }else if (requestCode == Constant.RequestCode.FILE_CHOOSE) {
                String filePath = FileUtils.getPath(ICoreMapClient.application, data.getData());
                localFilePath = filePath;
                upload(filePath);

            }
        }
    }


    /**
     * 上传图片
     */
    public void upload(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        uploadToService(path);
    }

    /**
     * 上传到服务器
     */
    private void uploadToService(String path) {
        RequestCall call = RequestCallFactory.getHttpUpload(Constant.UPLOAD_IAMGE, new File(path), this);
        call.execute(new GsonCallback<UploadInfo>(mIview, BaseCallBack.DIALOG) {
            @Override
            public void onResponse(UploadInfo response) {
                if (response == null) {
                    super.isSuccess = false;
                    super.msg = ICoreMapClient.application.getString(R.string.network_unable);
                } else {
                    uploadInfo = response;
                    mIview.uploadSucess(localFilePath,uploadInfo,mTag);
                }
            }
        });
    }

    /**
     * 解析选择图片的url
     */
    protected String getImageUrl(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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

    public String getLocalImagePath() {
        return localFilePath;
    }

    public UploadInfo getUploadInfo() {
        return uploadInfo;
    }




}
