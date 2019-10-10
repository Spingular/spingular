package com.spingular.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Celeb.
 */
@Entity
@Table(name = "celeb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Celeb implements Serializable {

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
    @JoinTable(name = "celeb_appuser",
               joinColumns = @JoinColumn(name = "celeb_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "appuser_id", referencedColumnName = "id"))
    private Set<Appuser> appusers = new HashSet<>();

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

    public Celeb celebName(String celebName) {
        this.celebName = celebName;
        return this;
    }

    public void setCelebName(String celebName) {
        this.celebName = celebName;
    }

    public Set<Appuser> getAppusers() {
        return appusers;
    }

    public Celeb appusers(Set<Appuser> appusers) {
        this.appusers = appusers;
        return this;
    }

    public Celeb addAppuser(Appuser appuser) {
        this.appusers.add(appuser);
        appuser.getCelebs().add(this);
        return this;
    }

    public Celeb removeAppuser(Appuser appuser) {
        this.appusers.remove(appuser);
        appuser.getCelebs().remove(this);
        return this;
    }

    public void setAppusers(Set<Appuser> appusers) {
        this.appusers = appusers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Celeb)) {
            return false;
        }
        return id != null && id.equals(((Celeb) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Celeb{" +
            "id=" + getId() +
            ", celebName='" + getCelebName() + "'" +
            "}";
    }
}
