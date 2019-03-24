package com.kdp.wanandroidclient.ui.core.view;


/**
 * 列表View
 * author: 康栋普
 * date: 2018/3/7
 */

public interface IPageLoadDataView<T> extends IListDataView<T>{

    int getFirstPage();
    int getPage();
    void autoLoadMore();//自动加载
    void clearListData();//清空所有数据
    void showNoMore();//没有更多数据
}
