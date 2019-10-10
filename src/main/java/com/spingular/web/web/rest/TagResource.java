package com.spingular.web.web.rest;

import com.spingular.web.service.TagService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.TagDTO;
import com.spingular.web.service.dto.TagCriteria;
import com.spingular.web.service.TagQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Tag}.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private static final String ENTITY_NAME = "tag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagService tagService;

    private final TagQueryService tagQueryService;

    public TagResource(TagService tagService, TagQueryService tagQueryService) {
        this.tagService = tagService;
        this.tagQueryService = tagQueryService;
    }

    /**
     * {@code POST  /tags} : Create a new tag.
     *
     * @param tagDTO the tagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagDTO, or with status {@code 400 (Bad Request)} if the tag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tags")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) throws URISyntaxException {
        log.debug("REST request to save Tag : {}", tagDTO);
        if (tagDTO.getId() != null) {
            throw new BadRequestAlertException("A new tag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagDTO result = tagService.save(tagDTO);
        return ResponseEntity.created(new URI("/api/tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tags} : Updates an existing tag.
     *
     * @param tagDTO the tagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagDTO,
     * or with status {@code 400 (Bad Request)} if the tagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tags")
    public ResponseEntity<TagDTO> updateTag(@Valid @RequestBody TagDTO tagDTO) throws URISyntaxException {
        log.debug("REST request to update Tag : {}", tagDTO);
        if (tagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagDTO result = tagService.save(tagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tags} : get all the tags.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tags in body.
     */
    @GetMapping("/tags")
    public ResponseEntity<List<TagDTO>> getAllTags(TagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tags by criteria: {}", criteria);
        Page<TagDTO> page = tagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tags/count} : count all the tags.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tags/count")
    public ResponseEntity<Long> countTags(TagCriteria criteria) {
        log.debug("REST request to count Tags by criteria: {}", criteria);
        return ResponseEntity.ok().body(tagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tags/:id} : get the "id" tag.
     *
     * @param id the id of the tagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tags/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable Long id) {
        log.debug("REST request to get Tag : {}", id);
        Optional<TagDTO> tagDTO = tagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagDTO);
    }

    /**
     * {@code DELETE  /tags/:id} : delete the "id" tag.
     *
     * @param id the id of the tagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        log.debug("REST request to delete Tag : {}", id);
        tagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
