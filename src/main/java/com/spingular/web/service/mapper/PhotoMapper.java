package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlbumMapper.class, CalbumMapper.class})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.title", target = "albumTitle")
    @Mapping(source = "calbum.id", target = "calbumId")
    @Mapping(source = "calbum.title", target = "calbumTitle")
    PhotoDTO toDto(Photo photo);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "calbumId", target = "calbum")
    Photo toEntity(PhotoDTO photoDTO);

    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
