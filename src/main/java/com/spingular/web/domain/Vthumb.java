package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Vthumb.
 */
@Entity
@Table(name = "vthumb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vthumb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "vthumb_up")
    private Boolean vthumbUp;

    @Column(name = "vthumb_down")
    private Boolean vthumbDown;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vthumbs")
    private Appuser appuser;

    @ManyToOne
    @JsonIgnoreProperties("vthumbs")
    private Vquestion vquestion;

    @ManyToOne
    @JsonIgnoreProperties("vthumbs")
    private Vanswer vanswer;

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

    public Vthumb creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isVthumbUp() {
        return vthumbUp;
    }

    public Vthumb vthumbUp(Boolean vthumbUp) {
        this.vthumbUp = vthumbUp;
        return this;
    }

    public void setVthumbUp(Boolean vthumbUp) {
        this.vthumbUp = vthumbUp;
    }

    public Boolean isVthumbDown() {
        return vthumbDown;
    }

    public Vthumb vthumbDown(Boolean vthumbDown) {
        this.vthumbDown = vthumbDown;
        return this;
    }

    public void setVthumbDown(Boolean vthumbDown) {
        this.vthumbDown = vthumbDown;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public Vthumb appuser(Appuser appuser) {
        this.appuser = appuser;
        return this;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }

    public Vquestion getVquestion() {
        return vquestion;
    }

    public Vthumb vquestion(Vquestion vquestion) {
        this.vquestion = vquestion;
        return this;
    }

    public void setVquestion(Vquestion vquestion) {
        this.vquestion = vquestion;
    }

    public Vanswer getVanswer() {
        return vanswer;
    }

    public Vthumb vanswer(Vanswer vanswer) {
        this.vanswer = vanswer;
        return this;
    }

    public void setVanswer(Vanswer vanswer) {
        this.vanswer = vanswer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vthumb)) {
            return false;
        }
        return id != null && id.equals(((Vthumb) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vthumb{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", vthumbUp='" + isVthumbUp() + "'" +
            ", vthumbDown='" + isVthumbDown() + "'" +
            "}";
    }
}
