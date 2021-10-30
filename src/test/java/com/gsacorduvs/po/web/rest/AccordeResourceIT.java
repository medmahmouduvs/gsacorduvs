package com.gsacorduvs.po.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gsacorduvs.po.IntegrationTest;
import com.gsacorduvs.po.domain.Accorde;
import com.gsacorduvs.po.domain.enumeration.Statueoption;
import com.gsacorduvs.po.domain.enumeration.Teritoir;
import com.gsacorduvs.po.repository.AccordeRepository;
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
 * Integration tests for the {@link AccordeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccordeResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Teritoir DEFAULT_TERITORNATURE = Teritoir.INTERNATIONAL;
    private static final Teritoir UPDATED_TERITORNATURE = Teritoir.NATIONAL;

    private static final Statueoption DEFAULT_STATUSACORD = Statueoption.PROGRAMEE;
    private static final Statueoption UPDATED_STATUSACORD = Statueoption.DEROULEE;

    private static final LocalDate DEFAULT_DATE_ACCORD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCORD = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_SIGNATUREREACTERU = false;
    private static final Boolean UPDATED_SIGNATUREREACTERU = true;

    private static final Boolean DEFAULT_SIGNATURE_DIIRCORE = false;
    private static final Boolean UPDATED_SIGNATURE_DIIRCORE = true;

    private static final Boolean DEFAULT_SIGNATURE_CHEF_ETAB = false;
    private static final Boolean UPDATED_SIGNATURE_CHEF_ETAB = true;

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/accordes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccordeRepository accordeRepository;

    @Mock
    private AccordeRepository accordeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccordeMockMvc;

    private Accorde accorde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accorde createEntity(EntityManager em) {
        Accorde accorde = new Accorde()
            .titre(DEFAULT_TITRE)
            .teritornature(DEFAULT_TERITORNATURE)
            .statusacord(DEFAULT_STATUSACORD)
            .dateAccord(DEFAULT_DATE_ACCORD)
            .signaturereacteru(DEFAULT_SIGNATUREREACTERU)
            .signatureDiircore(DEFAULT_SIGNATURE_DIIRCORE)
            .signatureChefEtab(DEFAULT_SIGNATURE_CHEF_ETAB)
            .article(DEFAULT_ARTICLE);
        return accorde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accorde createUpdatedEntity(EntityManager em) {
        Accorde accorde = new Accorde()
            .titre(UPDATED_TITRE)
            .teritornature(UPDATED_TERITORNATURE)
            .statusacord(UPDATED_STATUSACORD)
            .dateAccord(UPDATED_DATE_ACCORD)
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB)
            .article(UPDATED_ARTICLE);
        return accorde;
    }

    @BeforeEach
    public void initTest() {
        accorde = createEntity(em);
    }

    @Test
    @Transactional
    void createAccorde() throws Exception {
        int databaseSizeBeforeCreate = accordeRepository.findAll().size();
        // Create the Accorde
        restAccordeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accorde)))
            .andExpect(status().isCreated());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeCreate + 1);
        Accorde testAccorde = accordeList.get(accordeList.size() - 1);
        assertThat(testAccorde.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testAccorde.getTeritornature()).isEqualTo(DEFAULT_TERITORNATURE);
        assertThat(testAccorde.getStatusacord()).isEqualTo(DEFAULT_STATUSACORD);
        assertThat(testAccorde.getDateAccord()).isEqualTo(DEFAULT_DATE_ACCORD);
        assertThat(testAccorde.getSignaturereacteru()).isEqualTo(DEFAULT_SIGNATUREREACTERU);
        assertThat(testAccorde.getSignatureDiircore()).isEqualTo(DEFAULT_SIGNATURE_DIIRCORE);
        assertThat(testAccorde.getSignatureChefEtab()).isEqualTo(DEFAULT_SIGNATURE_CHEF_ETAB);
        assertThat(testAccorde.getArticle()).isEqualTo(DEFAULT_ARTICLE);
    }

    @Test
    @Transactional
    void createAccordeWithExistingId() throws Exception {
        // Create the Accorde with an existing ID
        accorde.setId(1L);

        int databaseSizeBeforeCreate = accordeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccordeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accorde)))
            .andExpect(status().isBadRequest());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccordes() throws Exception {
        // Initialize the database
        accordeRepository.saveAndFlush(accorde);

        // Get all the accordeList
        restAccordeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accorde.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].teritornature").value(hasItem(DEFAULT_TERITORNATURE.toString())))
            .andExpect(jsonPath("$.[*].statusacord").value(hasItem(DEFAULT_STATUSACORD.toString())))
            .andExpect(jsonPath("$.[*].dateAccord").value(hasItem(DEFAULT_DATE_ACCORD.toString())))
            .andExpect(jsonPath("$.[*].signaturereacteru").value(hasItem(DEFAULT_SIGNATUREREACTERU.booleanValue())))
            .andExpect(jsonPath("$.[*].signatureDiircore").value(hasItem(DEFAULT_SIGNATURE_DIIRCORE.booleanValue())))
            .andExpect(jsonPath("$.[*].signatureChefEtab").value(hasItem(DEFAULT_SIGNATURE_CHEF_ETAB.booleanValue())))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAccordesWithEagerRelationshipsIsEnabled() throws Exception {
        when(accordeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAccordeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(accordeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAccordesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(accordeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAccordeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(accordeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAccorde() throws Exception {
        // Initialize the database
        accordeRepository.saveAndFlush(accorde);

        // Get the accorde
        restAccordeMockMvc
            .perform(get(ENTITY_API_URL_ID, accorde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accorde.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.teritornature").value(DEFAULT_TERITORNATURE.toString()))
            .andExpect(jsonPath("$.statusacord").value(DEFAULT_STATUSACORD.toString()))
            .andExpect(jsonPath("$.dateAccord").value(DEFAULT_DATE_ACCORD.toString()))
            .andExpect(jsonPath("$.signaturereacteru").value(DEFAULT_SIGNATUREREACTERU.booleanValue()))
            .andExpect(jsonPath("$.signatureDiircore").value(DEFAULT_SIGNATURE_DIIRCORE.booleanValue()))
            .andExpect(jsonPath("$.signatureChefEtab").value(DEFAULT_SIGNATURE_CHEF_ETAB.booleanValue()))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE));
    }

    @Test
    @Transactional
    void getNonExistingAccorde() throws Exception {
        // Get the accorde
        restAccordeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccorde() throws Exception {
        // Initialize the database
        accordeRepository.saveAndFlush(accorde);

        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();

        // Update the accorde
        Accorde updatedAccorde = accordeRepository.findById(accorde.getId()).get();
        // Disconnect from session so that the updates on updatedAccorde are not directly saved in db
        em.detach(updatedAccorde);
        updatedAccorde
            .titre(UPDATED_TITRE)
            .teritornature(UPDATED_TERITORNATURE)
            .statusacord(UPDATED_STATUSACORD)
            .dateAccord(UPDATED_DATE_ACCORD)
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB)
            .article(UPDATED_ARTICLE);

        restAccordeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccorde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccorde))
            )
            .andExpect(status().isOk());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
        Accorde testAccorde = accordeList.get(accordeList.size() - 1);
        assertThat(testAccorde.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAccorde.getTeritornature()).isEqualTo(UPDATED_TERITORNATURE);
        assertThat(testAccorde.getStatusacord()).isEqualTo(UPDATED_STATUSACORD);
        assertThat(testAccorde.getDateAccord()).isEqualTo(UPDATED_DATE_ACCORD);
        assertThat(testAccorde.getSignaturereacteru()).isEqualTo(UPDATED_SIGNATUREREACTERU);
        assertThat(testAccorde.getSignatureDiircore()).isEqualTo(UPDATED_SIGNATURE_DIIRCORE);
        assertThat(testAccorde.getSignatureChefEtab()).isEqualTo(UPDATED_SIGNATURE_CHEF_ETAB);
        assertThat(testAccorde.getArticle()).isEqualTo(UPDATED_ARTICLE);
    }

    @Test
    @Transactional
    void putNonExistingAccorde() throws Exception {
        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();
        accorde.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccordeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accorde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accorde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccorde() throws Exception {
        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();
        accorde.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccordeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accorde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccorde() throws Exception {
        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();
        accorde.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccordeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accorde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccordeWithPatch() throws Exception {
        // Initialize the database
        accordeRepository.saveAndFlush(accorde);

        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();

        // Update the accorde using partial update
        Accorde partialUpdatedAccorde = new Accorde();
        partialUpdatedAccorde.setId(accorde.getId());

        partialUpdatedAccorde.titre(UPDATED_TITRE).teritornature(UPDATED_TERITORNATURE).signaturereacteru(UPDATED_SIGNATUREREACTERU);

        restAccordeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccorde.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccorde))
            )
            .andExpect(status().isOk());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
        Accorde testAccorde = accordeList.get(accordeList.size() - 1);
        assertThat(testAccorde.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAccorde.getTeritornature()).isEqualTo(UPDATED_TERITORNATURE);
        assertThat(testAccorde.getStatusacord()).isEqualTo(DEFAULT_STATUSACORD);
        assertThat(testAccorde.getDateAccord()).isEqualTo(DEFAULT_DATE_ACCORD);
        assertThat(testAccorde.getSignaturereacteru()).isEqualTo(UPDATED_SIGNATUREREACTERU);
        assertThat(testAccorde.getSignatureDiircore()).isEqualTo(DEFAULT_SIGNATURE_DIIRCORE);
        assertThat(testAccorde.getSignatureChefEtab()).isEqualTo(DEFAULT_SIGNATURE_CHEF_ETAB);
        assertThat(testAccorde.getArticle()).isEqualTo(DEFAULT_ARTICLE);
    }

    @Test
    @Transactional
    void fullUpdateAccordeWithPatch() throws Exception {
        // Initialize the database
        accordeRepository.saveAndFlush(accorde);

        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();

        // Update the accorde using partial update
        Accorde partialUpdatedAccorde = new Accorde();
        partialUpdatedAccorde.setId(accorde.getId());

        partialUpdatedAccorde
            .titre(UPDATED_TITRE)
            .teritornature(UPDATED_TERITORNATURE)
            .statusacord(UPDATED_STATUSACORD)
            .dateAccord(UPDATED_DATE_ACCORD)
            .signaturereacteru(UPDATED_SIGNATUREREACTERU)
            .signatureDiircore(UPDATED_SIGNATURE_DIIRCORE)
            .signatureChefEtab(UPDATED_SIGNATURE_CHEF_ETAB)
            .article(UPDATED_ARTICLE);

        restAccordeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccorde.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccorde))
            )
            .andExpect(status().isOk());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
        Accorde testAccorde = accordeList.get(accordeList.size() - 1);
        assertThat(testAccorde.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAccorde.getTeritornature()).isEqualTo(UPDATED_TERITORNATURE);
        assertThat(testAccorde.getStatusacord()).isEqualTo(UPDATED_STATUSACORD);
        assertThat(testAccorde.getDateAccord()).isEqualTo(UPDATED_DATE_ACCORD);
        assertThat(testAccorde.getSignaturereacteru()).isEqualTo(UPDATED_SIGNATUREREACTERU);
        assertThat(testAccorde.getSignatureDiircore()).isEqualTo(UPDATED_SIGNATURE_DIIRCORE);
        assertThat(testAccorde.getSignatureChefEtab()).isEqualTo(UPDATED_SIGNATURE_CHEF_ETAB);
        assertThat(testAccorde.getArticle()).isEqualTo(UPDATED_ARTICLE);
    }

    @Test
    @Transactional
    void patchNonExistingAccorde() throws Exception {
        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();
        accorde.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccordeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accorde.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accorde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccorde() throws Exception {
        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();
        accorde.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccordeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accorde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccorde() throws Exception {
        int databaseSizeBeforeUpdate = accordeRepository.findAll().size();
        accorde.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccordeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accorde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accorde in the database
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccorde() throws Exception {
        // Initialize the database
        accordeRepository.saveAndFlush(accorde);

        int databaseSizeBeforeDelete = accordeRepository.findAll().size();

        // Delete the accorde
        restAccordeMockMvc
            .perform(delete(ENTITY_API_URL_ID, accorde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accorde> accordeList = accordeRepository.findAll();
        assertThat(accordeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
