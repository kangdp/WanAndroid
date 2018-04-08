package com.kdp.wanandroidclient.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * author: 康栋普
 * date: 2018/2/27
 */

public class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());
        response.header("Cookie",response.headers("Set-Cookie").get(0));
        return response;
    }
}
