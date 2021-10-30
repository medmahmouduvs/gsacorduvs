package com.gsacorduvs.po.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gsacorduvs.po.IntegrationTest;
import com.gsacorduvs.po.domain.EtablisUser;
import com.gsacorduvs.po.repository.EtablisUserRepository;
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
 * Integration tests for the {@link EtablisUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtablisUserResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etablis-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtablisUserRepository etablisUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtablisUserMockMvc;

    private EtablisUser etablisUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtablisUser createEntity(EntityManager em) {
        EtablisUser etablisUser = new EtablisUser()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .position(DEFAULT_POSITION)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD);
        return etablisUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtablisUser createUpdatedEntity(EntityManager em) {
        EtablisUser etablisUser = new EtablisUser()
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .position(UPDATED_POSITION)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);
        return etablisUser;
    }

    @BeforeEach
    public void initTest() {
        etablisUser = createEntity(em);
    }

    @Test
    @Transactional
    void createEtablisUser() throws Exception {
        int databaseSizeBeforeCreate = etablisUserRepository.findAll().size();
        // Create the EtablisUser
        restEtablisUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etablisUser)))
            .andExpect(status().isCreated());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeCreate + 1);
        EtablisUser testEtablisUser = etablisUserList.get(etablisUserList.size() - 1);
        assertThat(testEtablisUser.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEtablisUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEtablisUser.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testEtablisUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEtablisUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void createEtablisUserWithExistingId() throws Exception {
        // Create the EtablisUser with an existing ID
        etablisUser.setId(1L);

        int databaseSizeBeforeCreate = etablisUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtablisUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etablisUser)))
            .andExpect(status().isBadRequest());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtablisUsers() throws Exception {
        // Initialize the database
        etablisUserRepository.saveAndFlush(etablisUser);

        // Get all the etablisUserList
        restEtablisUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etablisUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getEtablisUser() throws Exception {
        // Initialize the database
        etablisUserRepository.saveAndFlush(etablisUser);

        // Get the etablisUser
        restEtablisUserMockMvc
            .perform(get(ENTITY_API_URL_ID, etablisUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etablisUser.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingEtablisUser() throws Exception {
        // Get the etablisUser
        restEtablisUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtablisUser() throws Exception {
        // Initialize the database
        etablisUserRepository.saveAndFlush(etablisUser);

        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();

        // Update the etablisUser
        EtablisUser updatedEtablisUser = etablisUserRepository.findById(etablisUser.getId()).get();
        // Disconnect from session so that the updates on updatedEtablisUser are not directly saved in db
        em.detach(updatedEtablisUser);
        updatedEtablisUser
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .position(UPDATED_POSITION)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);

        restEtablisUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtablisUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtablisUser))
            )
            .andExpect(status().isOk());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
        EtablisUser testEtablisUser = etablisUserList.get(etablisUserList.size() - 1);
        assertThat(testEtablisUser.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEtablisUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEtablisUser.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testEtablisUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEtablisUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void putNonExistingEtablisUser() throws Exception {
        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();
        etablisUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablisUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etablisUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etablisUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtablisUser() throws Exception {
        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();
        etablisUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etablisUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtablisUser() throws Exception {
        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();
        etablisUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etablisUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtablisUserWithPatch() throws Exception {
        // Initialize the database
        etablisUserRepository.saveAndFlush(etablisUser);

        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();

        // Update the etablisUser using partial update
        EtablisUser partialUpdatedEtablisUser = new EtablisUser();
        partialUpdatedEtablisUser.setId(etablisUser.getId());

        partialUpdatedEtablisUser.username(UPDATED_USERNAME).password(UPDATED_PASSWORD);

        restEtablisUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablisUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtablisUser))
            )
            .andExpect(status().isOk());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
        EtablisUser testEtablisUser = etablisUserList.get(etablisUserList.size() - 1);
        assertThat(testEtablisUser.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEtablisUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEtablisUser.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testEtablisUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEtablisUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void fullUpdateEtablisUserWithPatch() throws Exception {
        // Initialize the database
        etablisUserRepository.saveAndFlush(etablisUser);

        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();

        // Update the etablisUser using partial update
        EtablisUser partialUpdatedEtablisUser = new EtablisUser();
        partialUpdatedEtablisUser.setId(etablisUser.getId());

        partialUpdatedEtablisUser
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .position(UPDATED_POSITION)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);

        restEtablisUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtablisUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtablisUser))
            )
            .andExpect(status().isOk());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
        EtablisUser testEtablisUser = etablisUserList.get(etablisUserList.size() - 1);
        assertThat(testEtablisUser.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEtablisUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEtablisUser.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testEtablisUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEtablisUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void patchNonExistingEtablisUser() throws Exception {
        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();
        etablisUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablisUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etablisUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etablisUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtablisUser() throws Exception {
        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();
        etablisUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etablisUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtablisUser() throws Exception {
        int databaseSizeBeforeUpdate = etablisUserRepository.findAll().size();
        etablisUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtablisUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etablisUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtablisUser in the database
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtablisUser() throws Exception {
        // Initialize the database
        etablisUserRepository.saveAndFlush(etablisUser);

        int databaseSizeBeforeDelete = etablisUserRepository.findAll().size();

        // Delete the etablisUser
        restEtablisUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, etablisUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtablisUser> etablisUserList = etablisUserRepository.findAll();
        assertThat(etablisUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
