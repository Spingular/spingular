package com.spingular.web.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.spingular.web.domain.ProposalVote} entity.
 */
public class ProposalVoteDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    private Long votePoints;


    private Long appuserId;

    private Long proposalId;

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

    public Long getVotePoints() {
        return votePoints;
    }

    public void setVotePoints(Long votePoints) {
        this.votePoints = votePoints;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProposalVoteDTO proposalVoteDTO = (ProposalVoteDTO) o;
        if (proposalVoteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proposalVoteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProposalVoteDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", votePoints=" + getVotePoints() +
            ", appuser=" + getAppuserId() +
            ", proposal=" + getProposalId() +
            "}";
    }
}
