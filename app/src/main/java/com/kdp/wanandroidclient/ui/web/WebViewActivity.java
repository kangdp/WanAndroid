package com.kdp.wanandroidclient.ui.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.just.agentweb.AgentWeb;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.Article;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.base.BasePresenterActivity;
import com.kdp.wanandroidclient.utils.IntentUtils;

import java.lang.reflect.Method;

/**
 * 文章详情H5
 * author: 康栋普
 * date: 2018/2/27
 */

public class WebViewActivity extends BasePresenterActivity<WebViewPresenter> implements WebViewContract.IWebView {
    private FrameLayout mContainer;
    private AgentWeb mAgentWeb;
    private Article bean;
    private String actionType;
    private int id;
    private String title = "";
    private String url = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(title);
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        bean = (Article) bundle.getSerializable(Const.BUNDLE_KEY.OBJ);
        actionType = intent.getStringExtra(Const.BUNDLE_KEY.TYPE);
        if (bean != null) {
            id = bean.getId();
            title = bean.getTitle();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                title = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                title = Html.fromHtml(title).toString();
            }
            url = bean.getLink();
        }
    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected void initViews() {
        mContainer = findViewById(R.id.container);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(R.color.black)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //将事件交给AgentWeb做处理
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.content_menu_setting, menu);
        if (TextUtils.isEmpty(actionType))
            menu.findItem(R.id.collect).setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                share();
                break;
            case R.id.collect:
                collect();
                break;
            case R.id.browser:
                openInBrowser();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    //分享
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "玩Android分享(" + title + "):" + url);
        intent.setType("text/plain");//分享文本
        startActivity(Intent.createChooser(intent, "分享"));
    }

    //收藏
    private void collect() {
        if (!UserInfoManager.isLogin())
            IntentUtils.goLogin(this);
        if (bean.isCollect()) return;
        mPresenter.collectArticle();
    }

    //打开浏览器
    private void openInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.destroy();
    }

    @Override
    public int getArticleId() {
        return id;
    }

    @Override
    public void collect(boolean isCollect, String result) {
        if (!bean.isCollect()) {
            bean.setCollect(isCollect);
            Event mEvent = new Event(Event.Type.REFRESH_ITEM, bean);
            RxEvent.getInstance().postEvent(actionType,mEvent);
        }
    }
}
