package com.spingular.web.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.spingular.web.domain.Appuser} entity.
 */
public class AppuserDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    private Long assignedVotesPoints;


    private Long userId;

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

    public Long getAssignedVotesPoints() {
        return assignedVotesPoints;
    }

    public void setAssignedVotesPoints(Long assignedVotesPoints) {
        this.assignedVotesPoints = assignedVotesPoints;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppuserDTO appuserDTO = (AppuserDTO) o;
        if (appuserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appuserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppuserDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", assignedVotesPoints=" + getAssignedVotesPoints() +
            ", user=" + getUserId() +
            "}";
    }
}
