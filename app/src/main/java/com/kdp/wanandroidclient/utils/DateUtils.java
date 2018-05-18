package com.kdp.wanandroidclient.utils;

import java.text.SimpleDateFormat;

/**
 * 时间解析工具
 * author: 康栋普
 * date: 2018/2/23
 */

public class DateUtils {

    public static String parseTime(long publishTime) {
        long time;
        time = (System.currentTimeMillis() - publishTime) / 1000;
        int day, hour, minute, senond;
        day = (int) (time / 3600 / 24);

        if (day == 1)
            return "1天前";
        if (day > 1)
            return new SimpleDateFormat("yyyy-MM-dd").format(publishTime);
        hour = (int) (time / 3600);
        if (hour > 0)
            return String.valueOf(hour) + "小时前";
        minute = (int) (time / 60);
        if (minute > 0)
            return String.valueOf(minute) + "分钟前";
        senond = (int) time;
        if (senond >= 0)
            return "刚刚";
        return "";
    }
}
