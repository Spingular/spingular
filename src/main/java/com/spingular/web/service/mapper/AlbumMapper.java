package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.AlbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Album} and its DTO {@link AlbumDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class})
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {

    @Mapping(source = "appuser.id", target = "appuserId")
    AlbumDTO toDto(Album album);

    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "removePhoto", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    Album toEntity(AlbumDTO albumDTO);

    default Album fromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
