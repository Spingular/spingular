package com.spingular.web.web.rest;

import com.spingular.web.service.CalbumService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.CalbumDTO;
import com.spingular.web.service.dto.CalbumCriteria;
import com.spingular.web.service.CalbumQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Calbum}.
 */
@RestController
@RequestMapping("/api")
public class CalbumResource {

    private final Logger log = LoggerFactory.getLogger(CalbumResource.class);

    private static final String ENTITY_NAME = "calbum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalbumService calbumService;

    private final CalbumQueryService calbumQueryService;

    public CalbumResource(CalbumService calbumService, CalbumQueryService calbumQueryService) {
        this.calbumService = calbumService;
        this.calbumQueryService = calbumQueryService;
    }

    /**
     * {@code POST  /calbums} : Create a new calbum.
     *
     * @param calbumDTO the calbumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calbumDTO, or with status {@code 400 (Bad Request)} if the calbum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calbums")
    public ResponseEntity<CalbumDTO> createCalbum(@Valid @RequestBody CalbumDTO calbumDTO) throws URISyntaxException {
        log.debug("REST request to save Calbum : {}", calbumDTO);
        if (calbumDTO.getId() != null) {
            throw new BadRequestAlertException("A new calbum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalbumDTO result = calbumService.save(calbumDTO);
        return ResponseEntity.created(new URI("/api/calbums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calbums} : Updates an existing calbum.
     *
     * @param calbumDTO the calbumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calbumDTO,
     * or with status {@code 400 (Bad Request)} if the calbumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calbumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calbums")
    public ResponseEntity<CalbumDTO> updateCalbum(@Valid @RequestBody CalbumDTO calbumDTO) throws URISyntaxException {
        log.debug("REST request to update Calbum : {}", calbumDTO);
        if (calbumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalbumDTO result = calbumService.save(calbumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calbumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calbums} : get all the calbums.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calbums in body.
     */
    @GetMapping("/calbums")
    public ResponseEntity<List<CalbumDTO>> getAllCalbums(CalbumCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Calbums by criteria: {}", criteria);
        Page<CalbumDTO> page = calbumQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /calbums/count} : count all the calbums.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/calbums/count")
    public ResponseEntity<Long> countCalbums(CalbumCriteria criteria) {
        log.debug("REST request to count Calbums by criteria: {}", criteria);
        return ResponseEntity.ok().body(calbumQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calbums/:id} : get the "id" calbum.
     *
     * @param id the id of the calbumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calbumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calbums/{id}")
    public ResponseEntity<CalbumDTO> getCalbum(@PathVariable Long id) {
        log.debug("REST request to get Calbum : {}", id);
        Optional<CalbumDTO> calbumDTO = calbumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calbumDTO);
    }

    /**
     * {@code DELETE  /calbums/:id} : delete the "id" calbum.
     *
     * @param id the id of the calbumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calbums/{id}")
    public ResponseEntity<Void> deleteCalbum(@PathVariable Long id) {
        log.debug("REST request to delete Calbum : {}", id);
        calbumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
