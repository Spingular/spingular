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
 * Criteria class for the {@link com.spingular.web.domain.Vanswer} entity. This class is used
 * in {@link com.spingular.web.web.rest.VanswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vanswers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VanswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter urlVanswer;

    private BooleanFilter accepted;

    private LongFilter vthumbId;

    private LongFilter appuserId;

    private LongFilter vquestionId;

    public VanswerCriteria(){
    }

    public VanswerCriteria(VanswerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.urlVanswer = other.urlVanswer == null ? null : other.urlVanswer.copy();
        this.accepted = other.accepted == null ? null : other.accepted.copy();
        this.vthumbId = other.vthumbId == null ? null : other.vthumbId.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
        this.vquestionId = other.vquestionId == null ? null : other.vquestionId.copy();
    }

    @Override
    public VanswerCriteria copy() {
        return new VanswerCriteria(this);
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

    public StringFilter getUrlVanswer() {
        return urlVanswer;
    }

    public void setUrlVanswer(StringFilter urlVanswer) {
        this.urlVanswer = urlVanswer;
    }

    public BooleanFilter getAccepted() {
        return accepted;
    }

    public void setAccepted(BooleanFilter accepted) {
        this.accepted = accepted;
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

    public LongFilter getVquestionId() {
        return vquestionId;
    }

    public void setVquestionId(LongFilter vquestionId) {
        this.vquestionId = vquestionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VanswerCriteria that = (VanswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(urlVanswer, that.urlVanswer) &&
            Objects.equals(accepted, that.accepted) &&
            Objects.equals(vthumbId, that.vthumbId) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(vquestionId, that.vquestionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        urlVanswer,
        accepted,
        vthumbId,
        appuserId,
        vquestionId
        );
    }

    @Override
    public String toString() {
        return "VanswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (urlVanswer != null ? "urlVanswer=" + urlVanswer + ", " : "") +
                (accepted != null ? "accepted=" + accepted + ", " : "") +
                (vthumbId != null ? "vthumbId=" + vthumbId + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
                (vquestionId != null ? "vquestionId=" + vquestionId + ", " : "") +
            "}";
    }

}
