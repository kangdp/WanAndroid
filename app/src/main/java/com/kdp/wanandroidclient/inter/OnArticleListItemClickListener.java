package com.kdp.wanandroidclient.inter;

import com.kdp.wanandroidclient.bean.Article;

/**
 * 文章列表接口
 * author: 康栋普
 * date: 2018/2/27
 */

public interface OnArticleListItemClickListener extends OnItemClickListener<Article>{
    void onDeleteCollectClick(int position,int id,int originId);
    void onCollectClick(int position,int id);
}
