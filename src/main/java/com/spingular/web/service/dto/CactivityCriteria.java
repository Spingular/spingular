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

/**
 * Criteria class for the {@link com.spingular.web.domain.Cactivity} entity. This class is used
 * in {@link com.spingular.web.web.rest.CactivityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cactivities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CactivityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter activityName;

    private LongFilter communityId;

    public CactivityCriteria(){
    }

    public CactivityCriteria(CactivityCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.activityName = other.activityName == null ? null : other.activityName.copy();
        this.communityId = other.communityId == null ? null : other.communityId.copy();
    }

    @Override
    public CactivityCriteria copy() {
        return new CactivityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getActivityName() {
        return activityName;
    }

    public void setActivityName(StringFilter activityName) {
        this.activityName = activityName;
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
        final CactivityCriteria that = (CactivityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(activityName, that.activityName) &&
            Objects.equals(communityId, that.communityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        activityName,
        communityId
        );
    }

    @Override
    public String toString() {
        return "CactivityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (activityName != null ? "activityName=" + activityName + ", " : "") +
                (communityId != null ? "communityId=" + communityId + ", " : "") +
            "}";
    }

}
