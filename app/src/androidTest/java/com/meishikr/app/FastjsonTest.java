package com.meishikr.app;

import android.content.Context;
import android.test.AndroidTestCase;

import com.alibaba.fastjson.JSON;
import com.meishikr.app.domain.entity.post.Blog;

import timber.log.Timber;

/**
 * Created by yinhang on 16/3/18.
 */
public class FastjsonTest extends AndroidTestCase {

    private static final String TAG = FastjsonTest.class.getSimpleName();
    private Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
    }

    public void testTimestamp(){
        Blog post = new Blog();
        long time = System.currentTimeMillis();
//        blog.date = new Date(timestamp);
//        blog.timestamp = new Time(timestamp);
//        blog.timestamp = new Timestamp(timestamp);
        String jsonString = JSON.toJSONString(post);

        Timber.d(TAG, jsonString);
    }
}
