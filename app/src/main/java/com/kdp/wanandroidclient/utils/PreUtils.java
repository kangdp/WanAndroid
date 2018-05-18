package com.kdp.wanandroidclient.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 缓存
 * author: 康栋普
 * date: 2018/2/7
 */

public class PreUtils {

    private static String pre_name = "user_info";
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }
    /**
     * 存
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        SharedPreferences sp = mContext.getSharedPreferences(pre_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        if (value instanceof String) {
            et.putString(key, (String) value);
        } else if (value instanceof Integer) {
            et.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            et.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            et.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            et.putLong(key, (Long) value);
        }
        et.commit();
    }

    public static SharedPreferences getSharedPreferences() {
        if (mContext == null)
            throw new IllegalStateException("SharedPreferences have not initialized");
        return mContext.getSharedPreferences(pre_name, Context.MODE_PRIVATE);
    }

    /**
     * 取
     *
     * @param key
     * @param defauleValue
     * @return
     */
    public static Object get(String key, Object defauleValue) {
        SharedPreferences sp = getSharedPreferences();
        if (defauleValue instanceof String) {
            return sp.getString(key, (String) defauleValue);
        } else if (defauleValue instanceof Integer) {
            return sp.getInt(key, (Integer) defauleValue);
        } else if (defauleValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defauleValue);
        } else if (defauleValue instanceof Float) {
            return sp.getFloat(key, (Float) defauleValue);
        } else if (defauleValue instanceof Long) {
            return sp.getLong(key, (Long) defauleValue);
        }
        return null;
    }

    /**
     * 移除指定key
     *
     * @param remove_key
     */
    public static void remove(String remove_key) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor et = sp.edit();
        et.remove(remove_key);
        et.commit();
    }

    /**
     * 清空数据
     */
    public static void clearAll() {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor et = sp.edit();
        et.clear();
        et.commit();
    }


}
