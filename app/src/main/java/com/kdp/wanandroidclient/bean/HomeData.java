package com.kdp.wanandroidclient.bean;

import java.util.List;

public class HomeData {
    private BaseBean<List<Banner>> banner;
    private BaseBean<List<Article>> homeTop;
    private BaseBean<PageListData<Article>> home;

    public BaseBean<List<Banner>> getBanner() {
        return banner;
    }

    public void setBanner(BaseBean<List<Banner>> banner) {
        this.banner = banner;
    }

    public BaseBean<List<Article>> getHomeTop() {
        return homeTop;
    }

    public void setHomeTop(BaseBean<List<Article>> homeTop) {
        this.homeTop = homeTop;
    }

    public BaseBean<PageListData<Article>> getHome() {
        return home;
    }

    public void setHome(BaseBean<PageListData<Article>> home) {
        this.home = home;
    }
}
