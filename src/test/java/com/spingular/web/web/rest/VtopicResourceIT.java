package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Vtopic;
import com.spingular.web.domain.Vquestion;
import com.spingular.web.domain.Appuser;
import com.spingular.web.repository.VtopicRepository;
import com.spingular.web.service.VtopicService;
import com.spingular.web.service.dto.VtopicDTO;
import com.spingular.web.service.mapper.VtopicMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.VtopicCriteria;
import com.spingular.web.service.VtopicQueryService;

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
 * Integration tests for the {@link VtopicResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class VtopicResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VTOPIC_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_VTOPIC_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_VTOPIC_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_VTOPIC_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private VtopicRepository vtopicRepository;

    @Autowired
    private VtopicMapper vtopicMapper;

    @Autowired
    private VtopicService vtopicService;

    @Autowired
    private VtopicQueryService vtopicQueryService;

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

    private MockMvc restVtopicMockMvc;

    private Vtopic vtopic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VtopicResource vtopicResource = new VtopicResource(vtopicService, vtopicQueryService);
        this.restVtopicMockMvc = MockMvcBuilders.standaloneSetup(vtopicResource)
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
    public static Vtopic createEntity(EntityManager em) {
        Vtopic vtopic = new Vtopic()
            .creationDate(DEFAULT_CREATION_DATE)
            .vtopicTitle(DEFAULT_VTOPIC_TITLE)
            .vtopicDescription(DEFAULT_VTOPIC_DESCRIPTION);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        vtopic.setAppuser(appuser);
        return vtopic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vtopic createUpdatedEntity(EntityManager em) {
        Vtopic vtopic = new Vtopic()
            .creationDate(UPDATED_CREATION_DATE)
            .vtopicTitle(UPDATED_VTOPIC_TITLE)
            .vtopicDescription(UPDATED_VTOPIC_DESCRIPTION);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        vtopic.setAppuser(appuser);
        return vtopic;
    }

    @BeforeEach
    public void initTest() {
        vtopic = createEntity(em);
    }

    @Test
    @Transactional
    public void createVtopic() throws Exception {
        int databaseSizeBeforeCreate = vtopicRepository.findAll().size();

        // Create the Vtopic
        VtopicDTO vtopicDTO = vtopicMapper.toDto(vtopic);
        restVtopicMockMvc.perform(post("/api/vtopics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vtopicDTO)))
            .andExpect(status().isCreated());

        // Validate the Vtopic in the database
        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeCreate + 1);
        Vtopic testVtopic = vtopicList.get(vtopicList.size() - 1);
        assertThat(testVtopic.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testVtopic.getVtopicTitle()).isEqualTo(DEFAULT_VTOPIC_TITLE);
        assertThat(testVtopic.getVtopicDescription()).isEqualTo(DEFAULT_VTOPIC_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createVtopicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vtopicRepository.findAll().size();

        // Create the Vtopic with an existing ID
        vtopic.setId(1L);
        VtopicDTO vtopicDTO = vtopicMapper.toDto(vtopic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVtopicMockMvc.perform(post("/api/vtopics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vtopicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vtopic in the database
        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vtopicRepository.findAll().size();
        // set the field null
        vtopic.setCreationDate(null);

        // Create the Vtopic, which fails.
        VtopicDTO vtopicDTO = vtopicMapper.toDto(vtopic);

        restVtopicMockMvc.perform(post("/api/vtopics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vtopicDTO)))
            .andExpect(status().isBadRequest());

        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVtopicTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = vtopicRepository.findAll().size();
        // set the field null
        vtopic.setVtopicTitle(null);

        // Create the Vtopic, which fails.
        VtopicDTO vtopicDTO = vtopicMapper.toDto(vtopic);

        restVtopicMockMvc.perform(post("/api/vtopics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vtopicDTO)))
            .andExpect(status().isBadRequest());

        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVtopics() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList
        restVtopicMockMvc.perform(get("/api/vtopics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vtopic.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].vtopicTitle").value(hasItem(DEFAULT_VTOPIC_TITLE)))
            .andExpect(jsonPath("$.[*].vtopicDescription").value(hasItem(DEFAULT_VTOPIC_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getVtopic() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get the vtopic
        restVtopicMockMvc.perform(get("/api/vtopics/{id}", vtopic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vtopic.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.vtopicTitle").value(DEFAULT_VTOPIC_TITLE))
            .andExpect(jsonPath("$.vtopicDescription").value(DEFAULT_VTOPIC_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getAllVtopicsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where creationDate equals to DEFAULT_CREATION_DATE
        defaultVtopicShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the vtopicList where creationDate equals to UPDATED_CREATION_DATE
        defaultVtopicShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultVtopicShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the vtopicList where creationDate not equals to UPDATED_CREATION_DATE
        defaultVtopicShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultVtopicShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the vtopicList where creationDate equals to UPDATED_CREATION_DATE
        defaultVtopicShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where creationDate is not null
        defaultVtopicShouldBeFound("creationDate.specified=true");

        // Get all the vtopicList where creationDate is null
        defaultVtopicShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicTitle equals to DEFAULT_VTOPIC_TITLE
        defaultVtopicShouldBeFound("vtopicTitle.equals=" + DEFAULT_VTOPIC_TITLE);

        // Get all the vtopicList where vtopicTitle equals to UPDATED_VTOPIC_TITLE
        defaultVtopicShouldNotBeFound("vtopicTitle.equals=" + UPDATED_VTOPIC_TITLE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicTitle not equals to DEFAULT_VTOPIC_TITLE
        defaultVtopicShouldNotBeFound("vtopicTitle.notEquals=" + DEFAULT_VTOPIC_TITLE);

        // Get all the vtopicList where vtopicTitle not equals to UPDATED_VTOPIC_TITLE
        defaultVtopicShouldBeFound("vtopicTitle.notEquals=" + UPDATED_VTOPIC_TITLE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicTitleIsInShouldWork() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicTitle in DEFAULT_VTOPIC_TITLE or UPDATED_VTOPIC_TITLE
        defaultVtopicShouldBeFound("vtopicTitle.in=" + DEFAULT_VTOPIC_TITLE + "," + UPDATED_VTOPIC_TITLE);

        // Get all the vtopicList where vtopicTitle equals to UPDATED_VTOPIC_TITLE
        defaultVtopicShouldNotBeFound("vtopicTitle.in=" + UPDATED_VTOPIC_TITLE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicTitle is not null
        defaultVtopicShouldBeFound("vtopicTitle.specified=true");

        // Get all the vtopicList where vtopicTitle is null
        defaultVtopicShouldNotBeFound("vtopicTitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllVtopicsByVtopicTitleContainsSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicTitle contains DEFAULT_VTOPIC_TITLE
        defaultVtopicShouldBeFound("vtopicTitle.contains=" + DEFAULT_VTOPIC_TITLE);

        // Get all the vtopicList where vtopicTitle contains UPDATED_VTOPIC_TITLE
        defaultVtopicShouldNotBeFound("vtopicTitle.contains=" + UPDATED_VTOPIC_TITLE);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicTitleNotContainsSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicTitle does not contain DEFAULT_VTOPIC_TITLE
        defaultVtopicShouldNotBeFound("vtopicTitle.doesNotContain=" + DEFAULT_VTOPIC_TITLE);

        // Get all the vtopicList where vtopicTitle does not contain UPDATED_VTOPIC_TITLE
        defaultVtopicShouldBeFound("vtopicTitle.doesNotContain=" + UPDATED_VTOPIC_TITLE);
    }


    @Test
    @Transactional
    public void getAllVtopicsByVtopicDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicDescription equals to DEFAULT_VTOPIC_DESCRIPTION
        defaultVtopicShouldBeFound("vtopicDescription.equals=" + DEFAULT_VTOPIC_DESCRIPTION);

        // Get all the vtopicList where vtopicDescription equals to UPDATED_VTOPIC_DESCRIPTION
        defaultVtopicShouldNotBeFound("vtopicDescription.equals=" + UPDATED_VTOPIC_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicDescription not equals to DEFAULT_VTOPIC_DESCRIPTION
        defaultVtopicShouldNotBeFound("vtopicDescription.notEquals=" + DEFAULT_VTOPIC_DESCRIPTION);

        // Get all the vtopicList where vtopicDescription not equals to UPDATED_VTOPIC_DESCRIPTION
        defaultVtopicShouldBeFound("vtopicDescription.notEquals=" + UPDATED_VTOPIC_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicDescription in DEFAULT_VTOPIC_DESCRIPTION or UPDATED_VTOPIC_DESCRIPTION
        defaultVtopicShouldBeFound("vtopicDescription.in=" + DEFAULT_VTOPIC_DESCRIPTION + "," + UPDATED_VTOPIC_DESCRIPTION);

        // Get all the vtopicList where vtopicDescription equals to UPDATED_VTOPIC_DESCRIPTION
        defaultVtopicShouldNotBeFound("vtopicDescription.in=" + UPDATED_VTOPIC_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicDescription is not null
        defaultVtopicShouldBeFound("vtopicDescription.specified=true");

        // Get all the vtopicList where vtopicDescription is null
        defaultVtopicShouldNotBeFound("vtopicDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllVtopicsByVtopicDescriptionContainsSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicDescription contains DEFAULT_VTOPIC_DESCRIPTION
        defaultVtopicShouldBeFound("vtopicDescription.contains=" + DEFAULT_VTOPIC_DESCRIPTION);

        // Get all the vtopicList where vtopicDescription contains UPDATED_VTOPIC_DESCRIPTION
        defaultVtopicShouldNotBeFound("vtopicDescription.contains=" + UPDATED_VTOPIC_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVtopicsByVtopicDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        // Get all the vtopicList where vtopicDescription does not contain DEFAULT_VTOPIC_DESCRIPTION
        defaultVtopicShouldNotBeFound("vtopicDescription.doesNotContain=" + DEFAULT_VTOPIC_DESCRIPTION);

        // Get all the vtopicList where vtopicDescription does not contain UPDATED_VTOPIC_DESCRIPTION
        defaultVtopicShouldBeFound("vtopicDescription.doesNotContain=" + UPDATED_VTOPIC_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllVtopicsByVquestionIsEqualToSomething() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);
        Vquestion vquestion = VquestionResourceIT.createEntity(em);
        em.persist(vquestion);
        em.flush();
        vtopic.addVquestion(vquestion);
        vtopicRepository.saveAndFlush(vtopic);
        Long vquestionId = vquestion.getId();

        // Get all the vtopicList where vquestion equals to vquestionId
        defaultVtopicShouldBeFound("vquestionId.equals=" + vquestionId);

        // Get all the vtopicList where vquestion equals to vquestionId + 1
        defaultVtopicShouldNotBeFound("vquestionId.equals=" + (vquestionId + 1));
    }


    @Test
    @Transactional
    public void getAllVtopicsByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = vtopic.getAppuser();
        vtopicRepository.saveAndFlush(vtopic);
        Long appuserId = appuser.getId();

        // Get all the vtopicList where appuser equals to appuserId
        defaultVtopicShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the vtopicList where appuser equals to appuserId + 1
        defaultVtopicShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVtopicShouldBeFound(String filter) throws Exception {
        restVtopicMockMvc.perform(get("/api/vtopics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vtopic.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].vtopicTitle").value(hasItem(DEFAULT_VTOPIC_TITLE)))
            .andExpect(jsonPath("$.[*].vtopicDescription").value(hasItem(DEFAULT_VTOPIC_DESCRIPTION)));

        // Check, that the count call also returns 1
        restVtopicMockMvc.perform(get("/api/vtopics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVtopicShouldNotBeFound(String filter) throws Exception {
        restVtopicMockMvc.perform(get("/api/vtopics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVtopicMockMvc.perform(get("/api/vtopics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVtopic() throws Exception {
        // Get the vtopic
        restVtopicMockMvc.perform(get("/api/vtopics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVtopic() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        int databaseSizeBeforeUpdate = vtopicRepository.findAll().size();

        // Update the vtopic
        Vtopic updatedVtopic = vtopicRepository.findById(vtopic.getId()).get();
        // Disconnect from session so that the updates on updatedVtopic are not directly saved in db
        em.detach(updatedVtopic);
        updatedVtopic
            .creationDate(UPDATED_CREATION_DATE)
            .vtopicTitle(UPDATED_VTOPIC_TITLE)
            .vtopicDescription(UPDATED_VTOPIC_DESCRIPTION);
        VtopicDTO vtopicDTO = vtopicMapper.toDto(updatedVtopic);

        restVtopicMockMvc.perform(put("/api/vtopics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vtopicDTO)))
            .andExpect(status().isOk());

        // Validate the Vtopic in the database
        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeUpdate);
        Vtopic testVtopic = vtopicList.get(vtopicList.size() - 1);
        assertThat(testVtopic.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testVtopic.getVtopicTitle()).isEqualTo(UPDATED_VTOPIC_TITLE);
        assertThat(testVtopic.getVtopicDescription()).isEqualTo(UPDATED_VTOPIC_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingVtopic() throws Exception {
        int databaseSizeBeforeUpdate = vtopicRepository.findAll().size();

        // Create the Vtopic
        VtopicDTO vtopicDTO = vtopicMapper.toDto(vtopic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVtopicMockMvc.perform(put("/api/vtopics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vtopicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vtopic in the database
        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVtopic() throws Exception {
        // Initialize the database
        vtopicRepository.saveAndFlush(vtopic);

        int databaseSizeBeforeDelete = vtopicRepository.findAll().size();

        // Delete the vtopic
        restVtopicMockMvc.perform(delete("/api/vtopics/{id}", vtopic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vtopic> vtopicList = vtopicRepository.findAll();
        assertThat(vtopicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vtopic.class);
        Vtopic vtopic1 = new Vtopic();
        vtopic1.setId(1L);
        Vtopic vtopic2 = new Vtopic();
        vtopic2.setId(vtopic1.getId());
        assertThat(vtopic1).isEqualTo(vtopic2);
        vtopic2.setId(2L);
        assertThat(vtopic1).isNotEqualTo(vtopic2);
        vtopic1.setId(null);
        assertThat(vtopic1).isNotEqualTo(vtopic2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VtopicDTO.class);
        VtopicDTO vtopicDTO1 = new VtopicDTO();
        vtopicDTO1.setId(1L);
        VtopicDTO vtopicDTO2 = new VtopicDTO();
        assertThat(vtopicDTO1).isNotEqualTo(vtopicDTO2);
        vtopicDTO2.setId(vtopicDTO1.getId());
        assertThat(vtopicDTO1).isEqualTo(vtopicDTO2);
        vtopicDTO2.setId(2L);
        assertThat(vtopicDTO1).isNotEqualTo(vtopicDTO2);
        vtopicDTO1.setId(null);
        assertThat(vtopicDTO1).isNotEqualTo(vtopicDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vtopicMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vtopicMapper.fromId(null)).isNull();
    }
}
