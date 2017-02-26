package com.meishikr.app.viewmodel.event;

/**
 * Created by yinhang on 2016/12/27.
 */

public class EventLoadBlog {

    private String blogStr;

    public EventLoadBlog(String blogStr) {
        this.blogStr = blogStr;
    }

    public String getBlogStr() {
        return blogStr;
    }

    public void setBlogStr(String blogStr) {
        this.blogStr = blogStr;
    }
}

