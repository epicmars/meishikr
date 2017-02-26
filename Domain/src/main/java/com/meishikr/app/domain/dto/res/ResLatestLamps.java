package com.meishikr.app.domain.dto.res;

import com.meishikr.app.domain.entity.post.Lamp;

import java.util.List;

/**
 * Created by yinhang on 2016/12/20.
 */

public class ResLatestLamps {
    public List<Lamp> lamps;
    public int count;
    public String next;
    public String prev;
}
