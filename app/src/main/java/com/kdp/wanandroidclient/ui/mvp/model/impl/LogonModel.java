package com.kdp.wanandroidclient.ui.mvp.model.impl;

import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.ui.mvp.model.ILogonModel;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;

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

}
