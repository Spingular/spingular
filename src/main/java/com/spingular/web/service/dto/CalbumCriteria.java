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
 * Criteria class for the {@link com.spingular.web.domain.Calbum} entity. This class is used
 * in {@link com.spingular.web.web.rest.CalbumResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /calbums?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CalbumCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter title;

    private LongFilter photoId;

    private LongFilter communityId;

    public CalbumCriteria(){
    }

    public CalbumCriteria(CalbumCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.photoId = other.photoId == null ? null : other.photoId.copy();
        this.communityId = other.communityId == null ? null : other.communityId.copy();
    }

    @Override
    public CalbumCriteria copy() {
        return new CalbumCriteria(this);
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

    public LongFilter getPhotoId() {
        return photoId;
    }

    public void setPhotoId(LongFilter photoId) {
        this.photoId = photoId;
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
        final CalbumCriteria that = (CalbumCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(title, that.title) &&
            Objects.equals(photoId, that.photoId) &&
            Objects.equals(communityId, that.communityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        title,
        photoId,
        communityId
        );
    }

    @Override
    public String toString() {
        return "CalbumCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (photoId != null ? "photoId=" + photoId + ", " : "") +
                (communityId != null ? "communityId=" + communityId + ", " : "") +
            "}";
    }

}
