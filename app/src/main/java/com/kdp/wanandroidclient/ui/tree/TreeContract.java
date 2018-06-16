package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.TreeBean;
import com.kdp.wanandroidclient.ui.mvp.view.IListDataView;

import java.util.List;

/**
 * 知识体系协约类
 * author: 康栋普
 * date: 2018/3/6
 */

public interface TreeContract {

    interface ITreePresenter {

        void loadTree();
    }

    interface ITreeView extends IListDataView<TreeBean>{
        void setData(List<TreeBean> tree);
    }
}
