package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.ProposalVote;
import com.spingular.web.domain.Appuser;
import com.spingular.web.domain.Proposal;
import com.spingular.web.repository.ProposalVoteRepository;
import com.spingular.web.service.ProposalVoteService;
import com.spingular.web.service.dto.ProposalVoteDTO;
import com.spingular.web.service.mapper.ProposalVoteMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.ProposalVoteCriteria;
import com.spingular.web.service.ProposalVoteQueryService;

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
 * Integration tests for the {@link ProposalVoteResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class ProposalVoteResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_VOTE_POINTS = 1L;
    private static final Long UPDATED_VOTE_POINTS = 2L;
    private static final Long SMALLER_VOTE_POINTS = 1L - 1L;

    @Autowired
    private ProposalVoteRepository proposalVoteRepository;

    @Autowired
    private ProposalVoteMapper proposalVoteMapper;

    @Autowired
    private ProposalVoteService proposalVoteService;

    @Autowired
    private ProposalVoteQueryService proposalVoteQueryService;

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

    private MockMvc restProposalVoteMockMvc;

    private ProposalVote proposalVote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProposalVoteResource proposalVoteResource = new ProposalVoteResource(proposalVoteService, proposalVoteQueryService);
        this.restProposalVoteMockMvc = MockMvcBuilders.standaloneSetup(proposalVoteResource)
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
    public static ProposalVote createEntity(EntityManager em) {
        ProposalVote proposalVote = new ProposalVote()
            .creationDate(DEFAULT_CREATION_DATE)
            .votePoints(DEFAULT_VOTE_POINTS);
        return proposalVote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProposalVote createUpdatedEntity(EntityManager em) {
        ProposalVote proposalVote = new ProposalVote()
            .creationDate(UPDATED_CREATION_DATE)
            .votePoints(UPDATED_VOTE_POINTS);
        return proposalVote;
    }

    @BeforeEach
    public void initTest() {
        proposalVote = createEntity(em);
    }

    @Test
    @Transactional
    public void createProposalVote() throws Exception {
        int databaseSizeBeforeCreate = proposalVoteRepository.findAll().size();

        // Create the ProposalVote
        ProposalVoteDTO proposalVoteDTO = proposalVoteMapper.toDto(proposalVote);
        restProposalVoteMockMvc.perform(post("/api/proposal-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalVoteDTO)))
            .andExpect(status().isCreated());

        // Validate the ProposalVote in the database
        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeCreate + 1);
        ProposalVote testProposalVote = proposalVoteList.get(proposalVoteList.size() - 1);
        assertThat(testProposalVote.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testProposalVote.getVotePoints()).isEqualTo(DEFAULT_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void createProposalVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proposalVoteRepository.findAll().size();

        // Create the ProposalVote with an existing ID
        proposalVote.setId(1L);
        ProposalVoteDTO proposalVoteDTO = proposalVoteMapper.toDto(proposalVote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProposalVoteMockMvc.perform(post("/api/proposal-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalVoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProposalVote in the database
        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = proposalVoteRepository.findAll().size();
        // set the field null
        proposalVote.setCreationDate(null);

        // Create the ProposalVote, which fails.
        ProposalVoteDTO proposalVoteDTO = proposalVoteMapper.toDto(proposalVote);

        restProposalVoteMockMvc.perform(post("/api/proposal-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalVoteDTO)))
            .andExpect(status().isBadRequest());

        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVotePointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = proposalVoteRepository.findAll().size();
        // set the field null
        proposalVote.setVotePoints(null);

        // Create the ProposalVote, which fails.
        ProposalVoteDTO proposalVoteDTO = proposalVoteMapper.toDto(proposalVote);

        restProposalVoteMockMvc.perform(post("/api/proposal-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalVoteDTO)))
            .andExpect(status().isBadRequest());

        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProposalVotes() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList
        restProposalVoteMockMvc.perform(get("/api/proposal-votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proposalVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].votePoints").value(hasItem(DEFAULT_VOTE_POINTS.intValue())));
    }
    
    @Test
    @Transactional
    public void getProposalVote() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get the proposalVote
        restProposalVoteMockMvc.perform(get("/api/proposal-votes/{id}", proposalVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proposalVote.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.votePoints").value(DEFAULT_VOTE_POINTS.intValue()));
    }

    @Test
    @Transactional
    public void getAllProposalVotesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where creationDate equals to DEFAULT_CREATION_DATE
        defaultProposalVoteShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the proposalVoteList where creationDate equals to UPDATED_CREATION_DATE
        defaultProposalVoteShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultProposalVoteShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the proposalVoteList where creationDate not equals to UPDATED_CREATION_DATE
        defaultProposalVoteShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultProposalVoteShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the proposalVoteList where creationDate equals to UPDATED_CREATION_DATE
        defaultProposalVoteShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where creationDate is not null
        defaultProposalVoteShouldBeFound("creationDate.specified=true");

        // Get all the proposalVoteList where creationDate is null
        defaultProposalVoteShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints equals to DEFAULT_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.equals=" + DEFAULT_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints equals to UPDATED_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.equals=" + UPDATED_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints not equals to DEFAULT_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.notEquals=" + DEFAULT_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints not equals to UPDATED_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.notEquals=" + UPDATED_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsInShouldWork() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints in DEFAULT_VOTE_POINTS or UPDATED_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.in=" + DEFAULT_VOTE_POINTS + "," + UPDATED_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints equals to UPDATED_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.in=" + UPDATED_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints is not null
        defaultProposalVoteShouldBeFound("votePoints.specified=true");

        // Get all the proposalVoteList where votePoints is null
        defaultProposalVoteShouldNotBeFound("votePoints.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints is greater than or equal to DEFAULT_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.greaterThanOrEqual=" + DEFAULT_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints is greater than or equal to UPDATED_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.greaterThanOrEqual=" + UPDATED_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints is less than or equal to DEFAULT_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.lessThanOrEqual=" + DEFAULT_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints is less than or equal to SMALLER_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.lessThanOrEqual=" + SMALLER_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsLessThanSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints is less than DEFAULT_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.lessThan=" + DEFAULT_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints is less than UPDATED_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.lessThan=" + UPDATED_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void getAllProposalVotesByVotePointsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        // Get all the proposalVoteList where votePoints is greater than DEFAULT_VOTE_POINTS
        defaultProposalVoteShouldNotBeFound("votePoints.greaterThan=" + DEFAULT_VOTE_POINTS);

        // Get all the proposalVoteList where votePoints is greater than SMALLER_VOTE_POINTS
        defaultProposalVoteShouldBeFound("votePoints.greaterThan=" + SMALLER_VOTE_POINTS);
    }


    @Test
    @Transactional
    public void getAllProposalVotesByAppuserIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);
        Appuser appuser = AppuserResourceIT.createEntity(em);
        em.persist(appuser);
        em.flush();
        proposalVote.setAppuser(appuser);
        proposalVoteRepository.saveAndFlush(proposalVote);
        Long appuserId = appuser.getId();

        // Get all the proposalVoteList where appuser equals to appuserId
        defaultProposalVoteShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the proposalVoteList where appuser equals to appuserId + 1
        defaultProposalVoteShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }


    @Test
    @Transactional
    public void getAllProposalVotesByProposalIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);
        Proposal proposal = ProposalResourceIT.createEntity(em);
        em.persist(proposal);
        em.flush();
        proposalVote.setProposal(proposal);
        proposalVoteRepository.saveAndFlush(proposalVote);
        Long proposalId = proposal.getId();

        // Get all the proposalVoteList where proposal equals to proposalId
        defaultProposalVoteShouldBeFound("proposalId.equals=" + proposalId);

        // Get all the proposalVoteList where proposal equals to proposalId + 1
        defaultProposalVoteShouldNotBeFound("proposalId.equals=" + (proposalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProposalVoteShouldBeFound(String filter) throws Exception {
        restProposalVoteMockMvc.perform(get("/api/proposal-votes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proposalVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].votePoints").value(hasItem(DEFAULT_VOTE_POINTS.intValue())));

        // Check, that the count call also returns 1
        restProposalVoteMockMvc.perform(get("/api/proposal-votes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProposalVoteShouldNotBeFound(String filter) throws Exception {
        restProposalVoteMockMvc.perform(get("/api/proposal-votes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProposalVoteMockMvc.perform(get("/api/proposal-votes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProposalVote() throws Exception {
        // Get the proposalVote
        restProposalVoteMockMvc.perform(get("/api/proposal-votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProposalVote() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        int databaseSizeBeforeUpdate = proposalVoteRepository.findAll().size();

        // Update the proposalVote
        ProposalVote updatedProposalVote = proposalVoteRepository.findById(proposalVote.getId()).get();
        // Disconnect from session so that the updates on updatedProposalVote are not directly saved in db
        em.detach(updatedProposalVote);
        updatedProposalVote
            .creationDate(UPDATED_CREATION_DATE)
            .votePoints(UPDATED_VOTE_POINTS);
        ProposalVoteDTO proposalVoteDTO = proposalVoteMapper.toDto(updatedProposalVote);

        restProposalVoteMockMvc.perform(put("/api/proposal-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalVoteDTO)))
            .andExpect(status().isOk());

        // Validate the ProposalVote in the database
        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeUpdate);
        ProposalVote testProposalVote = proposalVoteList.get(proposalVoteList.size() - 1);
        assertThat(testProposalVote.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testProposalVote.getVotePoints()).isEqualTo(UPDATED_VOTE_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingProposalVote() throws Exception {
        int databaseSizeBeforeUpdate = proposalVoteRepository.findAll().size();

        // Create the ProposalVote
        ProposalVoteDTO proposalVoteDTO = proposalVoteMapper.toDto(proposalVote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProposalVoteMockMvc.perform(put("/api/proposal-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalVoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProposalVote in the database
        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProposalVote() throws Exception {
        // Initialize the database
        proposalVoteRepository.saveAndFlush(proposalVote);

        int databaseSizeBeforeDelete = proposalVoteRepository.findAll().size();

        // Delete the proposalVote
        restProposalVoteMockMvc.perform(delete("/api/proposal-votes/{id}", proposalVote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProposalVote> proposalVoteList = proposalVoteRepository.findAll();
        assertThat(proposalVoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProposalVote.class);
        ProposalVote proposalVote1 = new ProposalVote();
        proposalVote1.setId(1L);
        ProposalVote proposalVote2 = new ProposalVote();
        proposalVote2.setId(proposalVote1.getId());
        assertThat(proposalVote1).isEqualTo(proposalVote2);
        proposalVote2.setId(2L);
        assertThat(proposalVote1).isNotEqualTo(proposalVote2);
        proposalVote1.setId(null);
        assertThat(proposalVote1).isNotEqualTo(proposalVote2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProposalVoteDTO.class);
        ProposalVoteDTO proposalVoteDTO1 = new ProposalVoteDTO();
        proposalVoteDTO1.setId(1L);
        ProposalVoteDTO proposalVoteDTO2 = new ProposalVoteDTO();
        assertThat(proposalVoteDTO1).isNotEqualTo(proposalVoteDTO2);
        proposalVoteDTO2.setId(proposalVoteDTO1.getId());
        assertThat(proposalVoteDTO1).isEqualTo(proposalVoteDTO2);
        proposalVoteDTO2.setId(2L);
        assertThat(proposalVoteDTO1).isNotEqualTo(proposalVoteDTO2);
        proposalVoteDTO1.setId(null);
        assertThat(proposalVoteDTO1).isNotEqualTo(proposalVoteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proposalVoteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proposalVoteMapper.fromId(null)).isNull();
    }
}
