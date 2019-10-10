package com.spingular.web.service;

import com.spingular.web.service.dto.ProposalVoteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.spingular.web.domain.ProposalVote}.
 */
public interface ProposalVoteService {

    /**
     * Save a proposalVote.
     *
     * @param proposalVoteDTO the entity to save.
     * @return the persisted entity.
     */
    ProposalVoteDTO save(ProposalVoteDTO proposalVoteDTO);

    /**
     * Get all the proposalVotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProposalVoteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" proposalVote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProposalVoteDTO> findOne(Long id);

    /**
     * Delete the "id" proposalVote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
