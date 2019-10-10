package com.spingular.web.service;

import com.spingular.web.service.dto.AppphotoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.spingular.web.domain.Appphoto}.
 */
public interface AppphotoService {

    /**
     * Save a appphoto.
     *
     * @param appphotoDTO the entity to save.
     * @return the persisted entity.
     */
    AppphotoDTO save(AppphotoDTO appphotoDTO);

    /**
     * Get all the appphotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppphotoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" appphoto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppphotoDTO> findOne(Long id);

    /**
     * Delete the "id" appphoto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
