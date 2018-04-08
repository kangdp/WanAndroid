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

    //Intent传值
    public static class BUNDLE_KEY {
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String URL = "url";
        public static final String OBJ = "obj";
        public static final String CHAPTER_ID = "chapter_id";
        public static final String CHAPTER_NAME = "chapter_name";
        public static final String INTENT_ACTION_TYPE = "intent_action_type";
        public static final int INTENT_ACTION_TREE = 1;
        public static final int INTENT_ACTION_LIST = 2;
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
    public static class LIST_TYPE{
        public static final int HOME = 0;
        public static final int TREE = 1;
        public static final int COLLECT = 2;
        public static final int SEARCH = 3;
    }

}
