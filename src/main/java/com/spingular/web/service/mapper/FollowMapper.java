package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Follow} and its DTO {@link FollowDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, CommunityMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {

    @Mapping(source = "followed.id", target = "followedId")
    @Mapping(source = "following.id", target = "followingId")
    @Mapping(source = "cfollowed.id", target = "cfollowedId")
    @Mapping(source = "cfollowing.id", target = "cfollowingId")
    FollowDTO toDto(Follow follow);

    @Mapping(source = "followedId", target = "followed")
    @Mapping(source = "followingId", target = "following")
    @Mapping(source = "cfollowedId", target = "cfollowed")
    @Mapping(source = "cfollowingId", target = "cfollowing")
    Follow toEntity(FollowDTO followDTO);

    default Follow fromId(Long id) {
        if (id == null) {
            return null;
        }
        Follow follow = new Follow();
        follow.setId(id);
        return follow;
    }
}
