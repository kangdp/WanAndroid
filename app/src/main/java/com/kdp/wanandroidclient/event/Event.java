package com.kdp.wanandroidclient.event;

/**
 * 事件类型
 * author: 康栋普
 * date: 2018/4/12
 */

public class Event {
    public enum Type {
        REFRESH_ITEM, REFRESH_LIST,  SCROLL_TOP,SCALE
    }

    public Type type;
    public Object object;
    public Event(Type type) {
        this(type,null);
    }

    public Event(Type type, Object object) {
        this.type = type;
        this.object = object;
    }
}
