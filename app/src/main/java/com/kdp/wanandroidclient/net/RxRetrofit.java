package com.kdp.wanandroidclient.net;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kdp.wanandroidclient.api.ApiServer;
import com.kdp.wanandroidclient.utils.PreUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络工具类
 */
public class RxRetrofit {
    private Retrofit retrofit;
    private static ApiServer apiServer;
    private static Gson gson = new Gson();

    private static final class Holder {
        private static final RxRetrofit INSTANCE = new RxRetrofit();
    }

    public void initRxRetrofit(final Context context, String baseUrl) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //链接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(10, TimeUnit.SECONDS)
                //缓存
                .cache(new Cache(context.getExternalFilesDir("http_cache"), 10 << 20))
                //设置Cookie
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        String cookieStr = (String) PreUtils.get(url.host(), "");
                        if (TextUtils.isEmpty(cookieStr)) {
                            //保存cookie
                            cookieStr = gson.toJson(cookies);
                            //持久化
                            PreUtils.put(url.host(), cookieStr);
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        //获取cookie
                        String cookieStr = (String) PreUtils.get(url.host(), "");
                        List<Cookie> cookies = gson.fromJson(cookieStr, new TypeToken<List<Cookie>>() {
                        }.getType());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServer = retrofit.create(ApiServer.class);
    }

    public static RxRetrofit getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取ApiServer对象
     *
     * @return apiServer
     */
    public static ApiServer Api() {
        if (apiServer == null)
            throw new IllegalStateException("You must invoke init method first in Application");
        return apiServer;
    }


}
