package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Comment;
import com.spingular.web.domain.Appuser;
import com.spingular.web.domain.Post;
import com.spingular.web.repository.CommentRepository;
import com.spingular.web.service.CommentService;
import com.spingular.web.service.dto.CommentDTO;
import com.spingular.web.service.mapper.CommentMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.CommentCriteria;
import com.spingular.web.service.CommentQueryService;

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
 * Integration tests for the {@link CommentResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class CommentResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_OFFENSIVE = false;
    private static final Boolean UPDATED_IS_OFFENSIVE = true;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentQueryService commentQueryService;

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

    private MockMvc restCommentMockMvc;

    private Comment comment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentResource commentResource = new CommentResource(commentService, commentQueryService);
        this.restCommentMockMvc = MockMvcBuilders.standaloneSetup(commentResource)
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
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .creationDate(DEFAULT_CREATION_DATE)
            .commentText(DEFAULT_COMMENT_TEXT)
            .isOffensive(DEFAULT_IS_OFFENSIVE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        comment.setAppuser(appuser);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        comment.setPost(post);
        return comment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment comment = new Comment()
            .creationDate(UPDATED_CREATION_DATE)
            .commentText(UPDATED_COMMENT_TEXT)
            .isOffensive(UPDATED_IS_OFFENSIVE);
        // Add required entity
        Appuser appuser;
        if (TestUtil.findAll(em, Appuser.class).isEmpty()) {
            appuser = AppuserResourceIT.createUpdatedEntity(em);
            em.persist(appuser);
            em.flush();
        } else {
            appuser = TestUtil.findAll(em, Appuser.class).get(0);
        }
        comment.setAppuser(appuser);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createUpdatedEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        comment.setPost(post);
        return comment;
    }

    @BeforeEach
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testComment.getCommentText()).isEqualTo(DEFAULT_COMMENT_TEXT);
        assertThat(testComment.isIsOffensive()).isEqualTo(DEFAULT_IS_OFFENSIVE);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        comment.setId(1L);
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setCreationDate(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setCommentText(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].commentText").value(hasItem(DEFAULT_COMMENT_TEXT)))
            .andExpect(jsonPath("$.[*].isOffensive").value(hasItem(DEFAULT_IS_OFFENSIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.commentText").value(DEFAULT_COMMENT_TEXT))
            .andExpect(jsonPath("$.isOffensive").value(DEFAULT_IS_OFFENSIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllCommentsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where creationDate equals to DEFAULT_CREATION_DATE
        defaultCommentShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the commentList where creationDate equals to UPDATED_CREATION_DATE
        defaultCommentShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommentsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultCommentShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the commentList where creationDate not equals to UPDATED_CREATION_DATE
        defaultCommentShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommentsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultCommentShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the commentList where creationDate equals to UPDATED_CREATION_DATE
        defaultCommentShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommentsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where creationDate is not null
        defaultCommentShouldBeFound("creationDate.specified=true");

        // Get all the commentList where creationDate is null
        defaultCommentShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentsByCommentTextIsEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where commentText equals to DEFAULT_COMMENT_TEXT
        defaultCommentShouldBeFound("commentText.equals=" + DEFAULT_COMMENT_TEXT);

        // Get all the commentList where commentText equals to UPDATED_COMMENT_TEXT
        defaultCommentShouldNotBeFound("commentText.equals=" + UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    public void getAllCommentsByCommentTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where commentText not equals to DEFAULT_COMMENT_TEXT
        defaultCommentShouldNotBeFound("commentText.notEquals=" + DEFAULT_COMMENT_TEXT);

        // Get all the commentList where commentText not equals to UPDATED_COMMENT_TEXT
        defaultCommentShouldBeFound("commentText.notEquals=" + UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    public void getAllCommentsByCommentTextIsInShouldWork() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where commentText in DEFAULT_COMMENT_TEXT or UPDATED_COMMENT_TEXT
        defaultCommentShouldBeFound("commentText.in=" + DEFAULT_COMMENT_TEXT + "," + UPDATED_COMMENT_TEXT);

        // Get all the commentList where commentText equals to UPDATED_COMMENT_TEXT
        defaultCommentShouldNotBeFound("commentText.in=" + UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    public void getAllCommentsByCommentTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where commentText is not null
        defaultCommentShouldBeFound("commentText.specified=true");

        // Get all the commentList where commentText is null
        defaultCommentShouldNotBeFound("commentText.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommentsByCommentTextContainsSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where commentText contains DEFAULT_COMMENT_TEXT
        defaultCommentShouldBeFound("commentText.contains=" + DEFAULT_COMMENT_TEXT);

        // Get all the commentList where commentText contains UPDATED_COMMENT_TEXT
        defaultCommentShouldNotBeFound("commentText.contains=" + UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    public void getAllCommentsByCommentTextNotContainsSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where commentText does not contain DEFAULT_COMMENT_TEXT
        defaultCommentShouldNotBeFound("commentText.doesNotContain=" + DEFAULT_COMMENT_TEXT);

        // Get all the commentList where commentText does not contain UPDATED_COMMENT_TEXT
        defaultCommentShouldBeFound("commentText.doesNotContain=" + UPDATED_COMMENT_TEXT);
    }


    @Test
    @Transactional
    public void getAllCommentsByIsOffensiveIsEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where isOffensive equals to DEFAULT_IS_OFFENSIVE
        defaultCommentShouldBeFound("isOffensive.equals=" + DEFAULT_IS_OFFENSIVE);

        // Get all the commentList where isOffensive equals to UPDATED_IS_OFFENSIVE
        defaultCommentShouldNotBeFound("isOffensive.equals=" + UPDATED_IS_OFFENSIVE);
    }

    @Test
    @Transactional
    public void getAllCommentsByIsOffensiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where isOffensive not equals to DEFAULT_IS_OFFENSIVE
        defaultCommentShouldNotBeFound("isOffensive.notEquals=" + DEFAULT_IS_OFFENSIVE);

        // Get all the commentList where isOffensive not equals to UPDATED_IS_OFFENSIVE
        defaultCommentShouldBeFound("isOffensive.notEquals=" + UPDATED_IS_OFFENSIVE);
    }

    @Test
    @Transactional
    public void getAllCommentsByIsOffensiveIsInShouldWork() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where isOffensive in DEFAULT_IS_OFFENSIVE or UPDATED_IS_OFFENSIVE
        defaultCommentShouldBeFound("isOffensive.in=" + DEFAULT_IS_OFFENSIVE + "," + UPDATED_IS_OFFENSIVE);

        // Get all the commentList where isOffensive equals to UPDATED_IS_OFFENSIVE
        defaultCommentShouldNotBeFound("isOffensive.in=" + UPDATED_IS_OFFENSIVE);
    }

    @Test
    @Transactional
    public void getAllCommentsByIsOffensiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList where isOffensive is not null
        defaultCommentShouldBeFound("isOffensive.specified=true");

        // Get all the commentList where isOffensive is null
        defaultCommentShouldNotBeFound("isOffensive.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentsByAppuserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Appuser appuser = comment.getAppuser();
        commentRepository.saveAndFlush(comment);
        Long appuserId = appuser.getId();

        // Get all the commentList where appuser equals to appuserId
        defaultCommentShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the commentList where appuser equals to appuserId + 1
        defaultCommentShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommentsByPostIsEqualToSomething() throws Exception {
        // Get already existing entity
        Post post = comment.getPost();
        commentRepository.saveAndFlush(comment);
        Long postId = post.getId();

        // Get all the commentList where post equals to postId
        defaultCommentShouldBeFound("postId.equals=" + postId);

        // Get all the commentList where post equals to postId + 1
        defaultCommentShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommentShouldBeFound(String filter) throws Exception {
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].commentText").value(hasItem(DEFAULT_COMMENT_TEXT)))
            .andExpect(jsonPath("$.[*].isOffensive").value(hasItem(DEFAULT_IS_OFFENSIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCommentMockMvc.perform(get("/api/comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommentShouldNotBeFound(String filter) throws Exception {
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommentMockMvc.perform(get("/api/comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment
            .creationDate(UPDATED_CREATION_DATE)
            .commentText(UPDATED_COMMENT_TEXT)
            .isOffensive(UPDATED_IS_OFFENSIVE);
        CommentDTO commentDTO = commentMapper.toDto(updatedComment);

        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testComment.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
        assertThat(testComment.isIsOffensive()).isEqualTo(UPDATED_IS_OFFENSIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Delete the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = new Comment();
        comment1.setId(1L);
        Comment comment2 = new Comment();
        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);
        comment2.setId(2L);
        assertThat(comment1).isNotEqualTo(comment2);
        comment1.setId(null);
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentDTO.class);
        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setId(1L);
        CommentDTO commentDTO2 = new CommentDTO();
        assertThat(commentDTO1).isNotEqualTo(commentDTO2);
        commentDTO2.setId(commentDTO1.getId());
        assertThat(commentDTO1).isEqualTo(commentDTO2);
        commentDTO2.setId(2L);
        assertThat(commentDTO1).isNotEqualTo(commentDTO2);
        commentDTO1.setId(null);
        assertThat(commentDTO1).isNotEqualTo(commentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commentMapper.fromId(null)).isNull();
    }
}
