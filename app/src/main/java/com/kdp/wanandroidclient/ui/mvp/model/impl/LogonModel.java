package com.kdp.wanandroidclient.ui.mvp.model.impl;

import android.text.TextUtils;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.inter.VerifyAccountCallback;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.mvp.model.ILogonModel;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public class LogonModel extends BaseModel implements ILogonModel {

    @Override
    public void login(String username, String password, RxObserver<UserBean> callback) {
        doRxRequest()
                .login(username, password)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

    @Override
    public void register(final String username, final String password, final RxObserver<String> callback) {
        doRxRequest()
                .register(username, password, password)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

    @Override
    public void saveUserInfo(UserBean userBean) {
        //加密保存用户信息和密钥
        UserInfoManager.saveUserInfo(userBean);
        UserInfoManager.saveIsLogin(true);
    }

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
