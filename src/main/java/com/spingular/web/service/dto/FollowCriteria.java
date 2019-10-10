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
 * Criteria class for the {@link com.spingular.web.domain.Follow} entity. This class is used
 * in {@link com.spingular.web.web.rest.FollowResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /follows?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FollowCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter followedId;

    private LongFilter followingId;

    private LongFilter cfollowedId;

    private LongFilter cfollowingId;

    public FollowCriteria(){
    }

    public FollowCriteria(FollowCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.followedId = other.followedId == null ? null : other.followedId.copy();
        this.followingId = other.followingId == null ? null : other.followingId.copy();
        this.cfollowedId = other.cfollowedId == null ? null : other.cfollowedId.copy();
        this.cfollowingId = other.cfollowingId == null ? null : other.cfollowingId.copy();
    }

    @Override
    public FollowCriteria copy() {
        return new FollowCriteria(this);
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

    public LongFilter getFollowedId() {
        return followedId;
    }

    public void setFollowedId(LongFilter followedId) {
        this.followedId = followedId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }

    public LongFilter getCfollowedId() {
        return cfollowedId;
    }

    public void setCfollowedId(LongFilter cfollowedId) {
        this.cfollowedId = cfollowedId;
    }

    public LongFilter getCfollowingId() {
        return cfollowingId;
    }

    public void setCfollowingId(LongFilter cfollowingId) {
        this.cfollowingId = cfollowingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FollowCriteria that = (FollowCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(followedId, that.followedId) &&
            Objects.equals(followingId, that.followingId) &&
            Objects.equals(cfollowedId, that.cfollowedId) &&
            Objects.equals(cfollowingId, that.cfollowingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        followedId,
        followingId,
        cfollowedId,
        cfollowingId
        );
    }

    @Override
    public String toString() {
        return "FollowCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (followedId != null ? "followedId=" + followedId + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
                (cfollowedId != null ? "cfollowedId=" + cfollowedId + ", " : "") +
                (cfollowingId != null ? "cfollowingId=" + cfollowingId + ", " : "") +
            "}";
    }

}
