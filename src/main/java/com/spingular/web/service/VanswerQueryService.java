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

import com.spingular.web.domain.Vanswer;
import com.spingular.web.domain.*; // for static metamodels
import com.spingular.web.repository.VanswerRepository;
import com.spingular.web.service.dto.VanswerCriteria;
import com.spingular.web.service.dto.VanswerDTO;
import com.spingular.web.service.mapper.VanswerMapper;

/**
 * Service for executing complex queries for {@link Vanswer} entities in the database.
 * The main input is a {@link VanswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VanswerDTO} or a {@link Page} of {@link VanswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VanswerQueryService extends QueryService<Vanswer> {

    private final Logger log = LoggerFactory.getLogger(VanswerQueryService.class);

    private final VanswerRepository vanswerRepository;

    private final VanswerMapper vanswerMapper;

    public VanswerQueryService(VanswerRepository vanswerRepository, VanswerMapper vanswerMapper) {
        this.vanswerRepository = vanswerRepository;
        this.vanswerMapper = vanswerMapper;
    }

    /**
     * Return a {@link List} of {@link VanswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VanswerDTO> findByCriteria(VanswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vanswer> specification = createSpecification(criteria);
        return vanswerMapper.toDto(vanswerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VanswerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VanswerDTO> findByCriteria(VanswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vanswer> specification = createSpecification(criteria);
        return vanswerRepository.findAll(specification, page)
            .map(vanswerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VanswerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vanswer> specification = createSpecification(criteria);
        return vanswerRepository.count(specification);
    }

    /**
     * Function to convert {@link VanswerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vanswer> createSpecification(VanswerCriteria criteria) {
        Specification<Vanswer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Vanswer_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Vanswer_.creationDate));
            }
            if (criteria.getUrlVanswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlVanswer(), Vanswer_.urlVanswer));
            }
            if (criteria.getAccepted() != null) {
                specification = specification.and(buildSpecification(criteria.getAccepted(), Vanswer_.accepted));
            }
            if (criteria.getVthumbId() != null) {
                specification = specification.and(buildSpecification(criteria.getVthumbId(),
                    root -> root.join(Vanswer_.vthumbs, JoinType.LEFT).get(Vthumb_.id)));
            }
            if (criteria.getAppuserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppuserId(),
                    root -> root.join(Vanswer_.appuser, JoinType.LEFT).get(Appuser_.id)));
            }
            if (criteria.getVquestionId() != null) {
                specification = specification.and(buildSpecification(criteria.getVquestionId(),
                    root -> root.join(Vanswer_.vquestion, JoinType.LEFT).get(Vquestion_.id)));
            }
        }
        return specification;
    }
}
