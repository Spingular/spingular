package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Appprofile;
import com.spingular.web.domain.Appuser;
import com.spingular.web.repository.AppprofileRepository;
import com.spingular.web.service.AppprofileService;
import com.spingular.web.service.dto.AppprofileDTO;
import com.spingular.web.service.mapper.AppprofileMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.AppprofileCriteria;
import com.spingular.web.service.AppprofileQueryService;

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

import com.spingular.web.domain.enumeration.Gender;
import com.spingular.web.domain.enumeration.CivilStatus;
import com.spingular.web.domain.enumeration.Gender;
import com.spingular.web.domain.enumeration.Purpose;
import com.spingular.web.domain.enumeration.Physical;
import com.spingular.web.domain.enumeration.Religion;
import com.spingular.web.domain.enumeration.EthnicGroup;
import com.spingular.web.domain.enumeration.Studies;
import com.spingular.web.domain.enumeration.Eyes;
import com.spingular.web.domain.enumeration.Smoker;
import com.spingular.web.domain.enumeration.Children;
import com.spingular.web.domain.enumeration.FutureChildren;
/**
 * Integration tests for the {@link AppprofileResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class AppprofileResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_PLUS = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_PLUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CivilStatus DEFAULT_CIVIL_STATUS = CivilStatus.NA;
    private static final CivilStatus UPDATED_CIVIL_STATUS = CivilStatus.SINGLE;

    private static final Gender DEFAULT_LOOKING_FOR = Gender.MALE;
    private static final Gender UPDATED_LOOKING_FOR = Gender.FEMALE;

    private static final Purpose DEFAULT_PURPOSE = Purpose.NOT_INTERESTED;
    private static final Purpose UPDATED_PURPOSE = Purpose.FRIENDSHIP;

    private static final Physical DEFAULT_PHYSICAL = Physical.NA;
    private static final Physical UPDATED_PHYSICAL = Physical.THIN;

    private static final Religion DEFAULT_RELIGION = Religion.NA;
    private static final Religion UPDATED_RELIGION = Religion.ATHEIST;

    private static final EthnicGroup DEFAULT_ETHNIC_GROUP = EthnicGroup.NA;
    private static final EthnicGroup UPDATED_ETHNIC_GROUP = EthnicGroup.MIXED;

    private static final Studies DEFAULT_STUDIES = Studies.NA;
    private static final Studies UPDATED_STUDIES = Studies.PRIMARY;

    private static final Integer DEFAULT_SIBBLINGS = -1;
    private static final Integer UPDATED_SIBBLINGS = 0;
    private static final Integer SMALLER_SIBBLINGS = -1 - 1;

    private static final Eyes DEFAULT_EYES = Eyes.NA;
    private static final Eyes UPDATED_EYES = Eyes.BLUE;

    private static final Smoker DEFAULT_SMOKER = Smoker.NA;
    private static final Smoker UPDATED_SMOKER = Smoker.YES;

    private static final Children DEFAULT_CHILDREN = Children.NA;
    private static final Children UPDATED_CHILDREN = Children.YES;

    private static final FutureChildren DEFAULT_FUTURE_CHILDREN = FutureChildren.NA;
    private static final FutureChildren UPDATED_FUTURE_CHILDREN = FutureChildren.YES;

    private static final Boolean DEFAULT_PET = false;
    private static final Boolean UPDATED_PET = true;

    @Autowired
    private AppprofileRepository appprofileRepository;

    @Autowired
    private AppprofileMapper appprofileMapper;

    @Autowired
    private AppprofileService appprofileService;

    @Autowired
    private AppprofileQueryService appprofileQueryService;

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

    private MockMvc restAppprofileMockMvc;

    private Appprofile appprofile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppprofileResource appprofileResource = new AppprofileResource(appprofileService, appprofileQueryService);
        this.restAppprofileMockMvc = MockMvcBuilders.standaloneSetup(appprofileResource)
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
    public static Appprofile createEntity(EntityManager em) {
        Appprofile appprofile = new Appprofile()
            .creationDate(DEFAULT_CREATION_DATE)
            .gender(DEFAULT_GENDER)
            .phone(DEFAULT_PHONE)
            .bio(DEFAULT_BIO)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .linkedin(DEFAULT_LINKEDIN)
            .instagram(DEFAULT_INSTAGRAM)
            .googlePlus(DEFAULT_GOOGLE_PLUS)
            .birthdate(DEFAULT_BIRTHDATE)
            .civilStatus(DEFAULT_CIVIL_STATUS)
            .lookingFor(DEFAULT_LOOKING_FOR)
            .purpose(DEFAULT_PURPOSE)
            .physical(DEFAULT_PHYSICAL)
            .religion(DEFAULT_RELIGION)
            .ethnicGroup(DEFAULT_ETHNIC_GROUP)
            .studies(DEFAULT_STUDIES)
            .sibblings(DEFAULT_SIBBLINGS)
            .eyes(DEFAULT_EYES)
            .smoker(DEFAULT_SMOKER)
            .children(DEFAULT_CHILDREN)
            .futureChildren(DEFAULT_FUTURE_CHILDREN)
            .pet(DEFAULT_PET);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        appprofile.setAppuser(appuser);
        return appprofile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appprofile createUpdatedEntity(EntityManager em) {
        Appprofile appprofile = new Appprofile()
            .creationDate(UPDATED_CREATION_DATE)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .linkedin(UPDATED_LINKEDIN)
            .instagram(UPDATED_INSTAGRAM)
            .googlePlus(UPDATED_GOOGLE_PLUS)
            .birthdate(UPDATED_BIRTHDATE)
            .civilStatus(UPDATED_CIVIL_STATUS)
            .lookingFor(UPDATED_LOOKING_FOR)
            .purpose(UPDATED_PURPOSE)
            .physical(UPDATED_PHYSICAL)
            .religion(UPDATED_RELIGION)
            .ethnicGroup(UPDATED_ETHNIC_GROUP)
            .studies(UPDATED_STUDIES)
            .sibblings(UPDATED_SIBBLINGS)
            .eyes(UPDATED_EYES)
            .smoker(UPDATED_SMOKER)
            .children(UPDATED_CHILDREN)
            .futureChildren(UPDATED_FUTURE_CHILDREN)
            .pet(UPDATED_PET);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        appprofile.setAppuser(appuser);
        return appprofile;
    }

    @BeforeEach
    public void initTest() {
        appprofile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppprofile() throws Exception {
        int databaseSizeBeforeCreate = appprofileRepository.findAll().size();

        // Create the Appprofile
        AppprofileDTO appprofileDTO = appprofileMapper.toDto(appprofile);
        restAppprofileMockMvc.perform(post("/api/appprofiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appprofileDTO)))
            .andExpect(status().isCreated());

        // Validate the Appprofile in the database
        List<Appprofile> appprofileList = appprofileRepository.findAll();
        assertThat(appprofileList).hasSize(databaseSizeBeforeCreate + 1);
        Appprofile testAppprofile = appprofileList.get(appprofileList.size() - 1);
        assertThat(testAppprofile.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAppprofile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testAppprofile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAppprofile.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testAppprofile.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testAppprofile.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testAppprofile.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testAppprofile.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testAppprofile.getGooglePlus()).isEqualTo(DEFAULT_GOOGLE_PLUS);
        assertThat(testAppprofile.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testAppprofile.getCivilStatus()).isEqualTo(DEFAULT_CIVIL_STATUS);
        assertThat(testAppprofile.getLookingFor()).isEqualTo(DEFAULT_LOOKING_FOR);
        assertThat(testAppprofile.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testAppprofile.getPhysical()).isEqualTo(DEFAULT_PHYSICAL);
        assertThat(testAppprofile.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testAppprofile.getEthnicGroup()).isEqualTo(DEFAULT_ETHNIC_GROUP);
        assertThat(testAppprofile.getStudies()).isEqualTo(DEFAULT_STUDIES);
        assertThat(testAppprofile.getSibblings()).isEqualTo(DEFAULT_SIBBLINGS);
        assertThat(testAppprofile.getEyes()).isEqualTo(DEFAULT_EYES);
        assertThat(testAppprofile.getSmoker()).isEqualTo(DEFAULT_SMOKER);
        assertThat(testAppprofile.getChildren()).isEqualTo(DEFAULT_CHILDREN);
        assertThat(testAppprofile.getFutureChildren()).isEqualTo(DEFAULT_FUTURE_CHILDREN);
        assertThat(testAppprofile.isPet()).isEqualTo(DEFAULT_PET);
    }

    @Test
    @Transactional
    public void createAppprofileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appprofileRepository.findAll().size();

        // Create the Appprofile with an existing ID
        appprofile.setId(1L);
        AppprofileDTO appprofileDTO = appprofileMapper.toDto(appprofile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppprofileMockMvc.perform(post("/api/appprofiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appprofileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appprofile in the database
        List<Appprofile> appprofileList = appprofileRepository.findAll();
        assertThat(appprofileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appprofileRepository.findAll().size();
        // set the field null
        appprofile.setCreationDate(null);

        // Create the Appprofile, which fails.
        AppprofileDTO appprofileDTO = appprofileMapper.toDto(appprofile);

        restAppprofileMockMvc.perform(post("/api/appprofiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appprofileDTO)))
            .andExpect(status().isBadRequest());

        List<Appprofile> appprofileList = appprofileRepository.findAll();
        assertThat(appprofileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppprofiles() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList
        restAppprofileMockMvc.perform(get("/api/appprofiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appprofile.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].googlePlus").value(hasItem(DEFAULT_GOOGLE_PLUS)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].civilStatus").value(hasItem(DEFAULT_CIVIL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lookingFor").value(hasItem(DEFAULT_LOOKING_FOR.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].ethnicGroup").value(hasItem(DEFAULT_ETHNIC_GROUP.toString())))
            .andExpect(jsonPath("$.[*].studies").value(hasItem(DEFAULT_STUDIES.toString())))
            .andExpect(jsonPath("$.[*].sibblings").value(hasItem(DEFAULT_SIBBLINGS)))
            .andExpect(jsonPath("$.[*].eyes").value(hasItem(DEFAULT_EYES.toString())))
            .andExpect(jsonPath("$.[*].smoker").value(hasItem(DEFAULT_SMOKER.toString())))
            .andExpect(jsonPath("$.[*].children").value(hasItem(DEFAULT_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].futureChildren").value(hasItem(DEFAULT_FUTURE_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].pet").value(hasItem(DEFAULT_PET.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAppprofile() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get the appprofile
        restAppprofileMockMvc.perform(get("/api/appprofiles/{id}", appprofile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appprofile.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM))
            .andExpect(jsonPath("$.googlePlus").value(DEFAULT_GOOGLE_PLUS))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.civilStatus").value(DEFAULT_CIVIL_STATUS.toString()))
            .andExpect(jsonPath("$.lookingFor").value(DEFAULT_LOOKING_FOR.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.physical").value(DEFAULT_PHYSICAL.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.ethnicGroup").value(DEFAULT_ETHNIC_GROUP.toString()))
            .andExpect(jsonPath("$.studies").value(DEFAULT_STUDIES.toString()))
            .andExpect(jsonPath("$.sibblings").value(DEFAULT_SIBBLINGS))
            .andExpect(jsonPath("$.eyes").value(DEFAULT_EYES.toString()))
            .andExpect(jsonPath("$.smoker").value(DEFAULT_SMOKER.toString()))
            .andExpect(jsonPath("$.children").value(DEFAULT_CHILDREN.toString()))
            .andExpect(jsonPath("$.futureChildren").value(DEFAULT_FUTURE_CHILDREN.toString()))
            .andExpect(jsonPath("$.pet").value(DEFAULT_PET.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where creationDate equals to DEFAULT_CREATION_DATE
        defaultAppprofileShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the appprofileList where creationDate equals to UPDATED_CREATION_DATE
        defaultAppprofileShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultAppprofileShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the appprofileList where creationDate not equals to UPDATED_CREATION_DATE
        defaultAppprofileShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultAppprofileShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the appprofileList where creationDate equals to UPDATED_CREATION_DATE
        defaultAppprofileShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where creationDate is not null
        defaultAppprofileShouldBeFound("creationDate.specified=true");

        // Get all the appprofileList where creationDate is null
        defaultAppprofileShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where gender equals to DEFAULT_GENDER
        defaultAppprofileShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the appprofileList where gender equals to UPDATED_GENDER
        defaultAppprofileShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where gender not equals to DEFAULT_GENDER
        defaultAppprofileShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the appprofileList where gender not equals to UPDATED_GENDER
        defaultAppprofileShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultAppprofileShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the appprofileList where gender equals to UPDATED_GENDER
        defaultAppprofileShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where gender is not null
        defaultAppprofileShouldBeFound("gender.specified=true");

        // Get all the appprofileList where gender is null
        defaultAppprofileShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where phone equals to DEFAULT_PHONE
        defaultAppprofileShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the appprofileList where phone equals to UPDATED_PHONE
        defaultAppprofileShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where phone not equals to DEFAULT_PHONE
        defaultAppprofileShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the appprofileList where phone not equals to UPDATED_PHONE
        defaultAppprofileShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultAppprofileShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the appprofileList where phone equals to UPDATED_PHONE
        defaultAppprofileShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where phone is not null
        defaultAppprofileShouldBeFound("phone.specified=true");

        // Get all the appprofileList where phone is null
        defaultAppprofileShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where phone contains DEFAULT_PHONE
        defaultAppprofileShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the appprofileList where phone contains UPDATED_PHONE
        defaultAppprofileShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where phone does not contain DEFAULT_PHONE
        defaultAppprofileShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the appprofileList where phone does not contain UPDATED_PHONE
        defaultAppprofileShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByBioIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where bio equals to DEFAULT_BIO
        defaultAppprofileShouldBeFound("bio.equals=" + DEFAULT_BIO);

        // Get all the appprofileList where bio equals to UPDATED_BIO
        defaultAppprofileShouldNotBeFound("bio.equals=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where bio not equals to DEFAULT_BIO
        defaultAppprofileShouldNotBeFound("bio.notEquals=" + DEFAULT_BIO);

        // Get all the appprofileList where bio not equals to UPDATED_BIO
        defaultAppprofileShouldBeFound("bio.notEquals=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBioIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where bio in DEFAULT_BIO or UPDATED_BIO
        defaultAppprofileShouldBeFound("bio.in=" + DEFAULT_BIO + "," + UPDATED_BIO);

        // Get all the appprofileList where bio equals to UPDATED_BIO
        defaultAppprofileShouldNotBeFound("bio.in=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBioIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where bio is not null
        defaultAppprofileShouldBeFound("bio.specified=true");

        // Get all the appprofileList where bio is null
        defaultAppprofileShouldNotBeFound("bio.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByBioContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where bio contains DEFAULT_BIO
        defaultAppprofileShouldBeFound("bio.contains=" + DEFAULT_BIO);

        // Get all the appprofileList where bio contains UPDATED_BIO
        defaultAppprofileShouldNotBeFound("bio.contains=" + UPDATED_BIO);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBioNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where bio does not contain DEFAULT_BIO
        defaultAppprofileShouldNotBeFound("bio.doesNotContain=" + DEFAULT_BIO);

        // Get all the appprofileList where bio does not contain UPDATED_BIO
        defaultAppprofileShouldBeFound("bio.doesNotContain=" + UPDATED_BIO);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByFacebookIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where facebook equals to DEFAULT_FACEBOOK
        defaultAppprofileShouldBeFound("facebook.equals=" + DEFAULT_FACEBOOK);

        // Get all the appprofileList where facebook equals to UPDATED_FACEBOOK
        defaultAppprofileShouldNotBeFound("facebook.equals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFacebookIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where facebook not equals to DEFAULT_FACEBOOK
        defaultAppprofileShouldNotBeFound("facebook.notEquals=" + DEFAULT_FACEBOOK);

        // Get all the appprofileList where facebook not equals to UPDATED_FACEBOOK
        defaultAppprofileShouldBeFound("facebook.notEquals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFacebookIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where facebook in DEFAULT_FACEBOOK or UPDATED_FACEBOOK
        defaultAppprofileShouldBeFound("facebook.in=" + DEFAULT_FACEBOOK + "," + UPDATED_FACEBOOK);

        // Get all the appprofileList where facebook equals to UPDATED_FACEBOOK
        defaultAppprofileShouldNotBeFound("facebook.in=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFacebookIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where facebook is not null
        defaultAppprofileShouldBeFound("facebook.specified=true");

        // Get all the appprofileList where facebook is null
        defaultAppprofileShouldNotBeFound("facebook.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByFacebookContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where facebook contains DEFAULT_FACEBOOK
        defaultAppprofileShouldBeFound("facebook.contains=" + DEFAULT_FACEBOOK);

        // Get all the appprofileList where facebook contains UPDATED_FACEBOOK
        defaultAppprofileShouldNotBeFound("facebook.contains=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFacebookNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where facebook does not contain DEFAULT_FACEBOOK
        defaultAppprofileShouldNotBeFound("facebook.doesNotContain=" + DEFAULT_FACEBOOK);

        // Get all the appprofileList where facebook does not contain UPDATED_FACEBOOK
        defaultAppprofileShouldBeFound("facebook.doesNotContain=" + UPDATED_FACEBOOK);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByTwitterIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where twitter equals to DEFAULT_TWITTER
        defaultAppprofileShouldBeFound("twitter.equals=" + DEFAULT_TWITTER);

        // Get all the appprofileList where twitter equals to UPDATED_TWITTER
        defaultAppprofileShouldNotBeFound("twitter.equals=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByTwitterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where twitter not equals to DEFAULT_TWITTER
        defaultAppprofileShouldNotBeFound("twitter.notEquals=" + DEFAULT_TWITTER);

        // Get all the appprofileList where twitter not equals to UPDATED_TWITTER
        defaultAppprofileShouldBeFound("twitter.notEquals=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByTwitterIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where twitter in DEFAULT_TWITTER or UPDATED_TWITTER
        defaultAppprofileShouldBeFound("twitter.in=" + DEFAULT_TWITTER + "," + UPDATED_TWITTER);

        // Get all the appprofileList where twitter equals to UPDATED_TWITTER
        defaultAppprofileShouldNotBeFound("twitter.in=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByTwitterIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where twitter is not null
        defaultAppprofileShouldBeFound("twitter.specified=true");

        // Get all the appprofileList where twitter is null
        defaultAppprofileShouldNotBeFound("twitter.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByTwitterContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where twitter contains DEFAULT_TWITTER
        defaultAppprofileShouldBeFound("twitter.contains=" + DEFAULT_TWITTER);

        // Get all the appprofileList where twitter contains UPDATED_TWITTER
        defaultAppprofileShouldNotBeFound("twitter.contains=" + UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByTwitterNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where twitter does not contain DEFAULT_TWITTER
        defaultAppprofileShouldNotBeFound("twitter.doesNotContain=" + DEFAULT_TWITTER);

        // Get all the appprofileList where twitter does not contain UPDATED_TWITTER
        defaultAppprofileShouldBeFound("twitter.doesNotContain=" + UPDATED_TWITTER);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByLinkedinIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where linkedin equals to DEFAULT_LINKEDIN
        defaultAppprofileShouldBeFound("linkedin.equals=" + DEFAULT_LINKEDIN);

        // Get all the appprofileList where linkedin equals to UPDATED_LINKEDIN
        defaultAppprofileShouldNotBeFound("linkedin.equals=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLinkedinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where linkedin not equals to DEFAULT_LINKEDIN
        defaultAppprofileShouldNotBeFound("linkedin.notEquals=" + DEFAULT_LINKEDIN);

        // Get all the appprofileList where linkedin not equals to UPDATED_LINKEDIN
        defaultAppprofileShouldBeFound("linkedin.notEquals=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLinkedinIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where linkedin in DEFAULT_LINKEDIN or UPDATED_LINKEDIN
        defaultAppprofileShouldBeFound("linkedin.in=" + DEFAULT_LINKEDIN + "," + UPDATED_LINKEDIN);

        // Get all the appprofileList where linkedin equals to UPDATED_LINKEDIN
        defaultAppprofileShouldNotBeFound("linkedin.in=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLinkedinIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where linkedin is not null
        defaultAppprofileShouldBeFound("linkedin.specified=true");

        // Get all the appprofileList where linkedin is null
        defaultAppprofileShouldNotBeFound("linkedin.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByLinkedinContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where linkedin contains DEFAULT_LINKEDIN
        defaultAppprofileShouldBeFound("linkedin.contains=" + DEFAULT_LINKEDIN);

        // Get all the appprofileList where linkedin contains UPDATED_LINKEDIN
        defaultAppprofileShouldNotBeFound("linkedin.contains=" + UPDATED_LINKEDIN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLinkedinNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where linkedin does not contain DEFAULT_LINKEDIN
        defaultAppprofileShouldNotBeFound("linkedin.doesNotContain=" + DEFAULT_LINKEDIN);

        // Get all the appprofileList where linkedin does not contain UPDATED_LINKEDIN
        defaultAppprofileShouldBeFound("linkedin.doesNotContain=" + UPDATED_LINKEDIN);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByInstagramIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where instagram equals to DEFAULT_INSTAGRAM
        defaultAppprofileShouldBeFound("instagram.equals=" + DEFAULT_INSTAGRAM);

        // Get all the appprofileList where instagram equals to UPDATED_INSTAGRAM
        defaultAppprofileShouldNotBeFound("instagram.equals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByInstagramIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where instagram not equals to DEFAULT_INSTAGRAM
        defaultAppprofileShouldNotBeFound("instagram.notEquals=" + DEFAULT_INSTAGRAM);

        // Get all the appprofileList where instagram not equals to UPDATED_INSTAGRAM
        defaultAppprofileShouldBeFound("instagram.notEquals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByInstagramIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where instagram in DEFAULT_INSTAGRAM or UPDATED_INSTAGRAM
        defaultAppprofileShouldBeFound("instagram.in=" + DEFAULT_INSTAGRAM + "," + UPDATED_INSTAGRAM);

        // Get all the appprofileList where instagram equals to UPDATED_INSTAGRAM
        defaultAppprofileShouldNotBeFound("instagram.in=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByInstagramIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where instagram is not null
        defaultAppprofileShouldBeFound("instagram.specified=true");

        // Get all the appprofileList where instagram is null
        defaultAppprofileShouldNotBeFound("instagram.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByInstagramContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where instagram contains DEFAULT_INSTAGRAM
        defaultAppprofileShouldBeFound("instagram.contains=" + DEFAULT_INSTAGRAM);

        // Get all the appprofileList where instagram contains UPDATED_INSTAGRAM
        defaultAppprofileShouldNotBeFound("instagram.contains=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByInstagramNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where instagram does not contain DEFAULT_INSTAGRAM
        defaultAppprofileShouldNotBeFound("instagram.doesNotContain=" + DEFAULT_INSTAGRAM);

        // Get all the appprofileList where instagram does not contain UPDATED_INSTAGRAM
        defaultAppprofileShouldBeFound("instagram.doesNotContain=" + UPDATED_INSTAGRAM);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByGooglePlusIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where googlePlus equals to DEFAULT_GOOGLE_PLUS
        defaultAppprofileShouldBeFound("googlePlus.equals=" + DEFAULT_GOOGLE_PLUS);

        // Get all the appprofileList where googlePlus equals to UPDATED_GOOGLE_PLUS
        defaultAppprofileShouldNotBeFound("googlePlus.equals=" + UPDATED_GOOGLE_PLUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGooglePlusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where googlePlus not equals to DEFAULT_GOOGLE_PLUS
        defaultAppprofileShouldNotBeFound("googlePlus.notEquals=" + DEFAULT_GOOGLE_PLUS);

        // Get all the appprofileList where googlePlus not equals to UPDATED_GOOGLE_PLUS
        defaultAppprofileShouldBeFound("googlePlus.notEquals=" + UPDATED_GOOGLE_PLUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGooglePlusIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where googlePlus in DEFAULT_GOOGLE_PLUS or UPDATED_GOOGLE_PLUS
        defaultAppprofileShouldBeFound("googlePlus.in=" + DEFAULT_GOOGLE_PLUS + "," + UPDATED_GOOGLE_PLUS);

        // Get all the appprofileList where googlePlus equals to UPDATED_GOOGLE_PLUS
        defaultAppprofileShouldNotBeFound("googlePlus.in=" + UPDATED_GOOGLE_PLUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGooglePlusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where googlePlus is not null
        defaultAppprofileShouldBeFound("googlePlus.specified=true");

        // Get all the appprofileList where googlePlus is null
        defaultAppprofileShouldNotBeFound("googlePlus.specified=false");
    }
                @Test
    @Transactional
    public void getAllAppprofilesByGooglePlusContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where googlePlus contains DEFAULT_GOOGLE_PLUS
        defaultAppprofileShouldBeFound("googlePlus.contains=" + DEFAULT_GOOGLE_PLUS);

        // Get all the appprofileList where googlePlus contains UPDATED_GOOGLE_PLUS
        defaultAppprofileShouldNotBeFound("googlePlus.contains=" + UPDATED_GOOGLE_PLUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByGooglePlusNotContainsSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where googlePlus does not contain DEFAULT_GOOGLE_PLUS
        defaultAppprofileShouldNotBeFound("googlePlus.doesNotContain=" + DEFAULT_GOOGLE_PLUS);

        // Get all the appprofileList where googlePlus does not contain UPDATED_GOOGLE_PLUS
        defaultAppprofileShouldBeFound("googlePlus.doesNotContain=" + UPDATED_GOOGLE_PLUS);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where birthdate equals to DEFAULT_BIRTHDATE
        defaultAppprofileShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the appprofileList where birthdate equals to UPDATED_BIRTHDATE
        defaultAppprofileShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBirthdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where birthdate not equals to DEFAULT_BIRTHDATE
        defaultAppprofileShouldNotBeFound("birthdate.notEquals=" + DEFAULT_BIRTHDATE);

        // Get all the appprofileList where birthdate not equals to UPDATED_BIRTHDATE
        defaultAppprofileShouldBeFound("birthdate.notEquals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultAppprofileShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the appprofileList where birthdate equals to UPDATED_BIRTHDATE
        defaultAppprofileShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where birthdate is not null
        defaultAppprofileShouldBeFound("birthdate.specified=true");

        // Get all the appprofileList where birthdate is null
        defaultAppprofileShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCivilStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where civilStatus equals to DEFAULT_CIVIL_STATUS
        defaultAppprofileShouldBeFound("civilStatus.equals=" + DEFAULT_CIVIL_STATUS);

        // Get all the appprofileList where civilStatus equals to UPDATED_CIVIL_STATUS
        defaultAppprofileShouldNotBeFound("civilStatus.equals=" + UPDATED_CIVIL_STATUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCivilStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where civilStatus not equals to DEFAULT_CIVIL_STATUS
        defaultAppprofileShouldNotBeFound("civilStatus.notEquals=" + DEFAULT_CIVIL_STATUS);

        // Get all the appprofileList where civilStatus not equals to UPDATED_CIVIL_STATUS
        defaultAppprofileShouldBeFound("civilStatus.notEquals=" + UPDATED_CIVIL_STATUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCivilStatusIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where civilStatus in DEFAULT_CIVIL_STATUS or UPDATED_CIVIL_STATUS
        defaultAppprofileShouldBeFound("civilStatus.in=" + DEFAULT_CIVIL_STATUS + "," + UPDATED_CIVIL_STATUS);

        // Get all the appprofileList where civilStatus equals to UPDATED_CIVIL_STATUS
        defaultAppprofileShouldNotBeFound("civilStatus.in=" + UPDATED_CIVIL_STATUS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByCivilStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where civilStatus is not null
        defaultAppprofileShouldBeFound("civilStatus.specified=true");

        // Get all the appprofileList where civilStatus is null
        defaultAppprofileShouldNotBeFound("civilStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLookingForIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where lookingFor equals to DEFAULT_LOOKING_FOR
        defaultAppprofileShouldBeFound("lookingFor.equals=" + DEFAULT_LOOKING_FOR);

        // Get all the appprofileList where lookingFor equals to UPDATED_LOOKING_FOR
        defaultAppprofileShouldNotBeFound("lookingFor.equals=" + UPDATED_LOOKING_FOR);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLookingForIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where lookingFor not equals to DEFAULT_LOOKING_FOR
        defaultAppprofileShouldNotBeFound("lookingFor.notEquals=" + DEFAULT_LOOKING_FOR);

        // Get all the appprofileList where lookingFor not equals to UPDATED_LOOKING_FOR
        defaultAppprofileShouldBeFound("lookingFor.notEquals=" + UPDATED_LOOKING_FOR);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLookingForIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where lookingFor in DEFAULT_LOOKING_FOR or UPDATED_LOOKING_FOR
        defaultAppprofileShouldBeFound("lookingFor.in=" + DEFAULT_LOOKING_FOR + "," + UPDATED_LOOKING_FOR);

        // Get all the appprofileList where lookingFor equals to UPDATED_LOOKING_FOR
        defaultAppprofileShouldNotBeFound("lookingFor.in=" + UPDATED_LOOKING_FOR);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByLookingForIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where lookingFor is not null
        defaultAppprofileShouldBeFound("lookingFor.specified=true");

        // Get all the appprofileList where lookingFor is null
        defaultAppprofileShouldNotBeFound("lookingFor.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where purpose equals to DEFAULT_PURPOSE
        defaultAppprofileShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the appprofileList where purpose equals to UPDATED_PURPOSE
        defaultAppprofileShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPurposeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where purpose not equals to DEFAULT_PURPOSE
        defaultAppprofileShouldNotBeFound("purpose.notEquals=" + DEFAULT_PURPOSE);

        // Get all the appprofileList where purpose not equals to UPDATED_PURPOSE
        defaultAppprofileShouldBeFound("purpose.notEquals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultAppprofileShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the appprofileList where purpose equals to UPDATED_PURPOSE
        defaultAppprofileShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where purpose is not null
        defaultAppprofileShouldBeFound("purpose.specified=true");

        // Get all the appprofileList where purpose is null
        defaultAppprofileShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhysicalIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where physical equals to DEFAULT_PHYSICAL
        defaultAppprofileShouldBeFound("physical.equals=" + DEFAULT_PHYSICAL);

        // Get all the appprofileList where physical equals to UPDATED_PHYSICAL
        defaultAppprofileShouldNotBeFound("physical.equals=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhysicalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where physical not equals to DEFAULT_PHYSICAL
        defaultAppprofileShouldNotBeFound("physical.notEquals=" + DEFAULT_PHYSICAL);

        // Get all the appprofileList where physical not equals to UPDATED_PHYSICAL
        defaultAppprofileShouldBeFound("physical.notEquals=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhysicalIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where physical in DEFAULT_PHYSICAL or UPDATED_PHYSICAL
        defaultAppprofileShouldBeFound("physical.in=" + DEFAULT_PHYSICAL + "," + UPDATED_PHYSICAL);

        // Get all the appprofileList where physical equals to UPDATED_PHYSICAL
        defaultAppprofileShouldNotBeFound("physical.in=" + UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPhysicalIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where physical is not null
        defaultAppprofileShouldBeFound("physical.specified=true");

        // Get all the appprofileList where physical is null
        defaultAppprofileShouldNotBeFound("physical.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where religion equals to DEFAULT_RELIGION
        defaultAppprofileShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the appprofileList where religion equals to UPDATED_RELIGION
        defaultAppprofileShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByReligionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where religion not equals to DEFAULT_RELIGION
        defaultAppprofileShouldNotBeFound("religion.notEquals=" + DEFAULT_RELIGION);

        // Get all the appprofileList where religion not equals to UPDATED_RELIGION
        defaultAppprofileShouldBeFound("religion.notEquals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultAppprofileShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the appprofileList where religion equals to UPDATED_RELIGION
        defaultAppprofileShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where religion is not null
        defaultAppprofileShouldBeFound("religion.specified=true");

        // Get all the appprofileList where religion is null
        defaultAppprofileShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEthnicGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where ethnicGroup equals to DEFAULT_ETHNIC_GROUP
        defaultAppprofileShouldBeFound("ethnicGroup.equals=" + DEFAULT_ETHNIC_GROUP);

        // Get all the appprofileList where ethnicGroup equals to UPDATED_ETHNIC_GROUP
        defaultAppprofileShouldNotBeFound("ethnicGroup.equals=" + UPDATED_ETHNIC_GROUP);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEthnicGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where ethnicGroup not equals to DEFAULT_ETHNIC_GROUP
        defaultAppprofileShouldNotBeFound("ethnicGroup.notEquals=" + DEFAULT_ETHNIC_GROUP);

        // Get all the appprofileList where ethnicGroup not equals to UPDATED_ETHNIC_GROUP
        defaultAppprofileShouldBeFound("ethnicGroup.notEquals=" + UPDATED_ETHNIC_GROUP);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEthnicGroupIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where ethnicGroup in DEFAULT_ETHNIC_GROUP or UPDATED_ETHNIC_GROUP
        defaultAppprofileShouldBeFound("ethnicGroup.in=" + DEFAULT_ETHNIC_GROUP + "," + UPDATED_ETHNIC_GROUP);

        // Get all the appprofileList where ethnicGroup equals to UPDATED_ETHNIC_GROUP
        defaultAppprofileShouldNotBeFound("ethnicGroup.in=" + UPDATED_ETHNIC_GROUP);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEthnicGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where ethnicGroup is not null
        defaultAppprofileShouldBeFound("ethnicGroup.specified=true");

        // Get all the appprofileList where ethnicGroup is null
        defaultAppprofileShouldNotBeFound("ethnicGroup.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByStudiesIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where studies equals to DEFAULT_STUDIES
        defaultAppprofileShouldBeFound("studies.equals=" + DEFAULT_STUDIES);

        // Get all the appprofileList where studies equals to UPDATED_STUDIES
        defaultAppprofileShouldNotBeFound("studies.equals=" + UPDATED_STUDIES);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByStudiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where studies not equals to DEFAULT_STUDIES
        defaultAppprofileShouldNotBeFound("studies.notEquals=" + DEFAULT_STUDIES);

        // Get all the appprofileList where studies not equals to UPDATED_STUDIES
        defaultAppprofileShouldBeFound("studies.notEquals=" + UPDATED_STUDIES);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByStudiesIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where studies in DEFAULT_STUDIES or UPDATED_STUDIES
        defaultAppprofileShouldBeFound("studies.in=" + DEFAULT_STUDIES + "," + UPDATED_STUDIES);

        // Get all the appprofileList where studies equals to UPDATED_STUDIES
        defaultAppprofileShouldNotBeFound("studies.in=" + UPDATED_STUDIES);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByStudiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where studies is not null
        defaultAppprofileShouldBeFound("studies.specified=true");

        // Get all the appprofileList where studies is null
        defaultAppprofileShouldNotBeFound("studies.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings equals to DEFAULT_SIBBLINGS
        defaultAppprofileShouldBeFound("sibblings.equals=" + DEFAULT_SIBBLINGS);

        // Get all the appprofileList where sibblings equals to UPDATED_SIBBLINGS
        defaultAppprofileShouldNotBeFound("sibblings.equals=" + UPDATED_SIBBLINGS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings not equals to DEFAULT_SIBBLINGS
        defaultAppprofileShouldNotBeFound("sibblings.notEquals=" + DEFAULT_SIBBLINGS);

        // Get all the appprofileList where sibblings not equals to UPDATED_SIBBLINGS
        defaultAppprofileShouldBeFound("sibblings.notEquals=" + UPDATED_SIBBLINGS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings in DEFAULT_SIBBLINGS or UPDATED_SIBBLINGS
        defaultAppprofileShouldBeFound("sibblings.in=" + DEFAULT_SIBBLINGS + "," + UPDATED_SIBBLINGS);

        // Get all the appprofileList where sibblings equals to UPDATED_SIBBLINGS
        defaultAppprofileShouldNotBeFound("sibblings.in=" + UPDATED_SIBBLINGS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings is not null
        defaultAppprofileShouldBeFound("sibblings.specified=true");

        // Get all the appprofileList where sibblings is null
        defaultAppprofileShouldNotBeFound("sibblings.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings is greater than or equal to DEFAULT_SIBBLINGS
        defaultAppprofileShouldBeFound("sibblings.greaterThanOrEqual=" + DEFAULT_SIBBLINGS);

        // Get all the appprofileList where sibblings is greater than or equal to (DEFAULT_SIBBLINGS + 1)
        defaultAppprofileShouldNotBeFound("sibblings.greaterThanOrEqual=" + (DEFAULT_SIBBLINGS + 1));
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings is less than or equal to DEFAULT_SIBBLINGS
        defaultAppprofileShouldBeFound("sibblings.lessThanOrEqual=" + DEFAULT_SIBBLINGS);

        // Get all the appprofileList where sibblings is less than or equal to SMALLER_SIBBLINGS
        defaultAppprofileShouldNotBeFound("sibblings.lessThanOrEqual=" + SMALLER_SIBBLINGS);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsLessThanSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings is less than DEFAULT_SIBBLINGS
        defaultAppprofileShouldNotBeFound("sibblings.lessThan=" + DEFAULT_SIBBLINGS);

        // Get all the appprofileList where sibblings is less than (DEFAULT_SIBBLINGS + 1)
        defaultAppprofileShouldBeFound("sibblings.lessThan=" + (DEFAULT_SIBBLINGS + 1));
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySibblingsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where sibblings is greater than DEFAULT_SIBBLINGS
        defaultAppprofileShouldNotBeFound("sibblings.greaterThan=" + DEFAULT_SIBBLINGS);

        // Get all the appprofileList where sibblings is greater than SMALLER_SIBBLINGS
        defaultAppprofileShouldBeFound("sibblings.greaterThan=" + SMALLER_SIBBLINGS);
    }


    @Test
    @Transactional
    public void getAllAppprofilesByEyesIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where eyes equals to DEFAULT_EYES
        defaultAppprofileShouldBeFound("eyes.equals=" + DEFAULT_EYES);

        // Get all the appprofileList where eyes equals to UPDATED_EYES
        defaultAppprofileShouldNotBeFound("eyes.equals=" + UPDATED_EYES);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEyesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where eyes not equals to DEFAULT_EYES
        defaultAppprofileShouldNotBeFound("eyes.notEquals=" + DEFAULT_EYES);

        // Get all the appprofileList where eyes not equals to UPDATED_EYES
        defaultAppprofileShouldBeFound("eyes.notEquals=" + UPDATED_EYES);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEyesIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where eyes in DEFAULT_EYES or UPDATED_EYES
        defaultAppprofileShouldBeFound("eyes.in=" + DEFAULT_EYES + "," + UPDATED_EYES);

        // Get all the appprofileList where eyes equals to UPDATED_EYES
        defaultAppprofileShouldNotBeFound("eyes.in=" + UPDATED_EYES);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByEyesIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where eyes is not null
        defaultAppprofileShouldBeFound("eyes.specified=true");

        // Get all the appprofileList where eyes is null
        defaultAppprofileShouldNotBeFound("eyes.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySmokerIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where smoker equals to DEFAULT_SMOKER
        defaultAppprofileShouldBeFound("smoker.equals=" + DEFAULT_SMOKER);

        // Get all the appprofileList where smoker equals to UPDATED_SMOKER
        defaultAppprofileShouldNotBeFound("smoker.equals=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySmokerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where smoker not equals to DEFAULT_SMOKER
        defaultAppprofileShouldNotBeFound("smoker.notEquals=" + DEFAULT_SMOKER);

        // Get all the appprofileList where smoker not equals to UPDATED_SMOKER
        defaultAppprofileShouldBeFound("smoker.notEquals=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySmokerIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where smoker in DEFAULT_SMOKER or UPDATED_SMOKER
        defaultAppprofileShouldBeFound("smoker.in=" + DEFAULT_SMOKER + "," + UPDATED_SMOKER);

        // Get all the appprofileList where smoker equals to UPDATED_SMOKER
        defaultAppprofileShouldNotBeFound("smoker.in=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    public void getAllAppprofilesBySmokerIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where smoker is not null
        defaultAppprofileShouldBeFound("smoker.specified=true");

        // Get all the appprofileList where smoker is null
        defaultAppprofileShouldNotBeFound("smoker.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where children equals to DEFAULT_CHILDREN
        defaultAppprofileShouldBeFound("children.equals=" + DEFAULT_CHILDREN);

        // Get all the appprofileList where children equals to UPDATED_CHILDREN
        defaultAppprofileShouldNotBeFound("children.equals=" + UPDATED_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByChildrenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where children not equals to DEFAULT_CHILDREN
        defaultAppprofileShouldNotBeFound("children.notEquals=" + DEFAULT_CHILDREN);

        // Get all the appprofileList where children not equals to UPDATED_CHILDREN
        defaultAppprofileShouldBeFound("children.notEquals=" + UPDATED_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByChildrenIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where children in DEFAULT_CHILDREN or UPDATED_CHILDREN
        defaultAppprofileShouldBeFound("children.in=" + DEFAULT_CHILDREN + "," + UPDATED_CHILDREN);

        // Get all the appprofileList where children equals to UPDATED_CHILDREN
        defaultAppprofileShouldNotBeFound("children.in=" + UPDATED_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByChildrenIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where children is not null
        defaultAppprofileShouldBeFound("children.specified=true");

        // Get all the appprofileList where children is null
        defaultAppprofileShouldNotBeFound("children.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFutureChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where futureChildren equals to DEFAULT_FUTURE_CHILDREN
        defaultAppprofileShouldBeFound("futureChildren.equals=" + DEFAULT_FUTURE_CHILDREN);

        // Get all the appprofileList where futureChildren equals to UPDATED_FUTURE_CHILDREN
        defaultAppprofileShouldNotBeFound("futureChildren.equals=" + UPDATED_FUTURE_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFutureChildrenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where futureChildren not equals to DEFAULT_FUTURE_CHILDREN
        defaultAppprofileShouldNotBeFound("futureChildren.notEquals=" + DEFAULT_FUTURE_CHILDREN);

        // Get all the appprofileList where futureChildren not equals to UPDATED_FUTURE_CHILDREN
        defaultAppprofileShouldBeFound("futureChildren.notEquals=" + UPDATED_FUTURE_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFutureChildrenIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where futureChildren in DEFAULT_FUTURE_CHILDREN or UPDATED_FUTURE_CHILDREN
        defaultAppprofileShouldBeFound("futureChildren.in=" + DEFAULT_FUTURE_CHILDREN + "," + UPDATED_FUTURE_CHILDREN);

        // Get all the appprofileList where futureChildren equals to UPDATED_FUTURE_CHILDREN
        defaultAppprofileShouldNotBeFound("futureChildren.in=" + UPDATED_FUTURE_CHILDREN);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByFutureChildrenIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where futureChildren is not null
        defaultAppprofileShouldBeFound("futureChildren.specified=true");

        // Get all the appprofileList where futureChildren is null
        defaultAppprofileShouldNotBeFound("futureChildren.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPetIsEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where pet equals to DEFAULT_PET
        defaultAppprofileShouldBeFound("pet.equals=" + DEFAULT_PET);

        // Get all the appprofileList where pet equals to UPDATED_PET
        defaultAppprofileShouldNotBeFound("pet.equals=" + UPDATED_PET);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where pet not equals to DEFAULT_PET
        defaultAppprofileShouldNotBeFound("pet.notEquals=" + DEFAULT_PET);

        // Get all the appprofileList where pet not equals to UPDATED_PET
        defaultAppprofileShouldBeFound("pet.notEquals=" + UPDATED_PET);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPetIsInShouldWork() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where pet in DEFAULT_PET or UPDATED_PET
        defaultAppprofileShouldBeFound("pet.in=" + DEFAULT_PET + "," + UPDATED_PET);

        // Get all the appprofileList where pet equals to UPDATED_PET
        defaultAppprofileShouldNotBeFound("pet.in=" + UPDATED_PET);
    }

    @Test
    @Transactional
    public void getAllAppprofilesByPetIsNullOrNotNull() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        // Get all the appprofileList where pet is not null
        defaultAppprofileShouldBeFound("pet.specified=true");

        // Get all the appprofileList where pet is null
        defaultAppprofileShouldNotBeFound("pet.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppprofilesByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = appprofile.getAppuser();
        appprofileRepository.saveAndFlush(appprofile);
        Long appuserId = appuser.getId();

        // Get all the appprofileList where appuser equals to appuserId
        defaultAppprofileShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the appprofileList where appuser equals to appuserId + 1
        defaultAppprofileShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppprofileShouldBeFound(String filter) throws Exception {
        restAppprofileMockMvc.perform(get("/api/appprofiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appprofile.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].googlePlus").value(hasItem(DEFAULT_GOOGLE_PLUS)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].civilStatus").value(hasItem(DEFAULT_CIVIL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lookingFor").value(hasItem(DEFAULT_LOOKING_FOR.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].ethnicGroup").value(hasItem(DEFAULT_ETHNIC_GROUP.toString())))
            .andExpect(jsonPath("$.[*].studies").value(hasItem(DEFAULT_STUDIES.toString())))
            .andExpect(jsonPath("$.[*].sibblings").value(hasItem(DEFAULT_SIBBLINGS)))
            .andExpect(jsonPath("$.[*].eyes").value(hasItem(DEFAULT_EYES.toString())))
            .andExpect(jsonPath("$.[*].smoker").value(hasItem(DEFAULT_SMOKER.toString())))
            .andExpect(jsonPath("$.[*].children").value(hasItem(DEFAULT_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].futureChildren").value(hasItem(DEFAULT_FUTURE_CHILDREN.toString())))
            .andExpect(jsonPath("$.[*].pet").value(hasItem(DEFAULT_PET.booleanValue())));

        // Check, that the count call also returns 1
        restAppprofileMockMvc.perform(get("/api/appprofiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppprofileShouldNotBeFound(String filter) throws Exception {
        restAppprofileMockMvc.perform(get("/api/appprofiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppprofileMockMvc.perform(get("/api/appprofiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAppprofile() throws Exception {
        // Get the appprofile
        restAppprofileMockMvc.perform(get("/api/appprofiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppprofile() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        int databaseSizeBeforeUpdate = appprofileRepository.findAll().size();

        // Update the appprofile
        Appprofile updatedAppprofile = appprofileRepository.findById(appprofile.getId()).get();
        // Disconnect from session so that the updates on updatedAppprofile are not directly saved in db
        em.detach(updatedAppprofile);
        updatedAppprofile
            .creationDate(UPDATED_CREATION_DATE)
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .bio(UPDATED_BIO)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .linkedin(UPDATED_LINKEDIN)
            .instagram(UPDATED_INSTAGRAM)
            .googlePlus(UPDATED_GOOGLE_PLUS)
            .birthdate(UPDATED_BIRTHDATE)
            .civilStatus(UPDATED_CIVIL_STATUS)
            .lookingFor(UPDATED_LOOKING_FOR)
            .purpose(UPDATED_PURPOSE)
            .physical(UPDATED_PHYSICAL)
            .religion(UPDATED_RELIGION)
            .ethnicGroup(UPDATED_ETHNIC_GROUP)
            .studies(UPDATED_STUDIES)
            .sibblings(UPDATED_SIBBLINGS)
            .eyes(UPDATED_EYES)
            .smoker(UPDATED_SMOKER)
            .children(UPDATED_CHILDREN)
            .futureChildren(UPDATED_FUTURE_CHILDREN)
            .pet(UPDATED_PET);
        AppprofileDTO appprofileDTO = appprofileMapper.toDto(updatedAppprofile);

        restAppprofileMockMvc.perform(put("/api/appprofiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appprofileDTO)))
            .andExpect(status().isOk());

        // Validate the Appprofile in the database
        List<Appprofile> appprofileList = appprofileRepository.findAll();
        assertThat(appprofileList).hasSize(databaseSizeBeforeUpdate);
        Appprofile testAppprofile = appprofileList.get(appprofileList.size() - 1);
        assertThat(testAppprofile.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAppprofile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testAppprofile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAppprofile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testAppprofile.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testAppprofile.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testAppprofile.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testAppprofile.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testAppprofile.getGooglePlus()).isEqualTo(UPDATED_GOOGLE_PLUS);
        assertThat(testAppprofile.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testAppprofile.getCivilStatus()).isEqualTo(UPDATED_CIVIL_STATUS);
        assertThat(testAppprofile.getLookingFor()).isEqualTo(UPDATED_LOOKING_FOR);
        assertThat(testAppprofile.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testAppprofile.getPhysical()).isEqualTo(UPDATED_PHYSICAL);
        assertThat(testAppprofile.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testAppprofile.getEthnicGroup()).isEqualTo(UPDATED_ETHNIC_GROUP);
        assertThat(testAppprofile.getStudies()).isEqualTo(UPDATED_STUDIES);
        assertThat(testAppprofile.getSibblings()).isEqualTo(UPDATED_SIBBLINGS);
        assertThat(testAppprofile.getEyes()).isEqualTo(UPDATED_EYES);
        assertThat(testAppprofile.getSmoker()).isEqualTo(UPDATED_SMOKER);
        assertThat(testAppprofile.getChildren()).isEqualTo(UPDATED_CHILDREN);
        assertThat(testAppprofile.getFutureChildren()).isEqualTo(UPDATED_FUTURE_CHILDREN);
        assertThat(testAppprofile.isPet()).isEqualTo(UPDATED_PET);
    }

    @Test
    @Transactional
    public void updateNonExistingAppprofile() throws Exception {
        int databaseSizeBeforeUpdate = appprofileRepository.findAll().size();

        // Create the Appprofile
        AppprofileDTO appprofileDTO = appprofileMapper.toDto(appprofile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppprofileMockMvc.perform(put("/api/appprofiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appprofileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appprofile in the database
        List<Appprofile> appprofileList = appprofileRepository.findAll();
        assertThat(appprofileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppprofile() throws Exception {
        // Initialize the database
        appprofileRepository.saveAndFlush(appprofile);

        int databaseSizeBeforeDelete = appprofileRepository.findAll().size();

        // Delete the appprofile
        restAppprofileMockMvc.perform(delete("/api/appprofiles/{id}", appprofile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appprofile> appprofileList = appprofileRepository.findAll();
        assertThat(appprofileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appprofile.class);
        Appprofile appprofile1 = new Appprofile();
        appprofile1.setId(1L);
        Appprofile appprofile2 = new Appprofile();
        appprofile2.setId(appprofile1.getId());
        assertThat(appprofile1).isEqualTo(appprofile2);
        appprofile2.setId(2L);
        assertThat(appprofile1).isNotEqualTo(appprofile2);
        appprofile1.setId(null);
        assertThat(appprofile1).isNotEqualTo(appprofile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppprofileDTO.class);
        AppprofileDTO appprofileDTO1 = new AppprofileDTO();
        appprofileDTO1.setId(1L);
        AppprofileDTO appprofileDTO2 = new AppprofileDTO();
        assertThat(appprofileDTO1).isNotEqualTo(appprofileDTO2);
        appprofileDTO2.setId(appprofileDTO1.getId());
        assertThat(appprofileDTO1).isEqualTo(appprofileDTO2);
        appprofileDTO2.setId(2L);
        assertThat(appprofileDTO1).isNotEqualTo(appprofileDTO2);
        appprofileDTO1.setId(null);
        assertThat(appprofileDTO1).isNotEqualTo(appprofileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appprofileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appprofileMapper.fromId(null)).isNull();
    }
}
