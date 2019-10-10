package com.spingular.web.web.rest;

import com.spingular.web.service.FeedbackService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.FeedbackDTO;
import com.spingular.web.service.dto.FeedbackCriteria;
import com.spingular.web.service.FeedbackQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Feedback}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackService feedbackService;

    private final FeedbackQueryService feedbackQueryService;

    public FeedbackResource(FeedbackService feedbackService, FeedbackQueryService feedbackQueryService) {
        this.feedbackService = feedbackService;
        this.feedbackQueryService = feedbackQueryService;
    }

    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param feedbackDTO the feedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackDTO, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackDTO> createFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedbacks} : Updates an existing feedback.
     *
     * @param feedbackDTO the feedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedbacks")
    public ResponseEntity<FeedbackDTO> updateFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedbackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feedbacks} : get all the feedbacks.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks(FeedbackCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Feedbacks by criteria: {}", criteria);
        Page<FeedbackDTO> page = feedbackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /feedbacks/count} : count all the feedbacks.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/feedbacks/count")
    public ResponseEntity<Long> countFeedbacks(FeedbackCriteria criteria) {
        log.debug("REST request to count Feedbacks by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedbacks/:id} : get the "id" feedback.
     *
     * @param id the id of the feedbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackDTO> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<FeedbackDTO> feedbackDTO = feedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackDTO);
    }

    /**
     * {@code DELETE  /feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
