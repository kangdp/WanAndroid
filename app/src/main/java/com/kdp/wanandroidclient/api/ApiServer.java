package com.kdp.wanandroidclient.api;

import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.bean.Banner;
import com.kdp.wanandroidclient.bean.BaseBean;
import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.bean.Friend;
import com.kdp.wanandroidclient.bean.Hotword;
import com.kdp.wanandroidclient.bean.PageListData;
import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.bean.User;
import com.kdp.wanandroidclient.common.UrlConstainer;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * 接口
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
    Observable<BaseBean<User>> login(@Field("username") String username, @Field("password") String password);

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
    Observable<BaseBean<List<Banner>>> getBanner();

    /**
     * 首页置顶列表
     * @return
     */
    @GET(UrlConstainer.HOME_TOP_LIST)
    Observable<BaseBean<List<Article>>> getHomeTopList();

    /**
     * 首页文章列表
     *
     * @return
     */
    @GET(UrlConstainer.HOME_LIST)
    Observable<BaseBean<PageListData<Article>>> getHomeList(@Path("page") int page);

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST(UrlConstainer.COLLECT_ARTICLE)
    Observable<BaseBean<String>> collectArticle(@Path("id") int id);

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
    Observable<BaseBean<List<Tree>>> getTree();

    /**
     * 知识体系列表
     *
     * @param cid
     * @param page
     * @return
     */
    @GET(UrlConstainer.TREE_LIST)
    Observable<BaseBean<PageListData<Article>>> getTreeList(@Path("page") int page, @Query("cid") int cid);


    /**
     * 收藏的文章列表
     *
     * @param page
     * @return
     */
    @GET(UrlConstainer.COLLECT_ARTICLE_LIST)
    Observable<BaseBean<PageListData<Article>>> getCollectArticleList(@Path("page") int page);

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
    Observable<BaseBean<PageListData<Article>>> search(@Path("page") int page, @Field("k") String keyword);

    /**
     * 搜索热词
     *
     * @return
     */
    @GET(UrlConstainer.HOT_KEYWORD)
    Observable<BaseBean<List<Hotword>>> getHotKeyword();

    /**
     * 常用网站
     *
     * @return
     */
    @GET(UrlConstainer.FRIEND)
    Observable<BaseBean<List<Friend>>> getFriend();

    /**
     * 项目分类
     * @return
     */
    @GET(UrlConstainer.PROJECT_CATE)
    Observable<BaseBean<List<ProjectCate>>> getProjectCate();

    /**
     * 项目列表
     * @param page
     * @param cid
     * @return
     */
    @GET(UrlConstainer.PROJECT)
    Observable<BaseBean<PageListData<Article>>> getProjectList(@Path("page") int page,@Query("cid") int cid);

    /**
     * 获取公众号
     * @return
     */
    @GET(UrlConstainer.CHAPTERS)
    Observable<BaseBean<List<Chapter>>> getChapters();

    /**
     * 获取公众号文章列表
     * @param page
     * @param id
     * @return
     */
    @GET(UrlConstainer.CHAPTER_LIST)
    Observable<BaseBean<PageListData<Article>>> getChapterList(@Path("page") int page,@Path("id") int id);

}
