package com.kdp.wanandroidclient.ui.project;
import com.kdp.wanandroidclient.bean.ProjectCate;
import com.kdp.wanandroidclient.ui.core.view.IListDataView;

/***
 * @author kdp
 * @date 2019/3/20 16:58
 * @description
 */
public interface ProjectCateContract {

    interface IProjectCatePresenter {
        void getProjectCate();
    }
    interface IProjectCateView extends IListDataView<ProjectCate>{
    }
}
