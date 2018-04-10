package com.kdp.wanandroidclient.ui.mvp.view;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/3/7
 */

public interface IListDataView<T> extends IView {

    int getPage();

    void setData(List<T> data);

    List<T> getData();

    void showContent(); //显示内容

    void autoLoadMore();//自动加载

    void clearListData();//清空所有数据

    void showNoMore();//没有更多数据

    int getArticleId();//文章id

}
