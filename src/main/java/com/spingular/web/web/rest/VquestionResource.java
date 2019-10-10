package com.spingular.web.web.rest;

import com.spingular.web.service.VquestionService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.VquestionDTO;
import com.spingular.web.service.dto.VquestionCriteria;
import com.spingular.web.service.VquestionQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Vquestion}.
 */
@RestController
@RequestMapping("/api")
public class VquestionResource {

    private final Logger log = LoggerFactory.getLogger(VquestionResource.class);

    private static final String ENTITY_NAME = "vquestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VquestionService vquestionService;

    private final VquestionQueryService vquestionQueryService;

    public VquestionResource(VquestionService vquestionService, VquestionQueryService vquestionQueryService) {
        this.vquestionService = vquestionService;
        this.vquestionQueryService = vquestionQueryService;
    }

    /**
     * {@code POST  /vquestions} : Create a new vquestion.
     *
     * @param vquestionDTO the vquestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vquestionDTO, or with status {@code 400 (Bad Request)} if the vquestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vquestions")
    public ResponseEntity<VquestionDTO> createVquestion(@Valid @RequestBody VquestionDTO vquestionDTO) throws URISyntaxException {
        log.debug("REST request to save Vquestion : {}", vquestionDTO);
        if (vquestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new vquestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VquestionDTO result = vquestionService.save(vquestionDTO);
        return ResponseEntity.created(new URI("/api/vquestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vquestions} : Updates an existing vquestion.
     *
     * @param vquestionDTO the vquestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vquestionDTO,
     * or with status {@code 400 (Bad Request)} if the vquestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vquestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vquestions")
    public ResponseEntity<VquestionDTO> updateVquestion(@Valid @RequestBody VquestionDTO vquestionDTO) throws URISyntaxException {
        log.debug("REST request to update Vquestion : {}", vquestionDTO);
        if (vquestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VquestionDTO result = vquestionService.save(vquestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vquestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vquestions} : get all the vquestions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vquestions in body.
     */
    @GetMapping("/vquestions")
    public ResponseEntity<List<VquestionDTO>> getAllVquestions(VquestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vquestions by criteria: {}", criteria);
        Page<VquestionDTO> page = vquestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /vquestions/count} : count all the vquestions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/vquestions/count")
    public ResponseEntity<Long> countVquestions(VquestionCriteria criteria) {
        log.debug("REST request to count Vquestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(vquestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vquestions/:id} : get the "id" vquestion.
     *
     * @param id the id of the vquestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vquestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vquestions/{id}")
    public ResponseEntity<VquestionDTO> getVquestion(@PathVariable Long id) {
        log.debug("REST request to get Vquestion : {}", id);
        Optional<VquestionDTO> vquestionDTO = vquestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vquestionDTO);
    }

    /**
     * {@code DELETE  /vquestions/:id} : delete the "id" vquestion.
     *
     * @param id the id of the vquestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vquestions/{id}")
    public ResponseEntity<Void> deleteVquestion(@PathVariable Long id) {
        log.debug("REST request to delete Vquestion : {}", id);
        vquestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
