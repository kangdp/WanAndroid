package com.kdp.wanandroidclient.ui.base;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.event.RxEvent;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected FrameLayout mContainerLayout;
    private ProgressDialog loadingDialog = null;
    private PublishSubject mSubject;
    private DisposableObserver mDisposableObserver;

    @Override
    protected void onCreate(Bundle bundle) {
        if (bundle != null) {
            //如果系统回收Activity,但是系统却保留了Fragment,当Activity被重新初始化,此时,系统保存的Fragment 的getActivity为空，
            //所以要移除旧的Fragment,重新初始化新的Fragment
            String FRAGMENTS_TAG = "android:support:fragments";
            bundle.remove(FRAGMENTS_TAG);
        }
        super.onCreate(bundle);

        setContentView(R.layout.activity_base);
        Intent intent = getIntent();
        if (intent != null)
            getIntent(intent);
        mToolbar =  findViewById(R.id.toolbar);
        mContainerLayout =  findViewById(R.id.frameLayout);

        //初始化ToolBar
        boolean isToolbar = initToolbar();
        if (isToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                //必须要在setSupportActionBar之后,不然不起作用
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
        }
        //初始化Content
        initContent(getLayoutId());
        //注册事件线
        mSubject = RxEvent.getInstance().registerEvent(registerEvent());
        mDisposableObserver = new ReceiveEvent();
        mSubject.subscribe(mDisposableObserver);
    }





    private class ReceiveEvent extends DisposableObserver{
        @Override
        public void onNext(Object o) {
            receiveEvent(o);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销事件
        RxEvent.getInstance().unRegisterEvent(registerEvent(), mSubject, mDisposableObserver);
    }

    private void initContent(int layoutId) {
        if (layoutId != 0) {
            View contentView = LayoutInflater.from(this).inflate(layoutId, mContainerLayout, false);
            mContainerLayout.addView(contentView);
            initViews();
        }
    }

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 创建LodingDialog
     */
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected void receiveEvent(Object object){ }

    protected String registerEvent(){
        return null;
    }

    protected void onNavigationClick() {
        finish();
    }

    protected abstract int getLayoutId();

    protected boolean initToolbar(){
        return false;
    }

    protected void getIntent(Intent intent){ }

    protected abstract void initViews();

}
