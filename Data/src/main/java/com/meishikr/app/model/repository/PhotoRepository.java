package com.meishikr.app.model.repository;

import com.meishikr.app.domain.entity.comment.DaoSession;
import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.repository.PhotoRepo;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/23.
 */

public class PhotoRepository implements PhotoRepo {

    private DaoSession daoSession;

    @Inject
    public PhotoRepository(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Observable<List<Photo>> savePhotos(final List<Photo> photos) {
        return Observable.fromCallable(new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() throws Exception {
                daoSession.getPhotoDao().saveInTx(photos);
                return photos;
            }
        });
    }
}
