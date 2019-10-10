package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Proposal;
import com.spingular.web.domain.ProposalVote;
import com.spingular.web.domain.Appuser;
import com.spingular.web.domain.Post;
import com.spingular.web.repository.ProposalRepository;
import com.spingular.web.service.ProposalService;
import com.spingular.web.service.dto.ProposalDTO;
import com.spingular.web.service.mapper.ProposalMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.ProposalCriteria;
import com.spingular.web.service.ProposalQueryService;

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

import com.spingular.web.domain.enumeration.ProposalType;
import com.spingular.web.domain.enumeration.ProposalRole;
/**
 * Integration tests for the {@link ProposalResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class ProposalResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROPOSAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSAL_NAME = "BBBBBBBBBB";

    private static final ProposalType DEFAULT_PROPOSAL_TYPE = ProposalType.STUDY;
    private static final ProposalType UPDATED_PROPOSAL_TYPE = ProposalType.APPROVED;

    private static final ProposalRole DEFAULT_PROPOSAL_ROLE = ProposalRole.USER;
    private static final ProposalRole UPDATED_PROPOSAL_ROLE = ProposalRole.ORGANIZER;

    private static final Instant DEFAULT_RELEASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RELEASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_OPEN = false;
    private static final Boolean UPDATED_IS_OPEN = true;

    private static final Boolean DEFAULT_IS_ACCEPTED = false;
    private static final Boolean UPDATED_IS_ACCEPTED = true;

    private static final Boolean DEFAULT_IS_PAID = false;
    private static final Boolean UPDATED_IS_PAID = true;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalMapper proposalMapper;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private ProposalQueryService proposalQueryService;

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

    private MockMvc restProposalMockMvc;

    private Proposal proposal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProposalResource proposalResource = new ProposalResource(proposalService, proposalQueryService);
        this.restProposalMockMvc = MockMvcBuilders.standaloneSetup(proposalResource)
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
    public static Proposal createEntity(EntityManager em) {
        Proposal proposal = new Proposal()
            .creationDate(DEFAULT_CREATION_DATE)
            .proposalName(DEFAULT_PROPOSAL_NAME)
            .proposalType(DEFAULT_PROPOSAL_TYPE)
            .proposalRole(DEFAULT_PROPOSAL_ROLE)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .isOpen(DEFAULT_IS_OPEN)
            .isAccepted(DEFAULT_IS_ACCEPTED)
            .isPaid(DEFAULT_IS_PAID);
        return proposal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proposal createUpdatedEntity(EntityManager em) {
        Proposal proposal = new Proposal()
            .creationDate(UPDATED_CREATION_DATE)
            .proposalName(UPDATED_PROPOSAL_NAME)
            .proposalType(UPDATED_PROPOSAL_TYPE)
            .proposalRole(UPDATED_PROPOSAL_ROLE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .isOpen(UPDATED_IS_OPEN)
            .isAccepted(UPDATED_IS_ACCEPTED)
            .isPaid(UPDATED_IS_PAID);
        return proposal;
    }

    @BeforeEach
    public void initTest() {
        proposal = createEntity(em);
    }

    @Test
    @Transactional
    public void createProposal() throws Exception {
        int databaseSizeBeforeCreate = proposalRepository.findAll().size();

        // Create the Proposal
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);
        restProposalMockMvc.perform(post("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isCreated());

        // Validate the Proposal in the database
        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeCreate + 1);
        Proposal testProposal = proposalList.get(proposalList.size() - 1);
        assertThat(testProposal.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testProposal.getProposalName()).isEqualTo(DEFAULT_PROPOSAL_NAME);
        assertThat(testProposal.getProposalType()).isEqualTo(DEFAULT_PROPOSAL_TYPE);
        assertThat(testProposal.getProposalRole()).isEqualTo(DEFAULT_PROPOSAL_ROLE);
        assertThat(testProposal.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testProposal.isIsOpen()).isEqualTo(DEFAULT_IS_OPEN);
        assertThat(testProposal.isIsAccepted()).isEqualTo(DEFAULT_IS_ACCEPTED);
        assertThat(testProposal.isIsPaid()).isEqualTo(DEFAULT_IS_PAID);
    }

    @Test
    @Transactional
    public void createProposalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proposalRepository.findAll().size();

        // Create the Proposal with an existing ID
        proposal.setId(1L);
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProposalMockMvc.perform(post("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proposal in the database
        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = proposalRepository.findAll().size();
        // set the field null
        proposal.setCreationDate(null);

        // Create the Proposal, which fails.
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);

        restProposalMockMvc.perform(post("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isBadRequest());

        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProposalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = proposalRepository.findAll().size();
        // set the field null
        proposal.setProposalName(null);

        // Create the Proposal, which fails.
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);

        restProposalMockMvc.perform(post("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isBadRequest());

        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProposalTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = proposalRepository.findAll().size();
        // set the field null
        proposal.setProposalType(null);

        // Create the Proposal, which fails.
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);

        restProposalMockMvc.perform(post("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isBadRequest());

        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProposalRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = proposalRepository.findAll().size();
        // set the field null
        proposal.setProposalRole(null);

        // Create the Proposal, which fails.
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);

        restProposalMockMvc.perform(post("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isBadRequest());

        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProposals() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList
        restProposalMockMvc.perform(get("/api/proposals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].proposalName").value(hasItem(DEFAULT_PROPOSAL_NAME)))
            .andExpect(jsonPath("$.[*].proposalType").value(hasItem(DEFAULT_PROPOSAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].proposalRole").value(hasItem(DEFAULT_PROPOSAL_ROLE.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOpen").value(hasItem(DEFAULT_IS_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].isAccepted").value(hasItem(DEFAULT_IS_ACCEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPaid").value(hasItem(DEFAULT_IS_PAID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProposal() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get the proposal
        restProposalMockMvc.perform(get("/api/proposals/{id}", proposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proposal.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.proposalName").value(DEFAULT_PROPOSAL_NAME))
            .andExpect(jsonPath("$.proposalType").value(DEFAULT_PROPOSAL_TYPE.toString()))
            .andExpect(jsonPath("$.proposalRole").value(DEFAULT_PROPOSAL_ROLE.toString()))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.isOpen").value(DEFAULT_IS_OPEN.booleanValue()))
            .andExpect(jsonPath("$.isAccepted").value(DEFAULT_IS_ACCEPTED.booleanValue()))
            .andExpect(jsonPath("$.isPaid").value(DEFAULT_IS_PAID.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllProposalsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where creationDate equals to DEFAULT_CREATION_DATE
        defaultProposalShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the proposalList where creationDate equals to UPDATED_CREATION_DATE
        defaultProposalShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultProposalShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the proposalList where creationDate not equals to UPDATED_CREATION_DATE
        defaultProposalShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultProposalShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the proposalList where creationDate equals to UPDATED_CREATION_DATE
        defaultProposalShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where creationDate is not null
        defaultProposalShouldBeFound("creationDate.specified=true");

        // Get all the proposalList where creationDate is null
        defaultProposalShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalName equals to DEFAULT_PROPOSAL_NAME
        defaultProposalShouldBeFound("proposalName.equals=" + DEFAULT_PROPOSAL_NAME);

        // Get all the proposalList where proposalName equals to UPDATED_PROPOSAL_NAME
        defaultProposalShouldNotBeFound("proposalName.equals=" + UPDATED_PROPOSAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalName not equals to DEFAULT_PROPOSAL_NAME
        defaultProposalShouldNotBeFound("proposalName.notEquals=" + DEFAULT_PROPOSAL_NAME);

        // Get all the proposalList where proposalName not equals to UPDATED_PROPOSAL_NAME
        defaultProposalShouldBeFound("proposalName.notEquals=" + UPDATED_PROPOSAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalNameIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalName in DEFAULT_PROPOSAL_NAME or UPDATED_PROPOSAL_NAME
        defaultProposalShouldBeFound("proposalName.in=" + DEFAULT_PROPOSAL_NAME + "," + UPDATED_PROPOSAL_NAME);

        // Get all the proposalList where proposalName equals to UPDATED_PROPOSAL_NAME
        defaultProposalShouldNotBeFound("proposalName.in=" + UPDATED_PROPOSAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalName is not null
        defaultProposalShouldBeFound("proposalName.specified=true");

        // Get all the proposalList where proposalName is null
        defaultProposalShouldNotBeFound("proposalName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProposalsByProposalNameContainsSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalName contains DEFAULT_PROPOSAL_NAME
        defaultProposalShouldBeFound("proposalName.contains=" + DEFAULT_PROPOSAL_NAME);

        // Get all the proposalList where proposalName contains UPDATED_PROPOSAL_NAME
        defaultProposalShouldNotBeFound("proposalName.contains=" + UPDATED_PROPOSAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalNameNotContainsSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalName does not contain DEFAULT_PROPOSAL_NAME
        defaultProposalShouldNotBeFound("proposalName.doesNotContain=" + DEFAULT_PROPOSAL_NAME);

        // Get all the proposalList where proposalName does not contain UPDATED_PROPOSAL_NAME
        defaultProposalShouldBeFound("proposalName.doesNotContain=" + UPDATED_PROPOSAL_NAME);
    }


    @Test
    @Transactional
    public void getAllProposalsByProposalTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalType equals to DEFAULT_PROPOSAL_TYPE
        defaultProposalShouldBeFound("proposalType.equals=" + DEFAULT_PROPOSAL_TYPE);

        // Get all the proposalList where proposalType equals to UPDATED_PROPOSAL_TYPE
        defaultProposalShouldNotBeFound("proposalType.equals=" + UPDATED_PROPOSAL_TYPE);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalType not equals to DEFAULT_PROPOSAL_TYPE
        defaultProposalShouldNotBeFound("proposalType.notEquals=" + DEFAULT_PROPOSAL_TYPE);

        // Get all the proposalList where proposalType not equals to UPDATED_PROPOSAL_TYPE
        defaultProposalShouldBeFound("proposalType.notEquals=" + UPDATED_PROPOSAL_TYPE);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalTypeIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalType in DEFAULT_PROPOSAL_TYPE or UPDATED_PROPOSAL_TYPE
        defaultProposalShouldBeFound("proposalType.in=" + DEFAULT_PROPOSAL_TYPE + "," + UPDATED_PROPOSAL_TYPE);

        // Get all the proposalList where proposalType equals to UPDATED_PROPOSAL_TYPE
        defaultProposalShouldNotBeFound("proposalType.in=" + UPDATED_PROPOSAL_TYPE);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalType is not null
        defaultProposalShouldBeFound("proposalType.specified=true");

        // Get all the proposalList where proposalType is null
        defaultProposalShouldNotBeFound("proposalType.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalRole equals to DEFAULT_PROPOSAL_ROLE
        defaultProposalShouldBeFound("proposalRole.equals=" + DEFAULT_PROPOSAL_ROLE);

        // Get all the proposalList where proposalRole equals to UPDATED_PROPOSAL_ROLE
        defaultProposalShouldNotBeFound("proposalRole.equals=" + UPDATED_PROPOSAL_ROLE);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalRole not equals to DEFAULT_PROPOSAL_ROLE
        defaultProposalShouldNotBeFound("proposalRole.notEquals=" + DEFAULT_PROPOSAL_ROLE);

        // Get all the proposalList where proposalRole not equals to UPDATED_PROPOSAL_ROLE
        defaultProposalShouldBeFound("proposalRole.notEquals=" + UPDATED_PROPOSAL_ROLE);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalRoleIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalRole in DEFAULT_PROPOSAL_ROLE or UPDATED_PROPOSAL_ROLE
        defaultProposalShouldBeFound("proposalRole.in=" + DEFAULT_PROPOSAL_ROLE + "," + UPDATED_PROPOSAL_ROLE);

        // Get all the proposalList where proposalRole equals to UPDATED_PROPOSAL_ROLE
        defaultProposalShouldNotBeFound("proposalRole.in=" + UPDATED_PROPOSAL_ROLE);
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where proposalRole is not null
        defaultProposalShouldBeFound("proposalRole.specified=true");

        // Get all the proposalList where proposalRole is null
        defaultProposalShouldNotBeFound("proposalRole.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByReleaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where releaseDate equals to DEFAULT_RELEASE_DATE
        defaultProposalShouldBeFound("releaseDate.equals=" + DEFAULT_RELEASE_DATE);

        // Get all the proposalList where releaseDate equals to UPDATED_RELEASE_DATE
        defaultProposalShouldNotBeFound("releaseDate.equals=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalsByReleaseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where releaseDate not equals to DEFAULT_RELEASE_DATE
        defaultProposalShouldNotBeFound("releaseDate.notEquals=" + DEFAULT_RELEASE_DATE);

        // Get all the proposalList where releaseDate not equals to UPDATED_RELEASE_DATE
        defaultProposalShouldBeFound("releaseDate.notEquals=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalsByReleaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where releaseDate in DEFAULT_RELEASE_DATE or UPDATED_RELEASE_DATE
        defaultProposalShouldBeFound("releaseDate.in=" + DEFAULT_RELEASE_DATE + "," + UPDATED_RELEASE_DATE);

        // Get all the proposalList where releaseDate equals to UPDATED_RELEASE_DATE
        defaultProposalShouldNotBeFound("releaseDate.in=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProposalsByReleaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where releaseDate is not null
        defaultProposalShouldBeFound("releaseDate.specified=true");

        // Get all the proposalList where releaseDate is null
        defaultProposalShouldNotBeFound("releaseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByIsOpenIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isOpen equals to DEFAULT_IS_OPEN
        defaultProposalShouldBeFound("isOpen.equals=" + DEFAULT_IS_OPEN);

        // Get all the proposalList where isOpen equals to UPDATED_IS_OPEN
        defaultProposalShouldNotBeFound("isOpen.equals=" + UPDATED_IS_OPEN);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsOpenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isOpen not equals to DEFAULT_IS_OPEN
        defaultProposalShouldNotBeFound("isOpen.notEquals=" + DEFAULT_IS_OPEN);

        // Get all the proposalList where isOpen not equals to UPDATED_IS_OPEN
        defaultProposalShouldBeFound("isOpen.notEquals=" + UPDATED_IS_OPEN);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsOpenIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isOpen in DEFAULT_IS_OPEN or UPDATED_IS_OPEN
        defaultProposalShouldBeFound("isOpen.in=" + DEFAULT_IS_OPEN + "," + UPDATED_IS_OPEN);

        // Get all the proposalList where isOpen equals to UPDATED_IS_OPEN
        defaultProposalShouldNotBeFound("isOpen.in=" + UPDATED_IS_OPEN);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsOpenIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isOpen is not null
        defaultProposalShouldBeFound("isOpen.specified=true");

        // Get all the proposalList where isOpen is null
        defaultProposalShouldNotBeFound("isOpen.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByIsAcceptedIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isAccepted equals to DEFAULT_IS_ACCEPTED
        defaultProposalShouldBeFound("isAccepted.equals=" + DEFAULT_IS_ACCEPTED);

        // Get all the proposalList where isAccepted equals to UPDATED_IS_ACCEPTED
        defaultProposalShouldNotBeFound("isAccepted.equals=" + UPDATED_IS_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsAcceptedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isAccepted not equals to DEFAULT_IS_ACCEPTED
        defaultProposalShouldNotBeFound("isAccepted.notEquals=" + DEFAULT_IS_ACCEPTED);

        // Get all the proposalList where isAccepted not equals to UPDATED_IS_ACCEPTED
        defaultProposalShouldBeFound("isAccepted.notEquals=" + UPDATED_IS_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsAcceptedIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isAccepted in DEFAULT_IS_ACCEPTED or UPDATED_IS_ACCEPTED
        defaultProposalShouldBeFound("isAccepted.in=" + DEFAULT_IS_ACCEPTED + "," + UPDATED_IS_ACCEPTED);

        // Get all the proposalList where isAccepted equals to UPDATED_IS_ACCEPTED
        defaultProposalShouldNotBeFound("isAccepted.in=" + UPDATED_IS_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsAcceptedIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isAccepted is not null
        defaultProposalShouldBeFound("isAccepted.specified=true");

        // Get all the proposalList where isAccepted is null
        defaultProposalShouldNotBeFound("isAccepted.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByIsPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isPaid equals to DEFAULT_IS_PAID
        defaultProposalShouldBeFound("isPaid.equals=" + DEFAULT_IS_PAID);

        // Get all the proposalList where isPaid equals to UPDATED_IS_PAID
        defaultProposalShouldNotBeFound("isPaid.equals=" + UPDATED_IS_PAID);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsPaidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isPaid not equals to DEFAULT_IS_PAID
        defaultProposalShouldNotBeFound("isPaid.notEquals=" + DEFAULT_IS_PAID);

        // Get all the proposalList where isPaid not equals to UPDATED_IS_PAID
        defaultProposalShouldBeFound("isPaid.notEquals=" + UPDATED_IS_PAID);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsPaidIsInShouldWork() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isPaid in DEFAULT_IS_PAID or UPDATED_IS_PAID
        defaultProposalShouldBeFound("isPaid.in=" + DEFAULT_IS_PAID + "," + UPDATED_IS_PAID);

        // Get all the proposalList where isPaid equals to UPDATED_IS_PAID
        defaultProposalShouldNotBeFound("isPaid.in=" + UPDATED_IS_PAID);
    }

    @Test
    @Transactional
    public void getAllProposalsByIsPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        // Get all the proposalList where isPaid is not null
        defaultProposalShouldBeFound("isPaid.specified=true");

        // Get all the proposalList where isPaid is null
        defaultProposalShouldNotBeFound("isPaid.specified=false");
    }

    @Test
    @Transactional
    public void getAllProposalsByProposalVoteIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);
        ProposalVote proposalVote = ProposalVoteResourceIT.createEntity(em);
        em.persist(proposalVote);
        em.flush();
        proposal.addProposalVote(proposalVote);
        proposalRepository.saveAndFlush(proposal);
        Long proposalVoteId = proposalVote.getId();

        // Get all the proposalList where proposalVote equals to proposalVoteId
        defaultProposalShouldBeFound("proposalVoteId.equals=" + proposalVoteId);

        // Get all the proposalList where proposalVote equals to proposalVoteId + 1
        defaultProposalShouldNotBeFound("proposalVoteId.equals=" + (proposalVoteId + 1));
    }


    @Test
    @Transactional
    public void getAllProposalsByAppuserIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);
        Appuser appuser = AppuserResourceIT.createEntity(em);
        em.persist(appuser);
        em.flush();
        proposal.setAppuser(appuser);
        proposalRepository.saveAndFlush(proposal);
        Long appuserId = appuser.getId();

        // Get all the proposalList where appuser equals to appuserId
        defaultProposalShouldBeFound("appuserId.equals=" + appuserId);

        // Get all the proposalList where appuser equals to appuserId + 1
        defaultProposalShouldNotBeFound("appuserId.equals=" + (appuserId + 1));
    }


    @Test
    @Transactional
    public void getAllProposalsByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        proposal.setPost(post);
        proposalRepository.saveAndFlush(proposal);
        Long postId = post.getId();

        // Get all the proposalList where post equals to postId
        defaultProposalShouldBeFound("postId.equals=" + postId);

        // Get all the proposalList where post equals to postId + 1
        defaultProposalShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProposalShouldBeFound(String filter) throws Exception {
        restProposalMockMvc.perform(get("/api/proposals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].proposalName").value(hasItem(DEFAULT_PROPOSAL_NAME)))
            .andExpect(jsonPath("$.[*].proposalType").value(hasItem(DEFAULT_PROPOSAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].proposalRole").value(hasItem(DEFAULT_PROPOSAL_ROLE.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOpen").value(hasItem(DEFAULT_IS_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].isAccepted").value(hasItem(DEFAULT_IS_ACCEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPaid").value(hasItem(DEFAULT_IS_PAID.booleanValue())));

        // Check, that the count call also returns 1
        restProposalMockMvc.perform(get("/api/proposals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProposalShouldNotBeFound(String filter) throws Exception {
        restProposalMockMvc.perform(get("/api/proposals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProposalMockMvc.perform(get("/api/proposals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProposal() throws Exception {
        // Get the proposal
        restProposalMockMvc.perform(get("/api/proposals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProposal() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        int databaseSizeBeforeUpdate = proposalRepository.findAll().size();

        // Update the proposal
        Proposal updatedProposal = proposalRepository.findById(proposal.getId()).get();
        // Disconnect from session so that the updates on updatedProposal are not directly saved in db
        em.detach(updatedProposal);
        updatedProposal
            .creationDate(UPDATED_CREATION_DATE)
            .proposalName(UPDATED_PROPOSAL_NAME)
            .proposalType(UPDATED_PROPOSAL_TYPE)
            .proposalRole(UPDATED_PROPOSAL_ROLE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .isOpen(UPDATED_IS_OPEN)
            .isAccepted(UPDATED_IS_ACCEPTED)
            .isPaid(UPDATED_IS_PAID);
        ProposalDTO proposalDTO = proposalMapper.toDto(updatedProposal);

        restProposalMockMvc.perform(put("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isOk());

        // Validate the Proposal in the database
        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeUpdate);
        Proposal testProposal = proposalList.get(proposalList.size() - 1);
        assertThat(testProposal.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testProposal.getProposalName()).isEqualTo(UPDATED_PROPOSAL_NAME);
        assertThat(testProposal.getProposalType()).isEqualTo(UPDATED_PROPOSAL_TYPE);
        assertThat(testProposal.getProposalRole()).isEqualTo(UPDATED_PROPOSAL_ROLE);
        assertThat(testProposal.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProposal.isIsOpen()).isEqualTo(UPDATED_IS_OPEN);
        assertThat(testProposal.isIsAccepted()).isEqualTo(UPDATED_IS_ACCEPTED);
        assertThat(testProposal.isIsPaid()).isEqualTo(UPDATED_IS_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingProposal() throws Exception {
        int databaseSizeBeforeUpdate = proposalRepository.findAll().size();

        // Create the Proposal
        ProposalDTO proposalDTO = proposalMapper.toDto(proposal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProposalMockMvc.perform(put("/api/proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proposalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proposal in the database
        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProposal() throws Exception {
        // Initialize the database
        proposalRepository.saveAndFlush(proposal);

        int databaseSizeBeforeDelete = proposalRepository.findAll().size();

        // Delete the proposal
        restProposalMockMvc.perform(delete("/api/proposals/{id}", proposal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proposal> proposalList = proposalRepository.findAll();
        assertThat(proposalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proposal.class);
        Proposal proposal1 = new Proposal();
        proposal1.setId(1L);
        Proposal proposal2 = new Proposal();
        proposal2.setId(proposal1.getId());
        assertThat(proposal1).isEqualTo(proposal2);
        proposal2.setId(2L);
        assertThat(proposal1).isNotEqualTo(proposal2);
        proposal1.setId(null);
        assertThat(proposal1).isNotEqualTo(proposal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProposalDTO.class);
        ProposalDTO proposalDTO1 = new ProposalDTO();
        proposalDTO1.setId(1L);
        ProposalDTO proposalDTO2 = new ProposalDTO();
        assertThat(proposalDTO1).isNotEqualTo(proposalDTO2);
        proposalDTO2.setId(proposalDTO1.getId());
        assertThat(proposalDTO1).isEqualTo(proposalDTO2);
        proposalDTO2.setId(2L);
        assertThat(proposalDTO1).isNotEqualTo(proposalDTO2);
        proposalDTO1.setId(null);
        assertThat(proposalDTO1).isNotEqualTo(proposalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proposalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proposalMapper.fromId(null)).isNull();
    }
}
