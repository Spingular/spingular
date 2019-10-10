package com.spingular.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.spingular.web.domain.enumeration.ProposalType;
import com.spingular.web.domain.enumeration.ProposalRole;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.spingular.web.domain.Proposal} entity. This class is used
 * in {@link com.spingular.web.web.rest.ProposalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /proposals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProposalCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProposalType
     */
    public static class ProposalTypeFilter extends Filter<ProposalType> {

        public ProposalTypeFilter() {
        }

        public ProposalTypeFilter(ProposalTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProposalTypeFilter copy() {
            return new ProposalTypeFilter(this);
        }

    }
    /**
     * Class for filtering ProposalRole
     */
    public static class ProposalRoleFilter extends Filter<ProposalRole> {

        public ProposalRoleFilter() {
        }

        public ProposalRoleFilter(ProposalRoleFilter filter) {
            super(filter);
        }

        @Override
        public ProposalRoleFilter copy() {
            return new ProposalRoleFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter proposalName;

    private ProposalTypeFilter proposalType;

    private ProposalRoleFilter proposalRole;

    private InstantFilter releaseDate;

    private BooleanFilter isOpen;

    private BooleanFilter isAccepted;

    private BooleanFilter isPaid;

    private LongFilter proposalVoteId;

    private LongFilter appuserId;

    private LongFilter postId;

    public ProposalCriteria(){
    }

    public ProposalCriteria(ProposalCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.proposalName = other.proposalName == null ? null : other.proposalName.copy();
        this.proposalType = other.proposalType == null ? null : other.proposalType.copy();
        this.proposalRole = other.proposalRole == null ? null : other.proposalRole.copy();
        this.releaseDate = other.releaseDate == null ? null : other.releaseDate.copy();
        this.isOpen = other.isOpen == null ? null : other.isOpen.copy();
        this.isAccepted = other.isAccepted == null ? null : other.isAccepted.copy();
        this.isPaid = other.isPaid == null ? null : other.isPaid.copy();
        this.proposalVoteId = other.proposalVoteId == null ? null : other.proposalVoteId.copy();
        this.appuserId = other.appuserId == null ? null : other.appuserId.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
    }

    @Override
    public ProposalCriteria copy() {
        return new ProposalCriteria(this);
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

    public StringFilter getProposalName() {
        return proposalName;
    }

    public void setProposalName(StringFilter proposalName) {
        this.proposalName = proposalName;
    }

    public ProposalTypeFilter getProposalType() {
        return proposalType;
    }

    public void setProposalType(ProposalTypeFilter proposalType) {
        this.proposalType = proposalType;
    }

    public ProposalRoleFilter getProposalRole() {
        return proposalRole;
    }

    public void setProposalRole(ProposalRoleFilter proposalRole) {
        this.proposalRole = proposalRole;
    }

    public InstantFilter getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(InstantFilter releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BooleanFilter getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(BooleanFilter isOpen) {
        this.isOpen = isOpen;
    }

    public BooleanFilter getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(BooleanFilter isAccepted) {
        this.isAccepted = isAccepted;
    }

    public BooleanFilter getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(BooleanFilter isPaid) {
        this.isPaid = isPaid;
    }

    public LongFilter getProposalVoteId() {
        return proposalVoteId;
    }

    public void setProposalVoteId(LongFilter proposalVoteId) {
        this.proposalVoteId = proposalVoteId;
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
        final ProposalCriteria that = (ProposalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(proposalName, that.proposalName) &&
            Objects.equals(proposalType, that.proposalType) &&
            Objects.equals(proposalRole, that.proposalRole) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(isOpen, that.isOpen) &&
            Objects.equals(isAccepted, that.isAccepted) &&
            Objects.equals(isPaid, that.isPaid) &&
            Objects.equals(proposalVoteId, that.proposalVoteId) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        proposalName,
        proposalType,
        proposalRole,
        releaseDate,
        isOpen,
        isAccepted,
        isPaid,
        proposalVoteId,
        appuserId,
        postId
        );
    }

    @Override
    public String toString() {
        return "ProposalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (proposalName != null ? "proposalName=" + proposalName + ", " : "") +
                (proposalType != null ? "proposalType=" + proposalType + ", " : "") +
                (proposalRole != null ? "proposalRole=" + proposalRole + ", " : "") +
                (releaseDate != null ? "releaseDate=" + releaseDate + ", " : "") +
                (isOpen != null ? "isOpen=" + isOpen + ", " : "") +
                (isAccepted != null ? "isAccepted=" + isAccepted + ", " : "") +
                (isPaid != null ? "isPaid=" + isPaid + ", " : "") +
                (proposalVoteId != null ? "proposalVoteId=" + proposalVoteId + ", " : "") +
                (appuserId != null ? "appuserId=" + appuserId + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
            "}";
    }

}
