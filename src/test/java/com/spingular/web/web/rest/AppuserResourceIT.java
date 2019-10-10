package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Appuser;
import com.spingular.web.domain.User;
import com.spingular.web.domain.Appprofile;
import com.spingular.web.domain.Appphoto;
import com.spingular.web.domain.Community;
import com.spingular.web.domain.Blog;
import com.spingular.web.domain.Notification;
import com.spingular.web.domain.Album;
import com.spingular.web.domain.Comment;
import com.spingular.web.domain.Post;
import com.spingular.web.domain.Message;
import com.spingular.web.domain.Follow;
import com.spingular.web.domain.Blockuser;
import com.spingular.web.domain.Vtopic;
import com.spingular.web.domain.Vquestion;
import com.spingular.web.domain.Vanswer;
import com.spingular.web.domain.Vthumb;
import com.spingular.web.domain.Proposal;
import com.spingular.web.domain.ProposalVote;
import com.spingular.web.domain.Interest;
import com.spingular.web.domain.Activity;
import com.spingular.web.domain.Celeb;
import com.spingular.web.repository.AppuserRepository;
import com.spingular.web.service.AppuserService;
import com.spingular.web.service.dto.AppuserDTO;
import com.spingular.web.service.mapper.AppuserMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.AppuserCriteria;
import com.spingular.web.service.AppuserQueryService;

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
 * Integration tests for the {@link AppuserResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class AppuserResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ASSIGNED_VOTES_POINTS = 1L;
    private static final Long UPDATED_ASSIGNED_VOTES_POINTS = 2L;
    private static final Long SMALLER_ASSIGNED_VOTES_POINTS = 1L - 1L;

    @Autowired
    private AppuserRepository appuserRepository;

    @Autowired
    private AppuserMapper appuserMapper;

    @Autowired
    private AppuserService appuserService;

    @Autowired
    private AppuserQueryService appuserQueryService;

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

    private MockMvc restAppuserMockMvc;

    private Appuser appuser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppuserResource appuserResource = new AppuserResource(appuserService, appuserQueryService);
        this.restAppuserMockMvc = MockMvcBuilders.standaloneSetup(appuserResource)
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
    public static Appuser createEntity(EntityManager em) {
        Appuser appuser = new Appuser()
            .creationDate(DEFAULT_CREATION_DATE)
            .assignedVotesPoints(DEFAULT_ASSIGNED_VOTES_POINTS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        appuser.setUser(user);
        return appuser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appuser createUpdatedEntity(EntityManager em) {
        Appuser appuser = new Appuser()
            .creationDate(UPDATED_CREATION_DATE)
            .assignedVotesPoints(UPDATED_ASSIGNED_VOTES_POINTS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        appuser.setUser(user);
        return appuser;
    }

    @BeforeEach
    public void initTest() {
        appuser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppuser() throws Exception {
        int databaseSizeBeforeCreate = appuserRepository.findAll().size();

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);
        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appuserDTO)))
            .andExpect(status().isCreated());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeCreate + 1);
        Appuser testAppuser = appuserList.get(appuserList.size() - 1);
        assertThat(testAppuser.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAppuser.getAssignedVotesPoints()).isEqualTo(DEFAULT_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void createAppuserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appuserRepository.findAll().size();

        // Create the Appuser with an existing ID
        appuser.setId(1L);
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appuserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appuserRepository.findAll().size();
        // set the field null
        appuser.setCreationDate(null);

        // Create the Appuser, which fails.
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appuserDTO)))
            .andExpect(status().isBadRequest());

        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppusers() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList
        restAppuserMockMvc.perform(get("/api/appusers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appuser.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].assignedVotesPoints").value(hasItem(DEFAULT_ASSIGNED_VOTES_POINTS.intValue())));
    }
    
    @Test
    @Transactional
    public void getAppuser() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get the appuser
        restAppuserMockMvc.perform(get("/api/appusers/{id}", appuser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appuser.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.assignedVotesPoints").value(DEFAULT_ASSIGNED_VOTES_POINTS.intValue()));
    }

    @Test
    @Transactional
    public void getAllAppusersByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate equals to DEFAULT_CREATION_DATE
        defaultAppuserShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the appuserList where creationDate equals to UPDATED_CREATION_DATE
        defaultAppuserShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppusersByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultAppuserShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the appuserList where creationDate not equals to UPDATED_CREATION_DATE
        defaultAppuserShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppusersByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultAppuserShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the appuserList where creationDate equals to UPDATED_CREATION_DATE
        defaultAppuserShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAppusersByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where creationDate is not null
        defaultAppuserShouldBeFound("creationDate.specified=true");

        // Get all the appuserList where creationDate is null
        defaultAppuserShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints equals to DEFAULT_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.equals=" + DEFAULT_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints equals to UPDATED_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.equals=" + UPDATED_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints not equals to DEFAULT_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.notEquals=" + DEFAULT_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints not equals to UPDATED_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.notEquals=" + UPDATED_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsInShouldWork() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints in DEFAULT_ASSIGNED_VOTES_POINTS or UPDATED_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.in=" + DEFAULT_ASSIGNED_VOTES_POINTS + "," + UPDATED_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints equals to UPDATED_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.in=" + UPDATED_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints is not null
        defaultAppuserShouldBeFound("assignedVotesPoints.specified=true");

        // Get all the appuserList where assignedVotesPoints is null
        defaultAppuserShouldNotBeFound("assignedVotesPoints.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints is greater than or equal to DEFAULT_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.greaterThanOrEqual=" + DEFAULT_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints is greater than or equal to UPDATED_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.greaterThanOrEqual=" + UPDATED_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints is less than or equal to DEFAULT_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.lessThanOrEqual=" + DEFAULT_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints is less than or equal to SMALLER_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.lessThanOrEqual=" + SMALLER_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsLessThanSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints is less than DEFAULT_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.lessThan=" + DEFAULT_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints is less than UPDATED_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.lessThan=" + UPDATED_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void getAllAppusersByAssignedVotesPointsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList where assignedVotesPoints is greater than DEFAULT_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldNotBeFound("assignedVotesPoints.greaterThan=" + DEFAULT_ASSIGNED_VOTES_POINTS);

        // Get all the appuserList where assignedVotesPoints is greater than SMALLER_ASSIGNED_VOTES_POINTS
        defaultAppuserShouldBeFound("assignedVotesPoints.greaterThan=" + SMALLER_ASSIGNED_VOTES_POINTS);
    }


    @Test
    @Transactional
    public void getAllAppusersByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = appuser.getUser();
        appuserRepository.saveAndFlush(appuser);
        Long userId = user.getId();

        // Get all the appuserList where user equals to userId
        defaultAppuserShouldBeFound("userId.equals=" + userId);

        // Get all the appuserList where user equals to userId + 1
        defaultAppuserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByAppprofileIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Appprofile appprofile = AppprofileResourceIT.createEntity(em);
        em.persist(appprofile);
        em.flush();
        appuser.setAppprofile(appprofile);
        appprofile.setAppuser(appuser);
        appuserRepository.saveAndFlush(appuser);
        Long appprofileId = appprofile.getId();

        // Get all the appuserList where appprofile equals to appprofileId
        defaultAppuserShouldBeFound("appprofileId.equals=" + appprofileId);

        // Get all the appuserList where appprofile equals to appprofileId + 1
        defaultAppuserShouldNotBeFound("appprofileId.equals=" + (appprofileId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByAppphotoIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Appphoto appphoto = AppphotoResourceIT.createEntity(em);
        em.persist(appphoto);
        em.flush();
        appuser.setAppphoto(appphoto);
        appphoto.setAppuser(appuser);
        appuserRepository.saveAndFlush(appuser);
        Long appphotoId = appphoto.getId();

        // Get all the appuserList where appphoto equals to appphotoId
        defaultAppuserShouldBeFound("appphotoId.equals=" + appphotoId);

        // Get all the appuserList where appphoto equals to appphotoId + 1
        defaultAppuserShouldNotBeFound("appphotoId.equals=" + (appphotoId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByCommunityIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Community community = CommunityResourceIT.createEntity(em);
        em.persist(community);
        em.flush();
        appuser.addCommunity(community);
        appuserRepository.saveAndFlush(appuser);
        Long communityId = community.getId();

        // Get all the appuserList where community equals to communityId
        defaultAppuserShouldBeFound("communityId.equals=" + communityId);

        // Get all the appuserList where community equals to communityId + 1
        defaultAppuserShouldNotBeFound("communityId.equals=" + (communityId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Blog blog = BlogResourceIT.createEntity(em);
        em.persist(blog);
        em.flush();
        appuser.addBlog(blog);
        appuserRepository.saveAndFlush(appuser);
        Long blogId = blog.getId();

        // Get all the appuserList where blog equals to blogId
        defaultAppuserShouldBeFound("blogId.equals=" + blogId);

        // Get all the appuserList where blog equals to blogId + 1
        defaultAppuserShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Notification notification = NotificationResourceIT.createEntity(em);
        em.persist(notification);
        em.flush();
        appuser.addNotification(notification);
        appuserRepository.saveAndFlush(appuser);
        Long notificationId = notification.getId();

        // Get all the appuserList where notification equals to notificationId
        defaultAppuserShouldBeFound("notificationId.equals=" + notificationId);

        // Get all the appuserList where notification equals to notificationId + 1
        defaultAppuserShouldNotBeFound("notificationId.equals=" + (notificationId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByAlbumIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Album album = AlbumResourceIT.createEntity(em);
        em.persist(album);
        em.flush();
        appuser.addAlbum(album);
        appuserRepository.saveAndFlush(appuser);
        Long albumId = album.getId();

        // Get all the appuserList where album equals to albumId
        defaultAppuserShouldBeFound("albumId.equals=" + albumId);

        // Get all the appuserList where album equals to albumId + 1
        defaultAppuserShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Comment comment = CommentResourceIT.createEntity(em);
        em.persist(comment);
        em.flush();
        appuser.addComment(comment);
        appuserRepository.saveAndFlush(appuser);
        Long commentId = comment.getId();

        // Get all the appuserList where comment equals to commentId
        defaultAppuserShouldBeFound("commentId.equals=" + commentId);

        // Get all the appuserList where comment equals to commentId + 1
        defaultAppuserShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        appuser.addPost(post);
        appuserRepository.saveAndFlush(appuser);
        Long postId = post.getId();

        // Get all the appuserList where post equals to postId
        defaultAppuserShouldBeFound("postId.equals=" + postId);

        // Get all the appuserList where post equals to postId + 1
        defaultAppuserShouldNotBeFound("postId.equals=" + (postId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersBySenderIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Message sender = MessageResourceIT.createEntity(em);
        em.persist(sender);
        em.flush();
        appuser.addSender(sender);
        appuserRepository.saveAndFlush(appuser);
        Long senderId = sender.getId();

        // Get all the appuserList where sender equals to senderId
        defaultAppuserShouldBeFound("senderId.equals=" + senderId);

        // Get all the appuserList where sender equals to senderId + 1
        defaultAppuserShouldNotBeFound("senderId.equals=" + (senderId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByReceiverIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Message receiver = MessageResourceIT.createEntity(em);
        em.persist(receiver);
        em.flush();
        appuser.addReceiver(receiver);
        appuserRepository.saveAndFlush(appuser);
        Long receiverId = receiver.getId();

        // Get all the appuserList where receiver equals to receiverId
        defaultAppuserShouldBeFound("receiverId.equals=" + receiverId);

        // Get all the appuserList where receiver equals to receiverId + 1
        defaultAppuserShouldNotBeFound("receiverId.equals=" + (receiverId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByFollowedIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Follow followed = FollowResourceIT.createEntity(em);
        em.persist(followed);
        em.flush();
        appuser.addFollowed(followed);
        appuserRepository.saveAndFlush(appuser);
        Long followedId = followed.getId();

        // Get all the appuserList where followed equals to followedId
        defaultAppuserShouldBeFound("followedId.equals=" + followedId);

        // Get all the appuserList where followed equals to followedId + 1
        defaultAppuserShouldNotBeFound("followedId.equals=" + (followedId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Follow following = FollowResourceIT.createEntity(em);
        em.persist(following);
        em.flush();
        appuser.addFollowing(following);
        appuserRepository.saveAndFlush(appuser);
        Long followingId = following.getId();

        // Get all the appuserList where following equals to followingId
        defaultAppuserShouldBeFound("followingId.equals=" + followingId);

        // Get all the appuserList where following equals to followingId + 1
        defaultAppuserShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByBlockeduserIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Blockuser blockeduser = BlockuserResourceIT.createEntity(em);
        em.persist(blockeduser);
        em.flush();
        appuser.addBlockeduser(blockeduser);
        appuserRepository.saveAndFlush(appuser);
        Long blockeduserId = blockeduser.getId();

        // Get all the appuserList where blockeduser equals to blockeduserId
        defaultAppuserShouldBeFound("blockeduserId.equals=" + blockeduserId);

        // Get all the appuserList where blockeduser equals to blockeduserId + 1
        defaultAppuserShouldNotBeFound("blockeduserId.equals=" + (blockeduserId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByBlockinguserIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Blockuser blockinguser = BlockuserResourceIT.createEntity(em);
        em.persist(blockinguser);
        em.flush();
        appuser.addBlockinguser(blockinguser);
        appuserRepository.saveAndFlush(appuser);
        Long blockinguserId = blockinguser.getId();

        // Get all the appuserList where blockinguser equals to blockinguserId
        defaultAppuserShouldBeFound("blockinguserId.equals=" + blockinguserId);

        // Get all the appuserList where blockinguser equals to blockinguserId + 1
        defaultAppuserShouldNotBeFound("blockinguserId.equals=" + (blockinguserId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByVtopicIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Vtopic vtopic = VtopicResourceIT.createEntity(em);
        em.persist(vtopic);
        em.flush();
        appuser.addVtopic(vtopic);
        appuserRepository.saveAndFlush(appuser);
        Long vtopicId = vtopic.getId();

        // Get all the appuserList where vtopic equals to vtopicId
        defaultAppuserShouldBeFound("vtopicId.equals=" + vtopicId);

        // Get all the appuserList where vtopic equals to vtopicId + 1
        defaultAppuserShouldNotBeFound("vtopicId.equals=" + (vtopicId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByVquestionIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Vquestion vquestion = VquestionResourceIT.createEntity(em);
        em.persist(vquestion);
        em.flush();
        appuser.addVquestion(vquestion);
        appuserRepository.saveAndFlush(appuser);
        Long vquestionId = vquestion.getId();

        // Get all the appuserList where vquestion equals to vquestionId
        defaultAppuserShouldBeFound("vquestionId.equals=" + vquestionId);

        // Get all the appuserList where vquestion equals to vquestionId + 1
        defaultAppuserShouldNotBeFound("vquestionId.equals=" + (vquestionId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByVanswerIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Vanswer vanswer = VanswerResourceIT.createEntity(em);
        em.persist(vanswer);
        em.flush();
        appuser.addVanswer(vanswer);
        appuserRepository.saveAndFlush(appuser);
        Long vanswerId = vanswer.getId();

        // Get all the appuserList where vanswer equals to vanswerId
        defaultAppuserShouldBeFound("vanswerId.equals=" + vanswerId);

        // Get all the appuserList where vanswer equals to vanswerId + 1
        defaultAppuserShouldNotBeFound("vanswerId.equals=" + (vanswerId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByVthumbIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Vthumb vthumb = VthumbResourceIT.createEntity(em);
        em.persist(vthumb);
        em.flush();
        appuser.addVthumb(vthumb);
        appuserRepository.saveAndFlush(appuser);
        Long vthumbId = vthumb.getId();

        // Get all the appuserList where vthumb equals to vthumbId
        defaultAppuserShouldBeFound("vthumbId.equals=" + vthumbId);

        // Get all the appuserList where vthumb equals to vthumbId + 1
        defaultAppuserShouldNotBeFound("vthumbId.equals=" + (vthumbId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByProposalIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Proposal proposal = ProposalResourceIT.createEntity(em);
        em.persist(proposal);
        em.flush();
        appuser.addProposal(proposal);
        appuserRepository.saveAndFlush(appuser);
        Long proposalId = proposal.getId();

        // Get all the appuserList where proposal equals to proposalId
        defaultAppuserShouldBeFound("proposalId.equals=" + proposalId);

        // Get all the appuserList where proposal equals to proposalId + 1
        defaultAppuserShouldNotBeFound("proposalId.equals=" + (proposalId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByProposalVoteIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        ProposalVote proposalVote = ProposalVoteResourceIT.createEntity(em);
        em.persist(proposalVote);
        em.flush();
        appuser.addProposalVote(proposalVote);
        appuserRepository.saveAndFlush(appuser);
        Long proposalVoteId = proposalVote.getId();

        // Get all the appuserList where proposalVote equals to proposalVoteId
        defaultAppuserShouldBeFound("proposalVoteId.equals=" + proposalVoteId);

        // Get all the appuserList where proposalVote equals to proposalVoteId + 1
        defaultAppuserShouldNotBeFound("proposalVoteId.equals=" + (proposalVoteId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Interest interest = InterestResourceIT.createEntity(em);
        em.persist(interest);
        em.flush();
        appuser.addInterest(interest);
        appuserRepository.saveAndFlush(appuser);
        Long interestId = interest.getId();

        // Get all the appuserList where interest equals to interestId
        defaultAppuserShouldBeFound("interestId.equals=" + interestId);

        // Get all the appuserList where interest equals to interestId + 1
        defaultAppuserShouldNotBeFound("interestId.equals=" + (interestId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Activity activity = ActivityResourceIT.createEntity(em);
        em.persist(activity);
        em.flush();
        appuser.addActivity(activity);
        appuserRepository.saveAndFlush(appuser);
        Long activityId = activity.getId();

        // Get all the appuserList where activity equals to activityId
        defaultAppuserShouldBeFound("activityId.equals=" + activityId);

        // Get all the appuserList where activity equals to activityId + 1
        defaultAppuserShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }


    @Test
    @Transactional
    public void getAllAppusersByCelebIsEqualToSomething() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        Celeb celeb = CelebResourceIT.createEntity(em);
        em.persist(celeb);
        em.flush();
        appuser.addCeleb(celeb);
        appuserRepository.saveAndFlush(appuser);
        Long celebId = celeb.getId();

        // Get all the appuserList where celeb equals to celebId
        defaultAppuserShouldBeFound("celebId.equals=" + celebId);

        // Get all the appuserList where celeb equals to celebId + 1
        defaultAppuserShouldNotBeFound("celebId.equals=" + (celebId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppuserShouldBeFound(String filter) throws Exception {
        restAppuserMockMvc.perform(get("/api/appusers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appuser.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].assignedVotesPoints").value(hasItem(DEFAULT_ASSIGNED_VOTES_POINTS.intValue())));

        // Check, that the count call also returns 1
        restAppuserMockMvc.perform(get("/api/appusers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppuserShouldNotBeFound(String filter) throws Exception {
        restAppuserMockMvc.perform(get("/api/appusers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppuserMockMvc.perform(get("/api/appusers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAppuser() throws Exception {
        // Get the appuser
        restAppuserMockMvc.perform(get("/api/appusers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppuser() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        int databaseSizeBeforeUpdate = appuserRepository.findAll().size();

        // Update the appuser
        Appuser updatedAppuser = appuserRepository.findById(appuser.getId()).get();
        // Disconnect from session so that the updates on updatedAppuser are not directly saved in db
        em.detach(updatedAppuser);
        updatedAppuser
            .creationDate(UPDATED_CREATION_DATE)
            .assignedVotesPoints(UPDATED_ASSIGNED_VOTES_POINTS);
        AppuserDTO appuserDTO = appuserMapper.toDto(updatedAppuser);

        restAppuserMockMvc.perform(put("/api/appusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appuserDTO)))
            .andExpect(status().isOk());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeUpdate);
        Appuser testAppuser = appuserList.get(appuserList.size() - 1);
        assertThat(testAppuser.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAppuser.getAssignedVotesPoints()).isEqualTo(UPDATED_ASSIGNED_VOTES_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingAppuser() throws Exception {
        int databaseSizeBeforeUpdate = appuserRepository.findAll().size();

        // Create the Appuser
        AppuserDTO appuserDTO = appuserMapper.toDto(appuser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppuserMockMvc.perform(put("/api/appusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appuserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppuser() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        int databaseSizeBeforeDelete = appuserRepository.findAll().size();

        // Delete the appuser
        restAppuserMockMvc.perform(delete("/api/appusers/{id}", appuser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appuser.class);
        Appuser appuser1 = new Appuser();
        appuser1.setId(1L);
        Appuser appuser2 = new Appuser();
        appuser2.setId(appuser1.getId());
        assertThat(appuser1).isEqualTo(appuser2);
        appuser2.setId(2L);
        assertThat(appuser1).isNotEqualTo(appuser2);
        appuser1.setId(null);
        assertThat(appuser1).isNotEqualTo(appuser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppuserDTO.class);
        AppuserDTO appuserDTO1 = new AppuserDTO();
        appuserDTO1.setId(1L);
        AppuserDTO appuserDTO2 = new AppuserDTO();
        assertThat(appuserDTO1).isNotEqualTo(appuserDTO2);
        appuserDTO2.setId(appuserDTO1.getId());
        assertThat(appuserDTO1).isEqualTo(appuserDTO2);
        appuserDTO2.setId(2L);
        assertThat(appuserDTO1).isNotEqualTo(appuserDTO2);
        appuserDTO1.setId(null);
        assertThat(appuserDTO1).isNotEqualTo(appuserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appuserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appuserMapper.fromId(null)).isNull();
    }
}
