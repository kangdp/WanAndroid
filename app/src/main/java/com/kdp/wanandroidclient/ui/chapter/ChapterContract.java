package com.kdp.wanandroidclient.ui.chapter;

import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.ui.core.view.IListDataView;

/***
 * @author kdp
 * @date 2019/3/25 16:53
 * @description
 */
public interface ChapterContract {

    interface IChaptersPresenter {
        void getChapters();
    }

    interface IChaptersView extends IListDataView<Chapter>{

    }
}
