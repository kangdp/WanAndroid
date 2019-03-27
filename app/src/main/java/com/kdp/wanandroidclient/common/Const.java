package com.kdp.wanandroidclient.common;

/**
 * App 常量类
 * Created by 康栋普 on 2018/2/1.
 */

public class Const {

    //用户相关
    public static class USERINFO_KEY {
        public static final String USER_INFO = "mUserInfo";  //用户信息
        public static final String IS_LOGIN = "mIsLogin";    //登录状态
        public static final String AES = "mAES";//用户信息密钥
    }

    //事件Action
    public static class EVENT_ACTION {
        public static final String MAIN = "main";
        public static final String HOME = "home";
        public static final String SYSTEM = "system";
        public static final String SYSTEM_LIST = "system_list";
        public static final String CHAPTER = "chapter";
        public static final String CHAPTER_LIST = "chapter_list";
        public static final String PROJECT = "project";
        public static final String PROJECT_LIST = "project_list";
        public static final String SEARCH = "search";
//        public static final String
    }

    //Intent传值
    public static class BUNDLE_KEY {
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String URL = "url";
        public static final String OBJ = "obj";
        public static final String TYPE = "type";
        public static final String CHAPTER_ID = "chapter_id";
        public static final String CHAPTER_NAME = "chapter_name";
        public static final String COLLECT_TYPE = "collect_type";//1收藏列表文章 2收藏站内文章
    }


    //图片加载
    public static class IMAGE_LOADER {
        public static final int HEAD_IMG = 0;
        public static final int NOMAL_IMG = 1;
    }

    //当前页面状态
    public static class PAGE_STATE {
        public static final int STATE_REFRESH = 0; //刷新
        public static final int STATE_LOAD_MORE = 1;//加载更多
    }

    //列表Type
    public static class LIST_TYPE {
        public static final int HOME = 0; //首页文章列表
        public static final int CHAPTER = 0; //公众号文章列表
        public static final int TREE = 1; //知识体系文章列表
        public static final int COLLECT = 2; //我的收藏
        public static final int SEARCH = 3; //搜索
    }

}
