package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.BlockuserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blockuser} and its DTO {@link BlockuserDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, CommunityMapper.class})
public interface BlockuserMapper extends EntityMapper<BlockuserDTO, Blockuser> {

    @Mapping(source = "blockeduser.id", target = "blockeduserId")
    @Mapping(source = "blockinguser.id", target = "blockinguserId")
    @Mapping(source = "cblockeduser.id", target = "cblockeduserId")
    @Mapping(source = "cblockinguser.id", target = "cblockinguserId")
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
