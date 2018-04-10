package com.kdp.wanandroidclient.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.bean.UserBean;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.manager.GlideLoaderManager;
import com.kdp.wanandroidclient.manager.UserInfoManager;
import com.kdp.wanandroidclient.ui.base.BaseActivity;
import com.kdp.wanandroidclient.ui.home.HomeFragment;
import com.kdp.wanandroidclient.ui.logon.LogonActivity;
import com.kdp.wanandroidclient.ui.tree.TreeFragment;
import com.kdp.wanandroidclient.ui.user.AboutUsActivity;
import com.kdp.wanandroidclient.ui.user.CollectArticleActivity;
import com.kdp.wanandroidclient.utils.PreUtils;
import com.kdp.wanandroidclient.utils.ToastUtils;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mNameView;
    private ImageView mAvatarView;
    private Button[] btns;
    private Fragment[] fragments;
    private int currentPosition;
    private int index;


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
    }


    @Override
    protected void getIntent(Intent intent) {
    }


    @Override
    protected void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        btns = new Button[2];
        btns[0] = (Button) findViewById(R.id.btn_main);
        btns[1] = (Button) findViewById(R.id.btn_system);
        btns[0].setOnClickListener(this);
        btns[1].setOnClickListener(this);
        btns[0].setSelected(true);


        for (int i = 0; i < btns.length; i++) {
            if (i != currentPosition) {
                btns[i].setScaleX(0.9f);
                btns[i].setScaleY(0.9f);
            }
        }
    }

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
    }


    private void initNavigationHeaderView() {
        View mHeaderView = mNavigationView.getHeaderView(0);
        mAvatarView = (ImageView) mHeaderView.findViewById(R.id.img_avatar);
        mNameView = (TextView) mHeaderView.findViewById(R.id.tv_name);
    }

    private void setUserData() {
        if (UserInfoManager.isLogin()) {
            UserBean userBean = UserInfoManager.getUserInfo();
            if (userBean != null) {
                mNameView.setText(userBean.getUsername());
                GlideLoaderManager.loadImage(userBean.getIcon(), mAvatarView, Const.IMAGE_LOADER.HEAD_IMG);
            }
        } else {
            mNameView.setText("未登录");
        }
    }


    private void initFragments() {
        fragments = new Fragment[]{new HomeFragment(), new TreeFragment()};
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
                        startActivity(new Intent(MainActivity.this, LogonActivity.class));
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
        startActivity(new Intent(MainActivity.this, LogonActivity.class));
        PreUtils.clearAll();
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
            default:
        }

        if (currentPosition != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentPosition]);
            if (!fragments[index].isAdded()) {
                ft.add(R.id.container, fragments[index]);
            }
            ft.show(fragments[index]).commitAllowingStateLoss();
            btns[currentPosition].setSelected(false);
            btns[index].setSelected(true);
            scaleView();
            currentPosition = index;
            setCurrentTitle();
        }

    }


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
                ToastUtils.showToast(this, "请再按一次退出程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}