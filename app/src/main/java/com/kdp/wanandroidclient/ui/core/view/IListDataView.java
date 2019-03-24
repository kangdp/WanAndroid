package com.kdp.wanandroidclient.ui.core.view;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/20 13:03
 * @description
 */
public interface IListDataView<T> extends IView{

    void setData(List<T> data);

    List<T> getData();

    void showContent(); //显示内容
}
