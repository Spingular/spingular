package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.VthumbDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vthumb} and its DTO {@link VthumbDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, VquestionMapper.class, VanswerMapper.class})
public interface VthumbMapper extends EntityMapper<VthumbDTO, Vthumb> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "vquestion.id", target = "vquestionId")
    @Mapping(source = "vanswer.id", target = "vanswerId")
    VthumbDTO toDto(Vthumb vthumb);

    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "vquestionId", target = "vquestion")
    @Mapping(source = "vanswerId", target = "vanswer")
    Vthumb toEntity(VthumbDTO vthumbDTO);

    default Vthumb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vthumb vthumb = new Vthumb();
        vthumb.setId(id);
        return vthumb;
    }
}
