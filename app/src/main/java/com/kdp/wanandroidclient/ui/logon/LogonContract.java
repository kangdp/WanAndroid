package com.kdp.wanandroidclient.ui.logon;

import com.kdp.wanandroidclient.ui.core.view.IView;

/**
 * 登录、注册协约类
 * author: 康栋普
 * date: 2018/3/6
 */

public interface LogonContract {

    interface IUserPresenter {
        void login();

        void register();
    }


    interface ILoginRegisterView extends IView {

        /**
         * 获取用户名
         *
         * @return
         */
        String getUserName();

        /**
         * 获取密码
         *
         * @return
         */
        String getPassWord();

        /**
         * 登录或注册Result
         */
        void showResult(String msg);
    }
}
