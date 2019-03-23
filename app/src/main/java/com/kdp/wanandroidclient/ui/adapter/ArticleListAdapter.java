package com.kdp.wanandroidclient.ui.adapter;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.common.ListDataHolder;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.utils.DateUtils;

/**
 * 文章列表
 * author: 康栋普
 * date: 2018/2/12
 */

public class ArticleListAdapter extends BaseListAdapter<Article> {

    private int Type;
    private OnArticleListItemClickListener listener;

    public ArticleListAdapter(OnArticleListItemClickListener listener, int type) {
        this.listener = listener;
        this.Type = type;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_article_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final Article bean, int itemType, final int position) {
        TextView tv_author = holder.getView(R.id.tv_author);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_type = holder.getView(R.id.tv_type);
        ImageView img_collect = holder.getView(R.id.img_collect);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_title.setText(Html.fromHtml(bean.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_title.setText(Html.fromHtml(bean.getTitle()));
        }
        tv_author.setText(bean.getAuthor());
        tv_time.setText(DateUtils.parseTime(bean.getPublishTime()));
        tv_type.setText(String.format("%1$s / %2$s",bean.getSuperChapterName(), bean.getChapterName()));

        switch (Type) {
            case Const.LIST_TYPE.TREE:
                tv_type.setVisibility(View.GONE);
            case Const.LIST_TYPE.HOME:
            case Const.LIST_TYPE.SEARCH:
                img_collect.setImageResource(bean.isCollect() ? R.drawable.ic_favorite_light_24dp : R.drawable.ic_favorite_gray_24dp);

//                tv_type.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            listener.onTreeClick(bean.getChapterId(), bean.getChapterName());
//                        }
//                    }
//                });

                img_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onCollectClick(position, bean.getId());
                        }
                    }
                });
                break;
            case Const.LIST_TYPE.COLLECT:
                img_collect.setImageResource(R.drawable.ic_favorite_light_24dp);
                img_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeleteCollectClick(position, bean.getId(), bean.getOriginId());
                        }
                    }
                });
                break;
            default:
                break;
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position,bean);
                }
            }
        });
    }

}
