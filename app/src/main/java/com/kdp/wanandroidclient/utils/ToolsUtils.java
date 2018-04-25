package com.kdp.wanandroidclient.utils;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * author: 康栋普
 * date: 2018/4/25
 */

public class ToolsUtils {


    //    //获取底部导航栏高度
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourcesId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取navigationBar高度
        int height = resources.getDimensionPixelSize(resourcesId);
        return height;
    }


    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
}
