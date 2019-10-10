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

import com.spingular.web.domain.Proposal;
import com.spingular.web.domain.*; // for static metamodels
import com.spingular.web.repository.ProposalRepository;
import com.spingular.web.service.dto.ProposalCriteria;
import com.spingular.web.service.dto.ProposalDTO;
import com.spingular.web.service.mapper.ProposalMapper;

/**
 * Service for executing complex queries for {@link Proposal} entities in the database.
 * The main input is a {@link ProposalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProposalDTO} or a {@link Page} of {@link ProposalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProposalQueryService extends QueryService<Proposal> {

    private final Logger log = LoggerFactory.getLogger(ProposalQueryService.class);

    private final ProposalRepository proposalRepository;

    private final ProposalMapper proposalMapper;

    public ProposalQueryService(ProposalRepository proposalRepository, ProposalMapper proposalMapper) {
        this.proposalRepository = proposalRepository;
        this.proposalMapper = proposalMapper;
    }

    /**
     * Return a {@link List} of {@link ProposalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProposalDTO> findByCriteria(ProposalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Proposal> specification = createSpecification(criteria);
        return proposalMapper.toDto(proposalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProposalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProposalDTO> findByCriteria(ProposalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Proposal> specification = createSpecification(criteria);
        return proposalRepository.findAll(specification, page)
            .map(proposalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProposalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Proposal> specification = createSpecification(criteria);
        return proposalRepository.count(specification);
    }

    /**
     * Function to convert {@link ProposalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Proposal> createSpecification(ProposalCriteria criteria) {
        Specification<Proposal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Proposal_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Proposal_.creationDate));
            }
            if (criteria.getProposalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProposalName(), Proposal_.proposalName));
            }
            if (criteria.getProposalType() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalType(), Proposal_.proposalType));
            }
            if (criteria.getProposalRole() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalRole(), Proposal_.proposalRole));
            }
            if (criteria.getReleaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReleaseDate(), Proposal_.releaseDate));
            }
            if (criteria.getIsOpen() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOpen(), Proposal_.isOpen));
            }
            if (criteria.getIsAccepted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAccepted(), Proposal_.isAccepted));
            }
            if (criteria.getIsPaid() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPaid(), Proposal_.isPaid));
            }
            if (criteria.getProposalVoteId() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalVoteId(),
                    root -> root.join(Proposal_.proposalVotes, JoinType.LEFT).get(ProposalVote_.id)));
            }
            if (criteria.getAppuserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppuserId(),
                    root -> root.join(Proposal_.appuser, JoinType.LEFT).get(Appuser_.id)));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Proposal_.post, JoinType.LEFT).get(Post_.id)));
            }
        }
        return specification;
    }
}
