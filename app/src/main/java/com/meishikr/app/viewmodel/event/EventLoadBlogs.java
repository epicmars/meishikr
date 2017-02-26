package com.meishikr.app.viewmodel.event;

import com.meishikr.app.domain.dto.res.ResLatestBlogs;

/**
 * Created by yinhang on 2016/12/26.
 */

public class EventLoadBlogs {

    public enum Result{
        SUCCEED,
        FAILED,
        ERROR,
        COMPLETED, }


    private Result result;

    private int page;

    private ResLatestBlogs resLatestBlogs;

    public EventLoadBlogs(Result result, int page, ResLatestBlogs resLatestBlogs) {
        this.result = result;
        this.page = page;
        this.resLatestBlogs = resLatestBlogs;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ResLatestBlogs getResLatestBlogs() {
        return resLatestBlogs;
    }

    public void setResLatestBlogs(ResLatestBlogs resLatestBlogs) {
        this.resLatestBlogs = resLatestBlogs;
    }
}
