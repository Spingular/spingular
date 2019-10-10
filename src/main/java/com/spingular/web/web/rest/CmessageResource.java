package com.spingular.web.web.rest;

import com.spingular.web.service.CmessageService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.CmessageDTO;
import com.spingular.web.service.dto.CmessageCriteria;
import com.spingular.web.service.CmessageQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Cmessage}.
 */
@RestController
@RequestMapping("/api")
public class CmessageResource {

    private final Logger log = LoggerFactory.getLogger(CmessageResource.class);

    private static final String ENTITY_NAME = "cmessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmessageService cmessageService;

    private final CmessageQueryService cmessageQueryService;

    public CmessageResource(CmessageService cmessageService, CmessageQueryService cmessageQueryService) {
        this.cmessageService = cmessageService;
        this.cmessageQueryService = cmessageQueryService;
    }

    /**
     * {@code POST  /cmessages} : Create a new cmessage.
     *
     * @param cmessageDTO the cmessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmessageDTO, or with status {@code 400 (Bad Request)} if the cmessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cmessages")
    public ResponseEntity<CmessageDTO> createCmessage(@Valid @RequestBody CmessageDTO cmessageDTO) throws URISyntaxException {
        log.debug("REST request to save Cmessage : {}", cmessageDTO);
        if (cmessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new cmessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmessageDTO result = cmessageService.save(cmessageDTO);
        return ResponseEntity.created(new URI("/api/cmessages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cmessages} : Updates an existing cmessage.
     *
     * @param cmessageDTO the cmessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmessageDTO,
     * or with status {@code 400 (Bad Request)} if the cmessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cmessages")
    public ResponseEntity<CmessageDTO> updateCmessage(@Valid @RequestBody CmessageDTO cmessageDTO) throws URISyntaxException {
        log.debug("REST request to update Cmessage : {}", cmessageDTO);
        if (cmessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmessageDTO result = cmessageService.save(cmessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cmessages} : get all the cmessages.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmessages in body.
     */
    @GetMapping("/cmessages")
    public ResponseEntity<List<CmessageDTO>> getAllCmessages(CmessageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cmessages by criteria: {}", criteria);
        Page<CmessageDTO> page = cmessageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /cmessages/count} : count all the cmessages.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cmessages/count")
    public ResponseEntity<Long> countCmessages(CmessageCriteria criteria) {
        log.debug("REST request to count Cmessages by criteria: {}", criteria);
        return ResponseEntity.ok().body(cmessageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cmessages/:id} : get the "id" cmessage.
     *
     * @param id the id of the cmessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cmessages/{id}")
    public ResponseEntity<CmessageDTO> getCmessage(@PathVariable Long id) {
        log.debug("REST request to get Cmessage : {}", id);
        Optional<CmessageDTO> cmessageDTO = cmessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmessageDTO);
    }

    /**
     * {@code DELETE  /cmessages/:id} : delete the "id" cmessage.
     *
     * @param id the id of the cmessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cmessages/{id}")
    public ResponseEntity<Void> deleteCmessage(@PathVariable Long id) {
        log.debug("REST request to delete Cmessage : {}", id);
        cmessageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
