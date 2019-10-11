package com.spingular.web.service.mapper;

import org.mapstruct.Mapper;

import com.spingular.web.domain.Interest;
import com.spingular.web.service.dto.CustomInterestDTO;



/**
 * Mapper for the entity Interest and its DTO InterestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomInterestMapper extends EntityMapper<CustomInterestDTO, Interest> {



    default Interest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interest interest = new Interest();
        interest.setId(id);
        return interest;
    }
}
