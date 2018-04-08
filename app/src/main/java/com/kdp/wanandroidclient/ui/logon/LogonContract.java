package com.kdp.wanandroidclient.ui.logon;

import com.kdp.wanandroidclient.ui.mvp.view.IView;

/**
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
         * 账号错误
         *
         * @param msg
         */
        void onAccoundError(String msg);

        /**
         * 登录或注册result
         */
        void showResult(String msg);


    }
}
