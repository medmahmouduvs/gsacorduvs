package com.gsacorduvs.po.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gsacorduvs.po.IntegrationTest;
import com.gsacorduvs.po.domain.EtudeAccord;
import com.gsacorduvs.po.repository.EtudeAccordRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EtudeAccordResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EtudeAccordResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ETUDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ETUDE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIVE_DIR_COOR = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVE_DIR_COOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SIGNATUREREACTERU = false;
    private static final Boolean UPDATED_SIGNATUREREACTERU = true;

    private static final Boolean DEFAULT_SIGNATURE_DIIRCORE = false;
    private static final Boolean UPDATED_SIGNATURE_DIIRCORE = true;

    private static final Boolean DEFAULT_SIGNATURE_CHEF_ETAB = false;
    private static final Boolean UPDATED_SIGNATURE_CHEF_ETAB = true;

    private static final String ENTITY_API_URL = "/api/etude-accords";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtudeAccordRepository etudeAccordRepository;

    @Mock
    private EtudeAccordRepository etudeAccordRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtudeAccordMockMvc;

    private EtudeAccord etudeAccord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtudeAccord createEntity(EntityManager em) {
        EtudeAccord etudeAccord = new EtudeAccord()
            .titre(DEFAULT_TITRE)
            .dateEtude(DEFAULT_DATE_ETUDE)
            .motiveDirCoor(DEFAULT_MOTIVE_DIR_COOR)
            .signaturereacteru(DEFAULT_SIGNATUREREACTERU)
            .signatureDiircore(DEFAULT_SIGNATURE_DIIRCORE)
            .signatureChefEtab(DEFAULT_SIGNATURE_CHEF_ETAB);
        return etudeAccord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtudeAccord createUpdatedEntity(EntityManager em) {
        EtudeAccord etudeAccord = new EtudeAccord()
            .titre(UPDATED_TITRE)
            .dateEtude(UPDATED_DATE_ETUDE)
            .motiveDirCoor(UPDATED_MOTIVE_DIR_COOR)
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB);
        return etudeAccord;
    }

    @BeforeEach
    public void initTest() {
        etudeAccord = createEntity(em);
    }

    @Test
    @Transactional
    void createEtudeAccord() throws Exception {
        int databaseSizeBeforeCreate = etudeAccordRepository.findAll().size();
        // Create the EtudeAccord
        restEtudeAccordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etudeAccord)))
            .andExpect(status().isCreated());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeCreate + 1);
        EtudeAccord testEtudeAccord = etudeAccordList.get(etudeAccordList.size() - 1);
        assertThat(testEtudeAccord.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testEtudeAccord.getDateEtude()).isEqualTo(DEFAULT_DATE_ETUDE);
        assertThat(testEtudeAccord.getMotiveDirCoor()).isEqualTo(DEFAULT_MOTIVE_DIR_COOR);
        assertThat(testEtudeAccord.getSignaturereacteru()).isEqualTo(DEFAULT_SIGNATUREREACTERU);
        assertThat(testEtudeAccord.getSignatureDiircore()).isEqualTo(DEFAULT_SIGNATURE_DIIRCORE);
        assertThat(testEtudeAccord.getSignatureChefEtab()).isEqualTo(DEFAULT_SIGNATURE_CHEF_ETAB);
    }

    @Test
    @Transactional
    void createEtudeAccordWithExistingId() throws Exception {
        // Create the EtudeAccord with an existing ID
        etudeAccord.setId(1L);

        int databaseSizeBeforeCreate = etudeAccordRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtudeAccordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etudeAccord)))
            .andExpect(status().isBadRequest());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtudeAccords() throws Exception {
        // Initialize the database
        etudeAccordRepository.saveAndFlush(etudeAccord);

        // Get all the etudeAccordList
        restEtudeAccordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudeAccord.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].dateEtude").value(hasItem(DEFAULT_DATE_ETUDE.toString())))
            .andExpect(jsonPath("$.[*].motiveDirCoor").value(hasItem(DEFAULT_MOTIVE_DIR_COOR)))
            .andExpect(jsonPath("$.[*].signaturereacteru").value(hasItem(DEFAULT_SIGNATUREREACTERU.booleanValue())))
            .andExpect(jsonPath("$.[*].signatureDiircore").value(hasItem(DEFAULT_SIGNATURE_DIIRCORE.booleanValue())))
            .andExpect(jsonPath("$.[*].signatureChefEtab").value(hasItem(DEFAULT_SIGNATURE_CHEF_ETAB.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEtudeAccordsWithEagerRelationshipsIsEnabled() throws Exception {
        when(etudeAccordRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEtudeAccordMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(etudeAccordRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEtudeAccordsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(etudeAccordRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEtudeAccordMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(etudeAccordRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEtudeAccord() throws Exception {
        // Initialize the database
        etudeAccordRepository.saveAndFlush(etudeAccord);

        // Get the etudeAccord
        restEtudeAccordMockMvc
            .perform(get(ENTITY_API_URL_ID, etudeAccord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etudeAccord.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.dateEtude").value(DEFAULT_DATE_ETUDE.toString()))
            .andExpect(jsonPath("$.motiveDirCoor").value(DEFAULT_MOTIVE_DIR_COOR))
            .andExpect(jsonPath("$.signaturereacteru").value(DEFAULT_SIGNATUREREACTERU.booleanValue()))
            .andExpect(jsonPath("$.signatureDiircore").value(DEFAULT_SIGNATURE_DIIRCORE.booleanValue()))
            .andExpect(jsonPath("$.signatureChefEtab").value(DEFAULT_SIGNATURE_CHEF_ETAB.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEtudeAccord() throws Exception {
        // Get the etudeAccord
        restEtudeAccordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtudeAccord() throws Exception {
        // Initialize the database
        etudeAccordRepository.saveAndFlush(etudeAccord);

        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();

        // Update the etudeAccord
        EtudeAccord updatedEtudeAccord = etudeAccordRepository.findById(etudeAccord.getId()).get();
        // Disconnect from session so that the updates on updatedEtudeAccord are not directly saved in db
        em.detach(updatedEtudeAccord);
        updatedEtudeAccord
            .titre(UPDATED_TITRE)
            .dateEtude(UPDATED_DATE_ETUDE)
            .motiveDirCoor(UPDATED_MOTIVE_DIR_COOR)
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB);

        restEtudeAccordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtudeAccord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtudeAccord))
            )
            .andExpect(status().isOk());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
        EtudeAccord testEtudeAccord = etudeAccordList.get(etudeAccordList.size() - 1);
        assertThat(testEtudeAccord.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEtudeAccord.getDateEtude()).isEqualTo(UPDATED_DATE_ETUDE);
        assertThat(testEtudeAccord.getMotiveDirCoor()).isEqualTo(UPDATED_MOTIVE_DIR_COOR);
        assertThat(testEtudeAccord.getSignaturereacteru()).isEqualTo(UPDATED_SIGNATUREREACTERU);
        assertThat(testEtudeAccord.getSignatureDiircore()).isEqualTo(UPDATED_SIGNATURE_DIIRCORE);
        assertThat(testEtudeAccord.getSignatureChefEtab()).isEqualTo(UPDATED_SIGNATURE_CHEF_ETAB);
    }

    @Test
    @Transactional
    void putNonExistingEtudeAccord() throws Exception {
        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();
        etudeAccord.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtudeAccordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etudeAccord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etudeAccord))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtudeAccord() throws Exception {
        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();
        etudeAccord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeAccordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etudeAccord))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtudeAccord() throws Exception {
        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();
        etudeAccord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeAccordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etudeAccord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtudeAccordWithPatch() throws Exception {
        // Initialize the database
        etudeAccordRepository.saveAndFlush(etudeAccord);

        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();

        // Update the etudeAccord using partial update
        EtudeAccord partialUpdatedEtudeAccord = new EtudeAccord();
        partialUpdatedEtudeAccord.setId(etudeAccord.getId());

        partialUpdatedEtudeAccord
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB);

        restEtudeAccordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtudeAccord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtudeAccord))
            )
            .andExpect(status().isOk());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
        EtudeAccord testEtudeAccord = etudeAccordList.get(etudeAccordList.size() - 1);
        assertThat(testEtudeAccord.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testEtudeAccord.getDateEtude()).isEqualTo(DEFAULT_DATE_ETUDE);
        assertThat(testEtudeAccord.getMotiveDirCoor()).isEqualTo(DEFAULT_MOTIVE_DIR_COOR);
        assertThat(testEtudeAccord.getSignaturereacteru()).isEqualTo(UPDATED_SIGNATUREREACTERU);
        assertThat(testEtudeAccord.getSignatureDiircore()).isEqualTo(UPDATED_SIGNATURE_DIIRCORE);
        assertThat(testEtudeAccord.getSignatureChefEtab()).isEqualTo(UPDATED_SIGNATURE_CHEF_ETAB);
    }

    @Test
    @Transactional
    void fullUpdateEtudeAccordWithPatch() throws Exception {
        // Initialize the database
        etudeAccordRepository.saveAndFlush(etudeAccord);

        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();

        // Update the etudeAccord using partial update
        EtudeAccord partialUpdatedEtudeAccord = new EtudeAccord();
        partialUpdatedEtudeAccord.setId(etudeAccord.getId());

        partialUpdatedEtudeAccord
            .titre(UPDATED_TITRE)
            .dateEtude(UPDATED_DATE_ETUDE)
            .motiveDirCoor(UPDATED_MOTIVE_DIR_COOR)
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB);

        restEtudeAccordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtudeAccord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtudeAccord))
            )
            .andExpect(status().isOk());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
        EtudeAccord testEtudeAccord = etudeAccordList.get(etudeAccordList.size() - 1);
        assertThat(testEtudeAccord.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEtudeAccord.getDateEtude()).isEqualTo(UPDATED_DATE_ETUDE);
        assertThat(testEtudeAccord.getMotiveDirCoor()).isEqualTo(UPDATED_MOTIVE_DIR_COOR);
        assertThat(testEtudeAccord.getSignaturereacteru()).isEqualTo(UPDATED_SIGNATUREREACTERU);
        assertThat(testEtudeAccord.getSignatureDiircore()).isEqualTo(UPDATED_SIGNATURE_DIIRCORE);
        assertThat(testEtudeAccord.getSignatureChefEtab()).isEqualTo(UPDATED_SIGNATURE_CHEF_ETAB);
    }

    @Test
    @Transactional
    void patchNonExistingEtudeAccord() throws Exception {
        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();
        etudeAccord.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtudeAccordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etudeAccord.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etudeAccord))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtudeAccord() throws Exception {
        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();
        etudeAccord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeAccordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etudeAccord))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtudeAccord() throws Exception {
        int databaseSizeBeforeUpdate = etudeAccordRepository.findAll().size();
        etudeAccord.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtudeAccordMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etudeAccord))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtudeAccord in the database
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtudeAccord() throws Exception {
        // Initialize the database
        etudeAccordRepository.saveAndFlush(etudeAccord);

        int databaseSizeBeforeDelete = etudeAccordRepository.findAll().size();

        // Delete the etudeAccord
        restEtudeAccordMockMvc
            .perform(delete(ENTITY_API_URL_ID, etudeAccord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtudeAccord> etudeAccordList = etudeAccordRepository.findAll();
        assertThat(etudeAccordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
