package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Album;
import com.spingular.web.domain.Photo;
import com.spingular.web.domain.Appuser;
import com.spingular.web.repository.AlbumRepository;
import com.spingular.web.service.AlbumService;
import com.spingular.web.service.dto.AlbumDTO;
import com.spingular.web.service.mapper.AlbumMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.AlbumCriteria;
import com.spingular.web.service.AlbumQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.spingular.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlbumResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class AlbumResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumQueryService albumQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAlbumMockMvc;

    private Album album;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlbumResource albumResource = new AlbumResource(albumService, albumQueryService);
        this.restAlbumMockMvc = MockMvcBuilders.standaloneSetup(albumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .creationDate(DEFAULT_CREATION_DATE)
            .title(DEFAULT_TITLE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        album.setAppuser(appuser);
        return album;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createUpdatedEntity(EntityManager em) {
        Album album = new Album()
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        album.setAppuser(appuser);
        return album;
    }

    @BeforeEach
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAlbum.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setCreationDate(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);

        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setTitle(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);

        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    public void getAllAlbumsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where creationDate equals to DEFAULT_CREATION_DATE
        defaultAlbumShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the albumList where creationDate equals to UPDATED_CREATION_DATE
        defaultAlbumShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultAlbumShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the albumList where creationDate not equals to UPDATED_CREATION_DATE
        defaultAlbumShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultAlbumShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the albumList where creationDate equals to UPDATED_CREATION_DATE
        defaultAlbumShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where creationDate is not null
        defaultAlbumShouldBeFound("creationDate.specified=true");

        // Get all the albumList where creationDate is null
        defaultAlbumShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlbumsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where title equals to DEFAULT_TITLE
        defaultAlbumShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the albumList where title equals to UPDATED_TITLE
        defaultAlbumShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where title not equals to DEFAULT_TITLE
        defaultAlbumShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the albumList where title not equals to UPDATED_TITLE
        defaultAlbumShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultAlbumShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the albumList where title equals to UPDATED_TITLE
        defaultAlbumShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where title is not null
        defaultAlbumShouldBeFound("title.specified=true");

        // Get all the albumList where title is null
        defaultAlbumShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByTitleContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where title contains DEFAULT_TITLE
        defaultAlbumShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the albumList where title contains UPDATED_TITLE
        defaultAlbumShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where title does not contain DEFAULT_TITLE
        defaultAlbumShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the albumList where title does not contain UPDATED_TITLE
        defaultAlbumShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllAlbumsByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        Photo photo = PhotoResourceIT.createEntity(em);
        em.persist(photo);
        em.flush();
        album.addPhoto(photo);
        albumRepository.saveAndFlush(album);
        Long photoId = photo.getId();

        // Get all the albumList where photo equals to photoId
        defaultAlbumShouldBeFound("photoId.equals=" + photoId);

        // Get all the albumList where photo equals to photoId + 1
        defaultAlbumShouldNotBeFound("photoId.equals=" + (photoId + 1));
    }


    @Test
    @Transactional
    public void getAllAlbumsByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = album.getAppuser();
        albumRepository.saveAndFlush(album);
        Long appuserId = appuser.getId();

        // Get all the albumList where appuser equals to appuserId
        defaultAlbumShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the albumList where appuser equals to appuserId + 1
        defaultAlbumShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlbumShouldBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlbumShouldNotBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findById(album.getId()).get();
        // Disconnect from session so that the updates on updatedAlbum are not directly saved in db
        em.detach(updatedAlbum);
        updatedAlbum
            .creationDate(UPDATED_CREATION_DATE)
            .title(UPDATED_TITLE);
        AlbumDTO albumDTO = albumMapper.toDto(updatedAlbum);

        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAlbum.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Delete the album
        restAlbumMockMvc.perform(delete("/api/albums/{id}", album.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Album.class);
        Album album1 = new Album();
        album1.setId(1L);
        Album album2 = new Album();
        album2.setId(album1.getId());
        assertThat(album1).isEqualTo(album2);
        album2.setId(2L);
        assertThat(album1).isNotEqualTo(album2);
        album1.setId(null);
        assertThat(album1).isNotEqualTo(album2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlbumDTO.class);
        AlbumDTO albumDTO1 = new AlbumDTO();
        albumDTO1.setId(1L);
        AlbumDTO albumDTO2 = new AlbumDTO();
        assertThat(albumDTO1).isNotEqualTo(albumDTO2);
        albumDTO2.setId(albumDTO1.getId());
        assertThat(albumDTO1).isEqualTo(albumDTO2);
        albumDTO2.setId(2L);
        assertThat(albumDTO1).isNotEqualTo(albumDTO2);
        albumDTO1.setId(null);
        assertThat(albumDTO1).isNotEqualTo(albumDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(albumMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(albumMapper.fromId(null)).isNull();
    }
}
