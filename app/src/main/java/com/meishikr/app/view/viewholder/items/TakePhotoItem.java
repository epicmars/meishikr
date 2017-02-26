package com.meishikr.app.view.viewholder.items;

import android.net.Uri;

/**
 * 相册中的拍照项。
 * Created by yinhang on 2016/11/13.
 */

public class TakePhotoItem {

    private Uri photoUri;

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }
}
