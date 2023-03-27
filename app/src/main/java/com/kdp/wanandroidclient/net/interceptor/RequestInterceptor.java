package com.kdp.wanandroidclient.net.interceptor;

import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 无网时仅从缓存中获取(缓存时间为Integer.MAX_VALUE秒)
 * author: 康栋普
 * date: 2018/5/24
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //无网络时从缓存中获取
        if (!NetworkUtils.isAvailable(AppContext.getContext())) {
            //max-stale:指示客户机可以接收超出max-age时间的响应消息,若respond中没有设置max-age,相当于max-age=0
            //请求的缓存过期时间: max-stale+max-age(response中)
            //在请求设置中有效，在响应设置中无效

//            CacheControl cacheControl = new CacheControl.Builder()
//                    .onlyIfCached()
//                    .maxStale(60*60,TimeUnit.SECONDS)
//                    .build();
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .cacheControl(cacheControl)
                    .build();
        }
        return chain.proceed(request);
    }
}
