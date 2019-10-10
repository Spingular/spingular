package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.ProposalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proposal} and its DTO {@link ProposalDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, PostMapper.class})
public interface ProposalMapper extends EntityMapper<ProposalDTO, Proposal> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "post.id", target = "postId")
    ProposalDTO toDto(Proposal proposal);

    @Mapping(target = "proposalVotes", ignore = true)
    @Mapping(target = "removeProposalVote", ignore = true)
    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "postId", target = "post")
    Proposal toEntity(ProposalDTO proposalDTO);

    default Proposal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proposal proposal = new Proposal();
        proposal.setId(id);
        return proposal;
    }
}
