package com.spingular.web.web.rest;

import com.spingular.web.service.AppprofileService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.AppprofileDTO;
import com.spingular.web.service.dto.AppprofileCriteria;
import com.spingular.web.service.AppprofileQueryService;

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

/**
 * REST controller for managing {@link com.spingular.web.domain.Appprofile}.
 */
@RestController
@RequestMapping("/api")
public class AppprofileResource {

    private final Logger log = LoggerFactory.getLogger(AppprofileResource.class);

    private static final String ENTITY_NAME = "appprofile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppprofileService appprofileService;

    private final AppprofileQueryService appprofileQueryService;

    public AppprofileResource(AppprofileService appprofileService, AppprofileQueryService appprofileQueryService) {
        this.appprofileService = appprofileService;
        this.appprofileQueryService = appprofileQueryService;
    }

    /**
     * {@code POST  /appprofiles} : Create a new appprofile.
     *
     * @param appprofileDTO the appprofileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appprofileDTO, or with status {@code 400 (Bad Request)} if the appprofile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appprofiles")
    public ResponseEntity<AppprofileDTO> createAppprofile(@Valid @RequestBody AppprofileDTO appprofileDTO) throws URISyntaxException {
        log.debug("REST request to save Appprofile : {}", appprofileDTO);
        if (appprofileDTO.getId() != null) {
            throw new BadRequestAlertException("A new appprofile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppprofileDTO result = appprofileService.save(appprofileDTO);
        return ResponseEntity.created(new URI("/api/appprofiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appprofiles} : Updates an existing appprofile.
     *
     * @param appprofileDTO the appprofileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appprofileDTO,
     * or with status {@code 400 (Bad Request)} if the appprofileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appprofileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appprofiles")
    public ResponseEntity<AppprofileDTO> updateAppprofile(@Valid @RequestBody AppprofileDTO appprofileDTO) throws URISyntaxException {
        log.debug("REST request to update Appprofile : {}", appprofileDTO);
        if (appprofileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppprofileDTO result = appprofileService.save(appprofileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appprofileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appprofiles} : get all the appprofiles.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appprofiles in body.
     */
    @GetMapping("/appprofiles")
    public ResponseEntity<List<AppprofileDTO>> getAllAppprofiles(AppprofileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Appprofiles by criteria: {}", criteria);
        Page<AppprofileDTO> page = appprofileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /appprofiles/count} : count all the appprofiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/appprofiles/count")
    public ResponseEntity<Long> countAppprofiles(AppprofileCriteria criteria) {
        log.debug("REST request to count Appprofiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(appprofileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appprofiles/:id} : get the "id" appprofile.
     *
     * @param id the id of the appprofileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appprofileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appprofiles/{id}")
    public ResponseEntity<AppprofileDTO> getAppprofile(@PathVariable Long id) {
        log.debug("REST request to get Appprofile : {}", id);
        Optional<AppprofileDTO> appprofileDTO = appprofileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appprofileDTO);
    }

    /**
     * {@code DELETE  /appprofiles/:id} : delete the "id" appprofile.
     *
     * @param id the id of the appprofileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appprofiles/{id}")
    public ResponseEntity<Void> deleteAppprofile(@PathVariable Long id) {
        log.debug("REST request to delete Appprofile : {}", id);
        appprofileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
