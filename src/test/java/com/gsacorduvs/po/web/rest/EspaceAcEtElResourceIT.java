package com.gsacorduvs.po.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gsacorduvs.po.IntegrationTest;
import com.gsacorduvs.po.domain.EspaceAcEtEl;
import com.gsacorduvs.po.repository.EspaceAcEtElRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EspaceAcEtElResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspaceAcEtElResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/espace-ac-et-els";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EspaceAcEtElRepository espaceAcEtElRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspaceAcEtElMockMvc;

    private EspaceAcEtEl espaceAcEtEl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspaceAcEtEl createEntity(EntityManager em) {
        EspaceAcEtEl espaceAcEtEl = new EspaceAcEtEl().name(DEFAULT_NAME).handle(DEFAULT_HANDLE);
        return espaceAcEtEl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspaceAcEtEl createUpdatedEntity(EntityManager em) {
        EspaceAcEtEl espaceAcEtEl = new EspaceAcEtEl().name(UPDATED_NAME).handle(UPDATED_HANDLE);
        return espaceAcEtEl;
    }

    @BeforeEach
    public void initTest() {
        espaceAcEtEl = createEntity(em);
    }

    @Test
    @Transactional
    void createEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeCreate = espaceAcEtElRepository.findAll().size();
        // Create the EspaceAcEtEl
        restEspaceAcEtElMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl)))
            .andExpect(status().isCreated());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeCreate + 1);
        EspaceAcEtEl testEspaceAcEtEl = espaceAcEtElList.get(espaceAcEtElList.size() - 1);
        assertThat(testEspaceAcEtEl.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEspaceAcEtEl.getHandle()).isEqualTo(DEFAULT_HANDLE);
    }

    @Test
    @Transactional
    void createEspaceAcEtElWithExistingId() throws Exception {
        // Create the EspaceAcEtEl with an existing ID
        espaceAcEtEl.setId(1L);

        int databaseSizeBeforeCreate = espaceAcEtElRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspaceAcEtElMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl)))
            .andExpect(status().isBadRequest());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaceAcEtElRepository.findAll().size();
        // set the field null
        espaceAcEtEl.setName(null);

        // Create the EspaceAcEtEl, which fails.

        restEspaceAcEtElMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl)))
            .andExpect(status().isBadRequest());

        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHandleIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaceAcEtElRepository.findAll().size();
        // set the field null
        espaceAcEtEl.setHandle(null);

        // Create the EspaceAcEtEl, which fails.

        restEspaceAcEtElMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl)))
            .andExpect(status().isBadRequest());

        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEspaceAcEtEls() throws Exception {
        // Initialize the database
        espaceAcEtElRepository.saveAndFlush(espaceAcEtEl);

        // Get all the espaceAcEtElList
        restEspaceAcEtElMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espaceAcEtEl.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)));
    }

    @Test
    @Transactional
    void getEspaceAcEtEl() throws Exception {
        // Initialize the database
        espaceAcEtElRepository.saveAndFlush(espaceAcEtEl);

        // Get the espaceAcEtEl
        restEspaceAcEtElMockMvc
            .perform(get(ENTITY_API_URL_ID, espaceAcEtEl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(espaceAcEtEl.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE));
    }

    @Test
    @Transactional
    void getNonExistingEspaceAcEtEl() throws Exception {
        // Get the espaceAcEtEl
        restEspaceAcEtElMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEspaceAcEtEl() throws Exception {
        // Initialize the database
        espaceAcEtElRepository.saveAndFlush(espaceAcEtEl);

        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();

        // Update the espaceAcEtEl
        EspaceAcEtEl updatedEspaceAcEtEl = espaceAcEtElRepository.findById(espaceAcEtEl.getId()).get();
        // Disconnect from session so that the updates on updatedEspaceAcEtEl are not directly saved in db
        em.detach(updatedEspaceAcEtEl);
        updatedEspaceAcEtEl.name(UPDATED_NAME).handle(UPDATED_HANDLE);

        restEspaceAcEtElMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEspaceAcEtEl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEspaceAcEtEl))
            )
            .andExpect(status().isOk());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
        EspaceAcEtEl testEspaceAcEtEl = espaceAcEtElList.get(espaceAcEtElList.size() - 1);
        assertThat(testEspaceAcEtEl.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEspaceAcEtEl.getHandle()).isEqualTo(UPDATED_HANDLE);
    }

    @Test
    @Transactional
    void putNonExistingEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();
        espaceAcEtEl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspaceAcEtElMockMvc
            .perform(
                put(ENTITY_API_URL_ID, espaceAcEtEl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();
        espaceAcEtEl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspaceAcEtElMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();
        espaceAcEtEl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspaceAcEtElMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspaceAcEtElWithPatch() throws Exception {
        // Initialize the database
        espaceAcEtElRepository.saveAndFlush(espaceAcEtEl);

        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();

        // Update the espaceAcEtEl using partial update
        EspaceAcEtEl partialUpdatedEspaceAcEtEl = new EspaceAcEtEl();
        partialUpdatedEspaceAcEtEl.setId(espaceAcEtEl.getId());

        partialUpdatedEspaceAcEtEl.name(UPDATED_NAME).handle(UPDATED_HANDLE);

        restEspaceAcEtElMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspaceAcEtEl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspaceAcEtEl))
            )
            .andExpect(status().isOk());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
        EspaceAcEtEl testEspaceAcEtEl = espaceAcEtElList.get(espaceAcEtElList.size() - 1);
        assertThat(testEspaceAcEtEl.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEspaceAcEtEl.getHandle()).isEqualTo(UPDATED_HANDLE);
    }

    @Test
    @Transactional
    void fullUpdateEspaceAcEtElWithPatch() throws Exception {
        // Initialize the database
        espaceAcEtElRepository.saveAndFlush(espaceAcEtEl);

        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();

        // Update the espaceAcEtEl using partial update
        EspaceAcEtEl partialUpdatedEspaceAcEtEl = new EspaceAcEtEl();
        partialUpdatedEspaceAcEtEl.setId(espaceAcEtEl.getId());

        partialUpdatedEspaceAcEtEl.name(UPDATED_NAME).handle(UPDATED_HANDLE);

        restEspaceAcEtElMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspaceAcEtEl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspaceAcEtEl))
            )
            .andExpect(status().isOk());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
        EspaceAcEtEl testEspaceAcEtEl = espaceAcEtElList.get(espaceAcEtElList.size() - 1);
        assertThat(testEspaceAcEtEl.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEspaceAcEtEl.getHandle()).isEqualTo(UPDATED_HANDLE);
    }

    @Test
    @Transactional
    void patchNonExistingEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();
        espaceAcEtEl.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspaceAcEtElMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, espaceAcEtEl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();
        espaceAcEtEl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspaceAcEtElMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspaceAcEtEl() throws Exception {
        int databaseSizeBeforeUpdate = espaceAcEtElRepository.findAll().size();
        espaceAcEtEl.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspaceAcEtElMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(espaceAcEtEl))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EspaceAcEtEl in the database
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspaceAcEtEl() throws Exception {
        // Initialize the database
        espaceAcEtElRepository.saveAndFlush(espaceAcEtEl);

        int databaseSizeBeforeDelete = espaceAcEtElRepository.findAll().size();

        // Delete the espaceAcEtEl
        restEspaceAcEtElMockMvc
            .perform(delete(ENTITY_API_URL_ID, espaceAcEtEl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EspaceAcEtEl> espaceAcEtElList = espaceAcEtElRepository.findAll();
        assertThat(espaceAcEtElList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
