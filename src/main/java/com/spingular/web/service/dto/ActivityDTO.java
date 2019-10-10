package com.spingular.web.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.spingular.web.domain.Activity} entity.
 */
public class ActivityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    private String activityName;


    private Set<AppuserDTO> appusers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Set<AppuserDTO> getAppusers() {
        return appusers;
    }

    public void setAppusers(Set<AppuserDTO> appusers) {
        this.appusers = appusers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityDTO activityDTO = (ActivityDTO) o;
        if (activityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            "}";
    }
}
