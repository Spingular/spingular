package com.spingular.web.service.impl;

import com.spingular.web.service.ProposalService;
import com.spingular.web.domain.Proposal;
import com.spingular.web.repository.ProposalRepository;
import com.spingular.web.service.dto.ProposalDTO;
import com.spingular.web.service.mapper.ProposalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Proposal}.
 */
@Service
@Transactional
public class ProposalServiceImpl implements ProposalService {

    private final Logger log = LoggerFactory.getLogger(ProposalServiceImpl.class);

    private final ProposalRepository proposalRepository;

    private final ProposalMapper proposalMapper;

    public ProposalServiceImpl(ProposalRepository proposalRepository, ProposalMapper proposalMapper) {
        this.proposalRepository = proposalRepository;
        this.proposalMapper = proposalMapper;
    }

    /**
     * Save a proposal.
     *
     * @param proposalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProposalDTO save(ProposalDTO proposalDTO) {
        log.debug("Request to save Proposal : {}", proposalDTO);
        Proposal proposal = proposalMapper.toEntity(proposalDTO);
        proposal = proposalRepository.save(proposal);
        return proposalMapper.toDto(proposal);
    }

    /**
     * Get all the proposals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proposals");
        return proposalRepository.findAll(pageable)
            .map(proposalMapper::toDto);
    }


    /**
     * Get one proposal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProposalDTO> findOne(Long id) {
        log.debug("Request to get Proposal : {}", id);
        return proposalRepository.findById(id)
            .map(proposalMapper::toDto);
    }

    /**
     * Delete the proposal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proposal : {}", id);
        proposalRepository.deleteById(id);
    }
}
