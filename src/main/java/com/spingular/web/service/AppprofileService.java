package com.spingular.web.service;

import com.spingular.web.service.dto.AppprofileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.spingular.web.domain.Appprofile}.
 */
public interface AppprofileService {

    /**
     * Save a appprofile.
     *
     * @param appprofileDTO the entity to save.
     * @return the persisted entity.
     */
    AppprofileDTO save(AppprofileDTO appprofileDTO);

    /**
     * Get all the appprofiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppprofileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" appprofile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppprofileDTO> findOne(Long id);

    /**
     * Delete the "id" appprofile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
