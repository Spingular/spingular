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
 * Criteria class for the {@link com.spingular.web.domain.Tag} entity. This class is used
 * in {@link com.spingular.web.web.rest.TagResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tags?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TagCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tagName;

    private LongFilter postId;

    public TagCriteria(){
    }

    public TagCriteria(TagCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tagName = other.tagName == null ? null : other.tagName.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
    }

    @Override
    public TagCriteria copy() {
        return new TagCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTagName() {
        return tagName;
    }

    public void setTagName(StringFilter tagName) {
        this.tagName = tagName;
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
        final TagCriteria that = (TagCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tagName, that.tagName) &&
            Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tagName,
        postId
        );
    }

    @Override
    public String toString() {
        return "TagCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tagName != null ? "tagName=" + tagName + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
            "}";
    }

}
