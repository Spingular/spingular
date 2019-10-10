package com.spingular.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.spingular.web.domain.Comment} entity. This class is used
 * in {@link com.spingular.web.web.rest.CommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter commentText;

    private BooleanFilter isOffensive;

    private LongFilter appuserId;

    private LongFilter postId;

    public CommentCriteria(){
    }

    public CommentCriteria(CommentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.commentText = other.commentText == null ? null : other.commentText.copy();
        this.isOffensive = other.isOffensive == null ? null : other.isOffensive.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
    }

    @Override
    public CommentCriteria copy() {
        return new CommentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public StringFilter getCommentText() {
        return commentText;
    }

    public void setCommentText(StringFilter commentText) {
        this.commentText = commentText;
    }

    public BooleanFilter getIsOffensive() {
        return isOffensive;
    }

    public void setIsOffensive(BooleanFilter isOffensive) {
        this.isOffensive = isOffensive;
    }

    public LongFilter getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(LongFilter appuserId) {
        this.appuserId = appuserId;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommentCriteria that = (CommentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(commentText, that.commentText) &&
            Objects.equals(isOffensive, that.isOffensive) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        commentText,
        isOffensive,
        appuserId,
        postId
        );
    }

    @Override
    public String toString() {
        return "CommentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (commentText != null ? "commentText=" + commentText + ", " : "") +
                (isOffensive != null ? "isOffensive=" + isOffensive + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
            "}";
    }

}
