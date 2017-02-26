package com.meishikr.app.model.repository;

import com.meishikr.app.model.api.BlogApi;
import com.meishikr.app.domain.dto.res.ResLatestBlogs;
import com.meishikr.app.domain.entity.comment.DaoSession;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.entity.post.BlogDao;
import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.repository.BlogRepo;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 16/11/1.
 */

public class BlogRepository implements BlogRepo {

    private BlogApi blogApi;
    private DaoSession daoSession;

    @Inject
    public BlogRepository(BlogApi blogApi, DaoSession daoSession) {
        this.blogApi = blogApi;
        this.daoSession = daoSession;
    }

    @Override
    public Observable<Blog> blog(String uuid) {
        return null;
    }

    @Override
    public Observable<ResLatestBlogs> latestBlogs(int page, int count) {
        return blogApi.latestBlogs(page, count);
    }

    @Override
    public Observable<Long> save(final Blog post) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                BlogDao dao = daoSession.getBlogDao();
                if (dao.hasKey(post)) {
                    dao.update(post);
                    // 保存图像
                    savePhotos(post.getPhotos(), post.getId());
                    // 保存评论
                    return post.getId();
                } else {
                    long postId = dao.insert(post);
                    savePhotos(post.getPhotos(), postId);
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
    public Observable<Blog> load(final long id) {
        return Observable.fromCallable(new Callable<Blog>() {
            @Override
            public Blog call() throws Exception {
                return daoSession.getBlogDao()
                        .load(id);
            }
        });
    }

    @Override
    public Observable<Blog> load(final String uuid) {
        return Observable.fromCallable(new Callable<Blog>() {
            @Override
            public Blog call() throws Exception {
                return daoSession.getBlogDao()
                        .queryBuilder()
                        .where(BlogDao.Properties.Uuid.eq(uuid))
                        .unique();
            }
        });
    }

    @Override
    public Observable<List<Blog>> myBlogsLocal() {
        return Observable.fromCallable(new Callable<List<Blog>>() {
            @Override
            public List<Blog> call() throws Exception {
                return daoSession.getBlogDao().loadAll();
            }
        });

    }
}
