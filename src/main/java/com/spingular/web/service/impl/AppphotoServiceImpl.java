package com.spingular.web.service.impl;

import com.spingular.web.service.AppphotoService;
import com.spingular.web.domain.Appphoto;
import com.spingular.web.repository.AppphotoRepository;
import com.spingular.web.service.dto.AppphotoDTO;
import com.spingular.web.service.mapper.AppphotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Appphoto}.
 */
@Service
@Transactional
public class AppphotoServiceImpl implements AppphotoService {

    private final Logger log = LoggerFactory.getLogger(AppphotoServiceImpl.class);

    private final AppphotoRepository appphotoRepository;

    private final AppphotoMapper appphotoMapper;

    public AppphotoServiceImpl(AppphotoRepository appphotoRepository, AppphotoMapper appphotoMapper) {
        this.appphotoRepository = appphotoRepository;
        this.appphotoMapper = appphotoMapper;
    }

    /**
     * Save a appphoto.
     *
     * @param appphotoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AppphotoDTO save(AppphotoDTO appphotoDTO) {
        log.debug("Request to save Appphoto : {}", appphotoDTO);
        Appphoto appphoto = appphotoMapper.toEntity(appphotoDTO);
        appphoto = appphotoRepository.save(appphoto);
        return appphotoMapper.toDto(appphoto);
    }

    /**
     * Get all the appphotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppphotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Appphotos");
        return appphotoRepository.findAll(pageable)
            .map(appphotoMapper::toDto);
    }


    /**
     * Get one appphoto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppphotoDTO> findOne(Long id) {
        log.debug("Request to get Appphoto : {}", id);
        return appphotoRepository.findById(id)
            .map(appphotoMapper::toDto);
    }

    /**
     * Delete the appphoto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appphoto : {}", id);
        appphotoRepository.deleteById(id);
    }
}
