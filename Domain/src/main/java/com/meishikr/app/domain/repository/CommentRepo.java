package com.meishikr.app.domain.repository;

import com.meishikr.app.domain.entity.comment.Comment;

import java.util.List;

import rx.Observable;

/**
 * Comment repository interface.
 * Created by yinhang on 16/7/18.
 */
public interface CommentRepo {

    /**
     * Get latest comments of a post.
     * @param postId
     * @return
     */
    Observable<List<Comment>> latestComments(long postId);
}
