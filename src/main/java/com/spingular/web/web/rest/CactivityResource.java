package com.spingular.web.web.rest;

import com.spingular.web.service.CactivityService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.CactivityDTO;
import com.spingular.web.service.dto.CactivityCriteria;
import com.spingular.web.service.CactivityQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Cactivity}.
 */
@RestController
@RequestMapping("/api")
public class CactivityResource {

    private final Logger log = LoggerFactory.getLogger(CactivityResource.class);

    private static final String ENTITY_NAME = "cactivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CactivityService cactivityService;

    private final CactivityQueryService cactivityQueryService;

    public CactivityResource(CactivityService cactivityService, CactivityQueryService cactivityQueryService) {
        this.cactivityService = cactivityService;
        this.cactivityQueryService = cactivityQueryService;
    }

    /**
     * {@code POST  /cactivities} : Create a new cactivity.
     *
     * @param cactivityDTO the cactivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cactivityDTO, or with status {@code 400 (Bad Request)} if the cactivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cactivities")
    public ResponseEntity<CactivityDTO> createCactivity(@Valid @RequestBody CactivityDTO cactivityDTO) throws URISyntaxException {
        log.debug("REST request to save Cactivity : {}", cactivityDTO);
        if (cactivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new cactivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CactivityDTO result = cactivityService.save(cactivityDTO);
        return ResponseEntity.created(new URI("/api/cactivities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cactivities} : Updates an existing cactivity.
     *
     * @param cactivityDTO the cactivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cactivityDTO,
     * or with status {@code 400 (Bad Request)} if the cactivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cactivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cactivities")
    public ResponseEntity<CactivityDTO> updateCactivity(@Valid @RequestBody CactivityDTO cactivityDTO) throws URISyntaxException {
        log.debug("REST request to update Cactivity : {}", cactivityDTO);
        if (cactivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CactivityDTO result = cactivityService.save(cactivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cactivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cactivities} : get all the cactivities.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cactivities in body.
     */
    @GetMapping("/cactivities")
    public ResponseEntity<List<CactivityDTO>> getAllCactivities(CactivityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cactivities by criteria: {}", criteria);
        Page<CactivityDTO> page = cactivityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /cactivities/count} : count all the cactivities.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cactivities/count")
    public ResponseEntity<Long> countCactivities(CactivityCriteria criteria) {
        log.debug("REST request to count Cactivities by criteria: {}", criteria);
        return ResponseEntity.ok().body(cactivityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cactivities/:id} : get the "id" cactivity.
     *
     * @param id the id of the cactivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cactivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cactivities/{id}")
    public ResponseEntity<CactivityDTO> getCactivity(@PathVariable Long id) {
        log.debug("REST request to get Cactivity : {}", id);
        Optional<CactivityDTO> cactivityDTO = cactivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cactivityDTO);
    }

    /**
     * {@code DELETE  /cactivities/:id} : delete the "id" cactivity.
     *
     * @param id the id of the cactivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cactivities/{id}")
    public ResponseEntity<Void> deleteCactivity(@PathVariable Long id) {
        log.debug("REST request to delete Cactivity : {}", id);
        cactivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
