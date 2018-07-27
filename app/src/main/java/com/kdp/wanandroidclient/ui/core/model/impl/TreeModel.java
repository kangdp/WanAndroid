package com.kdp.wanandroidclient.ui.core.model.impl;

import com.kdp.wanandroidclient.bean.TreeBean;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.ITreeModel;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/2/24
 */

public class TreeModel extends BaseModel implements ITreeModel {

    @Override
    public void getTree(RxObserver<List<TreeBean>> callback) {
        doRxRequest()
                .getTree()
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }


}
