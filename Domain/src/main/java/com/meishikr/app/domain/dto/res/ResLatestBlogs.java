package com.meishikr.app.domain.dto.res;

import com.meishikr.app.domain.entity.post.Blog;
import com.sin2pi.brick.common.http.BaseResponse;

import java.util.List;

/**
 * Created by yinhang on 16/3/17.
 */
public class ResLatestBlogs extends BaseResponse {

    public List<Blog> blogs;
    public int count;
    public String next;
    public String prev;
}
