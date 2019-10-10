package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.CommunityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Community} and its DTO {@link CommunityDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class})
public interface CommunityMapper extends EntityMapper<CommunityDTO, Community> {

    @Mapping(source = "appuser.id", target = "appuserId")
    CommunityDTO toDto(Community community);

    @Mapping(target = "blogs", ignore = true)
    @Mapping(target = "removeBlog", ignore = true)
    @Mapping(target = "csenders", ignore = true)
    @Mapping(target = "removeCsender", ignore = true)
    @Mapping(target = "creceivers", ignore = true)
    @Mapping(target = "removeCreceiver", ignore = true)
    @Mapping(target = "cfolloweds", ignore = true)
    @Mapping(target = "removeCfollowed", ignore = true)
    @Mapping(target = "cfollowings", ignore = true)
    @Mapping(target = "removeCfollowing", ignore = true)
    @Mapping(target = "cblockedusers", ignore = true)
    @Mapping(target = "removeCblockeduser", ignore = true)
    @Mapping(target = "cblockingusers", ignore = true)
    @Mapping(target = "removeCblockinguser", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(target = "calbums", ignore = true)
    @Mapping(target = "removeCalbum", ignore = true)
    @Mapping(target = "cinterests", ignore = true)
    @Mapping(target = "removeCinterest", ignore = true)
    @Mapping(target = "cactivities", ignore = true)
    @Mapping(target = "removeCactivity", ignore = true)
    @Mapping(target = "ccelebs", ignore = true)
    @Mapping(target = "removeCceleb", ignore = true)
    Community toEntity(CommunityDTO communityDTO);

    default Community fromId(Long id) {
        if (id == null) {
            return null;
        }
        Community community = new Community();
        community.setId(id);
        return community;
    }
}
