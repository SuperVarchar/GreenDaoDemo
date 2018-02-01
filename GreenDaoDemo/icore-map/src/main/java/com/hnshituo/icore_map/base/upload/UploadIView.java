package com.hnshituo.icore_map.base.upload;


import com.hnshituo.icore_map.okhttp.NetworkControl;

/**
 * Created by Administrator on 2017/1/5.
 */

public interface UploadIView extends NetworkControl {

    void uploadSucess(String localFilePath, UploadInfo uploadInfo, Object tag);
}
