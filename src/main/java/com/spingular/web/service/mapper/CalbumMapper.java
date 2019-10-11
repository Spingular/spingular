package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CalbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Calbum} and its DTO {@link CalbumDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, AppuserMapper.class, UserMapper.class})
public interface CalbumMapper extends EntityMapper<CalbumDTO, Calbum> {

    @Mapping(source = "community.id", target = "communityId")
    @Mapping(source = "community.communityName", target = "communityCommunityName")
    @Mapping(source = "community.appuser.user.id", target = "userId")
    CalbumDTO toDto(Calbum calbum);

    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "removePhoto", ignore = true)
    @Mapping(source = "communityId", target = "community")
    Calbum toEntity(CalbumDTO calbumDTO);

    default Calbum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Calbum calbum = new Calbum();
        calbum.setId(id);
        return calbum;
    }
}
