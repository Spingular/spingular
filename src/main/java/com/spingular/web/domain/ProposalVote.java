package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProposalVote.
 */
@Entity
@Table(name = "proposal_vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProposalVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "vote_points", nullable = false)
    private Long votePoints;

    @ManyToOne
    @JsonIgnoreProperties("proposalVotes")
    private Appuser appuser;

    @ManyToOne
    @JsonIgnoreProperties("proposalVotes")
    private Proposal proposal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public ProposalVote creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getVotePoints() {
        return votePoints;
    }

    public ProposalVote votePoints(Long votePoints) {
        this.votePoints = votePoints;
        return this;
    }

    public void setVotePoints(Long votePoints) {
        this.votePoints = votePoints;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public ProposalVote appuser(Appuser appuser) {
        this.appuser = appuser;
        return this;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public ProposalVote proposal(Proposal proposal) {
        this.proposal = proposal;
        return this;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProposalVote)) {
            return false;
        }
        return id != null && id.equals(((ProposalVote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProposalVote{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", votePoints=" + getVotePoints() +
            "}";
    }
}
