package com.spingular.web.web.rest;

import com.spingular.web.service.FollowService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.FollowDTO;
import com.spingular.web.service.dto.FollowCriteria;
import com.spingular.web.service.FollowQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.spingular.web.domain.Follow}.
 */
@RestController
@RequestMapping("/api")
public class FollowResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    private static final String ENTITY_NAME = "follow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FollowService followService;

    private final FollowQueryService followQueryService;

    public FollowResource(FollowService followService, FollowQueryService followQueryService) {
        this.followService = followService;
        this.followQueryService = followQueryService;
    }

    /**
     * {@code POST  /follows} : Create a new follow.
     *
     * @param followDTO the followDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new followDTO, or with status {@code 400 (Bad Request)} if the follow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/follows")
    public ResponseEntity<FollowDTO> createFollow(@RequestBody FollowDTO followDTO) throws URISyntaxException {
        log.debug("REST request to save Follow : {}", followDTO);
        if (followDTO.getId() != null) {
            throw new BadRequestAlertException("A new follow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FollowDTO result = followService.save(followDTO);
        return ResponseEntity.created(new URI("/api/follows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /follows} : Updates an existing follow.
     *
     * @param followDTO the followDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated followDTO,
     * or with status {@code 400 (Bad Request)} if the followDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the followDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/follows")
    public ResponseEntity<FollowDTO> updateFollow(@RequestBody FollowDTO followDTO) throws URISyntaxException {
        log.debug("REST request to update Follow : {}", followDTO);
        if (followDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FollowDTO result = followService.save(followDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, followDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /follows} : get all the follows.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of follows in body.
     */
    @GetMapping("/follows")
    public ResponseEntity<List<FollowDTO>> getAllFollows(FollowCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Follows by criteria: {}", criteria);
        Page<FollowDTO> page = followQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /follows/count} : count all the follows.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/follows/count")
    public ResponseEntity<Long> countFollows(FollowCriteria criteria) {
        log.debug("REST request to count Follows by criteria: {}", criteria);
        return ResponseEntity.ok().body(followQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /follows/:id} : get the "id" follow.
     *
     * @param id the id of the followDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the followDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/follows/{id}")
    public ResponseEntity<FollowDTO> getFollow(@PathVariable Long id) {
        log.debug("REST request to get Follow : {}", id);
        Optional<FollowDTO> followDTO = followService.findOne(id);
        return ResponseUtil.wrapOrNotFound(followDTO);
    }

    /**
     * {@code DELETE  /follows/:id} : delete the "id" follow.
     *
     * @param id the id of the followDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/follows/{id}")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        log.debug("REST request to delete Follow : {}", id);
        followService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
