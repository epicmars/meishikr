package com.meishikr.app.domain.repository;

import com.meishikr.app.domain.entity.post.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/23.
 */

public interface PhotoRepo {

    Observable<List<Photo>> savePhotos(List<Photo> photos);
}
