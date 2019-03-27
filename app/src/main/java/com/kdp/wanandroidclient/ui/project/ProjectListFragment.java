package com.kdp.wanandroidclient.ui.project;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.inter.OnProjectListItemClickListener;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.adapter.BaseListAdapter;
import com.kdp.wanandroidclient.ui.adapter.ProjectListAdapter;
import com.kdp.wanandroidclient.ui.base.BaseAbListFragment;
import com.kdp.wanandroidclient.ui.web.WebViewActivity;
import com.kdp.wanandroidclient.utils.IntentUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;

import java.util.List;

public class ProjectListFragment extends BaseAbListFragment<ProjectPresenter, Article> implements ProjectContract.IProjectView,OnProjectListItemClickListener {
    private int id;//文章id
    private int position;
    private int cid;//分类id
    public static ProjectListFragment instantiate(int cid){
        ProjectListFragment instance = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Const.BUNDLE_KEY.ID,cid);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected boolean isEnableLazy() {
        return true;
    }


    @Override
    protected void getBundle(Bundle bundle) {
        cid = bundle.getInt(Const.BUNDLE_KEY.ID);
    }

    @Override
    protected void loadDatas() {
        mPresenter.getProjectList();
    }

    @Override
    protected BaseListAdapter<Article> getListAdapter() {
        return new ProjectListAdapter(this);
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public int getCid() {
        return cid;
    }

    @Override
    public int getArticleId() {
        return id;
    }

    @Override
    public void collect(boolean isCollect, String result) {
        notifyItemData(isCollect, result);
    }

    private void notifyItemData(boolean isCollect, String result) {
        mListData.get(position).setCollect(isCollect);
        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
        ToastUtils.showToast(getActivity(), result);
    }

    @Override
    public void setData(List<Article> data) {
        mListData.addAll(data);
    }

    @Override
    public int getFirstPage() {
        return 1;
    }

    @Override
    public void onCollectClick(int position, int id) {
        if (!UserInfoManager.isLogin())
            IntentUtils.goLogin(getActivity());
        this.id = id;
        this.position = position;
        if (mListData.get(this.position).isCollect())
            mPresenter.unCollectArticle();
        else
            mPresenter.collectArticle();

    }

    @Override
    public void onItemClick(int position, Article bean) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_KEY.OBJ, bean);
        bundle.putString(Const.BUNDLE_KEY.TYPE, Const.EVENT_ACTION.PROJECT_LIST);
        intent.putExtras(bundle);
        startActivity(intent);
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
        }else if (mEvent.type == Event.Type.SCROLL_TOP && (int)mEvent.object == cid){
            mRecyclerView.smoothScrollToPosition(0);
        }else if (mEvent.type == Event.Type.REFRESH_LIST){
            refreshData();
        }
    }


    @Override
    protected String registerEvent() {
        return Const.EVENT_ACTION.PROJECT_LIST;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            RxEvent.getInstance().postEvent(Const.EVENT_ACTION.MAIN,new Event(Event.Type.SCALE,dy));
        }
    };
}
