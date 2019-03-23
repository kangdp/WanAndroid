package com.kdp.wanandroidclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kdp.wanandroidclient.ui.logon.LogonActivity;

/***
 * @author kdp
 * @date 2019/3/23 14:46
 * @description
 */
public class IntentUtils {
    public static void goLogin(Activity activity){
        if (activity!= null)
            activity.startActivity(new Intent(activity, LogonActivity.class));
    }
}
