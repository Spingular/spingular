package com.spingular.web.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.spingular.web.domain.ProposalVote;
import com.spingular.web.domain.*; // for static metamodels
import com.spingular.web.repository.ProposalVoteRepository;
import com.spingular.web.service.dto.ProposalVoteCriteria;
import com.spingular.web.service.dto.ProposalVoteDTO;
import com.spingular.web.service.mapper.ProposalVoteMapper;

/**
 * Service for executing complex queries for {@link ProposalVote} entities in the database.
 * The main input is a {@link ProposalVoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProposalVoteDTO} or a {@link Page} of {@link ProposalVoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProposalVoteQueryService extends QueryService<ProposalVote> {

    private final Logger log = LoggerFactory.getLogger(ProposalVoteQueryService.class);

    private final ProposalVoteRepository proposalVoteRepository;

    private final ProposalVoteMapper proposalVoteMapper;

    public ProposalVoteQueryService(ProposalVoteRepository proposalVoteRepository, ProposalVoteMapper proposalVoteMapper) {
        this.proposalVoteRepository = proposalVoteRepository;
        this.proposalVoteMapper = proposalVoteMapper;
    }

    /**
     * Return a {@link List} of {@link ProposalVoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProposalVoteDTO> findByCriteria(ProposalVoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProposalVote> specification = createSpecification(criteria);
        return proposalVoteMapper.toDto(proposalVoteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProposalVoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProposalVoteDTO> findByCriteria(ProposalVoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProposalVote> specification = createSpecification(criteria);
        return proposalVoteRepository.findAll(specification, page)
            .map(proposalVoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProposalVoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProposalVote> specification = createSpecification(criteria);
        return proposalVoteRepository.count(specification);
    }

    /**
     * Function to convert {@link ProposalVoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProposalVote> createSpecification(ProposalVoteCriteria criteria) {
        Specification<ProposalVote> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProposalVote_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), ProposalVote_.creationDate));
            }
            if (criteria.getVotePoints() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVotePoints(), ProposalVote_.votePoints));
            }
            if (criteria.getAppuserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppuserId(),
                    root -> root.join(ProposalVote_.appuser, JoinType.LEFT).get(Appuser_.id)));
            }
            if (criteria.getProposalId() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalId(),
                    root -> root.join(ProposalVote_.proposal, JoinType.LEFT).get(Proposal_.id)));
            }
        }
        return specification;
    }
}
