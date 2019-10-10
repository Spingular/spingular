package com.spingular.web.web.rest;

import com.spingular.web.service.ConfigVariablesService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.ConfigVariablesDTO;
import com.spingular.web.service.dto.ConfigVariablesCriteria;
import com.spingular.web.service.ConfigVariablesQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.ConfigVariables}.
 */
@RestController
@RequestMapping("/api")
public class ConfigVariablesResource {

    private final Logger log = LoggerFactory.getLogger(ConfigVariablesResource.class);

    private static final String ENTITY_NAME = "configVariables";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigVariablesService configVariablesService;

    private final ConfigVariablesQueryService configVariablesQueryService;

    public ConfigVariablesResource(ConfigVariablesService configVariablesService, ConfigVariablesQueryService configVariablesQueryService) {
        this.configVariablesService = configVariablesService;
        this.configVariablesQueryService = configVariablesQueryService;
    }

    /**
     * {@code POST  /config-variables} : Create a new configVariables.
     *
     * @param configVariablesDTO the configVariablesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configVariablesDTO, or with status {@code 400 (Bad Request)} if the configVariables has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-variables")
    public ResponseEntity<ConfigVariablesDTO> createConfigVariables(@RequestBody ConfigVariablesDTO configVariablesDTO) throws URISyntaxException {
        log.debug("REST request to save ConfigVariables : {}", configVariablesDTO);
        if (configVariablesDTO.getId() != null) {
            throw new BadRequestAlertException("A new configVariables cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigVariablesDTO result = configVariablesService.save(configVariablesDTO);
        return ResponseEntity.created(new URI("/api/config-variables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /config-variables} : Updates an existing configVariables.
     *
     * @param configVariablesDTO the configVariablesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configVariablesDTO,
     * or with status {@code 400 (Bad Request)} if the configVariablesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configVariablesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-variables")
    public ResponseEntity<ConfigVariablesDTO> updateConfigVariables(@RequestBody ConfigVariablesDTO configVariablesDTO) throws URISyntaxException {
        log.debug("REST request to update ConfigVariables : {}", configVariablesDTO);
        if (configVariablesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigVariablesDTO result = configVariablesService.save(configVariablesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configVariablesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /config-variables} : get all the configVariables.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configVariables in body.
     */
    @GetMapping("/config-variables")
    public ResponseEntity<List<ConfigVariablesDTO>> getAllConfigVariables(ConfigVariablesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ConfigVariables by criteria: {}", criteria);
        Page<ConfigVariablesDTO> page = configVariablesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /config-variables/count} : count all the configVariables.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/config-variables/count")
    public ResponseEntity<Long> countConfigVariables(ConfigVariablesCriteria criteria) {
        log.debug("REST request to count ConfigVariables by criteria: {}", criteria);
        return ResponseEntity.ok().body(configVariablesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /config-variables/:id} : get the "id" configVariables.
     *
     * @param id the id of the configVariablesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configVariablesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-variables/{id}")
    public ResponseEntity<ConfigVariablesDTO> getConfigVariables(@PathVariable Long id) {
        log.debug("REST request to get ConfigVariables : {}", id);
        Optional<ConfigVariablesDTO> configVariablesDTO = configVariablesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configVariablesDTO);
    }

    /**
     * {@code DELETE  /config-variables/:id} : delete the "id" configVariables.
     *
     * @param id the id of the configVariablesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-variables/{id}")
    public ResponseEntity<Void> deleteConfigVariables(@PathVariable Long id) {
        log.debug("REST request to delete ConfigVariables : {}", id);
        configVariablesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
