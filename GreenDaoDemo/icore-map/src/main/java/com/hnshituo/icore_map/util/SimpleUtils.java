package com.hnshituo.icore_map.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;


import java.io.File;
import java.math.BigInteger;

/**
 * Created by Administrator on 2016/7/14.
 * 文件的简单帮助类
 */
public class SimpleUtils {

    private SimpleUtils() {
    }

    private static final long ONE_KB = 1024;
    private static final BigInteger KB_BI = BigInteger.valueOf(ONE_KB);
    private static final BigInteger MB_BI = KB_BI.multiply(KB_BI);
    private static final BigInteger GB_BI = KB_BI.multiply(MB_BI);
    private static final BigInteger TB_BI = KB_BI.multiply(GB_BI);

    /**
     * 字节单位转换
     *
     * @param ls 字节的长度
     * @return 转换后的文件大小
     */
    public static String formatCalculatedSize(long ls) {
        BigInteger size = BigInteger.valueOf(ls);
        String displaySize;

        if (size.divide(TB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(TB_BI)) + " TB";
        } else if (size.divide(GB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(GB_BI)) + " GB";
        } else if (size.divide(MB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(MB_BI)) + " MB";
        } else if (size.divide(KB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(KB_BI)) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }
        return displaySize;
    }

    public static void openFile(final Context context, final File target) {
        final String mime = MimeTypes.getMimeType(target);
        final Intent i = new Intent(Intent.ACTION_VIEW);

        if (mime != null) {
            i.setDataAndType(Uri.fromFile(target), mime);
        } else {
            i.setDataAndType(Uri.fromFile(target), "*/*");
        }

        if (context.getPackageManager().queryIntentActivities(i, 0).isEmpty()) {
//            Toast.makeText(context, R.string.cantopenfile, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            context.startActivity(i);
        } catch (Exception e) {
//            Toast.makeText(context, context.getString(R.string.cantopenfile) + e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
        }
    }

    public static String getExtension(String name) {
        String ext;

        if (name.lastIndexOf(".") == -1) {
            ext = "";

        } else {
            int index = name.lastIndexOf(".");
            ext = name.substring(index + 1, name.length());
        }
        return ext;
    }

    public static void deleteTarget(String path) {
        File target = new File(path);

        if (target.isFile() && target.canWrite()) {
            target.delete();
        } else if (target.isDirectory() && target.canRead() && target.canWrite()) {
            String[] fileList = target.list();

            if (fileList != null && fileList.length == 0) {
                target.delete();
                return;
            } else if (fileList != null && fileList.length > 0) {
                for (String aFile_list : fileList) {
                    File tempF = new File(target.getAbsolutePath() + "/"
                            + aFile_list);

                    if (tempF.isDirectory())
                        deleteTarget(tempF.getAbsolutePath());
                    else if (tempF.isFile()) {
                        tempF.delete();
                    }
                }
            }
            if (target.exists())
                target.delete();
        }
    }
}
