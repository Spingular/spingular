package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CmessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cmessage} and its DTO {@link CmessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class})
public interface CmessageMapper extends EntityMapper<CmessageDTO, Cmessage> {

    @Mapping(source = "csender.id", target = "csenderId")
    @Mapping(source = "creceiver.id", target = "creceiverId")
    CmessageDTO toDto(Cmessage cmessage);

    @Mapping(source = "csenderId", target = "csender")
    @Mapping(source = "creceiverId", target = "creceiver")
    Cmessage toEntity(CmessageDTO cmessageDTO);

    default Cmessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cmessage cmessage = new Cmessage();
        cmessage.setId(id);
        return cmessage;
    }
}
