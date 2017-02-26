package com.meishikr.app.domain.repository;

import com.meishikr.app.domain.dto.res.ResLatestBlogs;
import com.meishikr.app.domain.entity.post.Blog;

import java.util.List;

import rx.Observable;

/**
 * Created by yinhang on 16/7/10.
 */
public interface BlogRepo {

    /**
     * Get a {@link Observable} which emit a {@link Blog}.
     * @param uuid
     * @return
     */
    Observable<Blog> blog(String uuid);

    /**
     * Get a {@link Observable} which emit a page of {@link Blog}s;
     * @param page blog page.
     * @param count count
     * @return
     */
    Observable<ResLatestBlogs> latestBlogs(int page, int count);

    Observable<Long> save(Blog post);

    Observable<Blog> load(long id);

    Observable<Blog> load(String uuid);

    /**
     * 返回本地数据库中的博客。
     *
     * @return 本地数据库中当前用户的博客列表
     */
    Observable<List<Blog>> myBlogsLocal();
}
