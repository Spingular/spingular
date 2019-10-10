package com.spingular.web.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.spingular.web.domain.Appphoto} entity.
 */
public class AppphotoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long appuserId;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(Long appuserId) {
        this.appuserId = appuserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppphotoDTO appphotoDTO = (AppphotoDTO) o;
        if (appphotoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appphotoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppphotoDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", image='" + getImage() + "'" +
            ", appuser=" + getAppuserId() +
            "}";
    }
}
