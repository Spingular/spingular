package com.spingular.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.spingular.web.domain.Vanswer} entity.
 */
public class VanswerDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 500)
    private String urlVanswer;

    private Boolean accepted;

    private Set<VthumbDTO> vthumbs;

    private Long appuserId;

    private Long vquestionId;

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

    public String getUrlVanswer() {
        return urlVanswer;
    }

    public void setUrlVanswer(String urlVanswer) {
        this.urlVanswer = urlVanswer;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    public Long getVquestionId() {
        return vquestionId;
    }

    public void setVquestionId(Long vquestionId) {
        this.vquestionId = vquestionId;
    }

    public Set<VthumbDTO> getVthumbs() {
        return vthumbs;
    }

    public void setVthumbs(Set<VthumbDTO> vthumbs) {
        this.vthumbs = vthumbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VanswerDTO vanswerDTO = (VanswerDTO) o;
        if (vanswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vanswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VanswerDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", urlVanswer='" + getUrlVanswer() + "'" +
            ", accepted='" + isAccepted() + "'" +
            ", appuser=" + getAppuserId() +
            ", vquestion=" + getVquestionId() +
            "}";
    }
}
