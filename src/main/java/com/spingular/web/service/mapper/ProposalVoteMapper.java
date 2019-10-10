package com.spingular.web.service.mapper;

import com.spingular.web.domain.*;
import com.spingular.web.service.dto.ProposalVoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProposalVote} and its DTO {@link ProposalVoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppuserMapper.class, ProposalMapper.class})
public interface ProposalVoteMapper extends EntityMapper<ProposalVoteDTO, ProposalVote> {

    @Mapping(source = "appuser.id", target = "appuserId")
    @Mapping(source = "proposal.id", target = "proposalId")
    ProposalVoteDTO toDto(ProposalVote proposalVote);

    @Mapping(source = "appuserId", target = "appuser")
    @Mapping(source = "proposalId", target = "proposal")
    ProposalVote toEntity(ProposalVoteDTO proposalVoteDTO);

    default ProposalVote fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProposalVote proposalVote = new ProposalVote();
        proposalVote.setId(id);
        return proposalVote;
    }
}
