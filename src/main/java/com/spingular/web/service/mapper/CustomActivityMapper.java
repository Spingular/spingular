package com.spingular.web.service.mapper;

import org.mapstruct.Mapper;

import com.spingular.web.domain.Activity;
import com.spingular.web.service.dto.CustomActivityDTO;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomActivityMapper extends EntityMapper<CustomActivityDTO, Activity> {



    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
