package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.InterestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interest} and its DTO {@link InterestDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class})
public interface InterestMapper extends EntityMapper<InterestDTO, Interest> {


    @Mapping(target = "removeAppuser", ignore = true)

    default Interest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interest interest = new Interest();
        interest.setId(id);
        return interest;
    }
}
