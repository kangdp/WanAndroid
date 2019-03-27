package com.kdp.wanandroidclient.ui.chapter;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.net.callback.RxPageListObserver;
import com.kdp.wanandroidclient.ui.core.model.impl.ChapterListModel;
import com.kdp.wanandroidclient.ui.core.presenter.BasePresenter;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/27 9:37
 * @description
 */
public class ChapterListPresenter extends BasePresenter<ChapterListContract.IChapterListView> implements ChapterListContract.IChapterListPresenter{
    private ChapterListModel chapterArticleModel;
    private ChapterListContract.IChapterListView chapterArticleView;

    public ChapterListPresenter() {
        this.chapterArticleModel = new ChapterListModel();
    }

    @Override
    public void getChapterList() {
        chapterArticleView = getView();
        RxPageListObserver<Article> rxPageListObserver = new RxPageListObserver<Article>(this) {
            @Override
            public void onSuccess(List<Article> mData) {
                chapterArticleView.setData(mData);
                if (chapterArticleView.getData().size() == 0){
                    chapterArticleView.showEmpty();
                }else {
                    chapterArticleView.showContent();
                }
            }
            @Override
            public void onFail(int errorCode, String errorMsg) {
                chapterArticleView.showFail(errorMsg);
            }
        };
        chapterArticleModel.getChapterArticleList(chapterArticleView.getPage(),chapterArticleView.getCid(),rxPageListObserver);
        addDisposable(rxPageListObserver);
    }

    @Override
    public void collectArticle() {
        RxObserver<String> mCollectRxObserver = new RxObserver<String>(this) {
            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                chapterArticleView.collect(true, AppContext.getContext().getString(R.string.collect_success));
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }

        };
        chapterArticleModel.collectArticle(chapterArticleView.getArticleId(), mCollectRxObserver);
        addDisposable(mCollectRxObserver);
    }

    @Override
    public void unCollectArticle() {
        RxObserver<String> unCollectRxObserver = new RxObserver<String>(this) {

            @Override
            protected void onStart() {
            }
            @Override
            protected void onSuccess(String data) {
                chapterArticleView.collect(false, AppContext.getContext().getString(R.string.uncollect_success));
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                view.showFail(errorMsg);
            }
        };
        chapterArticleModel.unCollectArticle(chapterArticleView.getArticleId(), unCollectRxObserver);
        addDisposable(unCollectRxObserver);
    }
}
