package com.kdp.wanandroidclient.inter;

import com.kdp.wanandroidclient.bean.ArticleBean;

/**
 * 文章列表接口
 * author: 康栋普
 * date: 2018/2/27
 */

public interface OnArticleListItemClickListener {

    void onItemClick(ArticleBean bean);

    void onDeleteCollectClick(int position,int id,int originId);
    void onCollectClick(int position,int id);

    void onTreeClick(int chapterId,String chapterName);
}
