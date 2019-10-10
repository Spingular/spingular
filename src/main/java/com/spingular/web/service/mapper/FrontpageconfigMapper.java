package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.FrontpageconfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Frontpageconfig} and its DTO {@link FrontpageconfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FrontpageconfigMapper extends EntityMapper<FrontpageconfigDTO, Frontpageconfig> {



    default Frontpageconfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        Frontpageconfig frontpageconfig = new Frontpageconfig();
        frontpageconfig.setId(id);
        return frontpageconfig;
    }
}
