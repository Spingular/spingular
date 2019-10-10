package com.spingular.web.service;

import com.spingular.web.service.dto.AppuserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.spingular.web.domain.Appuser}.
 */
public interface AppuserService {

    /**
     * Save a appuser.
     *
     * @param appuserDTO the entity to save.
     * @return the persisted entity.
     */
    AppuserDTO save(AppuserDTO appuserDTO);

    /**
     * Get all the appusers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppuserDTO> findAll(Pageable pageable);
    /**
     * Get all the AppuserDTO where Appprofile is {@code null}.
     *
     * @return the list of entities.
     */
    List<AppuserDTO> findAllWhereAppprofileIsNull();
    /**
     * Get all the AppuserDTO where Appphoto is {@code null}.
     *
     * @return the list of entities.
     */
    List<AppuserDTO> findAllWhereAppphotoIsNull();


    /**
     * Get the "id" appuser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppuserDTO> findOne(Long id);

    /**
     * Delete the "id" appuser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
