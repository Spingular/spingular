package com.spingular.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cactivity.
 */
@Entity
@Table(name = "cactivity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cactivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "activity_name", length = 40, nullable = false)
    private String activityName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cactivity_community",
               joinColumns = @JoinColumn(name = "cactivity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"))
    private Set<Community> communities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public Cactivity activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Cactivity communities(Set<Community> communities) {
        this.communities = communities;
        return this;
    }

    public Cactivity addCommunity(Community community) {
        this.communities.add(community);
        community.getCactivities().add(this);
        return this;
    }

    public Cactivity removeCommunity(Community community) {
        this.communities.remove(community);
        community.getCactivities().remove(this);
        return this;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cactivity)) {
            return false;
        }
        return id != null && id.equals(((Cactivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cactivity{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            "}";
    }
}
