package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.VtopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vtopic} and its DTO {@link VtopicDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class})
public interface VtopicMapper extends EntityMapper<VtopicDTO, Vtopic> {

    @Mapping(source = "appuser.id", target = "appuserId")
    VtopicDTO toDto(Vtopic vtopic);

    @Mapping(target = "vquestions", ignore = true)
    @Mapping(target = "removeVquestion", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    Vtopic toEntity(VtopicDTO vtopicDTO);

    default Vtopic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vtopic vtopic = new Vtopic();
        vtopic.setId(id);
        return vtopic;
    }
}
