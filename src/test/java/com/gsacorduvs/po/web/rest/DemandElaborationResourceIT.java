package com.gsacorduvs.po.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gsacorduvs.po.IntegrationTest;
import com.gsacorduvs.po.domain.DemandElaboration;
import com.gsacorduvs.po.domain.enumeration.TypeAccord;
import com.gsacorduvs.po.repository.DemandElaborationRepository;
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
 * Integration tests for the {@link DemandElaborationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandElaborationResourceIT {

    private static final TypeAccord DEFAULT_TYPE_ACCORD = TypeAccord.ACCORD;
    private static final TypeAccord UPDATED_TYPE_ACCORD = TypeAccord.AVENNANT;

    private static final String DEFAULT_TITRE_DEMAND = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_DEMAND = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEMAN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEMAN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FORME_ACCORD = "AAAAAAAAAA";
    private static final String UPDATED_FORME_ACCORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SIGNATUREDIRCOOR = false;
    private static final Boolean UPDATED_SIGNATUREDIRCOOR = true;

    private static final String ENTITY_API_URL = "/api/demand-elaborations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandElaborationRepository demandElaborationRepository;

    @Mock
    private DemandElaborationRepository demandElaborationRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandElaborationMockMvc;

    private DemandElaboration demandElaboration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandElaboration createEntity(EntityManager em) {
        DemandElaboration demandElaboration = new DemandElaboration()
            .typeAccord(DEFAULT_TYPE_ACCORD)
            .titreDemand(DEFAULT_TITRE_DEMAND)
            .dateDeman(DEFAULT_DATE_DEMAN)
            .formeAccord(DEFAULT_FORME_ACCORD)
            .signaturedircoor(DEFAULT_SIGNATUREDIRCOOR);
        return demandElaboration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandElaboration createUpdatedEntity(EntityManager em) {
        DemandElaboration demandElaboration = new DemandElaboration()
            .typeAccord(UPDATED_TYPE_ACCORD)
            .titreDemand(UPDATED_TITRE_DEMAND)
            .dateDeman(UPDATED_DATE_DEMAN)
            .formeAccord(UPDATED_FORME_ACCORD)
            .signaturedircoor(UPDATED_SIGNATUREDIRCOOR);
        return demandElaboration;
    }

    @BeforeEach
    public void initTest() {
        demandElaboration = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandElaboration() throws Exception {
        int databaseSizeBeforeCreate = demandElaborationRepository.findAll().size();
        // Create the DemandElaboration
        restDemandElaborationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isCreated());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeCreate + 1);
        DemandElaboration testDemandElaboration = demandElaborationList.get(demandElaborationList.size() - 1);
        assertThat(testDemandElaboration.getTypeAccord()).isEqualTo(DEFAULT_TYPE_ACCORD);
        assertThat(testDemandElaboration.getTitreDemand()).isEqualTo(DEFAULT_TITRE_DEMAND);
        assertThat(testDemandElaboration.getDateDeman()).isEqualTo(DEFAULT_DATE_DEMAN);
        assertThat(testDemandElaboration.getFormeAccord()).isEqualTo(DEFAULT_FORME_ACCORD);
        assertThat(testDemandElaboration.getSignaturedircoor()).isEqualTo(DEFAULT_SIGNATUREDIRCOOR);
    }

    @Test
    @Transactional
    void createDemandElaborationWithExistingId() throws Exception {
        // Create the DemandElaboration with an existing ID
        demandElaboration.setId(1L);

        int databaseSizeBeforeCreate = demandElaborationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandElaborationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandElaborations() throws Exception {
        // Initialize the database
        demandElaborationRepository.saveAndFlush(demandElaboration);

        // Get all the demandElaborationList
        restDemandElaborationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandElaboration.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeAccord").value(hasItem(DEFAULT_TYPE_ACCORD.toString())))
            .andExpect(jsonPath("$.[*].titreDemand").value(hasItem(DEFAULT_TITRE_DEMAND)))
            .andExpect(jsonPath("$.[*].dateDeman").value(hasItem(DEFAULT_DATE_DEMAN.toString())))
            .andExpect(jsonPath("$.[*].formeAccord").value(hasItem(DEFAULT_FORME_ACCORD)))
            .andExpect(jsonPath("$.[*].signaturedircoor").value(hasItem(DEFAULT_SIGNATUREDIRCOOR.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandElaborationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(demandElaborationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandElaborationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandElaborationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandElaborationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(demandElaborationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandElaborationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandElaborationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDemandElaboration() throws Exception {
        // Initialize the database
        demandElaborationRepository.saveAndFlush(demandElaboration);

        // Get the demandElaboration
        restDemandElaborationMockMvc
            .perform(get(ENTITY_API_URL_ID, demandElaboration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandElaboration.getId().intValue()))
            .andExpect(jsonPath("$.typeAccord").value(DEFAULT_TYPE_ACCORD.toString()))
            .andExpect(jsonPath("$.titreDemand").value(DEFAULT_TITRE_DEMAND))
            .andExpect(jsonPath("$.dateDeman").value(DEFAULT_DATE_DEMAN.toString()))
            .andExpect(jsonPath("$.formeAccord").value(DEFAULT_FORME_ACCORD))
            .andExpect(jsonPath("$.signaturedircoor").value(DEFAULT_SIGNATUREDIRCOOR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDemandElaboration() throws Exception {
        // Get the demandElaboration
        restDemandElaborationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandElaboration() throws Exception {
        // Initialize the database
        demandElaborationRepository.saveAndFlush(demandElaboration);

        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();

        // Update the demandElaboration
        DemandElaboration updatedDemandElaboration = demandElaborationRepository.findById(demandElaboration.getId()).get();
        // Disconnect from session so that the updates on updatedDemandElaboration are not directly saved in db
        em.detach(updatedDemandElaboration);
        updatedDemandElaboration
            .typeAccord(UPDATED_TYPE_ACCORD)
            .titreDemand(UPDATED_TITRE_DEMAND)
            .dateDeman(UPDATED_DATE_DEMAN)
            .formeAccord(UPDATED_FORME_ACCORD)
            .signaturedircoor(UPDATED_SIGNATUREDIRCOOR);

        restDemandElaborationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandElaboration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandElaboration))
            )
            .andExpect(status().isOk());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
        DemandElaboration testDemandElaboration = demandElaborationList.get(demandElaborationList.size() - 1);
        assertThat(testDemandElaboration.getTypeAccord()).isEqualTo(UPDATED_TYPE_ACCORD);
        assertThat(testDemandElaboration.getTitreDemand()).isEqualTo(UPDATED_TITRE_DEMAND);
        assertThat(testDemandElaboration.getDateDeman()).isEqualTo(UPDATED_DATE_DEMAN);
        assertThat(testDemandElaboration.getFormeAccord()).isEqualTo(UPDATED_FORME_ACCORD);
        assertThat(testDemandElaboration.getSignaturedircoor()).isEqualTo(UPDATED_SIGNATUREDIRCOOR);
    }

    @Test
    @Transactional
    void putNonExistingDemandElaboration() throws Exception {
        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();
        demandElaboration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandElaborationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandElaboration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandElaboration() throws Exception {
        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();
        demandElaboration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandElaborationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandElaboration() throws Exception {
        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();
        demandElaboration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandElaborationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandElaborationWithPatch() throws Exception {
        // Initialize the database
        demandElaborationRepository.saveAndFlush(demandElaboration);

        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();

        // Update the demandElaboration using partial update
        DemandElaboration partialUpdatedDemandElaboration = new DemandElaboration();
        partialUpdatedDemandElaboration.setId(demandElaboration.getId());

        partialUpdatedDemandElaboration
            .typeAccord(UPDATED_TYPE_ACCORD)
            .titreDemand(UPDATED_TITRE_DEMAND)
            .dateDeman(UPDATED_DATE_DEMAN)
            .formeAccord(UPDATED_FORME_ACCORD)
            .signaturedircoor(UPDATED_SIGNATUREDIRCOOR);

        restDemandElaborationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandElaboration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandElaboration))
            )
            .andExpect(status().isOk());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
        DemandElaboration testDemandElaboration = demandElaborationList.get(demandElaborationList.size() - 1);
        assertThat(testDemandElaboration.getTypeAccord()).isEqualTo(UPDATED_TYPE_ACCORD);
        assertThat(testDemandElaboration.getTitreDemand()).isEqualTo(UPDATED_TITRE_DEMAND);
        assertThat(testDemandElaboration.getDateDeman()).isEqualTo(UPDATED_DATE_DEMAN);
        assertThat(testDemandElaboration.getFormeAccord()).isEqualTo(UPDATED_FORME_ACCORD);
        assertThat(testDemandElaboration.getSignaturedircoor()).isEqualTo(UPDATED_SIGNATUREDIRCOOR);
    }

    @Test
    @Transactional
    void fullUpdateDemandElaborationWithPatch() throws Exception {
        // Initialize the database
        demandElaborationRepository.saveAndFlush(demandElaboration);

        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();

        // Update the demandElaboration using partial update
        DemandElaboration partialUpdatedDemandElaboration = new DemandElaboration();
        partialUpdatedDemandElaboration.setId(demandElaboration.getId());

        partialUpdatedDemandElaboration
            .typeAccord(UPDATED_TYPE_ACCORD)
            .titreDemand(UPDATED_TITRE_DEMAND)
            .dateDeman(UPDATED_DATE_DEMAN)
            .formeAccord(UPDATED_FORME_ACCORD)
            .signaturedircoor(UPDATED_SIGNATUREDIRCOOR);

        restDemandElaborationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandElaboration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandElaboration))
            )
            .andExpect(status().isOk());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
        DemandElaboration testDemandElaboration = demandElaborationList.get(demandElaborationList.size() - 1);
        assertThat(testDemandElaboration.getTypeAccord()).isEqualTo(UPDATED_TYPE_ACCORD);
        assertThat(testDemandElaboration.getTitreDemand()).isEqualTo(UPDATED_TITRE_DEMAND);
        assertThat(testDemandElaboration.getDateDeman()).isEqualTo(UPDATED_DATE_DEMAN);
        assertThat(testDemandElaboration.getFormeAccord()).isEqualTo(UPDATED_FORME_ACCORD);
        assertThat(testDemandElaboration.getSignaturedircoor()).isEqualTo(UPDATED_SIGNATUREDIRCOOR);
    }

    @Test
    @Transactional
    void patchNonExistingDemandElaboration() throws Exception {
        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();
        demandElaboration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandElaborationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandElaboration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandElaboration() throws Exception {
        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();
        demandElaboration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandElaborationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandElaboration() throws Exception {
        int databaseSizeBeforeUpdate = demandElaborationRepository.findAll().size();
        demandElaboration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandElaborationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandElaboration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandElaboration in the database
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandElaboration() throws Exception {
        // Initialize the database
        demandElaborationRepository.saveAndFlush(demandElaboration);

        int databaseSizeBeforeDelete = demandElaborationRepository.findAll().size();

        // Delete the demandElaboration
        restDemandElaborationMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandElaboration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandElaboration> demandElaborationList = demandElaborationRepository.findAll();
        assertThat(demandElaborationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
