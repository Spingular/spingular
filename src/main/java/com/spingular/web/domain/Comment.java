package com.spingular.web.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 65000)
    @Column(name = "comment_text", length = 65000, nullable = false)
    private String commentText;

    @Column(name = "is_offensive")
    private Boolean isOffensive;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("comments")
    private Appuser appuser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("comments")
    private Post post;

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

    public Comment creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getCommentText() {
        return commentText;
    }

    public Comment commentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean isIsOffensive() {
        return isOffensive;
    }

    public Comment isOffensive(Boolean isOffensive) {
        this.isOffensive = isOffensive;
        return this;
    }

    public void setIsOffensive(Boolean isOffensive) {
        this.isOffensive = isOffensive;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public Comment appuser(Appuser appuser) {
        this.appuser = appuser;
        return this;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }

    public Post getPost() {
        return post;
    }

    public Comment post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", commentText='" + getCommentText() + "'" +
            ", isOffensive='" + isIsOffensive() + "'" +
            "}";
    }
}
