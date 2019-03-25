package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.User;
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
    void login(String username, String password,RxObserver<User> callback);


    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     */
    void register(String username, String password,RxObserver<String> callback);


    /**
     * 保存用户信息
     * @param user 用户
     */
    void saveUserInfo(User user);

    /**
     * 账号密码判空
     * @param username 用户名
     * @param password 密码
     * @return
     */
    boolean verifyAccount(String username, String password, VerifyAccountCallback callback);
}
