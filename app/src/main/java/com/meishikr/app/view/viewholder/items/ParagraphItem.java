package com.meishikr.app.view.viewholder.items;

import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.List;

/**
 * 文章中的一个段落。
 * Created by yinhang on 2016/11/13.
 */

public class ParagraphItem {

    private int index;
    private String postUUID;
    private List<ImageFileEntry> images;
    private String content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPostUUID() {
        return postUUID;
    }

    public void setPostUUID(String postUUID) {
        this.postUUID = postUUID;
    }

    public List<ImageFileEntry> getImages() {
        return images;
    }

    public void setImages(List<ImageFileEntry> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
