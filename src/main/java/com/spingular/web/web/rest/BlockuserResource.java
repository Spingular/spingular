package com.spingular.web.web.rest;

import com.spingular.web.service.BlockuserService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.BlockuserDTO;
import com.spingular.web.service.dto.BlockuserCriteria;
import com.spingular.web.service.BlockuserQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Blockuser}.
 */
@RestController
@RequestMapping("/api")
public class BlockuserResource {

    private final Logger log = LoggerFactory.getLogger(BlockuserResource.class);

    private static final String ENTITY_NAME = "blockuser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlockuserService blockuserService;

    private final BlockuserQueryService blockuserQueryService;

    public BlockuserResource(BlockuserService blockuserService, BlockuserQueryService blockuserQueryService) {
        this.blockuserService = blockuserService;
        this.blockuserQueryService = blockuserQueryService;
    }

    /**
     * {@code POST  /blockusers} : Create a new blockuser.
     *
     * @param blockuserDTO the blockuserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blockuserDTO, or with status {@code 400 (Bad Request)} if the blockuser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blockusers")
    public ResponseEntity<BlockuserDTO> createBlockuser(@RequestBody BlockuserDTO blockuserDTO) throws URISyntaxException {
        log.debug("REST request to save Blockuser : {}", blockuserDTO);
        if (blockuserDTO.getId() != null) {
            throw new BadRequestAlertException("A new blockuser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlockuserDTO result = blockuserService.save(blockuserDTO);
        return ResponseEntity.created(new URI("/api/blockusers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blockusers} : Updates an existing blockuser.
     *
     * @param blockuserDTO the blockuserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockuserDTO,
     * or with status {@code 400 (Bad Request)} if the blockuserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blockuserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blockusers")
    public ResponseEntity<BlockuserDTO> updateBlockuser(@RequestBody BlockuserDTO blockuserDTO) throws URISyntaxException {
        log.debug("REST request to update Blockuser : {}", blockuserDTO);
        if (blockuserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlockuserDTO result = blockuserService.save(blockuserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blockuserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blockusers} : get all the blockusers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blockusers in body.
     */
    @GetMapping("/blockusers")
    public ResponseEntity<List<BlockuserDTO>> getAllBlockusers(BlockuserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Blockusers by criteria: {}", criteria);
        Page<BlockuserDTO> page = blockuserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /blockusers/count} : count all the blockusers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/blockusers/count")
    public ResponseEntity<Long> countBlockusers(BlockuserCriteria criteria) {
        log.debug("REST request to count Blockusers by criteria: {}", criteria);
        return ResponseEntity.ok().body(blockuserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blockusers/:id} : get the "id" blockuser.
     *
     * @param id the id of the blockuserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blockuserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blockusers/{id}")
    public ResponseEntity<BlockuserDTO> getBlockuser(@PathVariable Long id) {
        log.debug("REST request to get Blockuser : {}", id);
        Optional<BlockuserDTO> blockuserDTO = blockuserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockuserDTO);
    }

    /**
     * {@code DELETE  /blockusers/:id} : delete the "id" blockuser.
     *
     * @param id the id of the blockuserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blockusers/{id}")
    public ResponseEntity<Void> deleteBlockuser(@PathVariable Long id) {
        log.debug("REST request to delete Blockuser : {}", id);
        blockuserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
