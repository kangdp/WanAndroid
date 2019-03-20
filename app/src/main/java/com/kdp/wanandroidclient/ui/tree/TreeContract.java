package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

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

    interface ITreeView extends IPageLoadDataView<Tree> {
        void setData(List<Tree> tree);
    }
}
