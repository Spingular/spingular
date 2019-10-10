package com.spingular.web.web.rest;

import com.spingular.web.service.AlbumService;
import com.spingular.web.web.rest.errors.BadRequestAlertException;
import com.spingular.web.service.dto.AlbumDTO;
import com.spingular.web.service.dto.AlbumCriteria;
import com.spingular.web.service.AlbumQueryService;

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
 * REST controller for managing {@link com.spingular.web.domain.Album}.
 */
@RestController
@RequestMapping("/api")
public class AlbumResource {

    private final Logger log = LoggerFactory.getLogger(AlbumResource.class);

    private static final String ENTITY_NAME = "album";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlbumService albumService;

    private final AlbumQueryService albumQueryService;

    public AlbumResource(AlbumService albumService, AlbumQueryService albumQueryService) {
        this.albumService = albumService;
        this.albumQueryService = albumQueryService;
    }

    /**
     * {@code POST  /albums} : Create a new album.
     *
     * @param albumDTO the albumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new albumDTO, or with status {@code 400 (Bad Request)} if the album has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/albums")
    public ResponseEntity<AlbumDTO> createAlbum(@Valid @RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        log.debug("REST request to save Album : {}", albumDTO);
        if (albumDTO.getId() != null) {
            throw new BadRequestAlertException("A new album cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlbumDTO result = albumService.save(albumDTO);
        return ResponseEntity.created(new URI("/api/albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /albums} : Updates an existing album.
     *
     * @param albumDTO the albumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated albumDTO,
     * or with status {@code 400 (Bad Request)} if the albumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the albumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/albums")
    public ResponseEntity<AlbumDTO> updateAlbum(@Valid @RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        log.debug("REST request to update Album : {}", albumDTO);
        if (albumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlbumDTO result = albumService.save(albumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, albumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /albums} : get all the albums.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of albums in body.
     */
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumDTO>> getAllAlbums(AlbumCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Albums by criteria: {}", criteria);
        Page<AlbumDTO> page = albumQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /albums/count} : count all the albums.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/albums/count")
    public ResponseEntity<Long> countAlbums(AlbumCriteria criteria) {
        log.debug("REST request to count Albums by criteria: {}", criteria);
        return ResponseEntity.ok().body(albumQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /albums/:id} : get the "id" album.
     *
     * @param id the id of the albumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the albumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable Long id) {
        log.debug("REST request to get Album : {}", id);
        Optional<AlbumDTO> albumDTO = albumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(albumDTO);
    }

    /**
     * {@code DELETE  /albums/:id} : delete the "id" album.
     *
     * @param id the id of the albumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        log.debug("REST request to delete Album : {}", id);
        albumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
