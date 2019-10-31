package com.spingular.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.spingular.web.domain.Vquestion} entity.
 */
public class VquestionDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 100)
    private String vquestion;

    @Size(min = 2, max = 250)
    private String vquestionDescription;

    private Set<VanswerDTO> vanswers;

    private Long appuserId;

    private Long vtopicId;

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

    public String getVquestion() {
        return vquestion;
    }

    public void setVquestion(String vquestion) {
        this.vquestion = vquestion;
    }

    public String getVquestionDescription() {
        return vquestionDescription;
    }

    public void setVquestionDescription(String vquestionDescription) {
        this.vquestionDescription = vquestionDescription;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    public Long getVtopicId() {
        return vtopicId;
    }

    public void setVtopicId(Long vtopicId) {
        this.vtopicId = vtopicId;
    }

    public Set<VanswerDTO> getVanswers() {
        return vanswers;
    }

    public void setVanswers(Set<VanswerDTO> vanswers) {
        this.vanswers = vanswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VquestionDTO vquestionDTO = (VquestionDTO) o;
        if (vquestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vquestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VquestionDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", vquestion='" + getVquestion() + "'" +
            ", vquestionDescription='" + getVquestionDescription() + "'" +
            ", appuser=" + getAppuserId() +
            ", vtopic=" + getVtopicId() +
            "}";
    }
}
