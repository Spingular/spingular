package com.spingular.web.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.spingular.web.domain.enumeration.ProposalType;
import com.spingular.web.domain.enumeration.ProposalRole;

/**
 * A DTO for the {@link com.spingular.web.domain.Proposal} entity.
 */
public class ProposalDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 250)
    private String proposalName;

    @NotNull
    private ProposalType proposalType;

    @NotNull
    private ProposalRole proposalRole;

    private Instant releaseDate;

    private Boolean isOpen;

    private Boolean isAccepted;

    private Boolean isPaid;


    private Long appuserId;

    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getProposalName() {
        return proposalName;
    }

    public void setProposalName(String proposalName) {
        this.proposalName = proposalName;
    }

    public ProposalType getProposalType() {
        return proposalType;
    }

    public void setProposalType(ProposalType proposalType) {
        this.proposalType = proposalType;
    }

    public ProposalRole getProposalRole() {
        return proposalRole;
    }

    public void setProposalRole(ProposalRole proposalRole) {
        this.proposalRole = proposalRole;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean isIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean isIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
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

        ProposalDTO proposalDTO = (ProposalDTO) o;
        if (proposalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proposalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProposalDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", proposalName='" + getProposalName() + "'" +
            ", proposalType='" + getProposalType() + "'" +
            ", proposalRole='" + getProposalRole() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", isOpen='" + isIsOpen() + "'" +
            ", isAccepted='" + isIsAccepted() + "'" +
            ", isPaid='" + isIsPaid() + "'" +
            ", appuser=" + getAppuserId() +
            ", post=" + getPostId() +
            "}";
    }
}
