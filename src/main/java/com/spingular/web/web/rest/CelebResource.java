package com.spingular.web.web.rest;

import com.spingular.web.service.CelebService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.CelebDTO;
import com.spingular.web.service.dto.CelebCriteria;
import com.spingular.web.service.CelebQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Celeb}.
 */
@RestController
@RequestMapping("/api")
public class CelebResource {

    private final Logger log = LoggerFactory.getLogger(CelebResource.class);

    private static final String ENTITY_NAME = "celeb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CelebService celebService;

    private final CelebQueryService celebQueryService;

    public CelebResource(CelebService celebService, CelebQueryService celebQueryService) {
        this.celebService = celebService;
        this.celebQueryService = celebQueryService;
    }

    /**
     * {@code POST  /celebs} : Create a new celeb.
     *
     * @param celebDTO the celebDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new celebDTO, or with status {@code 400 (Bad Request)} if the celeb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/celebs")
    public ResponseEntity<CelebDTO> createCeleb(@Valid @RequestBody CelebDTO celebDTO) throws URISyntaxException {
        log.debug("REST request to save Celeb : {}", celebDTO);
        if (celebDTO.getId() != null) {
            throw new BadRequestAlertException("A new celeb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CelebDTO result = celebService.save(celebDTO);
        return ResponseEntity.created(new URI("/api/celebs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /celebs} : Updates an existing celeb.
     *
     * @param celebDTO the celebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated celebDTO,
     * or with status {@code 400 (Bad Request)} if the celebDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the celebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/celebs")
    public ResponseEntity<CelebDTO> updateCeleb(@Valid @RequestBody CelebDTO celebDTO) throws URISyntaxException {
        log.debug("REST request to update Celeb : {}", celebDTO);
        if (celebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CelebDTO result = celebService.save(celebDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, celebDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /celebs} : get all the celebs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of celebs in body.
     */
    @GetMapping("/celebs")
    public ResponseEntity<List<CelebDTO>> getAllCelebs(CelebCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Celebs by criteria: {}", criteria);
        Page<CelebDTO> page = celebQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /celebs/count} : count all the celebs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/celebs/count")
    public ResponseEntity<Long> countCelebs(CelebCriteria criteria) {
        log.debug("REST request to count Celebs by criteria: {}", criteria);
        return ResponseEntity.ok().body(celebQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /celebs/:id} : get the "id" celeb.
     *
     * @param id the id of the celebDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the celebDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/celebs/{id}")
    public ResponseEntity<CelebDTO> getCeleb(@PathVariable Long id) {
        log.debug("REST request to get Celeb : {}", id);
        Optional<CelebDTO> celebDTO = celebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(celebDTO);
    }

    /**
     * {@code DELETE  /celebs/:id} : delete the "id" celeb.
     *
     * @param id the id of the celebDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/celebs/{id}")
    public ResponseEntity<Void> deleteCeleb(@PathVariable Long id) {
        log.debug("REST request to delete Celeb : {}", id);
        celebService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
