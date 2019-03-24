package com.kdp.wanandroidclient.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.kdp.wanandroidclient.common.ListDataHolder;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Tree;
import com.kdp.wanandroidclient.inter.OnItemClickListener;

/**
 * 知识体系
 * author: 康栋普
 * date: 2018/2/24
 */

public class TreeAdapter extends BaseListAdapter<Tree> {
    private OnItemClickListener<Tree> listener;

    public TreeAdapter(OnItemClickListener<Tree> listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_tree;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final Tree bean, int itemType, final int position) {

        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_content = holder.getView(R.id.tv_content);
        tv_title.setText(bean.getName());
        tv_content.setText("");
        for (Tree.ChildrenBean child : bean.getChildren()) {
            tv_content.append(child.getName() + "     ");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onItemClick(position,bean);
            }
        });

    }
}
