package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.BlockuserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blockuser} and its DTO {@link BlockuserDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, CommunityMapper.class, UserMapper.class})
public interface BlockuserMapper extends EntityMapper<BlockuserDTO, Blockuser> {

    @Mapping(source = "cblockeduser.id", target = "cblockeduserId")		
    @Mapping(source = "cblockeduser.image", target = "cblockeduserImage")		
    @Mapping(source = "cblockeduser.imageContentType", target = "cblockeduserImageContentType")		
    @Mapping(source = "cblockeduser.communityName", target = "cblockeduserCommunityname")		
    @Mapping(source = "cblockinguser.id", target = "cblockinguserId")		
    @Mapping(source = "cblockinguser.image", target = "cblockinguserImage")		
    @Mapping(source = "cblockinguser.imageContentType", target = "cblockinguserImageContentType")		
    @Mapping(source = "cblockinguser.communityName", target = "cblockinguserCommunityname")
    @Mapping(source = "blockinguser.id", target = "blockinguserId")
    @Mapping(source = "blockinguser.user.firstName", target = "blockinguserFirstName")
    @Mapping(source = "blockinguser.user.lastName", target = "blockinguserLastName")
    @Mapping(source = "blockeduser.id", target = "blockeduserId")		
    @Mapping(source = "blockeduser.user.firstName", target = "blockeduserFirstName")		
    @Mapping(source = "blockeduser.user.lastName", target = "blockeduserLastName")
    BlockuserDTO toDto(Blockuser blockuser);

    @Mapping(source = "blockeduserId", target = "blockeduser")
    @Mapping(source = "blockinguserId", target = "blockinguser")
    @Mapping(source = "cblockeduserId", target = "cblockeduser")
    @Mapping(source = "cblockinguserId", target = "cblockinguser")
    Blockuser toEntity(BlockuserDTO blockuserDTO);

    default Blockuser fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blockuser blockuser = new Blockuser();
        blockuser.setId(id);
        return blockuser;
    }
}
