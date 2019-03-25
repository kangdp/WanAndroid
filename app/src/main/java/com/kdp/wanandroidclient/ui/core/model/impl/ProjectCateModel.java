package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.IProjectCateModel;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/20 16:55
 * @description
 */
public class ProjectCateModel extends BaseModel implements IProjectCateModel {

    /**
     * 获取项目分类
     * @param rxObserver
     */
    @Override
    public void getProjectCate(RxObserver<List<ProjectCate>> rxObserver) {
        doRxRequest()
                .getProjectCate()
                .compose(RxSchedulers.<List<ProjectCate>>io_main())
                .subscribe(rxObserver);
    }
}
