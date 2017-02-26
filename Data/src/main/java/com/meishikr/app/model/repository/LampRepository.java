package com.meishikr.app.model.repository;

import com.meishikr.app.model.api.LampApi;
import com.meishikr.app.domain.dto.res.ResLatestLamps;
import com.meishikr.app.domain.entity.comment.DaoSession;
import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.entity.post.LampDao;
import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.repository.LampRepo;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/20.
 */

public class LampRepository implements LampRepo {

    private LampApi lampApi;
    private DaoSession daoSession;

    @Inject
    public LampRepository(LampApi lampApi, DaoSession daoSession) {
        this.lampApi = lampApi;
        this.daoSession = daoSession;
    }

    public Observable<ResLatestLamps> latestLamps(int page, int count) {
        return lampApi.latestLamps(page, count);
    }

    @Override
    public Observable<Long> save(final Lamp lamp) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                LampDao dao = daoSession.getLampDao();
                if (dao.hasKey(lamp)) {
                    dao.update(lamp);
                    // 保存图像
                    savePhotos(lamp.getPhotos(), lamp.getId());
                    // 保存评论
                    return lamp.getId();
                } else {
                    long postId = dao.insert(lamp);
                    savePhotos(lamp.getPhotos(), postId);
                    return postId;
                }
            }
        });
    }

    private void savePhotos(List<Photo> photos, long topicId) {
        if (null != photos && !photos.isEmpty()) {
            for (Photo photo : photos) {
                photo.setTopicId(topicId);
                daoSession.getPhotoDao().save(photo);
            }
        }
    }

    @Override
    public Observable<Lamp> load(final long id) {
        return Observable.fromCallable(new Callable<Lamp>() {
            @Override
            public Lamp call() throws Exception {
                return daoSession.getLampDao()
                        .load(id);
            }
        });
    }

    @Override
    public Observable<Lamp> load(final String uuid) {
        return Observable.fromCallable(new Callable<Lamp>() {
            @Override
            public Lamp call() throws Exception {
                return daoSession.getLampDao()
                        .queryBuilder()
                        .where(LampDao.Properties.Uuid.eq(uuid))
                        .unique();
            }
        });    }

    @Override
    public Observable<List<Lamp>> myLocalLamps() {
        return Observable.fromCallable(new Callable<List<Lamp>>() {
            @Override
            public List<Lamp> call() throws Exception {
                return daoSession.getLampDao().loadAll();
            }
        });    }
}
