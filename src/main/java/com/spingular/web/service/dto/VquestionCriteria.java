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
 * Criteria class for the {@link com.spingular.web.domain.Vquestion} entity. This class is used
 * in {@link com.spingular.web.web.rest.VquestionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vquestions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VquestionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter vquestion;

    private StringFilter vquestionDescription;

    private LongFilter vanswerId;

    private LongFilter vthumbId;

    private LongFilter appuserId;

    private LongFilter vtopicId;

    public VquestionCriteria(){
    }

    public VquestionCriteria(VquestionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.vquestion = other.vquestion == null ? null : other.vquestion.copy();
        this.vquestionDescription = other.vquestionDescription == null ? null : other.vquestionDescription.copy();
        this.vanswerId = other.vanswerId == null ? null : other.vanswerId.copy();
        this.vthumbId = other.vthumbId == null ? null : other.vthumbId.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
        this.vtopicId = other.vtopicId == null ? null : other.vtopicId.copy();
    }

    @Override
    public VquestionCriteria copy() {
        return new VquestionCriteria(this);
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

    public StringFilter getVquestion() {
        return vquestion;
    }

    public void setVquestion(StringFilter vquestion) {
        this.vquestion = vquestion;
    }

    public StringFilter getVquestionDescription() {
        return vquestionDescription;
    }

    public void setVquestionDescription(StringFilter vquestionDescription) {
        this.vquestionDescription = vquestionDescription;
    }

    public LongFilter getVanswerId() {
        return vanswerId;
    }

    public void setVanswerId(LongFilter vanswerId) {
        this.vanswerId = vanswerId;
    }

    public LongFilter getVthumbId() {
        return vthumbId;
    }

    public void setVthumbId(LongFilter vthumbId) {
        this.vthumbId = vthumbId;
    }

    public LongFilter getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(LongFilter appuserId) {
        this.appuserId = appuserId;
    }

    public LongFilter getVtopicId() {
        return vtopicId;
    }

    public void setVtopicId(LongFilter vtopicId) {
        this.vtopicId = vtopicId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VquestionCriteria that = (VquestionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(vquestion, that.vquestion) &&
            Objects.equals(vquestionDescription, that.vquestionDescription) &&
            Objects.equals(vanswerId, that.vanswerId) &&
            Objects.equals(vthumbId, that.vthumbId) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(vtopicId, that.vtopicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        vquestion,
        vquestionDescription,
        vanswerId,
        vthumbId,
        appuserId,
        vtopicId
        );
    }

    @Override
    public String toString() {
        return "VquestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (vquestion != null ? "vquestion=" + vquestion + ", " : "") +
                (vquestionDescription != null ? "vquestionDescription=" + vquestionDescription + ", " : "") +
                (vanswerId != null ? "vanswerId=" + vanswerId + ", " : "") +
                (vthumbId != null ? "vthumbId=" + vthumbId + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
                (vtopicId != null ? "vtopicId=" + vtopicId + ", " : "") +
            "}";
    }

}
