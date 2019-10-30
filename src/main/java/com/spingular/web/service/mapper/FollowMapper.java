package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Follow} and its DTO {@link FollowDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, CommunityMapper.class, UserMapper.class, AppphotoMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {

    @Mapping(source = "followed.id", target = "followedId")
    @Mapping(source = "followed.user.firstName", target = "followedUserFirstName")
    @Mapping(source = "followed.user.lastName", target = "followedUserLastName")
    @Mapping(source = "followed.appphoto.image", target = "followedImage")
    @Mapping(source = "followed.appphoto.imageContentType", target = "followedImageContentType")
    @Mapping(source = "following.id", target = "followingId")
	@Mapping(source = "following.user.firstName", target = "followingUserFirstName")
    @Mapping(source = "following.user.lastName", target = "followingUserLastName")
    @Mapping(source = "following.appphoto.image", target = "followingImage")
    @Mapping(source = "following.appphoto.imageContentType", target = "followingImageContentType")
    @Mapping(source = "cfollowed.id", target = "cfollowedId")
    @Mapping(source = "cfollowed.image", target = "cfollowedImage")
	@Mapping(source = "cfollowed.imageContentType", target = "cfollowedImageContentType")
	@Mapping(source = "cfollowed.communityName", target = "cfollowedCommunityname")
    @Mapping(source = "cfollowing.id", target = "cfollowingId")
    @Mapping(source = "cfollowing.image", target = "cfollowingImage")
    @Mapping(source = "cfollowing.imageContentType", target = "cfollowingImageContentType")
    @Mapping(source = "cfollowing.communityName", target = "cfollowingCommunityname")
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
