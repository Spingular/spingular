package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.AppphotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appphoto} and its DTO {@link AppphotoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class})
public interface AppphotoMapper extends EntityMapper<AppphotoDTO, Appphoto> {

    @Mapping(source = "appuser.id", target = "appuserId")
    AppphotoDTO toDto(Appphoto appphoto);

    @Mapping(source = "appuserId", target = "appuser")
    Appphoto toEntity(AppphotoDTO appphotoDTO);

    default Appphoto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appphoto appphoto = new Appphoto();
        appphoto.setId(id);
        return appphoto;
    }
}
