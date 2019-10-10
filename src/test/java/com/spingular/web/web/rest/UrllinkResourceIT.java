package com.spingular.web.web.rest;

import com.spingular.web.SpingularApp;
import com.spingular.web.domain.Urllink;
import com.spingular.web.repository.UrllinkRepository;
import com.spingular.web.service.UrllinkService;
import com.spingular.web.service.dto.UrllinkDTO;
import com.spingular.web.service.mapper.UrllinkMapper;
import com.spingular.web.web.rest.errors.ExceptionTranslator;
import com.spingular.web.service.dto.UrllinkCriteria;
import com.spingular.web.service.UrllinkQueryService;

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
import java.util.List;

import static com.spingular.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UrllinkResource} REST controller.
 */
@SpringBootTest(classes = SpingularApp.class)
public class UrllinkResourceIT {

    private static final String DEFAULT_LINK_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_URL = "BBBBBBBBBB";

    @Autowired
    private UrllinkRepository urllinkRepository;

    @Autowired
    private UrllinkMapper urllinkMapper;

    @Autowired
    private UrllinkService urllinkService;

    @Autowired
    private UrllinkQueryService urllinkQueryService;

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

    private MockMvc restUrllinkMockMvc;

    private Urllink urllink;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UrllinkResource urllinkResource = new UrllinkResource(urllinkService, urllinkQueryService);
        this.restUrllinkMockMvc = MockMvcBuilders.standaloneSetup(urllinkResource)
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
    public static Urllink createEntity(EntityManager em) {
        Urllink urllink = new Urllink()
            .linkText(DEFAULT_LINK_TEXT)
            .linkURL(DEFAULT_LINK_URL);
        return urllink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Urllink createUpdatedEntity(EntityManager em) {
        Urllink urllink = new Urllink()
            .linkText(UPDATED_LINK_TEXT)
            .linkURL(UPDATED_LINK_URL);
        return urllink;
    }

    @BeforeEach
    public void initTest() {
        urllink = createEntity(em);
    }

    @Test
    @Transactional
    public void createUrllink() throws Exception {
        int databaseSizeBeforeCreate = urllinkRepository.findAll().size();

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);
        restUrllinkMockMvc.perform(post("/api/urllinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urllinkDTO)))
            .andExpect(status().isCreated());

        // Validate the Urllink in the database
        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeCreate + 1);
        Urllink testUrllink = urllinkList.get(urllinkList.size() - 1);
        assertThat(testUrllink.getLinkText()).isEqualTo(DEFAULT_LINK_TEXT);
        assertThat(testUrllink.getLinkURL()).isEqualTo(DEFAULT_LINK_URL);
    }

    @Test
    @Transactional
    public void createUrllinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = urllinkRepository.findAll().size();

        // Create the Urllink with an existing ID
        urllink.setId(1L);
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrllinkMockMvc.perform(post("/api/urllinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLinkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = urllinkRepository.findAll().size();
        // set the field null
        urllink.setLinkText(null);

        // Create the Urllink, which fails.
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        restUrllinkMockMvc.perform(post("/api/urllinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinkURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = urllinkRepository.findAll().size();
        // set the field null
        urllink.setLinkURL(null);

        // Create the Urllink, which fails.
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        restUrllinkMockMvc.perform(post("/api/urllinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUrllinks() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList
        restUrllinkMockMvc.perform(get("/api/urllinks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urllink.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkText").value(hasItem(DEFAULT_LINK_TEXT)))
            .andExpect(jsonPath("$.[*].linkURL").value(hasItem(DEFAULT_LINK_URL)));
    }
    
    @Test
    @Transactional
    public void getUrllink() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get the urllink
        restUrllinkMockMvc.perform(get("/api/urllinks/{id}", urllink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(urllink.getId().intValue()))
            .andExpect(jsonPath("$.linkText").value(DEFAULT_LINK_TEXT))
            .andExpect(jsonPath("$.linkURL").value(DEFAULT_LINK_URL));
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkTextIsEqualToSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText equals to DEFAULT_LINK_TEXT
        defaultUrllinkShouldBeFound("linkText.equals=" + DEFAULT_LINK_TEXT);

        // Get all the urllinkList where linkText equals to UPDATED_LINK_TEXT
        defaultUrllinkShouldNotBeFound("linkText.equals=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText not equals to DEFAULT_LINK_TEXT
        defaultUrllinkShouldNotBeFound("linkText.notEquals=" + DEFAULT_LINK_TEXT);

        // Get all the urllinkList where linkText not equals to UPDATED_LINK_TEXT
        defaultUrllinkShouldBeFound("linkText.notEquals=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkTextIsInShouldWork() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText in DEFAULT_LINK_TEXT or UPDATED_LINK_TEXT
        defaultUrllinkShouldBeFound("linkText.in=" + DEFAULT_LINK_TEXT + "," + UPDATED_LINK_TEXT);

        // Get all the urllinkList where linkText equals to UPDATED_LINK_TEXT
        defaultUrllinkShouldNotBeFound("linkText.in=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText is not null
        defaultUrllinkShouldBeFound("linkText.specified=true");

        // Get all the urllinkList where linkText is null
        defaultUrllinkShouldNotBeFound("linkText.specified=false");
    }
                @Test
    @Transactional
    public void getAllUrllinksByLinkTextContainsSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText contains DEFAULT_LINK_TEXT
        defaultUrllinkShouldBeFound("linkText.contains=" + DEFAULT_LINK_TEXT);

        // Get all the urllinkList where linkText contains UPDATED_LINK_TEXT
        defaultUrllinkShouldNotBeFound("linkText.contains=" + UPDATED_LINK_TEXT);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkTextNotContainsSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkText does not contain DEFAULT_LINK_TEXT
        defaultUrllinkShouldNotBeFound("linkText.doesNotContain=" + DEFAULT_LINK_TEXT);

        // Get all the urllinkList where linkText does not contain UPDATED_LINK_TEXT
        defaultUrllinkShouldBeFound("linkText.doesNotContain=" + UPDATED_LINK_TEXT);
    }


    @Test
    @Transactional
    public void getAllUrllinksByLinkURLIsEqualToSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL equals to DEFAULT_LINK_URL
        defaultUrllinkShouldBeFound("linkURL.equals=" + DEFAULT_LINK_URL);

        // Get all the urllinkList where linkURL equals to UPDATED_LINK_URL
        defaultUrllinkShouldNotBeFound("linkURL.equals=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkURLIsNotEqualToSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL not equals to DEFAULT_LINK_URL
        defaultUrllinkShouldNotBeFound("linkURL.notEquals=" + DEFAULT_LINK_URL);

        // Get all the urllinkList where linkURL not equals to UPDATED_LINK_URL
        defaultUrllinkShouldBeFound("linkURL.notEquals=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkURLIsInShouldWork() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL in DEFAULT_LINK_URL or UPDATED_LINK_URL
        defaultUrllinkShouldBeFound("linkURL.in=" + DEFAULT_LINK_URL + "," + UPDATED_LINK_URL);

        // Get all the urllinkList where linkURL equals to UPDATED_LINK_URL
        defaultUrllinkShouldNotBeFound("linkURL.in=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL is not null
        defaultUrllinkShouldBeFound("linkURL.specified=true");

        // Get all the urllinkList where linkURL is null
        defaultUrllinkShouldNotBeFound("linkURL.specified=false");
    }
                @Test
    @Transactional
    public void getAllUrllinksByLinkURLContainsSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL contains DEFAULT_LINK_URL
        defaultUrllinkShouldBeFound("linkURL.contains=" + DEFAULT_LINK_URL);

        // Get all the urllinkList where linkURL contains UPDATED_LINK_URL
        defaultUrllinkShouldNotBeFound("linkURL.contains=" + UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    public void getAllUrllinksByLinkURLNotContainsSomething() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        // Get all the urllinkList where linkURL does not contain DEFAULT_LINK_URL
        defaultUrllinkShouldNotBeFound("linkURL.doesNotContain=" + DEFAULT_LINK_URL);

        // Get all the urllinkList where linkURL does not contain UPDATED_LINK_URL
        defaultUrllinkShouldBeFound("linkURL.doesNotContain=" + UPDATED_LINK_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUrllinkShouldBeFound(String filter) throws Exception {
        restUrllinkMockMvc.perform(get("/api/urllinks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(urllink.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkText").value(hasItem(DEFAULT_LINK_TEXT)))
            .andExpect(jsonPath("$.[*].linkURL").value(hasItem(DEFAULT_LINK_URL)));

        // Check, that the count call also returns 1
        restUrllinkMockMvc.perform(get("/api/urllinks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUrllinkShouldNotBeFound(String filter) throws Exception {
        restUrllinkMockMvc.perform(get("/api/urllinks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUrllinkMockMvc.perform(get("/api/urllinks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUrllink() throws Exception {
        // Get the urllink
        restUrllinkMockMvc.perform(get("/api/urllinks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUrllink() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        int databaseSizeBeforeUpdate = urllinkRepository.findAll().size();

        // Update the urllink
        Urllink updatedUrllink = urllinkRepository.findById(urllink.getId()).get();
        // Disconnect from session so that the updates on updatedUrllink are not directly saved in db
        em.detach(updatedUrllink);
        updatedUrllink
            .linkText(UPDATED_LINK_TEXT)
            .linkURL(UPDATED_LINK_URL);
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(updatedUrllink);

        restUrllinkMockMvc.perform(put("/api/urllinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urllinkDTO)))
            .andExpect(status().isOk());

        // Validate the Urllink in the database
        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeUpdate);
        Urllink testUrllink = urllinkList.get(urllinkList.size() - 1);
        assertThat(testUrllink.getLinkText()).isEqualTo(UPDATED_LINK_TEXT);
        assertThat(testUrllink.getLinkURL()).isEqualTo(UPDATED_LINK_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingUrllink() throws Exception {
        int databaseSizeBeforeUpdate = urllinkRepository.findAll().size();

        // Create the Urllink
        UrllinkDTO urllinkDTO = urllinkMapper.toDto(urllink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrllinkMockMvc.perform(put("/api/urllinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(urllinkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Urllink in the database
        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUrllink() throws Exception {
        // Initialize the database
        urllinkRepository.saveAndFlush(urllink);

        int databaseSizeBeforeDelete = urllinkRepository.findAll().size();

        // Delete the urllink
        restUrllinkMockMvc.perform(delete("/api/urllinks/{id}", urllink.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Urllink> urllinkList = urllinkRepository.findAll();
        assertThat(urllinkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Urllink.class);
        Urllink urllink1 = new Urllink();
        urllink1.setId(1L);
        Urllink urllink2 = new Urllink();
        urllink2.setId(urllink1.getId());
        assertThat(urllink1).isEqualTo(urllink2);
        urllink2.setId(2L);
        assertThat(urllink1).isNotEqualTo(urllink2);
        urllink1.setId(null);
        assertThat(urllink1).isNotEqualTo(urllink2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrllinkDTO.class);
        UrllinkDTO urllinkDTO1 = new UrllinkDTO();
        urllinkDTO1.setId(1L);
        UrllinkDTO urllinkDTO2 = new UrllinkDTO();
        assertThat(urllinkDTO1).isNotEqualTo(urllinkDTO2);
        urllinkDTO2.setId(urllinkDTO1.getId());
        assertThat(urllinkDTO1).isEqualTo(urllinkDTO2);
        urllinkDTO2.setId(2L);
        assertThat(urllinkDTO1).isNotEqualTo(urllinkDTO2);
        urllinkDTO1.setId(null);
        assertThat(urllinkDTO1).isNotEqualTo(urllinkDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(urllinkMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(urllinkMapper.fromId(null)).isNull();
    }
}
