package com.meishikr.app.application;

/**
 * Created by yinhang on 2016/12/13.
 */

public interface Consts {

    interface DateTime{
        long ONE_SECOND = 1000;
        long ONE_MINUTE = 60 * ONE_SECOND;
        long ONE_HOUR = 60 * ONE_MINUTE;
    }

    interface Download{

    }

    interface PostType {
        String POST_TYPE_MEISHIKR = "美食客";
        String POST_TYPE_LAMP_PHOTO = "走马灯·照片";
        String POST_TYPE_LAMP_VIDEO = "走马灯·视频";
    }

    interface Extras {
        String BLOG_UUID = "com.meishikr.app.view.activity.blog.BLOG_UUID";
        String LAMP_POST_TYPE = "com.meishikr.app.view.activity.lamp.LAMP_POST_TYPE";
        String MAX_PHOTO_NUM = "com.meishikr.app.view.activity.gallery.MAX_PHOTO_NUM";
        String SELECTED_IMAGE_FILE_ENTRIES = "com.meishikr.app.view.activity.gallery.SELECTED_IMAGE_FILE_ENTRIES";
    }

}
