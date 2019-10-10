package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.AppuserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appuser} and its DTO {@link AppuserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AppuserMapper extends EntityMapper<AppuserDTO, Appuser> {

    @Mapping(source = "user.id", target = "userId")
    AppuserDTO toDto(Appuser appuser);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "appprofile", ignore = true)
    @Mapping(target = "appphoto", ignore = true)
    @Mapping(target = "communities", ignore = true)
    @Mapping(target = "removeCommunity", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    @Mapping(target = "removeBlog", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "removeNotification", ignore = true)
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "removeAlbum", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "removePost", ignore = true)
    @Mapping(target = "senders", ignore = true)
    @Mapping(target = "removeSender", ignore = true)
    @Mapping(target = "receivers", ignore = true)
    @Mapping(target = "removeReceiver", ignore = true)
    @Mapping(target = "followeds", ignore = true)
    @Mapping(target = "removeFollowed", ignore = true)
    @Mapping(target = "followings", ignore = true)
    @Mapping(target = "removeFollowing", ignore = true)
    @Mapping(target = "blockedusers", ignore = true)
    @Mapping(target = "removeBlockeduser", ignore = true)
    @Mapping(target = "blockingusers", ignore = true)
    @Mapping(target = "removeBlockinguser", ignore = true)
    @Mapping(target = "vtopics", ignore = true)
    @Mapping(target = "removeVtopic", ignore = true)
    @Mapping(target = "vquestions", ignore = true)
    @Mapping(target = "removeVquestion", ignore = true)
    @Mapping(target = "vanswers", ignore = true)
    @Mapping(target = "removeVanswer", ignore = true)
    @Mapping(target = "vthumbs", ignore = true)
    @Mapping(target = "removeVthumb", ignore = true)
    @Mapping(target = "proposals", ignore = true)
    @Mapping(target = "removeProposal", ignore = true)
    @Mapping(target = "proposalVotes", ignore = true)
    @Mapping(target = "removeProposalVote", ignore = true)
    @Mapping(target = "interests", ignore = true)
    @Mapping(target = "removeInterest", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "removeActivity", ignore = true)
    @Mapping(target = "celebs", ignore = true)
    @Mapping(target = "removeCeleb", ignore = true)
    Appuser toEntity(AppuserDTO appuserDTO);

    default Appuser fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appuser appuser = new Appuser();
        appuser.setId(id);
        return appuser;
    }
}
