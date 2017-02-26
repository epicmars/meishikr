package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.PhotoRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/23.
 */

public class SavePhotosCase extends UseCase<List<Photo>> {

    private PhotoRepo photoRepo;
    private List<Photo> photos;

    @Inject
    public SavePhotosCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PhotoRepo photoRepo) {
        super(threadExecutor, postExecutionThread);
        this.photoRepo = photoRepo;
    }

    @Override
    protected Observable<List<Photo>> buildUseCaseObservable() {
        return photoRepo.savePhotos(photos);
    }

    public SavePhotosCase setPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }
}
