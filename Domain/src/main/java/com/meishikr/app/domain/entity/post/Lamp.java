package com.meishikr.app.domain.entity.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.meishikr.app.domain.entity.comment.Comment;
import com.meishikr.app.domain.entity.comment.CommentDao;
import com.meishikr.app.domain.entity.comment.DaoSession;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.domain.entity.user.User;
import com.meishikr.app.domain.entity.user.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import java.util.UUID;

/**
 * 走马灯——人来人往，熙熙攘攘，走马观花也。
 * Created by yinhang on 2016/12/13.
 */
@Entity
public class Lamp {

    public static final int POSTING = 1, POSTED = 2;

    /**
     * 内容。
     */
    private String content;
    /**
     * 照片资源定位符列表。
     */
    @ToMany(referencedJoinProperty = "topicId")
    private List<Photo> photos;
    /**
     * 预留的字段，选定的封面定位符。
     */
    @Transient
    private Photo cover;
    /**
     * 视频资源定位符。
     */
    @JSONField(name = "video_url")
    private String videoUrl;
    /**
     * 显示的位置。
     */
    @JSONField(name = "display_location")
    private String displayLocation;

    /**
     * 评论
     */
    @ToMany(referencedJoinProperty = "topicId")
    @OrderBy("timestamp DESC")
    private List<Comment> comments;

    /**
     * 本地ID。
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * 服务器ID。
     */
    @JSONField(name = "lamp_id")
    private long lampId;
    /**
     * 全局唯一标识符。
     */
    private String uuid;
    /**
     * 用户ID。
     */
    @JSONField(name = "user_id")
    private long userId;
    /**
     * 用户。
     */
    @ToOne(joinProperty = "userId")
    private User user;
    /**
     * 创建时间。
     */
    private long timestamp;
    /**
     * 创建的位置id。
     */
    @Transient
    private Location location;

    /**
     * 发布状态。
     */
    @JSONField(name = "post_status")
    private int postStatus;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1599876323)
    private transient LampDao myDao;

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    @Keep
    public Lamp() {
        uuid = UUID.randomUUID().toString();
    }

    @Generated(hash = 1387614996)
    public Lamp(String content, String videoUrl, String displayLocation, Long id,
            long lampId, String uuid, long userId, long timestamp, int postStatus) {
        this.content = content;
        this.videoUrl = videoUrl;
        this.displayLocation = displayLocation;
        this.id = id;
        this.lampId = lampId;
        this.uuid = uuid;
        this.userId = userId;
        this.timestamp = timestamp;
        this.postStatus = postStatus;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDisplayLocation() {
        return this.displayLocation;
    }

    public void setDisplayLocation(String displayLocation) {
        this.displayLocation = displayLocation;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getLampId() {
        return this.lampId;
    }

    public void setLampId(long lampId) {
        this.lampId = lampId;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPostStatus() {
        return this.postStatus;
    }

    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
    }

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
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1925749473)
    public List<Photo> getPhotos() {
        if (photos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhotoDao targetDao = daoSession.getPhotoDao();
            List<Photo> photosNew = targetDao._queryLamp_Photos(id);
            synchronized (this) {
                if (photos == null) {
                    photos = photosNew;
                }
            }
        }
        return photos;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 781103891)
    public synchronized void resetPhotos() {
        photos = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 130228636)
    public List<Comment> getComments() {
        if (comments == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentDao targetDao = daoSession.getCommentDao();
            List<Comment> commentsNew = targetDao._queryLamp_Comments(id);
            synchronized (this) {
                if (comments == null) {
                    comments = commentsNew;
                }
            }
        }
        return comments;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 249603048)
    public synchronized void resetComments() {
        comments = null;
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
    @Generated(hash = 965377749)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLampDao() : null;
    }

    @Keep
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
