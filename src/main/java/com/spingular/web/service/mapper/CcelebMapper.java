package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CcelebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cceleb} and its DTO {@link CcelebDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class})
public interface CcelebMapper extends EntityMapper<CcelebDTO, Cceleb> {


    @Mapping(target = "removeCommunity", ignore = true)

    default Cceleb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cceleb cceleb = new Cceleb();
        cceleb.setId(id);
        return cceleb;
    }
}
