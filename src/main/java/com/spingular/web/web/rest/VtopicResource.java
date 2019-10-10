package com.spingular.web.web.rest;

import com.spingular.web.service.VtopicService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.VtopicDTO;
import com.spingular.web.service.dto.VtopicCriteria;
import com.spingular.web.service.VtopicQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Vtopic}.
 */
@RestController
@RequestMapping("/api")
public class VtopicResource {

    private final Logger log = LoggerFactory.getLogger(VtopicResource.class);

    private static final String ENTITY_NAME = "vtopic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VtopicService vtopicService;

    private final VtopicQueryService vtopicQueryService;

    public VtopicResource(VtopicService vtopicService, VtopicQueryService vtopicQueryService) {
        this.vtopicService = vtopicService;
        this.vtopicQueryService = vtopicQueryService;
    }

    /**
     * {@code POST  /vtopics} : Create a new vtopic.
     *
     * @param vtopicDTO the vtopicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vtopicDTO, or with status {@code 400 (Bad Request)} if the vtopic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vtopics")
    public ResponseEntity<VtopicDTO> createVtopic(@Valid @RequestBody VtopicDTO vtopicDTO) throws URISyntaxException {
        log.debug("REST request to save Vtopic : {}", vtopicDTO);
        if (vtopicDTO.getId() != null) {
            throw new BadRequestAlertException("A new vtopic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VtopicDTO result = vtopicService.save(vtopicDTO);
        return ResponseEntity.created(new URI("/api/vtopics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vtopics} : Updates an existing vtopic.
     *
     * @param vtopicDTO the vtopicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vtopicDTO,
     * or with status {@code 400 (Bad Request)} if the vtopicDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vtopicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vtopics")
    public ResponseEntity<VtopicDTO> updateVtopic(@Valid @RequestBody VtopicDTO vtopicDTO) throws URISyntaxException {
        log.debug("REST request to update Vtopic : {}", vtopicDTO);
        if (vtopicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VtopicDTO result = vtopicService.save(vtopicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vtopicDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vtopics} : get all the vtopics.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vtopics in body.
     */
    @GetMapping("/vtopics")
    public ResponseEntity<List<VtopicDTO>> getAllVtopics(VtopicCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vtopics by criteria: {}", criteria);
        Page<VtopicDTO> page = vtopicQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /vtopics/count} : count all the vtopics.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/vtopics/count")
    public ResponseEntity<Long> countVtopics(VtopicCriteria criteria) {
        log.debug("REST request to count Vtopics by criteria: {}", criteria);
        return ResponseEntity.ok().body(vtopicQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vtopics/:id} : get the "id" vtopic.
     *
     * @param id the id of the vtopicDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vtopicDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vtopics/{id}")
    public ResponseEntity<VtopicDTO> getVtopic(@PathVariable Long id) {
        log.debug("REST request to get Vtopic : {}", id);
        Optional<VtopicDTO> vtopicDTO = vtopicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vtopicDTO);
    }

    /**
     * {@code DELETE  /vtopics/:id} : delete the "id" vtopic.
     *
     * @param id the id of the vtopicDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vtopics/{id}")
    public ResponseEntity<Void> deleteVtopic(@PathVariable Long id) {
        log.debug("REST request to delete Vtopic : {}", id);
        vtopicService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
