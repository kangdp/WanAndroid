package com.kdp.wanandroidclient.ui.logon;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.User;
import com.kdp.wanandroidclient.inter.VerifyAccountCallback;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.LogonModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

/**
 * 登录、注册Presenter
 * Created by 康栋普 on 2018/2/1.
 */

public class LogonPresenter extends BasePresenter<LogonContract.ILoginRegisterView> implements LogonContract.IUserPresenter {

    private String username, password;
    private LogonModel logonModel;
    private LogonContract.ILoginRegisterView mLogonView;

    public LogonPresenter() {
        this.logonModel = new LogonModel();
    }

    /**
     * 登录
     */
    @Override
    public void login() {
        if(!verifyAccount()) return;
        RxObserver<User> mLoginRxObserver = new RxObserver<User>(this) {
            @Override
            protected void onStart() {
                mLogonView.showLoading(AppContext.getContext().getString(R.string.isLoging));
            }

            @Override
            protected void onSuccess(User userBean) {
                userBean.setPassword(password);
                logonModel.saveUserInfo(userBean);
                mLogonView.showResult(AppContext.getContext().getString(R.string.login_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mLogonView.showFail(errorMsg);
            }
        };
        logonModel.login(username, password, mLoginRxObserver);
        addDisposable(mLoginRxObserver);
    }

    /**
     * 注册
     */
    @Override
    public void register() {
        if (!verifyAccount()) return;
        RxObserver<String> mRegisterRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
                mLogonView.showLoading(AppContext.getContext().getString(R.string.isRegistering));
            }

            @Override
            protected void onSuccess(String data) {
                mLogonView.showResult(AppContext.getContext().getString(R.string.register_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mLogonView.showFail(errorMsg);
            }
        };
        logonModel.register(username, password, mRegisterRxObserver);
        addDisposable(mRegisterRxObserver);
    }

    private VerifyAccountCallback mVerifyAccountCallback = new VerifyAccountCallback() {
        @Override
        public void onVerifyResult(String msg) {
            mLogonView.showFail(msg);
        }
    };

    /**
     * 帐号验证
     */
    private boolean verifyAccount() {
        mLogonView = getView();
        username = mLogonView.getUserName();
        password = mLogonView.getPassWord();
        return logonModel.verifyAccount(username, password, mVerifyAccountCallback);
    }

}
