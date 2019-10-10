package com.spingular.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.spingular.web.domain.Topic} entity. This class is used
 * in {@link com.spingular.web.web.rest.TopicResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /topics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TopicCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter topicName;

    private LongFilter postId;

    public TopicCriteria(){
    }

    public TopicCriteria(TopicCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.topicName = other.topicName == null ? null : other.topicName.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
    }

    @Override
    public TopicCriteria copy() {
        return new TopicCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTopicName() {
        return topicName;
    }

    public void setTopicName(StringFilter topicName) {
        this.topicName = topicName;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TopicCriteria that = (TopicCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(topicName, that.topicName) &&
            Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        topicName,
        postId
        );
    }

    @Override
    public String toString() {
        return "TopicCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (topicName != null ? "topicName=" + topicName + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
            "}";
    }

}
