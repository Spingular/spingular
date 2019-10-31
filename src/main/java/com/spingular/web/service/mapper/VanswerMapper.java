package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.VanswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vanswer} and its DTO {@link VanswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, VquestionMapper.class, VthumbMapper.class})
public interface VanswerMapper extends EntityMapper<VanswerDTO, Vanswer> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "vquestion.id", target = "vquestionId")
    VanswerDTO toDto(Vanswer vanswer);

    @Mapping(target = "vthumbs", ignore = true)
    @Mapping(target = "removeVthumb", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "vquestionId", target = "vquestion")
    Vanswer toEntity(VanswerDTO vanswerDTO);

    default Vanswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vanswer vanswer = new Vanswer();
        vanswer.setId(id);
        return vanswer;
    }
}
