package com.kdp.wanandroidclient.ui.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.just.agentweb.AgentWeb;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.ui.base.BasePresenterActivity;
import com.kdp.wanandroidclient.utils.ToastUtils;

import java.lang.reflect.Method;

/**
 * 文章详情H5
 * author: 康栋普
 * date: 2018/2/27
 */

public class WebViewActivity extends BasePresenterActivity<WebViewPresenter, WebViewContract.IWebView> implements WebViewContract.IWebView {
    private FrameLayout mContainer;
    private AgentWeb mAgentWeb;
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
        title = intent.getStringExtra(Const.BUNDLE_KEY.TITLE);
        url = intent.getStringExtra(Const.BUNDLE_KEY.URL);
    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected void initViews() {
        mContainer = (FrameLayout) findViewById(R.id.container);
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                share();
                break;
            case R.id.collect:
//                mPresenter.collect();
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
        startActivity(Intent.createChooser(intent, "分享至"));
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
        return 0;
    }

    @Override
    public void collect(boolean isCollect, String result) {
        ToastUtils.showToast(this, result);
    }
}
