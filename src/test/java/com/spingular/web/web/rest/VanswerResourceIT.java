package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Vanswer;
import com.spingular.web.domain.Vthumb;
import com.spingular.web.domain.Appuser;
import com.spingular.web.domain.Vquestion;
import com.spingular.web.repository.VanswerRepository;
import com.spingular.web.service.VanswerService;
import com.spingular.web.service.dto.VanswerDTO;
import com.spingular.web.service.mapper.VanswerMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.VanswerCriteria;
import com.spingular.web.service.VanswerQueryService;

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
 * Integration tests for the {@link VanswerResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class VanswerResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_URL_VANSWER = "AAAAAAAAAA";
    private static final String UPDATED_URL_VANSWER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCEPTED = false;
    private static final Boolean UPDATED_ACCEPTED = true;

    @Autowired
    private VanswerRepository vanswerRepository;

    @Autowired
    private VanswerMapper vanswerMapper;

    @Autowired
    private VanswerService vanswerService;

    @Autowired
    private VanswerQueryService vanswerQueryService;

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

    private MockMvc restVanswerMockMvc;

    private Vanswer vanswer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VanswerResource vanswerResource = new VanswerResource(vanswerService, vanswerQueryService);
        this.restVanswerMockMvc = MockMvcBuilders.standaloneSetup(vanswerResource)
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
    public static Vanswer createEntity(EntityManager em) {
        Vanswer vanswer = new Vanswer()
            .creationDate(DEFAULT_CREATION_DATE)
            .urlVanswer(DEFAULT_URL_VANSWER)
            .accepted(DEFAULT_ACCEPTED);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        vanswer.setAppuser(appuser);
        // Add required entity
        Vquestion vquestion;
        if (TestUtil.findAll(em, Vquestion.class).isEmpty()) {
            vquestion = VquestionResourceIT.createEntity(em);
            em.persist(vquestion);
            em.flush();
        } else {
            vquestion = TestUtil.findAll(em, Vquestion.class).get(0);
        }
        vanswer.setVquestion(vquestion);
        return vanswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vanswer createUpdatedEntity(EntityManager em) {
        Vanswer vanswer = new Vanswer()
            .creationDate(UPDATED_CREATION_DATE)
            .urlVanswer(UPDATED_URL_VANSWER)
            .accepted(UPDATED_ACCEPTED);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        vanswer.setAppuser(appuser);
        // Add required entity
        Vquestion vquestion;
        if (TestUtil.findAll(em, Vquestion.class).isEmpty()) {
            vquestion = VquestionResourceIT.createUpdatedEntity(em);
            em.persist(vquestion);
            em.flush();
        } else {
            vquestion = TestUtil.findAll(em, Vquestion.class).get(0);
        }
        vanswer.setVquestion(vquestion);
        return vanswer;
    }

    @BeforeEach
    public void initTest() {
        vanswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createVanswer() throws Exception {
        int databaseSizeBeforeCreate = vanswerRepository.findAll().size();

        // Create the Vanswer
        VanswerDTO vanswerDTO = vanswerMapper.toDto(vanswer);
        restVanswerMockMvc.perform(post("/api/vanswers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vanswerDTO)))
            .andExpect(status().isCreated());

        // Validate the Vanswer in the database
        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeCreate + 1);
        Vanswer testVanswer = vanswerList.get(vanswerList.size() - 1);
        assertThat(testVanswer.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testVanswer.getUrlVanswer()).isEqualTo(DEFAULT_URL_VANSWER);
        assertThat(testVanswer.isAccepted()).isEqualTo(DEFAULT_ACCEPTED);
    }

    @Test
    @Transactional
    public void createVanswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vanswerRepository.findAll().size();

        // Create the Vanswer with an existing ID
        vanswer.setId(1L);
        VanswerDTO vanswerDTO = vanswerMapper.toDto(vanswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVanswerMockMvc.perform(post("/api/vanswers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vanswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vanswer in the database
        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vanswerRepository.findAll().size();
        // set the field null
        vanswer.setCreationDate(null);

        // Create the Vanswer, which fails.
        VanswerDTO vanswerDTO = vanswerMapper.toDto(vanswer);

        restVanswerMockMvc.perform(post("/api/vanswers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vanswerDTO)))
            .andExpect(status().isBadRequest());

        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlVanswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = vanswerRepository.findAll().size();
        // set the field null
        vanswer.setUrlVanswer(null);

        // Create the Vanswer, which fails.
        VanswerDTO vanswerDTO = vanswerMapper.toDto(vanswer);

        restVanswerMockMvc.perform(post("/api/vanswers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vanswerDTO)))
            .andExpect(status().isBadRequest());

        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVanswers() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList
        restVanswerMockMvc.perform(get("/api/vanswers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vanswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].urlVanswer").value(hasItem(DEFAULT_URL_VANSWER)))
            .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getVanswer() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get the vanswer
        restVanswerMockMvc.perform(get("/api/vanswers/{id}", vanswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vanswer.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.urlVanswer").value(DEFAULT_URL_VANSWER))
            .andExpect(jsonPath("$.accepted").value(DEFAULT_ACCEPTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllVanswersByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where creationDate equals to DEFAULT_CREATION_DATE
        defaultVanswerShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the vanswerList where creationDate equals to UPDATED_CREATION_DATE
        defaultVanswerShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllVanswersByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultVanswerShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the vanswerList where creationDate not equals to UPDATED_CREATION_DATE
        defaultVanswerShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllVanswersByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultVanswerShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the vanswerList where creationDate equals to UPDATED_CREATION_DATE
        defaultVanswerShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllVanswersByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where creationDate is not null
        defaultVanswerShouldBeFound("creationDate.specified=true");

        // Get all the vanswerList where creationDate is null
        defaultVanswerShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVanswersByUrlVanswerIsEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where urlVanswer equals to DEFAULT_URL_VANSWER
        defaultVanswerShouldBeFound("urlVanswer.equals=" + DEFAULT_URL_VANSWER);

        // Get all the vanswerList where urlVanswer equals to UPDATED_URL_VANSWER
        defaultVanswerShouldNotBeFound("urlVanswer.equals=" + UPDATED_URL_VANSWER);
    }

    @Test
    @Transactional
    public void getAllVanswersByUrlVanswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where urlVanswer not equals to DEFAULT_URL_VANSWER
        defaultVanswerShouldNotBeFound("urlVanswer.notEquals=" + DEFAULT_URL_VANSWER);

        // Get all the vanswerList where urlVanswer not equals to UPDATED_URL_VANSWER
        defaultVanswerShouldBeFound("urlVanswer.notEquals=" + UPDATED_URL_VANSWER);
    }

    @Test
    @Transactional
    public void getAllVanswersByUrlVanswerIsInShouldWork() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where urlVanswer in DEFAULT_URL_VANSWER or UPDATED_URL_VANSWER
        defaultVanswerShouldBeFound("urlVanswer.in=" + DEFAULT_URL_VANSWER + "," + UPDATED_URL_VANSWER);

        // Get all the vanswerList where urlVanswer equals to UPDATED_URL_VANSWER
        defaultVanswerShouldNotBeFound("urlVanswer.in=" + UPDATED_URL_VANSWER);
    }

    @Test
    @Transactional
    public void getAllVanswersByUrlVanswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where urlVanswer is not null
        defaultVanswerShouldBeFound("urlVanswer.specified=true");

        // Get all the vanswerList where urlVanswer is null
        defaultVanswerShouldNotBeFound("urlVanswer.specified=false");
    }
                @Test
    @Transactional
    public void getAllVanswersByUrlVanswerContainsSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where urlVanswer contains DEFAULT_URL_VANSWER
        defaultVanswerShouldBeFound("urlVanswer.contains=" + DEFAULT_URL_VANSWER);

        // Get all the vanswerList where urlVanswer contains UPDATED_URL_VANSWER
        defaultVanswerShouldNotBeFound("urlVanswer.contains=" + UPDATED_URL_VANSWER);
    }

    @Test
    @Transactional
    public void getAllVanswersByUrlVanswerNotContainsSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where urlVanswer does not contain DEFAULT_URL_VANSWER
        defaultVanswerShouldNotBeFound("urlVanswer.doesNotContain=" + DEFAULT_URL_VANSWER);

        // Get all the vanswerList where urlVanswer does not contain UPDATED_URL_VANSWER
        defaultVanswerShouldBeFound("urlVanswer.doesNotContain=" + UPDATED_URL_VANSWER);
    }


    @Test
    @Transactional
    public void getAllVanswersByAcceptedIsEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where accepted equals to DEFAULT_ACCEPTED
        defaultVanswerShouldBeFound("accepted.equals=" + DEFAULT_ACCEPTED);

        // Get all the vanswerList where accepted equals to UPDATED_ACCEPTED
        defaultVanswerShouldNotBeFound("accepted.equals=" + UPDATED_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllVanswersByAcceptedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where accepted not equals to DEFAULT_ACCEPTED
        defaultVanswerShouldNotBeFound("accepted.notEquals=" + DEFAULT_ACCEPTED);

        // Get all the vanswerList where accepted not equals to UPDATED_ACCEPTED
        defaultVanswerShouldBeFound("accepted.notEquals=" + UPDATED_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllVanswersByAcceptedIsInShouldWork() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where accepted in DEFAULT_ACCEPTED or UPDATED_ACCEPTED
        defaultVanswerShouldBeFound("accepted.in=" + DEFAULT_ACCEPTED + "," + UPDATED_ACCEPTED);

        // Get all the vanswerList where accepted equals to UPDATED_ACCEPTED
        defaultVanswerShouldNotBeFound("accepted.in=" + UPDATED_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllVanswersByAcceptedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        // Get all the vanswerList where accepted is not null
        defaultVanswerShouldBeFound("accepted.specified=true");

        // Get all the vanswerList where accepted is null
        defaultVanswerShouldNotBeFound("accepted.specified=false");
    }

    @Test
    @Transactional
    public void getAllVanswersByVthumbIsEqualToSomething() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);
        Vthumb vthumb = VthumbResourceIT.createEntity(em);
        em.persist(vthumb);
        em.flush();
        vanswer.addVthumb(vthumb);
        vanswerRepository.saveAndFlush(vanswer);
        Long vthumbId = vthumb.getId();

        // Get all the vanswerList where vthumb equals to vthumbId
        defaultVanswerShouldBeFound("vthumbId.equals=" + vthumbId);

        // Get all the vanswerList where vthumb equals to vthumbId + 1
        defaultVanswerShouldNotBeFound("vthumbId.equals=" + (vthumbId + 1));
    }


    @Test
    @Transactional
    public void getAllVanswersByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = vanswer.getAppuser();
        vanswerRepository.saveAndFlush(vanswer);
        Long appuserId = appuser.getId();

        // Get all the vanswerList where appuser equals to appuserId
        defaultVanswerShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the vanswerList where appuser equals to appuserId + 1
        defaultVanswerShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }


    @Test
    @Transactional
    public void getAllVanswersByVquestionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Vquestion vquestion = vanswer.getVquestion();
        vanswerRepository.saveAndFlush(vanswer);
        Long vquestionId = vquestion.getId();

        // Get all the vanswerList where vquestion equals to vquestionId
        defaultVanswerShouldBeFound("vquestionId.equals=" + vquestionId);

        // Get all the vanswerList where vquestion equals to vquestionId + 1
        defaultVanswerShouldNotBeFound("vquestionId.equals=" + (vquestionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVanswerShouldBeFound(String filter) throws Exception {
        restVanswerMockMvc.perform(get("/api/vanswers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vanswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].urlVanswer").value(hasItem(DEFAULT_URL_VANSWER)))
            .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())));

        // Check, that the count call also returns 1
        restVanswerMockMvc.perform(get("/api/vanswers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVanswerShouldNotBeFound(String filter) throws Exception {
        restVanswerMockMvc.perform(get("/api/vanswers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVanswerMockMvc.perform(get("/api/vanswers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVanswer() throws Exception {
        // Get the vanswer
        restVanswerMockMvc.perform(get("/api/vanswers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVanswer() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        int databaseSizeBeforeUpdate = vanswerRepository.findAll().size();

        // Update the vanswer
        Vanswer updatedVanswer = vanswerRepository.findById(vanswer.getId()).get();
        // Disconnect from session so that the updates on updatedVanswer are not directly saved in db
        em.detach(updatedVanswer);
        updatedVanswer
            .creationDate(UPDATED_CREATION_DATE)
            .urlVanswer(UPDATED_URL_VANSWER)
            .accepted(UPDATED_ACCEPTED);
        VanswerDTO vanswerDTO = vanswerMapper.toDto(updatedVanswer);

        restVanswerMockMvc.perform(put("/api/vanswers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vanswerDTO)))
            .andExpect(status().isOk());

        // Validate the Vanswer in the database
        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeUpdate);
        Vanswer testVanswer = vanswerList.get(vanswerList.size() - 1);
        assertThat(testVanswer.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testVanswer.getUrlVanswer()).isEqualTo(UPDATED_URL_VANSWER);
        assertThat(testVanswer.isAccepted()).isEqualTo(UPDATED_ACCEPTED);
    }

    @Test
    @Transactional
    public void updateNonExistingVanswer() throws Exception {
        int databaseSizeBeforeUpdate = vanswerRepository.findAll().size();

        // Create the Vanswer
        VanswerDTO vanswerDTO = vanswerMapper.toDto(vanswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVanswerMockMvc.perform(put("/api/vanswers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vanswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vanswer in the database
        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVanswer() throws Exception {
        // Initialize the database
        vanswerRepository.saveAndFlush(vanswer);

        int databaseSizeBeforeDelete = vanswerRepository.findAll().size();

        // Delete the vanswer
        restVanswerMockMvc.perform(delete("/api/vanswers/{id}", vanswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vanswer> vanswerList = vanswerRepository.findAll();
        assertThat(vanswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vanswer.class);
        Vanswer vanswer1 = new Vanswer();
        vanswer1.setId(1L);
        Vanswer vanswer2 = new Vanswer();
        vanswer2.setId(vanswer1.getId());
        assertThat(vanswer1).isEqualTo(vanswer2);
        vanswer2.setId(2L);
        assertThat(vanswer1).isNotEqualTo(vanswer2);
        vanswer1.setId(null);
        assertThat(vanswer1).isNotEqualTo(vanswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VanswerDTO.class);
        VanswerDTO vanswerDTO1 = new VanswerDTO();
        vanswerDTO1.setId(1L);
        VanswerDTO vanswerDTO2 = new VanswerDTO();
        assertThat(vanswerDTO1).isNotEqualTo(vanswerDTO2);
        vanswerDTO2.setId(vanswerDTO1.getId());
        assertThat(vanswerDTO1).isEqualTo(vanswerDTO2);
        vanswerDTO2.setId(2L);
        assertThat(vanswerDTO1).isNotEqualTo(vanswerDTO2);
        vanswerDTO1.setId(null);
        assertThat(vanswerDTO1).isNotEqualTo(vanswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vanswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vanswerMapper.fromId(null)).isNull();
    }
}
