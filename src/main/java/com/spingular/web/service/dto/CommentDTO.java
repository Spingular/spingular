package com.spingular.web.service.dto;
import java.time.Instant;

import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.spingular.web.domain.Comment} entity.
 */
public class CommentDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 65000)
    private String commentText;

    private Boolean isOffensive;

    private Long appuserId;

    private Long postId;

    private String commenterFirstName;

    private String commenterLastName;
    
    @Lob
    private byte[] commenterImage;
    private String commenterImageContentType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean isIsOffensive() {
        return isOffensive;
    }

    public void setIsOffensive(Boolean isOffensive) {
        this.isOffensive = isOffensive;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCommenterFirstName() {
		return commenterFirstName;
	}

	public void setCommenterFirstName(String commenterFirstName) {
		this.commenterFirstName = commenterFirstName;
	}

	public String getCommenterLastName() {
		return commenterLastName;
	}

	public void setCommenterLastName(String commenterLastName) {
		this.commenterLastName = commenterLastName;
	}

	public byte[] getCommenterImage() {
		return commenterImage;
	}

	public void setCommenterImage(byte[] commenterImage) {
		this.commenterImage = commenterImage;
	}

	public String getCommenterImageContentType() {
		return commenterImageContentType;
	}

	public void setCommenterImageContentType(String commenterImageContentType) {
		this.commenterImageContentType = commenterImageContentType;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", commentText='" + getCommentText() + "'" +
            ", isOffensive='" + isIsOffensive() + "'" +
            ", appuser=" + getAppuserId() +
            ", post=" + getPostId() +
            ", commenterFirstName=" + getCommenterFirstName() +
            ", commenterLastName=" + getCommenterLastName() +
            ", commenterImage='" + getCommenterImage() + "'" +
            "}";
    }
}
