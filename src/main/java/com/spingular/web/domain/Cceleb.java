package com.spingular.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cceleb.
 */
@Entity
@Table(name = "cceleb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cceleb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "celeb_name", length = 40, nullable = false)
    private String celebName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cceleb_community",
               joinColumns = @JoinColumn(name = "cceleb_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"))
    private Set<Community> communities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCelebName() {
        return celebName;
    }

    public Cceleb celebName(String celebName) {
        this.celebName = celebName;
        return this;
    }

    public void setCelebName(String celebName) {
        this.celebName = celebName;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Cceleb communities(Set<Community> communities) {
        this.communities = communities;
        return this;
    }

    public Cceleb addCommunity(Community community) {
        this.communities.add(community);
        community.getCcelebs().add(this);
        return this;
    }

    public Cceleb removeCommunity(Community community) {
        this.communities.remove(community);
        community.getCcelebs().remove(this);
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
        if (!(o instanceof Cceleb)) {
            return false;
        }
        return id != null && id.equals(((Cceleb) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cceleb{" +
            "id=" + getId() +
            ", celebName='" + getCelebName() + "'" +
            "}";
    }
}
