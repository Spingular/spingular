package com.spingular.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Urllink.
 */
@Entity
@Table(name = "urllink")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Urllink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "link_text", nullable = false)
    private String linkText;

    @NotNull
    @Column(name = "link_url", nullable = false)
    private String linkURL;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkText() {
        return linkText;
    }

    public Urllink linkText(String linkText) {
        this.linkText = linkText;
        return this;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public Urllink linkURL(String linkURL) {
        this.linkURL = linkURL;
        return this;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Urllink)) {
            return false;
        }
        return id != null && id.equals(((Urllink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Urllink{" +
            "id=" + getId() +
            ", linkText='" + getLinkText() + "'" +
            ", linkURL='" + getLinkURL() + "'" +
            "}";
    }
}
