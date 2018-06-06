package com.kdp.wanandroidclient.api;

import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.bean.BannerBean;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.FriendBean;
import com.kdp.wanandroidclient.bean.HotwordBean;
import com.kdp.wanandroidclient.bean.PageListDataBean;
import com.kdp.wanandroidclient.bean.TreeBean;
import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.common.UrlConstainer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 康栋普 on 2018/1/30.
 */

public interface ApiServer {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.LOGIN)
    Observable<BaseBean<UserBean>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 重复密码
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.REGISTER)
    Observable<BaseBean<String>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


    /**
     * 广告
     *
     * @return
     */
    @GET(UrlConstainer.MAIN_BANNER)
    Observable<BaseBean<List<BannerBean>>> getBanner();

    /**
     * 首页文章列表
     *
     * @return
     */
    @GET(UrlConstainer.HOME_LIST)
    Observable<BaseBean<PageListDataBean<ArticleBean>>> getArticleList(@Path("page") int page);

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST(UrlConstainer.COLLECT_ARTICLE)
    Observable<BaseBean<String>> collectArticle(@Path("id") int id);

    /**
     * 收藏站内文章
     *
     * @param id
     * @return
     */
    @POST(UrlConstainer.COLLECT_INSIDE_ARTICLE)
    Observable<BaseBean<String>> collectInsideArticle(@Path("id") int id);

    /**
     * 取消收藏文章
     *
     * @param id
     * @return
     */

    @POST(UrlConstainer.UNCOLLECT_ARTICLE)
    Observable<BaseBean<String>> unCollectArticle(@Path("id") int id);

    /**
     * 知识体系分类
     *
     * @return
     */
    @GET(UrlConstainer.TREE)
    Observable<BaseBean<List<TreeBean>>> getTree();

    /**
     * 知识体系列表
     *
     * @param cid
     * @param page
     * @return
     */
    @GET(UrlConstainer.TREE_LIST)
    Observable<BaseBean<PageListDataBean<ArticleBean>>> getTreeList(@Path("page") int page, @Query("cid") int cid);


    /**
     * 收藏的文章列表
     *
     * @param page
     * @return
     */
    @GET(UrlConstainer.COLLECT_ARTICLE_LIST)
    Observable<BaseBean<PageListDataBean<ArticleBean>>> getCollectArticleList(@Path("page") int page);

    /**
     * 删除收藏的文章
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.DELETE_COLLECT_ARTICLE)
    Observable<BaseBean<String>> deleteCollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 搜索文章
     *
     * @param page
     * @param keyword
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.SEARCH)
    Observable<BaseBean<PageListDataBean<ArticleBean>>> search(@Path("page") int page, @Field("k") String keyword);

    /**
     * 搜索热词
     *
     * @return
     */
    @GET(UrlConstainer.HOT_KEYWORD)
    Observable<BaseBean<List<HotwordBean>>> getHotKeyword();

    /**
     * 常用网站
     *
     * @return
     */
    @GET(UrlConstainer.FRIEND)
    Observable<BaseBean<List<FriendBean>>> getFriend();

}
