package com.spingular.web.service;

import com.spingular.web.service.dto.VthumbDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.spingular.web.domain.Vthumb}.
 */
public interface VthumbService {

    /**
     * Save a vthumb.
     *
     * @param vthumbDTO the entity to save.
     * @return the persisted entity.
     */
    VthumbDTO save(VthumbDTO vthumbDTO);

    /**
     * Get all the vthumbs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VthumbDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vthumb.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VthumbDTO> findOne(Long id);

    /**
     * Delete the "id" vthumb.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
