package com.spingular.web.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.spingular.web.domain.Vthumb} entity.
 */
public class VthumbDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    private Boolean vthumbUp;

    private Boolean vthumbDown;


    private Long appuserId;

    private Long vquestionId;

    private Long vanswerId;

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

    public Boolean isVthumbUp() {
        return vthumbUp;
    }

    public void setVthumbUp(Boolean vthumbUp) {
        this.vthumbUp = vthumbUp;
    }

    public Boolean isVthumbDown() {
        return vthumbDown;
    }

    public void setVthumbDown(Boolean vthumbDown) {
        this.vthumbDown = vthumbDown;
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

    public Long getVanswerId() {
        return vanswerId;
    }

    public void setVanswerId(Long vanswerId) {
        this.vanswerId = vanswerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VthumbDTO vthumbDTO = (VthumbDTO) o;
        if (vthumbDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vthumbDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VthumbDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", vthumbUp='" + isVthumbUp() + "'" +
            ", vthumbDown='" + isVthumbDown() + "'" +
            ", appuser=" + getAppuserId() +
            ", vquestion=" + getVquestionId() +
            ", vanswer=" + getVanswerId() +
            "}";
    }
}
