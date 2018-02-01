package com.hnshituo.icore_map.base;

import android.os.Environment;

/**
 * Created by Administrator on 2017/6/27.
 */

public interface Constant {

    String URL = "http://192.168.1.107:8080";
    //基本地址
    String BASE_URL = URL+"/yglms-server/service/";

    //文件上传
//        String UPLOAD = "http://192.168.1.101/";
    String UPLOAD = "http://192.168.211.120:8080";

    String BASE_IMAGE = UPLOAD + "icore-file-svr/fdfs/file/";

    //上传图片
    String UPLOAD_IAMGE = BASE_IMAGE + "upload.do";
    //删除上传文件
    String DELETE_IMAGE = URL + "/icore.oa/DeleteFile";

    //文件路径
    String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    //下载路径
    String DOWNLOAD_PATH = SDCARD_PATH + "/.xinsteelLogistics";


    //审批操作(同意或者拒绝)
    String APPROVE_CONTROL = BASE_URL + "/OaApproverService/doApproverOption";

    interface RequestCode{
        int FILE_CHOOSE = 1001;
    }

    interface ResultCode{
        //选择人员
        int CHOOSE_PERSON_OK = 10001;
        //图片上传完成
        int IMAGE_BORROW_FINISH = 10004;
        //拒绝结果
        int REFUSE_REASON_OK = 100010;
    }
}
