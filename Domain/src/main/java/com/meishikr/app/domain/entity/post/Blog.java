package com.meishikr.app.domain.entity.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.meishikr.app.domain.entity.comment.Comment;
import com.meishikr.app.domain.entity.comment.CommentDao;
import com.meishikr.app.domain.entity.comment.DaoSession;
import com.meishikr.app.domain.entity.user.User;
import com.meishikr.app.domain.entity.user.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * 用户发表的博客。
 * Created by yinhang on 16/2/28.
 */
@Entity
public class Blog {

    @Id(autoincrement = true)
    private Long id;

    @JSONField(name = "user_id")
    private long userId;

    @JSONField(name = "post_id")
    private long postId;

    @Index(unique = true)
    private String uuid;

    private String title;

    private String body;
    @JSONField(name = "body_html")

    private String bodyHtml;

    private Date timestamp;

    @JSONField(name = "updated_time")
    private Date updatedTime;

    private String url;

    private int status;

    @JSONField(name = "location_id")
    private long locationId;

    private int client;

    @JSONField(name = "cover_id")
    private long coverId;

    @ToOne(joinProperty = "userId")
    private User user;
    /**
     * 可选的封面照片
     */
    @Transient
    public Photo cover;
    /**
     * 文章照片，图片url从服务器获取。
     * XXX 关系型数据库设计, photo的外键为Post的id，那么不用再次在Post中循环引用了
     */
    @ToMany(referencedJoinProperty = "topicId")
    public List<Photo> photos;

    @ToMany(referencedJoinProperty = "topicId")
    @OrderBy("timestamp DESC")
    public List<Comment> comments;

    /**
     * Post status.
     */
    public static final int EDITING = 1, POSTED = 2, POSTING = 3;
//    @IntDef({EDITING, POSTED, POSTING})
//    public @interface Status{}

    public static enum Status {
        EDITING("editing"),
        POSTING("posting"),
        POSTED("posted");

        private String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
//
    /**
     * Client that used to post a blog.
     */
    public static final int  ANDROID = 1, IPHONE = 2, BROWSER = 3;
//    @IntDef({ANDROID, IPHONE, BROWSER})
//    public @interface Client{}

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 498467144)
    private transient BlogDao myDao;

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    @Keep
    public Blog(){
        uuid = UUID.randomUUID().toString();
    }

    @Generated(hash = 1187440016)
    public Blog(Long id, long userId, long postId, String uuid, String title,
            String body, String bodyHtml, Date timestamp, Date updatedTime,
            String url, int status, long locationId, int client, long coverId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.uuid = uuid;
        this.title = title;
        this.body = body;
        this.bodyHtml = bodyHtml;
        this.timestamp = timestamp;
        this.updatedTime = updatedTime;
        this.url = url;
        this.status = status;
        this.locationId = locationId;
        this.client = client;
        this.coverId = coverId;
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

    public long getPostId() {
        return this.postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyHtml() {
        return this.bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public int getClient() {
        return this.client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public long getCoverId() {
        return this.coverId;
    }

    public void setCoverId(long coverId) {
        this.coverId = coverId;
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
    @Generated(hash = 715337303)
    public List<Photo> getPhotos() {
        if (photos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhotoDao targetDao = daoSession.getPhotoDao();
            List<Photo> photosNew = targetDao._queryBlog_Photos(id);
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
    @Generated(hash = 1118661786)
    public List<Comment> getComments() {
        if (comments == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentDao targetDao = daoSession.getCommentDao();
            List<Comment> commentsNew = targetDao._queryBlog_Comments(id);
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
    @Generated(hash = 521451427)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBlogDao() : null;
    }

}
