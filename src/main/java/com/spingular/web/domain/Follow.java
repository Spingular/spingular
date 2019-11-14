package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Follow.
 */
@Entity
@Table(name = "follow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    @JsonIgnoreProperties("followeds")
    private Appuser followed;

    @ManyToOne
    @JsonIgnoreProperties("followings")
    private Appuser following;

    @ManyToOne
    @JsonIgnoreProperties("cfolloweds")
    private Community cfollowed;

    @ManyToOne
    @JsonIgnoreProperties("cfollowings")
    private Community cfollowing;

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

    public Follow creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Appuser getFollowed() {
        return followed;
    }

    public Follow followed(Appuser appuser) {
        this.followed = appuser;
        return this;
    }

    public void setFollowed(Appuser appuser) {
        this.followed = appuser;
    }

    public Appuser getFollowing() {
        return following;
    }

    public Follow following(Appuser appuser) {
        this.following = appuser;
        return this;
    }

    public void setFollowing(Appuser appuser) {
        this.following = appuser;
    }

    public Community getCfollowed() {
        return cfollowed;
    }

    public Follow cfollowed(Community community) {
        this.cfollowed = community;
        return this;
    }

    public void setCfollowed(Community community) {
        this.cfollowed = community;
    }

    public Community getCfollowing() {
        return cfollowing;
    }

    public Follow cfollowing(Community community) {
        this.cfollowing = community;
        return this;
    }

    public void setCfollowing(Community community) {
        this.cfollowing = community;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Follow)) {
            return false;
        }
        return id != null && id.equals(((Follow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Follow{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
