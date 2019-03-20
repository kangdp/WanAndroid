package com.kdp.wanandroidclient.ui.core.view;

/**
 * view基类
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
     * 失败
     *
     * @param msg
     */
    void showFail(String msg);

    /**
     * 错误
     */
    void showError();

    /**
     * 没有数据
     */
    void showEmpty();//没有数据



}
