package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.UrllinkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Urllink} and its DTO {@link UrllinkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UrllinkMapper extends EntityMapper<UrllinkDTO, Urllink> {



    default Urllink fromId(Long id) {
        if (id == null) {
            return null;
        }
        Urllink urllink = new Urllink();
        urllink.setId(id);
        return urllink;
    }
}
