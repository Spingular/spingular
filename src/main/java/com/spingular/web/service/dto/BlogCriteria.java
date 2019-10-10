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
 * Criteria class for the {@link com.spingular.web.domain.Blog} entity. This class is used
 * in {@link com.spingular.web.web.rest.BlogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BlogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter title;

    private LongFilter postId;

    private LongFilter appuserId;

    private LongFilter communityId;

    public BlogCriteria(){
    }

    public BlogCriteria(BlogCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
        this.communityId = other.communityId == null ? null : other.communityId.copy();
    }

    @Override
    public BlogCriteria copy() {
        return new BlogCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }

    public LongFilter getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(LongFilter appuserId) {
        this.appuserId = appuserId;
    }

    public LongFilter getCommunityId() {
        return communityId;
    }

    public void setCommunityId(LongFilter communityId) {
        this.communityId = communityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BlogCriteria that = (BlogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(title, that.title) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(communityId, that.communityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        title,
        postId,
        appuserId,
        communityId
        );
    }

    @Override
    public String toString() {
        return "BlogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
                (communityId != null ? "communityId=" + communityId + ", " : "") +
            "}";
    }

}
