package com.spingular.web.service.impl;

import com.spingular.web.service.ProposalVoteService;
import com.spingular.web.domain.ProposalVote;
import com.spingular.web.repository.ProposalVoteRepository;
import com.spingular.web.service.dto.ProposalVoteDTO;
import com.spingular.web.service.mapper.ProposalVoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProposalVote}.
 */
@Service
@Transactional
public class ProposalVoteServiceImpl implements ProposalVoteService {

    private final Logger log = LoggerFactory.getLogger(ProposalVoteServiceImpl.class);

    private final ProposalVoteRepository proposalVoteRepository;

    private final ProposalVoteMapper proposalVoteMapper;

    public ProposalVoteServiceImpl(ProposalVoteRepository proposalVoteRepository, ProposalVoteMapper proposalVoteMapper) {
        this.proposalVoteRepository = proposalVoteRepository;
        this.proposalVoteMapper = proposalVoteMapper;
    }

    /**
     * Save a proposalVote.
     *
     * @param proposalVoteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProposalVoteDTO save(ProposalVoteDTO proposalVoteDTO) {
        log.debug("Request to save ProposalVote : {}", proposalVoteDTO);
        ProposalVote proposalVote = proposalVoteMapper.toEntity(proposalVoteDTO);
        proposalVote = proposalVoteRepository.save(proposalVote);
        return proposalVoteMapper.toDto(proposalVote);
    }

    /**
     * Get all the proposalVotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProposalVoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProposalVotes");
        return proposalVoteRepository.findAll(pageable)
            .map(proposalVoteMapper::toDto);
    }


    /**
     * Get one proposalVote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProposalVoteDTO> findOne(Long id) {
        log.debug("Request to get ProposalVote : {}", id);
        return proposalVoteRepository.findById(id)
            .map(proposalVoteMapper::toDto);
    }

    /**
     * Delete the proposalVote by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProposalVote : {}", id);
        proposalVoteRepository.deleteById(id);
    }
}
