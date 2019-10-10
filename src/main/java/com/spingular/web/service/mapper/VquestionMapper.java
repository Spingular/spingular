package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.VquestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vquestion} and its DTO {@link VquestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, VtopicMapper.class})
public interface VquestionMapper extends EntityMapper<VquestionDTO, Vquestion> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "vtopic.id", target = "vtopicId")
    VquestionDTO toDto(Vquestion vquestion);

    @Mapping(target = "vanswers", ignore = true)
    @Mapping(target = "removeVanswer", ignore = true)
    @Mapping(target = "vthumbs", ignore = true)
    @Mapping(target = "removeVthumb", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "vtopicId", target = "vtopic")
    Vquestion toEntity(VquestionDTO vquestionDTO);

    default Vquestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vquestion vquestion = new Vquestion();
        vquestion.setId(id);
        return vquestion;
    }
}
