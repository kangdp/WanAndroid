package com.kdp.wanandroidclient.ui.project;

import com.kdp.wanandroidclient.bean.Project;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

public interface ProjectContract {
    interface IProjectPresenter {
        void getProjectList();
    }

    interface IProjectView extends IPageLoadDataView<Project>{
        int getCid();
    }
}
