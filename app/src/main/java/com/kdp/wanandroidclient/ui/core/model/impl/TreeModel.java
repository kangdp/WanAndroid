package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.ITreeModel;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/2/24
 */

public class TreeModel extends BaseModel implements ITreeModel {

    /**
     * 获取知识体系分类
     * @param callback
     */
    @Override
    public void getTree(RxObserver<List<Tree>> callback) {
        doRxRequest()
                .getTree()
                .compose(RxSchedulers.<List<Tree>>io_main())
                .subscribe(callback);
    }


}
