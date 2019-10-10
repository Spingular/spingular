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

import com.spingular.web.domain.Appphoto;
import com.spingular.web.domain.*; // for static metamodels
import com.spingular.web.repository.AppphotoRepository;
import com.spingular.web.service.dto.AppphotoCriteria;
import com.spingular.web.service.dto.AppphotoDTO;
import com.spingular.web.service.mapper.AppphotoMapper;

/**
 * Service for executing complex queries for {@link Appphoto} entities in the database.
 * The main input is a {@link AppphotoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppphotoDTO} or a {@link Page} of {@link AppphotoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppphotoQueryService extends QueryService<Appphoto> {

    private final Logger log = LoggerFactory.getLogger(AppphotoQueryService.class);

    private final AppphotoRepository appphotoRepository;

    private final AppphotoMapper appphotoMapper;

    public AppphotoQueryService(AppphotoRepository appphotoRepository, AppphotoMapper appphotoMapper) {
        this.appphotoRepository = appphotoRepository;
        this.appphotoMapper = appphotoMapper;
    }

    /**
     * Return a {@link List} of {@link AppphotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppphotoDTO> findByCriteria(AppphotoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Appphoto> specification = createSpecification(criteria);
        return appphotoMapper.toDto(appphotoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppphotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppphotoDTO> findByCriteria(AppphotoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Appphoto> specification = createSpecification(criteria);
        return appphotoRepository.findAll(specification, page)
            .map(appphotoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppphotoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Appphoto> specification = createSpecification(criteria);
        return appphotoRepository.count(specification);
    }

    /**
     * Function to convert {@link AppphotoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Appphoto> createSpecification(AppphotoCriteria criteria) {
        Specification<Appphoto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Appphoto_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Appphoto_.creationDate));
            }
            if (criteria.getAppuserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppuserId(),
                    root -> root.join(Appphoto_.appuser, JoinType.LEFT).get(Appuser_.id)));
            }
        }
        return specification;
    }
}
