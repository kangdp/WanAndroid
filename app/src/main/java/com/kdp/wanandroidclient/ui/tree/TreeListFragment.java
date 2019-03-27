package com.kdp.wanandroidclient.ui.tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.adapter.ArticleListAdapter;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;
import com.kdp.wanandroidclient.utils.IntentUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;

import java.util.List;

/**
 * 知识体系下的文章
 * author: 康栋普
 * date: 2018/3/20
 */

public class TreeListFragment extends BaseAbListFragment<TreeListPresenter, Article> implements TreeListContract.ITreeListView, OnArticleListItemClickListener {
    private int cid;//分类id
    private int id;//文章id
    private int position;

    //实例化fragment
    public static TreeListFragment instantiate(int cid) {
        TreeListFragment instance = new TreeListFragment();
        Bundle b = new Bundle();
        b.putInt(Const.BUNDLE_KEY.ID, cid);
        instance.setArguments(b);
        return instance;
    }


    @Override
    protected boolean isEnableLazy() {
        return true;
    }

    @Override
    protected TreeListPresenter createPresenter() {
        return new TreeListPresenter();
    }

    @Override
    protected void getBundle(Bundle bundle) {
        cid = bundle.getInt(Const.BUNDLE_KEY.ID);
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected BaseListAdapter<Article> getListAdapter() {
        return new ArticleListAdapter(this, Const.LIST_TYPE.TREE);
    }

    //分类id
    @Override
    public int getCid() {
        return cid;
    }

    //列表数据
    @Override
    public void setData(List<Article> data) {
        mListData.addAll(data);
    }

    //加载列表数据
    @Override
    protected void loadDatas() {
        mPresenter.loadTreeList();
    }

    //文章id
    @Override
    public int getArticleId() {
        return id;
    }

    //收藏结果
    @Override
    public void collect(boolean isCollect, String result) {
        notifyItemData(isCollect, result);
    }

    private void notifyItemData(boolean isCollect, String result) {
        mListData.get(position).setCollect(isCollect);
        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
        ToastUtils.showToast(getActivity(), result);
    }

    //进入详情
    @Override
    public void onItemClick(int position,Article bean) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
        bundle.putString(Const.BUNDLE_KEY.TYPE, Const.EVENT_ACTION.SYSTEM_LIST);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDeleteCollectClick(int position, int id, int originId) {
    }

    //收藏click
    @Override
    public void onCollectClick(int position, int id) {
        if (!UserInfoManager.isLogin())
            IntentUtils.goLogin(getActivity());
        this.position = position;
        this.id = id;
        if (mListData.get(this.position).isCollect())
            mPresenter.unCollectArticle();
        else
            mPresenter.collectArticle();
    }


    @Override
    protected void receiveEvent(Object object) {
        Event mEvent = (Event) object;
        if (mEvent.type == Event.Type.REFRESH_ITEM) {
            Article bean = (Article) mEvent.object;
            for (int i = 0; i < mListData.size(); i++) {
                if (bean.equals(mListData.get(i))) {
                    position = i;
                    notifyItemData(bean.isCollect(), getString(R.string.collect_success));
                }
            }
        }else if (mEvent.type == Event.Type.SCROLL_TOP && (int) mEvent.object == cid){
           mRecyclerView.smoothScrollToPosition(0);
        }else if (mEvent.type == Event.Type.REFRESH_LIST){
            refreshData();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    protected String registerEvent() {
        return Const.EVENT_ACTION.SYSTEM_LIST;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (getActivity() == null) return;
            ((TreeActivity)getActivity()).scroll(dy);
        }
    };

}
