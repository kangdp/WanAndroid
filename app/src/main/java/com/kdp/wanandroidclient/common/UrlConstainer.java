package com.kdp.wanandroidclient.common;

/**
 * Api接口地址
 */
public class UrlConstainer {
    public static final String baseUrl = "https://www.wanandroid.com/";
    /**
     * 登录
     */
    public static final String LOGIN = "user/login";

    /**
     * 注册
     */
    public static final String REGISTER = "user/register";

    /**
     * 首页置顶列表
     */
    public static final String HOME_TOP_LIST = "article/top/json";

    /**
     * 首页文章列表
     */
    public static final String HOME_LIST = "article/list/{page}/json";

    /**
     * 首页广告
     */
    public static final String MAIN_BANNER = "banner/json";

    /**
     * 收藏文章
     */
    public static final String COLLECT_ARTICLE = "lg/collect/{id}/json";

    /**
     * 取消收藏的文章
     */
    public static final String UNCOLLECT_ARTICLE = "lg/uncollect_originId/{id}/json";

    /**
     * 删除收藏的文章
     */
    public static final String DELETE_COLLECT_ARTICLE = "lg/uncollect/{id}/json";


    /**
     * 知识体系
     */
    public static final String TREE = "tree/json";

    /**
     * 知识体系文章列表
     */
    public static final String TREE_LIST = "article/list/{page}/json";
    /**
     * 收藏的文章列表
     */
    public static final String COLLECT_ARTICLE_LIST = "lg/collect/list/{page}/json";

    /**
     * 搜索
     */
    public static final String SEARCH = "article/query/{page}/json";

    /**
     * 搜索热词
     */
    public static final String HOT_KEYWORD = "/hotkey/json";

    /**
     * 常用网站
     */
    public static final String FRIEND = "friend/json";


    /**
     * 公众号
     */
    public static final String CHAPTERS = "wxarticle/chapters/json";

    /**
     * 公众号文章列表
     */
    public static final String CHAPTER_LIST = "wxarticle/list/{id}/{page}/json";

    /**
     * 项目分类
     */
    public static final String PROJECT_CATE = "project/tree/json";

    /**
     * 项目
     */
    public static final String PROJECT = "project/list/{page}/json";

}
