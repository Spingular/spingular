package com.spingular.web.web.rest;

import com.spingular.web.service.AppphotoService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.AppphotoDTO;
import com.spingular.web.service.dto.AppphotoCriteria;
import com.spingular.web.service.AppphotoQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Appphoto}.
 */
@RestController
@RequestMapping("/api")
public class AppphotoResource {

    private final Logger log = LoggerFactory.getLogger(AppphotoResource.class);

    private static final String ENTITY_NAME = "appphoto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppphotoService appphotoService;

    private final AppphotoQueryService appphotoQueryService;

    public AppphotoResource(AppphotoService appphotoService, AppphotoQueryService appphotoQueryService) {
        this.appphotoService = appphotoService;
        this.appphotoQueryService = appphotoQueryService;
    }

    /**
     * {@code POST  /appphotos} : Create a new appphoto.
     *
     * @param appphotoDTO the appphotoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appphotoDTO, or with status {@code 400 (Bad Request)} if the appphoto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appphotos")
    public ResponseEntity<AppphotoDTO> createAppphoto(@Valid @RequestBody AppphotoDTO appphotoDTO) throws URISyntaxException {
        log.debug("REST request to save Appphoto : {}", appphotoDTO);
        if (appphotoDTO.getId() != null) {
            throw new BadRequestAlertException("A new appphoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppphotoDTO result = appphotoService.save(appphotoDTO);
        return ResponseEntity.created(new URI("/api/appphotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appphotos} : Updates an existing appphoto.
     *
     * @param appphotoDTO the appphotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appphotoDTO,
     * or with status {@code 400 (Bad Request)} if the appphotoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appphotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appphotos")
    public ResponseEntity<AppphotoDTO> updateAppphoto(@Valid @RequestBody AppphotoDTO appphotoDTO) throws URISyntaxException {
        log.debug("REST request to update Appphoto : {}", appphotoDTO);
        if (appphotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppphotoDTO result = appphotoService.save(appphotoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appphotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appphotos} : get all the appphotos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appphotos in body.
     */
    @GetMapping("/appphotos")
    public ResponseEntity<List<AppphotoDTO>> getAllAppphotos(AppphotoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Appphotos by criteria: {}", criteria);
        Page<AppphotoDTO> page = appphotoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /appphotos/count} : count all the appphotos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/appphotos/count")
    public ResponseEntity<Long> countAppphotos(AppphotoCriteria criteria) {
        log.debug("REST request to count Appphotos by criteria: {}", criteria);
        return ResponseEntity.ok().body(appphotoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appphotos/:id} : get the "id" appphoto.
     *
     * @param id the id of the appphotoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appphotoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appphotos/{id}")
    public ResponseEntity<AppphotoDTO> getAppphoto(@PathVariable Long id) {
        log.debug("REST request to get Appphoto : {}", id);
        Optional<AppphotoDTO> appphotoDTO = appphotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appphotoDTO);
    }

    /**
     * {@code DELETE  /appphotos/:id} : delete the "id" appphoto.
     *
     * @param id the id of the appphotoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appphotos/{id}")
    public ResponseEntity<Void> deleteAppphoto(@PathVariable Long id) {
        log.debug("REST request to delete Appphoto : {}", id);
        appphotoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
