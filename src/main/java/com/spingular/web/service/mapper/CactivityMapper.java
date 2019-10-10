package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CactivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cactivity} and its DTO {@link CactivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class})
public interface CactivityMapper extends EntityMapper<CactivityDTO, Cactivity> {


    @Mapping(target = "removeCommunity", ignore = true)

    default Cactivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cactivity cactivity = new Cactivity();
        cactivity.setId(id);
        return cactivity;
    }
}
