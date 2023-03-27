package com.kdp.wanandroidclient.net.interceptor;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 有网时的缓存策略,且缓存60秒，过期后返回服务器的数据并刷新缓存
 * author: 康栋普
 * date: 2018/5/24
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //FORCE_NETWORK 里设置了 no-cache
        //no-cache,强制客户端直接向服务器发送请求,也就是说每次请求都必须向服务器发送。
        //如果缓存被删除，则返回请求的数据并重新缓存数据，否则如下:
        //  服务器接收到请求，然后判断资源是否变更，是则返回新内容，刷新缓存，否则返回304(Not Modified)。
        //  若资源未变更，且缓存时间未过期则返回缓存的数据，若缓存时间已过期，则会请求服务器获取数据。
        //  当缓存过期后，如果资源一直未变更(返回304)，okhttp客户端会请求网络返回数据，且刷新本地缓存。

        //若不设置no-cache,那么只要缓存时间过期，就会请求网络获取数据并刷新缓存，
        //若缓存时间未过期，不管资源有没有变更，则一直从缓存中取数据。

        //注意:拦截器只有配置到addInterceptor中时，onlyIfCached、maxStale才会生效，且如果no-cache和maxStale同时存在，maxStale也会失效。
        //若no-cache、onlyIfCached同时存在，no-cache会失效
//        CacheControl requestCacheControl = new CacheControl.Builder()
//                .noCache()
//                .onlyIfCached()
//                .maxStale(60*60,TimeUnit.SECONDS)
//                .build();

        request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_NETWORK)
//                .cacheControl(requestCacheControl)
                .build();
        Response response = chain.proceed(request);
        //max-age:如果仅在请求头中设置它,不起作用，必须要在响应头中设置，如果同时设置了它，且请求头中的max-age小于响应头中的max-age
        //则请求头的max-age生效。因此可以这样设计:用响应头的max-age控制缓存的时间，用请求头的max-age控制缓存刷新的时间
        //max-age会存储到缓存文件中,告知缓存多长时间，在没有超过缓存时间的情况下，请求会返回缓存内的数据，
        //在超出max-age的情况下向服务端发起新的请求，请求失败的情况下返回缓存数据，否则向服务端重新发起请求。

        //注意:拦截器只有配置到addNetInterceptor中，response中的max-age才会生效
        CacheControl responseCacheControl = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .build();
        response = response.newBuilder()
                    .removeHeader("Pragma")//若服务器不支持缓存{pragma : [no-cache]}，则需移除Pragma
                    .header("Cache-Control", responseCacheControl.toString())
                    .build();

        return response;
    }
}
