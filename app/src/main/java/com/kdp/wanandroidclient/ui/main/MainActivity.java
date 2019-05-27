package com.kdp.wanandroidclient.ui.main;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.User;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.event.Event;
import com.kdp.wanandroidclient.event.RxEvent;
import com.kdp.wanandroidclient.manager.ImageLoaderManager;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.base.BaseActivity;
import com.kdp.wanandroidclient.ui.chapter.ChaptersFragment;
import com.kdp.wanandroidclient.ui.home.HomeFragment;
import com.kdp.wanandroidclient.ui.project.ProjectFragment;
import com.kdp.wanandroidclient.ui.tree.TreeFragment;
import com.kdp.wanandroidclient.ui.user.AboutUsActivity;
import com.kdp.wanandroidclient.ui.user.CollectArticleActivity;
import com.kdp.wanandroidclient.utils.IntentUtils;
import com.kdp.wanandroidclient.utils.PreUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;
import com.kdp.wanandroidclient.utils.ViewAnimatorHelper;

/**
 * 管理首页Tab的Activity
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton btn_scroll_top;
    private TextView mNameView;
    private ImageView mAvatarView;
    private Button[] btns;
    private Fragment[] fragments;
    private int currentPosition;
    private int index;
    private ViewAnimatorHelper viewAnimatorHelper;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return true;
    }


    public void setCurrentTitle() {
        if (currentPosition == 0)
            mToolbar.setTitle(R.string.app_name);
        else if (currentPosition == 1)
            mToolbar.setTitle(R.string.system);
        else if(currentPosition == 2)
            mToolbar.setTitle(R.string.chapter);
        else if (currentPosition == 3)
            mToolbar.setTitle(R.string.project);

    }

    @Override
    protected void initViews() {
        mDrawerLayout =  findViewById(R.id.drawerLayout);
        mNavigationView =  findViewById(R.id.navigation_view);
        btn_scroll_top = findViewById(R.id.btn_scroll_top);
        btns = new Button[4];
        btns[0] =  findViewById(R.id.btn_main);
        btns[1] =  findViewById(R.id.btn_system);
        btns[2] =  findViewById(R.id.btn_chapter);
        btns[3] =  findViewById(R.id.btn_project);
        btns[0].setSelected(true);

        for (int i = 0; i < btns.length; i++) {
            btns[i].setOnClickListener(this);
            if (i != currentPosition) {
                btns[i].setScaleX(0.9f);
                btns[i].setScaleY(0.9f);
            }
        }

        btn_scroll_top.setOnClickListener(onScrollTopListener);
    }

    private View.OnClickListener onScrollTopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String action="";
            switch (currentPosition) {
                case 0:
                    action = Const.EVENT_ACTION.HOME;
                    break;
                case 1:
                    action = Const.EVENT_ACTION.SYSTEM;
                    break;
                case 2:
                    ((ChaptersFragment)fragments[2]).scrollToTop();
                    return;
                case 3:
                    ((ProjectFragment)fragments[3]).scrollToTop();
                    return;

            }
            RxEvent.getInstance().postEvent(action,new Event(Event.Type.SCROLL_TOP));
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //设置Home旋转开关按钮
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        //侧滑菜单
        initNavigationHeaderView();
        initFragments();
        viewAnimatorHelper = new ViewAnimatorHelper();
        viewAnimatorHelper.bindView(btn_scroll_top);
    }



    private void initNavigationHeaderView() {
        View mHeaderView = mNavigationView.getHeaderView(0);
        mAvatarView =  mHeaderView.findViewById(R.id.img_avatar);
        mNameView =  mHeaderView.findViewById(R.id.tv_name);
    }

    private void setUserData() {
        if (UserInfoManager.isLogin()) {
            User user = UserInfoManager.getUserInfo();
            if (user != null) {
                mNameView.setText(user.getUsername());
                ImageLoaderManager.displayImage(user.getIcon(), mAvatarView, Const.IMAGE_LOADER.HEAD_IMG);
            }
        } else {
            mNameView.setText("未登录");
        }
    }


    private void initFragments() {
        fragments = new Fragment[]{new HomeFragment(), new TreeFragment(),new ChaptersFragment(),new ProjectFragment()};
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, fragments[0]).show(fragments[0]).commitAllowingStateLoss();
    }


    //设置侧滑item click
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_favorite_article: {
                    if (!UserInfoManager.isLogin()) {
                        IntentUtils.goLogin(MainActivity.this);
                    } else {
                        startActivity(new Intent(MainActivity.this, CollectArticleActivity.class));
                    }
                }
                break;
                case R.id.menu_about:
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    break;
                case R.id.menu_exit:
                    exitToLogin();
                    break;
            }
            return true;
        }
    };


    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //退出登录
    private void exitToLogin() {
        IntentUtils.goLogin(this);
        PreUtils.clearAll();
        //刷新首页数据
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.HOME, new Event(Event.Type.REFRESH_LIST));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main:
                index = 0;
                break;
            case R.id.btn_system:
                index = 1;
                break;
            case R.id.btn_chapter:
                index = 2;
                break;
            case R.id.btn_project:
                index = 3;
                break;
            default:
        }

        showCurrentFragment(index);
    }

    /**
     * 切换显示当前Fragment
     *
     * @param index
     */
    private void showCurrentFragment(int index) {
        if (currentPosition != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentPosition]);
            if (!fragments[index].isAdded()) {
                ft.add(R.id.container, fragments[index]);
            }
            ft.show(fragments[index]).commit();
            btns[currentPosition].setSelected(false);
            btns[index].setSelected(true);
            scaleView();
            currentPosition = index;
            setCurrentTitle();
        }
    }

    /**
     * view放大缩小
     */
    private void scaleView() {
        btns[currentPosition].animate().scaleX(0.9f).scaleY(0.9f)
                .setDuration(150).start();
        btns[index].animate().scaleX(1.0f).scaleY(1.0f)
                .setDuration(150).start();
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
                return true;
            }

            if (System.currentTimeMillis() - mExitTime < 2000) {
                finish();
            } else {
                mExitTime = System.currentTimeMillis();
                ToastUtils.showToast(AppContext.getContext(), "请再按一次退出程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void receiveEvent(Object object) {
        Event event = (Event) object;
        if (event.type == Event.Type.SCALE){
            scroll((int) event.object);
        }
    }

    public void scroll(int offsetY){
        if (offsetY > 0 && btn_scroll_top.getVisibility() != View.INVISIBLE && !viewAnimatorHelper.isAnimating()){
            viewAnimatorHelper.hideFloatActionButton();
        }else if (offsetY < 0 && btn_scroll_top.getVisibility() != View.VISIBLE){
            viewAnimatorHelper.showFloatActionButton();
        }
    }

    @Override
    protected String registerEvent() {
        return Const.EVENT_ACTION.MAIN;
    }

}