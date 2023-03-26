package com.kdp.wanandroidclient.net.interceptor;

import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置缓存策略
 * author: 康栋普
 * date: 2018/5/24
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isAvailable(AppContext.getContext())) {
            //有网络时只从网络获取
            //FORCE_NETWORK 里设置了 no-cache
            //no-cache,强制客户端直接向服务器发送请求,也就是说每次请求都必须向服务器发送。
            //服务器接收到请求，然后判断资源是否变更，是则返回新内容，否则返回304(Not Modified)。
            //注意服务器根据 Etag 判断资源未变更时返回的是 304，这时还是从缓存中拿(如果缓存时间未过期)。
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }
        Response response = chain.proceed(request);
        //设置有网不缓存
        //max-age:如果仅在请求头中设置它,无法生成缓存文件，必须要在响应头中设置，如果同时设置了它，那么过期时间
        //以较短的时间为准。因此可以这样设计:用响应头的max-age控制缓存的时间，用请求头的max-age控制缓存刷新的时间

        //告知缓存多长时间，在没有超过缓存时间的情况下，请求会返回缓存内的数据，
        //在超出max-age的情况下向服务端发起新的请求，
        //请求失败的情况下返回缓存数据（测试中已验证），否则向服务端重新发起请求。
        if (NetworkUtils.isAvailable(AppContext.getContext())) {
            //有网络时，设置缓存超时为0
            int maxAge = 0;
            response = response.newBuilder()
                    .removeHeader("Pramga")//清除头消息
                    .header("Cache-Control", "public,max-age=" + maxAge)
                    .build();
        }
        return response;
    }
}
