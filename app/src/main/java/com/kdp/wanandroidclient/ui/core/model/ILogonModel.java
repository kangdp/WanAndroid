package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.inter.VerifyAccountCallback;
import com.kdp.wanandroidclient.net.callback.RxObserver;

/**
 * 登录注册业务接口
 * Created by 康栋普 on 2018/2/1.
 */

public interface ILogonModel {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password,RxObserver<UserBean> callback);


    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     */
    void register(String username, String password,RxObserver<String> callback);


    /**
     * 保存用户信息
     * @param userBean
     */
    void saveUserInfo(UserBean userBean);

    /**
     * 检验帐号
     * @param username
     * @param password
     * @return
     */
    boolean verifyAccount(String username, String password, VerifyAccountCallback callback);
}
