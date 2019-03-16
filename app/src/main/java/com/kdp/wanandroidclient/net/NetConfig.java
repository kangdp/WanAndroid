package com.kdp.wanandroidclient.net;

/**
 * 异常信息
 */

public interface NetConfig {

    //请求成功
    int REQUEST_SUCCESS = 0;
    //请求失败
    int REQUEST_ERROR = -1;
    /**
     * 连接错误,网络异常
     */
    int CONNECT_ERROR = 1001;

    /**
     * 无法连接到主机
     */
    int UNKNOWN_HOST = 1002;

    /**
     * 连接超时
     */
    int CONNECT_TIMEOUT = 1003;

    /**
     * 请求超时
     */
    int REQUEST_TIMEOUT = 1004;

    /**
     * 解析错误
     */
    int PARSE_ERROR = 1005;
    /**
     * 未知错误
     */
    int UNKNOWN_ERROR = 1006;

    /**
     * 非法参数
     */
    int ILLEGAL_PARAMS = 1007;




}
