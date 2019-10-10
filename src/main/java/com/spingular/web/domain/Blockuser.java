package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Blockuser.
 */
@Entity
@Table(name = "blockuser")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blockuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    @JsonIgnoreProperties("blockedusers")
    private Appuser blockeduser;

    @ManyToOne
    @JsonIgnoreProperties("blockingusers")
    private Appuser blockinguser;

    @ManyToOne
    @JsonIgnoreProperties("cblockedusers")
    private Community cblockeduser;

    @ManyToOne
    @JsonIgnoreProperties("cblockingusers")
    private Community cblockinguser;

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

    public Blockuser creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Appuser getBlockeduser() {
        return blockeduser;
    }

    public Blockuser blockeduser(Appuser appuser) {
        this.blockeduser = appuser;
        return this;
    }

    public void setBlockeduser(Appuser appuser) {
        this.blockeduser = appuser;
    }

    public Appuser getBlockinguser() {
        return blockinguser;
    }

    public Blockuser blockinguser(Appuser appuser) {
        this.blockinguser = appuser;
        return this;
    }

    public void setBlockinguser(Appuser appuser) {
        this.blockinguser = appuser;
    }

    public Community getCblockeduser() {
        return cblockeduser;
    }

    public Blockuser cblockeduser(Community community) {
        this.cblockeduser = community;
        return this;
    }

    public void setCblockeduser(Community community) {
        this.cblockeduser = community;
    }

    public Community getCblockinguser() {
        return cblockinguser;
    }

    public Blockuser cblockinguser(Community community) {
        this.cblockinguser = community;
        return this;
    }

    public void setCblockinguser(Community community) {
        this.cblockinguser = community;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blockuser)) {
            return false;
        }
        return id != null && id.equals(((Blockuser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Blockuser{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
