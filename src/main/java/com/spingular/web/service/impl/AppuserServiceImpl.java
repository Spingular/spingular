package com.spingular.web.service.impl;

import com.spingular.web.service.AppuserService;
import com.spingular.web.domain.Appuser;
import com.spingular.web.repository.AppuserRepository;
import com.spingular.web.service.dto.AppuserDTO;
import com.spingular.web.service.mapper.AppuserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Appuser}.
 */
@Service
@Transactional
public class AppuserServiceImpl implements AppuserService {

    private final Logger log = LoggerFactory.getLogger(AppuserServiceImpl.class);

    private final AppuserRepository appuserRepository;

    private final AppuserMapper appuserMapper;

    public AppuserServiceImpl(AppuserRepository appuserRepository, AppuserMapper appuserMapper) {
        this.appuserRepository = appuserRepository;
        this.appuserMapper = appuserMapper;
    }

    /**
     * Save a appuser.
     *
     * @param appuserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AppuserDTO save(AppuserDTO appuserDTO) {
        log.debug("Request to save Appuser : {}", appuserDTO);
        Appuser appuser = appuserMapper.toEntity(appuserDTO);
        appuser = appuserRepository.save(appuser);
        return appuserMapper.toDto(appuser);
    }

    /**
     * Get all the appusers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppuserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Appusers");
        return appuserRepository.findAll(pageable)
            .map(appuserMapper::toDto);
    }



    /**
    *  Get all the appusers where Appprofile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<AppuserDTO> findAllWhereAppprofileIsNull() {
        log.debug("Request to get all appusers where Appprofile is null");
        return StreamSupport
            .stream(appuserRepository.findAll().spliterator(), false)
            .filter(appuser -> appuser.getAppprofile() == null)
            .map(appuserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the appusers where Appphoto is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<AppuserDTO> findAllWhereAppphotoIsNull() {
        log.debug("Request to get all appusers where Appphoto is null");
        return StreamSupport
            .stream(appuserRepository.findAll().spliterator(), false)
            .filter(appuser -> appuser.getAppphoto() == null)
            .map(appuserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one appuser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppuserDTO> findOne(Long id) {
        log.debug("Request to get Appuser : {}", id);
        return appuserRepository.findById(id)
            .map(appuserMapper::toDto);
    }

    /**
     * Delete the appuser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appuser : {}", id);
        appuserRepository.deleteById(id);
    }
}
