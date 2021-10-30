package com.gsacorduvs.po.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gsacorduvs.po.IntegrationTest;
import com.gsacorduvs.po.domain.EtablisemntParten;
import com.gsacorduvs.po.repository.EtablisemntPartenRepository;
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
 * Integration tests for the {@link EtablisemntPartenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtablisemntPartenResourceIT {

    private static final String DEFAULT_CONTRY = "AAAAAAAAAA";
    private static final String UPDATED_CONTRY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ETAB = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_MENTION = "AAAAAAAAAA";
    private static final String UPDATED_MENTION = "BBBBBBBBBB";

    private static final String DEFAULT_REPRESENTANTNAME = "AAAAAAAAAA";
    private static final String UPDATED_REPRESENTANTNAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etablisemnt-partens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtablisemntPartenRepository etablisemntPartenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtablisemntPartenMockMvc;

    private EtablisemntParten etablisemntParten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtablisemntParten createEntity(EntityManager em) {
        EtablisemntParten etablisemntParten = new EtablisemntParten()
            .contry(DEFAULT_CONTRY)
            .nameEtab(DEFAULT_NAME_ETAB)
            .domain(DEFAULT_DOMAIN)
            .mention(DEFAULT_MENTION)
            .representantname(DEFAULT_REPRESENTANTNAME);
        return etablisemntParten;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtablisemntParten createUpdatedEntity(EntityManager em) {
        EtablisemntParten etablisemntParten = new EtablisemntParten()
            .contry(UPDATED_CONTRY)
            .nameEtab(UPDATED_NAME_ETAB)
            .domain(UPDATED_DOMAIN)
            .mention(UPDATED_MENTION)
            .representantname(UPDATED_REPRESENTANTNAME);
        return etablisemntParten;
    }

    @BeforeEach
    public void initTest() {
        etablisemntParten = createEntity(em);
    }

    @Test
    @Transactional
    void createEtablisemntParten() throws Exception {
        int databaseSizeBeforeCreate = etablisemntPartenRepository.findAll().size();
        // Create the EtablisemntParten
        restEtablisemntPartenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isCreated());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeCreate + 1);
        EtablisemntParten testEtablisemntParten = etablisemntPartenList.get(etablisemntPartenList.size() - 1);
        assertThat(testEtablisemntParten.getContry()).isEqualTo(DEFAULT_CONTRY);
        assertThat(testEtablisemntParten.getNameEtab()).isEqualTo(DEFAULT_NAME_ETAB);
        assertThat(testEtablisemntParten.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testEtablisemntParten.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testEtablisemntParten.getRepresentantname()).isEqualTo(DEFAULT_REPRESENTANTNAME);
    }

    @Test
    @Transactional
    void createEtablisemntPartenWithExistingId() throws Exception {
        // Create the EtablisemntParten with an existing ID
        etablisemntParten.setId(1L);

        int databaseSizeBeforeCreate = etablisemntPartenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtablisemntPartenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtablisemntPartens() throws Exception {
        // Initialize the database
        etablisemntPartenRepository.saveAndFlush(etablisemntParten);

        // Get all the etablisemntPartenList
        restEtablisemntPartenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etablisemntParten.getId().intValue())))
            .andExpect(jsonPath("$.[*].contry").value(hasItem(DEFAULT_CONTRY)))
            .andExpect(jsonPath("$.[*].nameEtab").value(hasItem(DEFAULT_NAME_ETAB)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION)))
            .andExpect(jsonPath("$.[*].representantname").value(hasItem(DEFAULT_REPRESENTANTNAME)));
    }

    @Test
    @Transactional
    void getEtablisemntParten() throws Exception {
        // Initialize the database
        etablisemntPartenRepository.saveAndFlush(etablisemntParten);

        // Get the etablisemntParten
        restEtablisemntPartenMockMvc
            .perform(get(ENTITY_API_URL_ID, etablisemntParten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etablisemntParten.getId().intValue()))
            .andExpect(jsonPath("$.contry").value(DEFAULT_CONTRY))
            .andExpect(jsonPath("$.nameEtab").value(DEFAULT_NAME_ETAB))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION))
            .andExpect(jsonPath("$.representantname").value(DEFAULT_REPRESENTANTNAME));
    }

    @Test
    @Transactional
    void getNonExistingEtablisemntParten() throws Exception {
        // Get the etablisemntParten
        restEtablisemntPartenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtablisemntParten() throws Exception {
        // Initialize the database
        etablisemntPartenRepository.saveAndFlush(etablisemntParten);

        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();

        // Update the etablisemntParten
        EtablisemntParten updatedEtablisemntParten = etablisemntPartenRepository.findById(etablisemntParten.getId()).get();
        // Disconnect from session so that the updates on updatedEtablisemntParten are not directly saved in db
        em.detach(updatedEtablisemntParten);
        updatedEtablisemntParten
            .contry(UPDATED_CONTRY)
            .nameEtab(UPDATED_NAME_ETAB)
            .domain(UPDATED_DOMAIN)
            .mention(UPDATED_MENTION)
            .representantname(UPDATED_REPRESENTANTNAME);

        restEtablisemntPartenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtablisemntParten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtablisemntParten))
            )
            .andExpect(status().isOk());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
        EtablisemntParten testEtablisemntParten = etablisemntPartenList.get(etablisemntPartenList.size() - 1);
        assertThat(testEtablisemntParten.getContry()).isEqualTo(UPDATED_CONTRY);
        assertThat(testEtablisemntParten.getNameEtab()).isEqualTo(UPDATED_NAME_ETAB);
        assertThat(testEtablisemntParten.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testEtablisemntParten.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testEtablisemntParten.getRepresentantname()).isEqualTo(UPDATED_REPRESENTANTNAME);
    }

    @Test
    @Transactional
    void putNonExistingEtablisemntParten() throws Exception {
        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();
        etablisemntParten.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablisemntPartenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etablisemntParten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtablisemntParten() throws Exception {
        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();
        etablisemntParten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisemntPartenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtablisemntParten() throws Exception {
        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();
        etablisemntParten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisemntPartenMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtablisemntPartenWithPatch() throws Exception {
        // Initialize the database
        etablisemntPartenRepository.saveAndFlush(etablisemntParten);

        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();

        // Update the etablisemntParten using partial update
        EtablisemntParten partialUpdatedEtablisemntParten = new EtablisemntParten();
        partialUpdatedEtablisemntParten.setId(etablisemntParten.getId());

        partialUpdatedEtablisemntParten
            .nameEtab(UPDATED_NAME_ETAB)
            .domain(UPDATED_DOMAIN)
            .mention(UPDATED_MENTION)
            .representantname(UPDATED_REPRESENTANTNAME);

        restEtablisemntPartenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablisemntParten.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtablisemntParten))
            )
            .andExpect(status().isOk());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
        EtablisemntParten testEtablisemntParten = etablisemntPartenList.get(etablisemntPartenList.size() - 1);
        assertThat(testEtablisemntParten.getContry()).isEqualTo(DEFAULT_CONTRY);
        assertThat(testEtablisemntParten.getNameEtab()).isEqualTo(UPDATED_NAME_ETAB);
        assertThat(testEtablisemntParten.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testEtablisemntParten.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testEtablisemntParten.getRepresentantname()).isEqualTo(UPDATED_REPRESENTANTNAME);
    }

    @Test
    @Transactional
    void fullUpdateEtablisemntPartenWithPatch() throws Exception {
        // Initialize the database
        etablisemntPartenRepository.saveAndFlush(etablisemntParten);

        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();

        // Update the etablisemntParten using partial update
        EtablisemntParten partialUpdatedEtablisemntParten = new EtablisemntParten();
        partialUpdatedEtablisemntParten.setId(etablisemntParten.getId());

        partialUpdatedEtablisemntParten
            .contry(UPDATED_CONTRY)
            .nameEtab(UPDATED_NAME_ETAB)
            .domain(UPDATED_DOMAIN)
            .mention(UPDATED_MENTION)
            .representantname(UPDATED_REPRESENTANTNAME);

        restEtablisemntPartenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablisemntParten.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtablisemntParten))
            )
            .andExpect(status().isOk());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
        EtablisemntParten testEtablisemntParten = etablisemntPartenList.get(etablisemntPartenList.size() - 1);
        assertThat(testEtablisemntParten.getContry()).isEqualTo(UPDATED_CONTRY);
        assertThat(testEtablisemntParten.getNameEtab()).isEqualTo(UPDATED_NAME_ETAB);
        assertThat(testEtablisemntParten.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testEtablisemntParten.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testEtablisemntParten.getRepresentantname()).isEqualTo(UPDATED_REPRESENTANTNAME);
    }

    @Test
    @Transactional
    void patchNonExistingEtablisemntParten() throws Exception {
        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();
        etablisemntParten.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablisemntPartenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etablisemntParten.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtablisemntParten() throws Exception {
        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();
        etablisemntParten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisemntPartenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtablisemntParten() throws Exception {
        int databaseSizeBeforeUpdate = etablisemntPartenRepository.findAll().size();
        etablisemntParten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisemntPartenMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etablisemntParten))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtablisemntParten in the database
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtablisemntParten() throws Exception {
        // Initialize the database
        etablisemntPartenRepository.saveAndFlush(etablisemntParten);

        int databaseSizeBeforeDelete = etablisemntPartenRepository.findAll().size();

        // Delete the etablisemntParten
        restEtablisemntPartenMockMvc
            .perform(delete(ENTITY_API_URL_ID, etablisemntParten.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtablisemntParten> etablisemntPartenList = etablisemntPartenRepository.findAll();
        assertThat(etablisemntPartenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
