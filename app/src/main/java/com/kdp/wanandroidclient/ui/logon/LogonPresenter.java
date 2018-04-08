package com.kdp.wanandroidclient.ui.logon;

import android.text.TextUtils;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.LogonModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public class LogonPresenter extends BasePresenter<LogonContract.ILoginRegisterView> implements LogonContract.IUserPresenter {

    private String username, password;
    private LogonModel logonModel;
    private LogonContract.ILoginRegisterView mLoginRegisterView;

    public LogonPresenter() {
        this.logonModel = new LogonModel();
    }


    @Override
    public void login() {
        if (!verifyAccount()) return;
        logonModel.login(username, password, new RxObserver<UserBean>(this,LogonModel.class.getName()) {

            @Override
            public void onSubscribe(Disposable d) {
                mLoginRegisterView.showLoading(AppContext.getContext().getString(R.string.isLoging));
            }

            @Override
            protected void onSuccess(UserBean data) {
                //加密保存用户信息和密钥
                UserInfoManager.saveUserInfo(data);
                UserInfoManager.saveIsLogin(true);
                mLoginRegisterView.showResult(AppContext.getContext().getString(R.string.login_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mLoginRegisterView.showFail(errorMsg);
            }

        });
    }

    @Override
    public void register() {
        if (!verifyAccount()) return;
        logonModel.register(username, password, new RxObserver<String>(this,LogonModel.class.getName()) {
            @Override
            protected void onSuccess(String data) {
                mLoginRegisterView.showResult(AppContext.getContext().getString(R.string.register_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mLoginRegisterView.showFail(errorMsg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                mLoginRegisterView.showLoading(AppContext.getContext().getString(R.string.isRegistering));
            }
        });
    }


    /**
     * verify account
     */
    private boolean verifyAccount() {
        mLoginRegisterView = getView();
        username = mLoginRegisterView.getUserName();
        password = mLoginRegisterView.getPassWord();
        if (TextUtils.isEmpty(username)) {
            mLoginRegisterView.onAccoundError("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginRegisterView.onAccoundError("密码不能为空");
            return false;
        }
        return true;
    }

}
