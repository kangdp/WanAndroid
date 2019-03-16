package com.kdp.wanandroidclient.net;

import android.content.Context;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.utils.NetworkUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/***
 * @author kdp
 * @date 2018/11/30 9:38
 * @description 异常处理类
 */
public class NetExceptionHandle {

    /**
     * 处理异常错误
     * @param context
     * @param t
     */
    public static void dealException(Context context, Throwable t) {
        if (!NetworkUtils.isAvailable(context)){
            onException(NetConfig.CONNECT_ERROR, context);
            return;
        }
        if (t instanceof ConnectException) {
            //连接错误,网络异常
            onException(NetConfig.CONNECT_ERROR, context);
        }else if (t instanceof UnknownHostException){
            //无法连接到主机
            onException(NetConfig.UNKNOWN_HOST,context);
        }
        else if (t instanceof InterruptedException) {
            //连接超时
            onException(NetConfig.CONNECT_TIMEOUT, context);
        } else if (t instanceof JSONException || t instanceof ParseException) {
            //解析错误
            onException(NetConfig.PARSE_ERROR, context);
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            onException(NetConfig.REQUEST_TIMEOUT, context);
        } else if (t instanceof UnknownError) {
            //未知错误
            onException(NetConfig.UNKNOWN_ERROR, context);
        } else if (t instanceof IllegalArgumentException){
            //未知错误
            onException(NetConfig.ILLEGAL_PARAMS, context);
        }
    }


    /**
     * 异常信息提示
     * @param errorCode
     * @param context
     */
    private static void onException(int errorCode, Context context) {
        switch (errorCode) {
            case NetConfig.CONNECT_ERROR:
                ToastUtils.showToast(context, R.string.connect_error);
                break;
            case NetConfig.UNKNOWN_HOST:
                ToastUtils.showToast(context,R.string.unknown_host);
                break;
            case NetConfig.CONNECT_TIMEOUT:
                ToastUtils.showToast(context, R.string.connect_timeout);
                break;
            case NetConfig.PARSE_ERROR:
                ToastUtils.showToast(context, R.string.parse_error);
                break;
            case NetConfig.REQUEST_TIMEOUT:
                ToastUtils.showToast(context, R.string.request_timeout);
                break;
            case NetConfig.UNKNOWN_ERROR:
                ToastUtils.showToast(context, R.string.unknown_error);
                break;
            case NetConfig.ILLEGAL_PARAMS:
                ToastUtils.showToast(context,R.string.illegal_params);
                break;
        }
    }

}
