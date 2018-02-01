package com.hnshituo.icore_map.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 磊 on 2016/4/26.
 * 将时间用户社群显示
 */
public class TimeDifferenceUtils {

    public static String getTimeDifference(String time){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long diff;
        try {
            Date publish_data = df.parse(time);
            Date now_data = new Date();
            diff = now_data.getTime() - publish_data.getTime();
            if (diff < 0)
                return "刚刚";
            else {
                diff /= (1000 * 60);

                if(diff == 0){
                    return "刚刚";
                }

                if (diff <= 60) {
                    return diff + "分钟前";
                }
                diff /= 60;
                if (diff <= 24) {
                    return diff + "小时前";
                }
                diff /= 24;
                if (diff <= 30) {
                    if (diff == 1) {
                        return "昨天";
                    }
                    return diff + "天前";
                }
                DateFormat df_data = new SimpleDateFormat("MM-dd");
                return df_data.format(publish_data);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;


    }

}
