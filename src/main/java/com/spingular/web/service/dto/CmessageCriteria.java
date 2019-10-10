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
 * Criteria class for the {@link com.spingular.web.domain.Cmessage} entity. This class is used
 * in {@link com.spingular.web.web.rest.CmessageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cmessages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CmessageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter messageText;

    private BooleanFilter isDelivered;

    private LongFilter csenderId;

    private LongFilter creceiverId;

    public CmessageCriteria(){
    }

    public CmessageCriteria(CmessageCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.messageText = other.messageText == null ? null : other.messageText.copy();
        this.isDelivered = other.isDelivered == null ? null : other.isDelivered.copy();
        this.csenderId = other.csenderId == null ? null : other.csenderId.copy();
        this.creceiverId = other.creceiverId == null ? null : other.creceiverId.copy();
    }

    @Override
    public CmessageCriteria copy() {
        return new CmessageCriteria(this);
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

    public StringFilter getMessageText() {
        return messageText;
    }

    public void setMessageText(StringFilter messageText) {
        this.messageText = messageText;
    }

    public BooleanFilter getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(BooleanFilter isDelivered) {
        this.isDelivered = isDelivered;
    }

    public LongFilter getCsenderId() {
        return csenderId;
    }

    public void setCsenderId(LongFilter csenderId) {
        this.csenderId = csenderId;
    }

    public LongFilter getCreceiverId() {
        return creceiverId;
    }

    public void setCreceiverId(LongFilter creceiverId) {
        this.creceiverId = creceiverId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CmessageCriteria that = (CmessageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(messageText, that.messageText) &&
            Objects.equals(isDelivered, that.isDelivered) &&
            Objects.equals(csenderId, that.csenderId) &&
            Objects.equals(creceiverId, that.creceiverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        messageText,
        isDelivered,
        csenderId,
        creceiverId
        );
    }

    @Override
    public String toString() {
        return "CmessageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (messageText != null ? "messageText=" + messageText + ", " : "") +
                (isDelivered != null ? "isDelivered=" + isDelivered + ", " : "") +
                (csenderId != null ? "csenderId=" + csenderId + ", " : "") +
                (creceiverId != null ? "creceiverId=" + creceiverId + ", " : "") +
            "}";
    }

}
