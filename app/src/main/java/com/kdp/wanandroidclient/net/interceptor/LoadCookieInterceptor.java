package com.kdp.wanandroidclient.net.interceptor;

import android.text.TextUtils;

import com.kdp.wanandroidclient.utils.PreUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加Cookie
 * author: 康栋普
 * date: 2018/2/27
 */

public class LoadCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String mCookieStr = (String) PreUtils.get(chain.request().url().host(), "");
        if (!TextUtils.isEmpty(mCookieStr)) {
            builder.addHeader("Cookie", mCookieStr.substring(0, mCookieStr.length() - 1));//长度减1为了去除最后的逗号
        }
        return chain.proceed(builder.build());
    }
}
