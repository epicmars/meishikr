package com.meishikr.app.domain.entity.post;

/**
 * Created by yinhang on 16/7/24.
 */

import com.alibaba.fastjson.annotation.JSONField;
import com.meishikr.app.domain.entity.comment.DaoSession;
import com.meishikr.app.domain.entity.user.User;
import com.meishikr.app.domain.entity.user.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;


/**
 * Photo consists of a url and a description.
 */
@Entity
public class Photo {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 用户id.
     */
    @JSONField(name = "user_id")
    private long userId;

    /**
     * 评论的主题，即评论的上下文，可以是博客、微博，话题等，对他人评论进行回复不会改变上下文。
     */
    private int topic;
    /**
     * 话题的id。
     */
    @JSONField(name = "topic_id")
    private long topicId;

    /**
     * 本地路径。
     */
    @JSONField(name = "local_path")
    private String localPath;

    /**
     * Photo URL.
     */
    private String url;
    /**
     * Photo description
     */
    private String description;

    // 关系声明
    @ToOne(joinProperty = "userId")
    private User user;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 566311508)
    private transient PhotoDao myDao;

    @Generated(hash = 1605563068)
    public Photo(Long id, long userId, int topic, long topicId, String localPath,
            String url, String description) {
        this.id = id;
        this.userId = userId;
        this.topic = topic;
        this.topicId = topicId;
        this.localPath = localPath;
        this.url = url;
        this.description = description;
    }

    @Generated(hash = 1043664727)
    public Photo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTopic() {
        return this.topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public long getTopicId() {
        return this.topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 115391908)
    public User getUser() {
        long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 462495677)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userId = user.getId();
            user__resolvedKey = userId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 442052972)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPhotoDao() : null;
    }

}
