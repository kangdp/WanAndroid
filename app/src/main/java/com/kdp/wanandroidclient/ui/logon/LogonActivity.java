package com.kdp.wanandroidclient.ui.logon;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.ui.base.BasePresenterActivity;
import com.kdp.wanandroidclient.utils.LightStatusbarUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;

/**
 * 登录、注册
 * Created by 康栋普 on 2018/2/1.
 */

public class LogonActivity extends BasePresenterActivity<LogonPresenter> implements LogonContract.ILoginRegisterView {
    private EditText et_username, et_password;

    @Override
    public String getUserName() {
        return et_username.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return et_password.getText().toString().trim();
    }

    @Override
    public void showResult(String msg) {
        ToastUtils.showToast(AppContext.getContext(), msg);
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
        finish();
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog(msg);
    }

    @Override
    protected LogonPresenter createPresenter() {
        return new LogonPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initViews() {
        et_username =  findViewById(R.id.et_username);
        et_password =  findViewById(R.id.et_password);
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });
        findViewById(R.id.bt_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register();
            }
        });
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LightStatusbarUtils.setLightStatusBar(this, true);
    }


}
