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
 * Criteria class for the {@link com.spingular.web.domain.Vtopic} entity. This class is used
 * in {@link com.spingular.web.web.rest.VtopicResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vtopics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VtopicCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter vtopicTitle;

    private StringFilter vtopicDescription;

    private LongFilter vquestionId;

    private LongFilter appuserId;

    public VtopicCriteria(){
    }

    public VtopicCriteria(VtopicCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.vtopicTitle = other.vtopicTitle == null ? null : other.vtopicTitle.copy();
        this.vtopicDescription = other.vtopicDescription == null ? null : other.vtopicDescription.copy();
        this.vquestionId = other.vquestionId == null ? null : other.vquestionId.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
    }

    @Override
    public VtopicCriteria copy() {
        return new VtopicCriteria(this);
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

    public StringFilter getVtopicTitle() {
        return vtopicTitle;
    }

    public void setVtopicTitle(StringFilter vtopicTitle) {
        this.vtopicTitle = vtopicTitle;
    }

    public StringFilter getVtopicDescription() {
        return vtopicDescription;
    }

    public void setVtopicDescription(StringFilter vtopicDescription) {
        this.vtopicDescription = vtopicDescription;
    }

    public LongFilter getVquestionId() {
        return vquestionId;
    }

    public void setVquestionId(LongFilter vquestionId) {
        this.vquestionId = vquestionId;
    }

    public LongFilter getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(LongFilter appuserId) {
        this.appuserId = appuserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VtopicCriteria that = (VtopicCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(vtopicTitle, that.vtopicTitle) &&
            Objects.equals(vtopicDescription, that.vtopicDescription) &&
            Objects.equals(vquestionId, that.vquestionId) &&
            Objects.equals(appuserId, that.appuserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        vtopicTitle,
        vtopicDescription,
        vquestionId,
        appuserId
        );
    }

    @Override
    public String toString() {
        return "VtopicCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (vtopicTitle != null ? "vtopicTitle=" + vtopicTitle + ", " : "") +
                (vtopicDescription != null ? "vtopicDescription=" + vtopicDescription + ", " : "") +
                (vquestionId != null ? "vquestionId=" + vquestionId + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
            "}";
    }

}
