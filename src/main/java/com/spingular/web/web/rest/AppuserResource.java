package com.spingular.web.web.rest;

import com.spingular.web.service.AppuserService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.AppuserDTO;
import com.spingular.web.service.dto.AppuserCriteria;
import com.spingular.web.service.AppuserQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.spingular.web.domain.Appuser}.
 */
@RestController
@RequestMapping("/api")
public class AppuserResource {

    private final Logger log = LoggerFactory.getLogger(AppuserResource.class);

    private static final String ENTITY_NAME = "appuser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppuserService appuserService;

    private final AppuserQueryService appuserQueryService;

    public AppuserResource(AppuserService appuserService, AppuserQueryService appuserQueryService) {
        this.appuserService = appuserService;
        this.appuserQueryService = appuserQueryService;
    }

    /**
     * {@code POST  /appusers} : Create a new appuser.
     *
     * @param appuserDTO the appuserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appuserDTO, or with status {@code 400 (Bad Request)} if the appuser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appusers")
    public ResponseEntity<AppuserDTO> createAppuser(@Valid @RequestBody AppuserDTO appuserDTO) throws URISyntaxException {
        log.debug("REST request to save Appuser : {}", appuserDTO);
        if (appuserDTO.getId() != null) {
            throw new BadRequestAlertException("A new appuser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppuserDTO result = appuserService.save(appuserDTO);
        return ResponseEntity.created(new URI("/api/appusers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appusers} : Updates an existing appuser.
     *
     * @param appuserDTO the appuserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appuserDTO,
     * or with status {@code 400 (Bad Request)} if the appuserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appuserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appusers")
    public ResponseEntity<AppuserDTO> updateAppuser(@Valid @RequestBody AppuserDTO appuserDTO) throws URISyntaxException {
        log.debug("REST request to update Appuser : {}", appuserDTO);
        if (appuserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppuserDTO result = appuserService.save(appuserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appuserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appusers} : get all the appusers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appusers in body.
     */
    @GetMapping("/appusers")
    public ResponseEntity<List<AppuserDTO>> getAllAppusers(AppuserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Appusers by criteria: {}", criteria);
        Page<AppuserDTO> page = appuserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /appusers/count} : count all the appusers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/appusers/count")
    public ResponseEntity<Long> countAppusers(AppuserCriteria criteria) {
        log.debug("REST request to count Appusers by criteria: {}", criteria);
        return ResponseEntity.ok().body(appuserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appusers/:id} : get the "id" appuser.
     *
     * @param id the id of the appuserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appuserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appusers/{id}")
    public ResponseEntity<AppuserDTO> getAppuser(@PathVariable Long id) {
        log.debug("REST request to get Appuser : {}", id);
        Optional<AppuserDTO> appuserDTO = appuserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appuserDTO);
    }

    /**
     * {@code DELETE  /appusers/:id} : delete the "id" appuser.
     *
     * @param id the id of the appuserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appusers/{id}")
    public ResponseEntity<Void> deleteAppuser(@PathVariable Long id) {
        log.debug("REST request to delete Appuser : {}", id);
        appuserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
