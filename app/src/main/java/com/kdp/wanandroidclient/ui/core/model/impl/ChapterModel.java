package com.kdp.wanandroidclient.ui.core.model.impl;
import com.kdp.wanandroidclient.bean.Chapter;
import com.kdp.wanandroidclient.net.RxSchedulers;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.core.model.IChapterModel;

import java.util.List;

/***
 * @author kdp
 * @date 2019/3/25 16:35
 * @description
 */
public class ChapterModel extends BaseModel implements IChapterModel{
    /**
     * 获取公众号
     * @param rxObserver
     */
    @Override
    public void getChapters(RxObserver<List<Chapter>> rxObserver) {
        doRxRequest()
                .getChapters()
                .compose(RxSchedulers.<List<Chapter>>io_main())
                .subscribe(rxObserver);
    }
}
