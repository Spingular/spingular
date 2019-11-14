package com.spingular.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Topic.
 */
@Entity
@Table(name = "topic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "topic_name", length = 40, nullable = false)
    private String topicName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "topic_post",
               joinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    private Set<Post> posts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public Topic topicName(String topicName) {
        this.topicName = topicName;
        return this;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Topic posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Topic addPost(Post post) {
        this.posts.add(post);
        post.getTopics().add(this);
        return this;
    }

    public Topic removePost(Post post) {
        this.posts.remove(post);
        post.getTopics().remove(this);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topic)) {
            return false;
        }
        return id != null && id.equals(((Topic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Topic{" +
            "id=" + getId() +
            ", topicName='" + getTopicName() + "'" +
            "}";
    }
}
