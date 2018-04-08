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

    void showContent();

    void autoLoadMore();//自动加载

    void refresh();//清空所有数据

    void showNoMore();//没有更多数据

    int getArticleId();//文章id

    void collect(boolean isCollect,String result); //收藏、取消


}
