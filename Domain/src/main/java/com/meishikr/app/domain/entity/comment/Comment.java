package com.meishikr.app.domain.entity.comment;


import com.meishikr.app.domain.entity.user.User;
import com.meishikr.app.domain.entity.user.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;


/**
 * 发表的评论（回复）。
 * 对于一组嵌套的Comment，采取树的结构存储，显示时进行拆分。
 * Created by yinhang on 16/2/29.
 */
@Entity
public class Comment {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 用户id.
     */
    private long userId;

    /**
     * 评论的主题，即评论的上下文，可以是博客、微博，话题等，对他人评论进行回复不会改变上下文。
     */
    private int topic;
    /**
     * 话题的id。
     */
//    @ForeignKey(tableClass = Post.class)
    private long topicId;
    /**
     * 对他人评论的回复，即评论的上一级id，为非正数表示没有回复他人评论。
     */
    private long parent;

    private long timestamp;

    private String content;

    // 数据库关系
    @ToOne(joinProperty = "userId")
    private User user;

    public static final int BLOG = 1, LAMP = 2, COMMENT = 3;
//    @IntDef({BLOG, LAMP, COMMENT})
//    public @interface Topic{}

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1903578761)
    private transient CommentDao myDao;

    @Generated(hash = 320725790)
    public Comment(Long id, long userId, int topic, long topicId, long parent,
            long timestamp, String content) {
        this.id = id;
        this.userId = userId;
        this.topic = topic;
        this.topicId = topicId;
        this.parent = parent;
        this.timestamp = timestamp;
        this.content = content;
    }

    @Generated(hash = 1669165771)
    public Comment() {
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

    public long getParent() {
        return this.parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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
    @Generated(hash = 2038262053)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommentDao() : null;
    }

}
