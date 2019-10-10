package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Appphoto;
import com.spingular.web.domain.Appuser;
import com.spingular.web.repository.AppphotoRepository;
import com.spingular.web.service.AppphotoService;
import com.spingular.web.service.dto.AppphotoDTO;
import com.spingular.web.service.mapper.AppphotoMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.AppphotoCriteria;
import com.spingular.web.service.AppphotoQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link AppphotoResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class AppphotoResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private AppphotoRepository appphotoRepository;

    @Autowired
    private AppphotoMapper appphotoMapper;

    @Autowired
    private AppphotoService appphotoService;

    @Autowired
    private AppphotoQueryService appphotoQueryService;

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

    private MockMvc restAppphotoMockMvc;

    private Appphoto appphoto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppphotoResource appphotoResource = new AppphotoResource(appphotoService, appphotoQueryService);
        this.restAppphotoMockMvc = MockMvcBuilders.standaloneSetup(appphotoResource)
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
    public static Appphoto createEntity(EntityManager em) {
        Appphoto appphoto = new Appphoto()
            .creationDate(DEFAULT_CREATION_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        appphoto.setAppuser(appuser);
        return appphoto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appphoto createUpdatedEntity(EntityManager em) {
        Appphoto appphoto = new Appphoto()
            .creationDate(UPDATED_CREATION_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        appphoto.setAppuser(appuser);
        return appphoto;
    }

    @BeforeEach
    public void initTest() {
        appphoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppphoto() throws Exception {
        int databaseSizeBeforeCreate = appphotoRepository.findAll().size();

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);
        restAppphotoMockMvc.perform(post("/api/appphotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appphotoDTO)))
            .andExpect(status().isCreated());

        // Validate the Appphoto in the database
        List<Appphoto> appphotoList = appphotoRepository.findAll();
        assertThat(appphotoList).hasSize(databaseSizeBeforeCreate + 1);
        Appphoto testAppphoto = appphotoList.get(appphotoList.size() - 1);
        assertThat(testAppphoto.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAppphoto.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAppphoto.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAppphotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appphotoRepository.findAll().size();

        // Create the Appphoto with an existing ID
        appphoto.setId(1L);
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppphotoMockMvc.perform(post("/api/appphotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appphotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        List<Appphoto> appphotoList = appphotoRepository.findAll();
        assertThat(appphotoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appphotoRepository.findAll().size();
        // set the field null
        appphoto.setCreationDate(null);

        // Create the Appphoto, which fails.
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        restAppphotoMockMvc.perform(post("/api/appphotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appphotoDTO)))
            .andExpect(status().isBadRequest());

        List<Appphoto> appphotoList = appphotoRepository.findAll();
        assertThat(appphotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppphotos() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList
        restAppphotoMockMvc.perform(get("/api/appphotos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appphoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getAppphoto() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        // Get the appphoto
        restAppphotoMockMvc.perform(get("/api/appphotos/{id}", appphoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appphoto.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getAllAppphotosByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate equals to DEFAULT_CREATION_DATE
        defaultAppphotoShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the appphotoList where creationDate equals to UPDATED_CREATION_DATE
        defaultAppphotoShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppphotosByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultAppphotoShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the appphotoList where creationDate not equals to UPDATED_CREATION_DATE
        defaultAppphotoShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppphotosByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultAppphotoShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the appphotoList where creationDate equals to UPDATED_CREATION_DATE
        defaultAppphotoShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppphotosByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        // Get all the appphotoList where creationDate is not null
        defaultAppphotoShouldBeFound("creationDate.specified=true");

        // Get all the appphotoList where creationDate is null
        defaultAppphotoShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppphotosByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = appphoto.getAppuser();
        appphotoRepository.saveAndFlush(appphoto);
        Long appuserId = appuser.getId();

        // Get all the appphotoList where appuser equals to appuserId
        defaultAppphotoShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the appphotoList where appuser equals to appuserId + 1
        defaultAppphotoShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppphotoShouldBeFound(String filter) throws Exception {
        restAppphotoMockMvc.perform(get("/api/appphotos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appphoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restAppphotoMockMvc.perform(get("/api/appphotos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppphotoShouldNotBeFound(String filter) throws Exception {
        restAppphotoMockMvc.perform(get("/api/appphotos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppphotoMockMvc.perform(get("/api/appphotos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAppphoto() throws Exception {
        // Get the appphoto
        restAppphotoMockMvc.perform(get("/api/appphotos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppphoto() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        int databaseSizeBeforeUpdate = appphotoRepository.findAll().size();

        // Update the appphoto
        Appphoto updatedAppphoto = appphotoRepository.findById(appphoto.getId()).get();
        // Disconnect from session so that the updates on updatedAppphoto are not directly saved in db
        em.detach(updatedAppphoto);
        updatedAppphoto
            .creationDate(UPDATED_CREATION_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(updatedAppphoto);

        restAppphotoMockMvc.perform(put("/api/appphotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appphotoDTO)))
            .andExpect(status().isOk());

        // Validate the Appphoto in the database
        List<Appphoto> appphotoList = appphotoRepository.findAll();
        assertThat(appphotoList).hasSize(databaseSizeBeforeUpdate);
        Appphoto testAppphoto = appphotoList.get(appphotoList.size() - 1);
        assertThat(testAppphoto.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAppphoto.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAppphoto.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppphoto() throws Exception {
        int databaseSizeBeforeUpdate = appphotoRepository.findAll().size();

        // Create the Appphoto
        AppphotoDTO appphotoDTO = appphotoMapper.toDto(appphoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppphotoMockMvc.perform(put("/api/appphotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appphotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appphoto in the database
        List<Appphoto> appphotoList = appphotoRepository.findAll();
        assertThat(appphotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppphoto() throws Exception {
        // Initialize the database
        appphotoRepository.saveAndFlush(appphoto);

        int databaseSizeBeforeDelete = appphotoRepository.findAll().size();

        // Delete the appphoto
        restAppphotoMockMvc.perform(delete("/api/appphotos/{id}", appphoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appphoto> appphotoList = appphotoRepository.findAll();
        assertThat(appphotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appphoto.class);
        Appphoto appphoto1 = new Appphoto();
        appphoto1.setId(1L);
        Appphoto appphoto2 = new Appphoto();
        appphoto2.setId(appphoto1.getId());
        assertThat(appphoto1).isEqualTo(appphoto2);
        appphoto2.setId(2L);
        assertThat(appphoto1).isNotEqualTo(appphoto2);
        appphoto1.setId(null);
        assertThat(appphoto1).isNotEqualTo(appphoto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppphotoDTO.class);
        AppphotoDTO appphotoDTO1 = new AppphotoDTO();
        appphotoDTO1.setId(1L);
        AppphotoDTO appphotoDTO2 = new AppphotoDTO();
        assertThat(appphotoDTO1).isNotEqualTo(appphotoDTO2);
        appphotoDTO2.setId(appphotoDTO1.getId());
        assertThat(appphotoDTO1).isEqualTo(appphotoDTO2);
        appphotoDTO2.setId(2L);
        assertThat(appphotoDTO1).isNotEqualTo(appphotoDTO2);
        appphotoDTO1.setId(null);
        assertThat(appphotoDTO1).isNotEqualTo(appphotoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appphotoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appphotoMapper.fromId(null)).isNull();
    }
}
