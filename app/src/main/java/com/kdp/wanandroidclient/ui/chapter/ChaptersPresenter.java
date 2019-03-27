package com.kdp.wanandroidclient.ui.chapter;

import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.ChapterModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/25 16:54
 * @description
 */
public class ChaptersPresenter extends BasePresenter<ChapterContract.IChaptersView> implements ChapterContract.IChaptersPresenter{
    private ChapterModel chapterModel;
    private ChapterContract.IChaptersView chaptersView;

    ChaptersPresenter() {
        this.chapterModel = new ChapterModel();
    }

    @Override
    public void getChapters() {
        chaptersView = getView();
        RxObserver<List<Chapter>> rxObserver = new RxObserver<List<Chapter>>(this) {
            @Override
            protected void onSuccess(List<Chapter> data) {
                chaptersView.setData(data);
                if (chaptersView.getData().size() == 0) {
                    chaptersView.showEmpty();
                }else {
                    chaptersView.showContent();
                }
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                chaptersView.showFail(errorMsg);
            }
        };
        chapterModel.getChapters(rxObserver);
        addDisposable(rxObserver);
    }
}
