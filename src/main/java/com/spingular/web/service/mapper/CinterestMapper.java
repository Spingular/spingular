package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CinterestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cinterest} and its DTO {@link CinterestDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class})
public interface CinterestMapper extends EntityMapper<CinterestDTO, Cinterest> {


    @Mapping(target = "removeCommunity", ignore = true)

    default Cinterest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cinterest cinterest = new Cinterest();
        cinterest.setId(id);
        return cinterest;
    }
}
