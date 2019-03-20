package com.kdp.wanandroidclient.ui.core.model;

import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.net.callback.RxObserver;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/20 16:52
 * @description
 */
public interface IProjectCateModel {

    /**
     * 获取项目分类
     * @param rxObserver
     */
    void getProjectCate(RxObserver<List<ProjectCate>> rxObserver);
}
