package com.kdp.wanandroidclient.ui.mvp.view;

/**
 * Created by 康栋普 on 2018/2/1.
 */

public interface IView{


    /**
     * 显示进度条
     *
     */
    void showLoading(String msg);

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 显示失败
     *
     * @param msg
     */
    void showFail(String msg);

    /**
     * 显示错误
     */
    void showError();

    /**
     * 显示没有数据
     */
    void showEmpty();//没有数据


}
