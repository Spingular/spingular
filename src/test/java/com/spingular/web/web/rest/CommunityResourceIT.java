package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Community;
import com.spingular.web.domain.Blog;
import com.spingular.web.domain.Cmessage;
import com.spingular.web.domain.Follow;
import com.spingular.web.domain.Blockuser;
import com.spingular.web.domain.Appuser;
import com.spingular.web.domain.Calbum;
import com.spingular.web.domain.Cinterest;
import com.spingular.web.domain.Cactivity;
import com.spingular.web.domain.Cceleb;
import com.spingular.web.repository.CommunityRepository;
import com.spingular.web.service.CommunityService;
import com.spingular.web.service.dto.CommunityDTO;
import com.spingular.web.service.mapper.CommunityMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.CommunityCriteria;
import com.spingular.web.service.CommunityQueryService;

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
 * Integration tests for the {@link CommunityResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class CommunityResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMUNITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITY_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunityQueryService communityQueryService;

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

    private MockMvc restCommunityMockMvc;

    private Community community;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommunityResource communityResource = new CommunityResource(communityService, communityQueryService);
        this.restCommunityMockMvc = MockMvcBuilders.standaloneSetup(communityResource)
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
    public static Community createEntity(EntityManager em) {
        Community community = new Community()
            .creationDate(DEFAULT_CREATION_DATE)
            .communityName(DEFAULT_COMMUNITY_NAME)
            .communityDescription(DEFAULT_COMMUNITY_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        community.setAppuser(appuser);
        // Add required entity
        Calbum calbum;
        if (TestUtil.findAll(em, Calbum.class).isEmpty()) {
            calbum = CalbumResourceIT.createEntity(em);
            em.persist(calbum);
            em.flush();
        } else {
            calbum = TestUtil.findAll(em, Calbum.class).get(0);
        }
        community.getCalbums().add(calbum);
        return community;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Community createUpdatedEntity(EntityManager em) {
        Community community = new Community()
            .creationDate(UPDATED_CREATION_DATE)
            .communityName(UPDATED_COMMUNITY_NAME)
            .communityDescription(UPDATED_COMMUNITY_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        community.setAppuser(appuser);
        // Add required entity
        Calbum calbum;
        if (TestUtil.findAll(em, Calbum.class).isEmpty()) {
            calbum = CalbumResourceIT.createUpdatedEntity(em);
            em.persist(calbum);
            em.flush();
        } else {
            calbum = TestUtil.findAll(em, Calbum.class).get(0);
        }
        community.getCalbums().add(calbum);
        return community;
    }

    @BeforeEach
    public void initTest() {
        community = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunity() throws Exception {
        int databaseSizeBeforeCreate = communityRepository.findAll().size();

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);
        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isCreated());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeCreate + 1);
        Community testCommunity = communityList.get(communityList.size() - 1);
        assertThat(testCommunity.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCommunity.getCommunityName()).isEqualTo(DEFAULT_COMMUNITY_NAME);
        assertThat(testCommunity.getCommunityDescription()).isEqualTo(DEFAULT_COMMUNITY_DESCRIPTION);
        assertThat(testCommunity.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCommunity.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCommunity.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createCommunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communityRepository.findAll().size();

        // Create the Community with an existing ID
        community.setId(1L);
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCreationDate(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommunityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCommunityName(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommunityDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCommunityDescription(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunities() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].communityName").value(hasItem(DEFAULT_COMMUNITY_NAME)))
            .andExpect(jsonPath("$.[*].communityDescription").value(hasItem(DEFAULT_COMMUNITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get the community
        restCommunityMockMvc.perform(get("/api/communities/{id}", community.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(community.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.communityName").value(DEFAULT_COMMUNITY_NAME))
            .andExpect(jsonPath("$.communityDescription").value(DEFAULT_COMMUNITY_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate equals to DEFAULT_CREATION_DATE
        defaultCommunityShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the communityList where creationDate equals to UPDATED_CREATION_DATE
        defaultCommunityShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultCommunityShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the communityList where creationDate not equals to UPDATED_CREATION_DATE
        defaultCommunityShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultCommunityShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the communityList where creationDate equals to UPDATED_CREATION_DATE
        defaultCommunityShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate is not null
        defaultCommunityShouldBeFound("creationDate.specified=true");

        // Get all the communityList where creationDate is null
        defaultCommunityShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName equals to DEFAULT_COMMUNITY_NAME
        defaultCommunityShouldBeFound("communityName.equals=" + DEFAULT_COMMUNITY_NAME);

        // Get all the communityList where communityName equals to UPDATED_COMMUNITY_NAME
        defaultCommunityShouldNotBeFound("communityName.equals=" + UPDATED_COMMUNITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName not equals to DEFAULT_COMMUNITY_NAME
        defaultCommunityShouldNotBeFound("communityName.notEquals=" + DEFAULT_COMMUNITY_NAME);

        // Get all the communityList where communityName not equals to UPDATED_COMMUNITY_NAME
        defaultCommunityShouldBeFound("communityName.notEquals=" + UPDATED_COMMUNITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityNameIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName in DEFAULT_COMMUNITY_NAME or UPDATED_COMMUNITY_NAME
        defaultCommunityShouldBeFound("communityName.in=" + DEFAULT_COMMUNITY_NAME + "," + UPDATED_COMMUNITY_NAME);

        // Get all the communityList where communityName equals to UPDATED_COMMUNITY_NAME
        defaultCommunityShouldNotBeFound("communityName.in=" + UPDATED_COMMUNITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName is not null
        defaultCommunityShouldBeFound("communityName.specified=true");

        // Get all the communityList where communityName is null
        defaultCommunityShouldNotBeFound("communityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommunitiesByCommunityNameContainsSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName contains DEFAULT_COMMUNITY_NAME
        defaultCommunityShouldBeFound("communityName.contains=" + DEFAULT_COMMUNITY_NAME);

        // Get all the communityList where communityName contains UPDATED_COMMUNITY_NAME
        defaultCommunityShouldNotBeFound("communityName.contains=" + UPDATED_COMMUNITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityNameNotContainsSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityName does not contain DEFAULT_COMMUNITY_NAME
        defaultCommunityShouldNotBeFound("communityName.doesNotContain=" + DEFAULT_COMMUNITY_NAME);

        // Get all the communityList where communityName does not contain UPDATED_COMMUNITY_NAME
        defaultCommunityShouldBeFound("communityName.doesNotContain=" + UPDATED_COMMUNITY_NAME);
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCommunityDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription equals to DEFAULT_COMMUNITY_DESCRIPTION
        defaultCommunityShouldBeFound("communityDescription.equals=" + DEFAULT_COMMUNITY_DESCRIPTION);

        // Get all the communityList where communityDescription equals to UPDATED_COMMUNITY_DESCRIPTION
        defaultCommunityShouldNotBeFound("communityDescription.equals=" + UPDATED_COMMUNITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription not equals to DEFAULT_COMMUNITY_DESCRIPTION
        defaultCommunityShouldNotBeFound("communityDescription.notEquals=" + DEFAULT_COMMUNITY_DESCRIPTION);

        // Get all the communityList where communityDescription not equals to UPDATED_COMMUNITY_DESCRIPTION
        defaultCommunityShouldBeFound("communityDescription.notEquals=" + UPDATED_COMMUNITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription in DEFAULT_COMMUNITY_DESCRIPTION or UPDATED_COMMUNITY_DESCRIPTION
        defaultCommunityShouldBeFound("communityDescription.in=" + DEFAULT_COMMUNITY_DESCRIPTION + "," + UPDATED_COMMUNITY_DESCRIPTION);

        // Get all the communityList where communityDescription equals to UPDATED_COMMUNITY_DESCRIPTION
        defaultCommunityShouldNotBeFound("communityDescription.in=" + UPDATED_COMMUNITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription is not null
        defaultCommunityShouldBeFound("communityDescription.specified=true");

        // Get all the communityList where communityDescription is null
        defaultCommunityShouldNotBeFound("communityDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommunitiesByCommunityDescriptionContainsSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription contains DEFAULT_COMMUNITY_DESCRIPTION
        defaultCommunityShouldBeFound("communityDescription.contains=" + DEFAULT_COMMUNITY_DESCRIPTION);

        // Get all the communityList where communityDescription contains UPDATED_COMMUNITY_DESCRIPTION
        defaultCommunityShouldNotBeFound("communityDescription.contains=" + UPDATED_COMMUNITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunityDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityDescription does not contain DEFAULT_COMMUNITY_DESCRIPTION
        defaultCommunityShouldNotBeFound("communityDescription.doesNotContain=" + DEFAULT_COMMUNITY_DESCRIPTION);

        // Get all the communityList where communityDescription does not contain UPDATED_COMMUNITY_DESCRIPTION
        defaultCommunityShouldBeFound("communityDescription.doesNotContain=" + UPDATED_COMMUNITY_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive equals to DEFAULT_IS_ACTIVE
        defaultCommunityShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the communityList where isActive equals to UPDATED_IS_ACTIVE
        defaultCommunityShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultCommunityShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the communityList where isActive not equals to UPDATED_IS_ACTIVE
        defaultCommunityShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultCommunityShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the communityList where isActive equals to UPDATED_IS_ACTIVE
        defaultCommunityShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive is not null
        defaultCommunityShouldBeFound("isActive.specified=true");

        // Get all the communityList where isActive is null
        defaultCommunityShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunitiesByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Blog blog = BlogResourceIT.createEntity(em);
        em.persist(blog);
        em.flush();
        community.addBlog(blog);
        communityRepository.saveAndFlush(community);
        Long blogId = blog.getId();

        // Get all the communityList where blog equals to blogId
        defaultCommunityShouldBeFound("blogId.equals=" + blogId);

        // Get all the communityList where blog equals to blogId + 1
        defaultCommunityShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCsenderIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Cmessage csender = CmessageResourceIT.createEntity(em);
        em.persist(csender);
        em.flush();
        community.addCsender(csender);
        communityRepository.saveAndFlush(community);
        Long csenderId = csender.getId();

        // Get all the communityList where csender equals to csenderId
        defaultCommunityShouldBeFound("csenderId.equals=" + csenderId);

        // Get all the communityList where csender equals to csenderId + 1
        defaultCommunityShouldNotBeFound("csenderId.equals=" + (csenderId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCreceiverIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Cmessage creceiver = CmessageResourceIT.createEntity(em);
        em.persist(creceiver);
        em.flush();
        community.addCreceiver(creceiver);
        communityRepository.saveAndFlush(community);
        Long creceiverId = creceiver.getId();

        // Get all the communityList where creceiver equals to creceiverId
        defaultCommunityShouldBeFound("creceiverId.equals=" + creceiverId);

        // Get all the communityList where creceiver equals to creceiverId + 1
        defaultCommunityShouldNotBeFound("creceiverId.equals=" + (creceiverId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCfollowedIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Follow cfollowed = FollowResourceIT.createEntity(em);
        em.persist(cfollowed);
        em.flush();
        community.addCfollowed(cfollowed);
        communityRepository.saveAndFlush(community);
        Long cfollowedId = cfollowed.getId();

        // Get all the communityList where cfollowed equals to cfollowedId
        defaultCommunityShouldBeFound("cfollowedId.equals=" + cfollowedId);

        // Get all the communityList where cfollowed equals to cfollowedId + 1
        defaultCommunityShouldNotBeFound("cfollowedId.equals=" + (cfollowedId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCfollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Follow cfollowing = FollowResourceIT.createEntity(em);
        em.persist(cfollowing);
        em.flush();
        community.addCfollowing(cfollowing);
        communityRepository.saveAndFlush(community);
        Long cfollowingId = cfollowing.getId();

        // Get all the communityList where cfollowing equals to cfollowingId
        defaultCommunityShouldBeFound("cfollowingId.equals=" + cfollowingId);

        // Get all the communityList where cfollowing equals to cfollowingId + 1
        defaultCommunityShouldNotBeFound("cfollowingId.equals=" + (cfollowingId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCblockeduserIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Blockuser cblockeduser = BlockuserResourceIT.createEntity(em);
        em.persist(cblockeduser);
        em.flush();
        community.addCblockeduser(cblockeduser);
        communityRepository.saveAndFlush(community);
        Long cblockeduserId = cblockeduser.getId();

        // Get all the communityList where cblockeduser equals to cblockeduserId
        defaultCommunityShouldBeFound("cblockeduserId.equals=" + cblockeduserId);

        // Get all the communityList where cblockeduser equals to cblockeduserId + 1
        defaultCommunityShouldNotBeFound("cblockeduserId.equals=" + (cblockeduserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCblockinguserIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Blockuser cblockinguser = BlockuserResourceIT.createEntity(em);
        em.persist(cblockinguser);
        em.flush();
        community.addCblockinguser(cblockinguser);
        communityRepository.saveAndFlush(community);
        Long cblockinguserId = cblockinguser.getId();

        // Get all the communityList where cblockinguser equals to cblockinguserId
        defaultCommunityShouldBeFound("cblockinguserId.equals=" + cblockinguserId);

        // Get all the communityList where cblockinguser equals to cblockinguserId + 1
        defaultCommunityShouldNotBeFound("cblockinguserId.equals=" + (cblockinguserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = community.getAppuser();
        communityRepository.saveAndFlush(community);
        Long appuserId = appuser.getId();

        // Get all the communityList where appuser equals to appuserId
        defaultCommunityShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the communityList where appuser equals to appuserId + 1
        defaultCommunityShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCalbumIsEqualToSomething() throws Exception {
        // Get already existing entity
        Calbum calbum = community.getCalbum();
        communityRepository.saveAndFlush(community);
        Long calbumId = calbum.getId();

        // Get all the communityList where calbum equals to calbumId
        defaultCommunityShouldBeFound("calbumId.equals=" + calbumId);

        // Get all the communityList where calbum equals to calbumId + 1
        defaultCommunityShouldNotBeFound("calbumId.equals=" + (calbumId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCinterestIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Cinterest cinterest = CinterestResourceIT.createEntity(em);
        em.persist(cinterest);
        em.flush();
        community.addCinterest(cinterest);
        communityRepository.saveAndFlush(community);
        Long cinterestId = cinterest.getId();

        // Get all the communityList where cinterest equals to cinterestId
        defaultCommunityShouldBeFound("cinterestId.equals=" + cinterestId);

        // Get all the communityList where cinterest equals to cinterestId + 1
        defaultCommunityShouldNotBeFound("cinterestId.equals=" + (cinterestId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCactivityIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Cactivity cactivity = CactivityResourceIT.createEntity(em);
        em.persist(cactivity);
        em.flush();
        community.addCactivity(cactivity);
        communityRepository.saveAndFlush(community);
        Long cactivityId = cactivity.getId();

        // Get all the communityList where cactivity equals to cactivityId
        defaultCommunityShouldBeFound("cactivityId.equals=" + cactivityId);

        // Get all the communityList where cactivity equals to cactivityId + 1
        defaultCommunityShouldNotBeFound("cactivityId.equals=" + (cactivityId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCcelebIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);
        Cceleb cceleb = CcelebResourceIT.createEntity(em);
        em.persist(cceleb);
        em.flush();
        community.addCceleb(cceleb);
        communityRepository.saveAndFlush(community);
        Long ccelebId = cceleb.getId();

        // Get all the communityList where cceleb equals to ccelebId
        defaultCommunityShouldBeFound("ccelebId.equals=" + ccelebId);

        // Get all the communityList where cceleb equals to ccelebId + 1
        defaultCommunityShouldNotBeFound("ccelebId.equals=" + (ccelebId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommunityShouldBeFound(String filter) throws Exception {
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].communityName").value(hasItem(DEFAULT_COMMUNITY_NAME)))
            .andExpect(jsonPath("$.[*].communityDescription").value(hasItem(DEFAULT_COMMUNITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCommunityMockMvc.perform(get("/api/communities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommunityShouldNotBeFound(String filter) throws Exception {
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommunityMockMvc.perform(get("/api/communities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommunity() throws Exception {
        // Get the community
        restCommunityMockMvc.perform(get("/api/communities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        int databaseSizeBeforeUpdate = communityRepository.findAll().size();

        // Update the community
        Community updatedCommunity = communityRepository.findById(community.getId()).get();
        // Disconnect from session so that the updates on updatedCommunity are not directly saved in db
        em.detach(updatedCommunity);
        updatedCommunity
            .creationDate(UPDATED_CREATION_DATE)
            .communityName(UPDATED_COMMUNITY_NAME)
            .communityDescription(UPDATED_COMMUNITY_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
        CommunityDTO communityDTO = communityMapper.toDto(updatedCommunity);

        restCommunityMockMvc.perform(put("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isOk());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeUpdate);
        Community testCommunity = communityList.get(communityList.size() - 1);
        assertThat(testCommunity.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCommunity.getCommunityName()).isEqualTo(UPDATED_COMMUNITY_NAME);
        assertThat(testCommunity.getCommunityDescription()).isEqualTo(UPDATED_COMMUNITY_DESCRIPTION);
        assertThat(testCommunity.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCommunity.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCommunity.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunity() throws Exception {
        int databaseSizeBeforeUpdate = communityRepository.findAll().size();

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityMockMvc.perform(put("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        int databaseSizeBeforeDelete = communityRepository.findAll().size();

        // Delete the community
        restCommunityMockMvc.perform(delete("/api/communities/{id}", community.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Community.class);
        Community community1 = new Community();
        community1.setId(1L);
        Community community2 = new Community();
        community2.setId(community1.getId());
        assertThat(community1).isEqualTo(community2);
        community2.setId(2L);
        assertThat(community1).isNotEqualTo(community2);
        community1.setId(null);
        assertThat(community1).isNotEqualTo(community2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityDTO.class);
        CommunityDTO communityDTO1 = new CommunityDTO();
        communityDTO1.setId(1L);
        CommunityDTO communityDTO2 = new CommunityDTO();
        assertThat(communityDTO1).isNotEqualTo(communityDTO2);
        communityDTO2.setId(communityDTO1.getId());
        assertThat(communityDTO1).isEqualTo(communityDTO2);
        communityDTO2.setId(2L);
        assertThat(communityDTO1).isNotEqualTo(communityDTO2);
        communityDTO1.setId(null);
        assertThat(communityDTO1).isNotEqualTo(communityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(communityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(communityMapper.fromId(null)).isNull();
    }
}
