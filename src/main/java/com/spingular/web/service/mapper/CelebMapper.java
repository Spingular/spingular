package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CelebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Celeb} and its DTO {@link CelebDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class})
public interface CelebMapper extends EntityMapper<CelebDTO, Celeb> {


    @Mapping(target = "removeAppuser", ignore = true)

    default Celeb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Celeb celeb = new Celeb();
        celeb.setId(id);
        return celeb;
    }
}
