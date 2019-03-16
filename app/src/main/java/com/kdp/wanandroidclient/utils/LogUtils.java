package com.kdp.wanandroidclient.utils;

import android.util.Log;

/**
 * Created by 康栋普 on 2017/11/29.
 * Log工具类
 */

public class LogUtils {
    private static String className;  //类名
    private static String methodName; //方法名
    private static int lineNumber;   //行数
    public static boolean isDebug; //是否开启debug模式，打印日志

    /**
     * 获取日志信息
     *
     * @param traceElements
     */
    private static void getLogInfo(StackTraceElement[] traceElements) {
        if (!isDebug) return;
        className = traceElements[1].getFileName();
        methodName = traceElements[1].getMethodName();
        lineNumber = traceElements[1].getLineNumber();
    }

    /**
     * i
     *
     * @param message
     */
    public static void i(String message) {
        getLogInfo(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    /**
     * v
     *
     * @param message
     */
    public static void v(String message) {
        getLogInfo(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    /**
     * d
     *
     * @param message
     */
    public static void d(String message) {
        getLogInfo(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    /**
     * w
     *
     * @param message
     */
    public static void w(String message) {
        getLogInfo(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    /**
     * e
     *
     * @param message
     */
    public static void e(String message) {
        getLogInfo(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }


    /**
     * 创建Log日志
     *
     * @param log
     * @return
     */
    private static String createLog(String log) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodName)
                .append("(")
                .append(className)
                .append(":")
                .append(lineNumber)
                .append(")");
        sb.append(log);
        return sb.toString();
    }

}
