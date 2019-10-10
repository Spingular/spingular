package com.spingular.web.service.impl;

import com.spingular.web.service.AppprofileService;
import com.spingular.web.domain.Appprofile;
import com.spingular.web.repository.AppprofileRepository;
import com.spingular.web.service.dto.AppprofileDTO;
import com.spingular.web.service.mapper.AppprofileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Appprofile}.
 */
@Service
@Transactional
public class AppprofileServiceImpl implements AppprofileService {

    private final Logger log = LoggerFactory.getLogger(AppprofileServiceImpl.class);

    private final AppprofileRepository appprofileRepository;

    private final AppprofileMapper appprofileMapper;

    public AppprofileServiceImpl(AppprofileRepository appprofileRepository, AppprofileMapper appprofileMapper) {
        this.appprofileRepository = appprofileRepository;
        this.appprofileMapper = appprofileMapper;
    }

    /**
     * Save a appprofile.
     *
     * @param appprofileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AppprofileDTO save(AppprofileDTO appprofileDTO) {
        log.debug("Request to save Appprofile : {}", appprofileDTO);
        Appprofile appprofile = appprofileMapper.toEntity(appprofileDTO);
        appprofile = appprofileRepository.save(appprofile);
        return appprofileMapper.toDto(appprofile);
    }

    /**
     * Get all the appprofiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppprofileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Appprofiles");
        return appprofileRepository.findAll(pageable)
            .map(appprofileMapper::toDto);
    }


    /**
     * Get one appprofile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppprofileDTO> findOne(Long id) {
        log.debug("Request to get Appprofile : {}", id);
        return appprofileRepository.findById(id)
            .map(appprofileMapper::toDto);
    }

    /**
     * Delete the appprofile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appprofile : {}", id);
        appprofileRepository.deleteById(id);
    }
}
