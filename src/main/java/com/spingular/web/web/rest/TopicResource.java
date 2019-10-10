package com.spingular.web.web.rest;

import com.spingular.web.service.TopicService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.TopicDTO;
import com.spingular.web.service.dto.TopicCriteria;
import com.spingular.web.service.TopicQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Topic}.
 */
@RestController
@RequestMapping("/api")
public class TopicResource {

    private final Logger log = LoggerFactory.getLogger(TopicResource.class);

    private static final String ENTITY_NAME = "topic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicService topicService;

    private final TopicQueryService topicQueryService;

    public TopicResource(TopicService topicService, TopicQueryService topicQueryService) {
        this.topicService = topicService;
        this.topicQueryService = topicQueryService;
    }

    /**
     * {@code POST  /topics} : Create a new topic.
     *
     * @param topicDTO the topicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicDTO, or with status {@code 400 (Bad Request)} if the topic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topics")
    public ResponseEntity<TopicDTO> createTopic(@Valid @RequestBody TopicDTO topicDTO) throws URISyntaxException {
        log.debug("REST request to save Topic : {}", topicDTO);
        if (topicDTO.getId() != null) {
            throw new BadRequestAlertException("A new topic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicDTO result = topicService.save(topicDTO);
        return ResponseEntity.created(new URI("/api/topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topics} : Updates an existing topic.
     *
     * @param topicDTO the topicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicDTO,
     * or with status {@code 400 (Bad Request)} if the topicDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topics")
    public ResponseEntity<TopicDTO> updateTopic(@Valid @RequestBody TopicDTO topicDTO) throws URISyntaxException {
        log.debug("REST request to update Topic : {}", topicDTO);
        if (topicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicDTO result = topicService.save(topicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topicDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topics} : get all the topics.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topics in body.
     */
    @GetMapping("/topics")
    public ResponseEntity<List<TopicDTO>> getAllTopics(TopicCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Topics by criteria: {}", criteria);
        Page<TopicDTO> page = topicQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /topics/count} : count all the topics.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/topics/count")
    public ResponseEntity<Long> countTopics(TopicCriteria criteria) {
        log.debug("REST request to count Topics by criteria: {}", criteria);
        return ResponseEntity.ok().body(topicQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /topics/:id} : get the "id" topic.
     *
     * @param id the id of the topicDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topics/{id}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable Long id) {
        log.debug("REST request to get Topic : {}", id);
        Optional<TopicDTO> topicDTO = topicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicDTO);
    }

    /**
     * {@code DELETE  /topics/:id} : delete the "id" topic.
     *
     * @param id the id of the topicDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        log.debug("REST request to delete Topic : {}", id);
        topicService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
