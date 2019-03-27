package com.kdp.wanandroidclient.ui.chapter;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.ui.core.view.IPageLoadDataView;

/***
 * @author kdp
 * @date 2019/3/27 9:33
 * @description
 */
public interface ChapterListContract {

    interface IChapterListPresenter{
        void getChapterList();
        void collectArticle();
        void unCollectArticle();
    }

    interface IChapterListView extends IPageLoadDataView<Article>{
        int getCid();
        int getArticleId();
        void collect(boolean isCollect,String result);
    }
}
