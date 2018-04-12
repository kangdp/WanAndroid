package com.kdp.wanandroidclient.event;

/**
 * author: 康栋普
 * date: 2018/4/12
 */

public class Event {
    public enum Type {
        ITEM, LIST
    }

    public Type type;
    public Object object;


    public Event(Type type, Object object) {
        this.type = type;
        this.object = object;
    }
}
