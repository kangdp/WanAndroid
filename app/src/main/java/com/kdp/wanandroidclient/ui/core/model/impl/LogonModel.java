package com.kdp.wanandroidclient.ui.core.model.impl;

import android.text.TextUtils;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.User;
import com.kdp.wanandroidclient.inter.VerifyAccountCallback;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.ILogonModel;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public class LogonModel extends BaseModel implements ILogonModel {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param callback
     */
    @Override
    public void login(String username, String password, RxObserver<User> callback) {
        doRxRequest()
                .login(username, password)
                .compose(RxSchedulers.<User>io_main())
                .subscribe(callback);


    }

    /**
     * 注册
     * @param username   用户名
     * @param password   密码
     * @param callback
     */
    @Override
    public void register(final String username, final String password,  RxObserver<String> callback) {
        doRxRequest()
                .register(username, password, password)
                .compose(RxSchedulers.<String>io_main())
                .subscribe(callback);
    }

    /**
     * 保存用户信息
     * @param user
     */
    @Override
    public void saveUserInfo(User user) {
        //加密保存用户信息和密钥
        UserInfoManager.saveUserInfo(user);
        UserInfoManager.saveIsLogin(true);
    }

    /**
     * 账号密码判空
     * @param username
     * @param password
     * @param callback
     * @return
     */
    @Override
    public boolean verifyAccount(String username, String password, VerifyAccountCallback callback) {
        if (TextUtils.isEmpty(username)) {
            callback.onVerifyResult(AppContext.getContext().getString(R.string.username_not_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            callback.onVerifyResult(AppContext.getContext().getString(R.string.password_not_empty));
            return false;
        }
        return true;
    }
}
